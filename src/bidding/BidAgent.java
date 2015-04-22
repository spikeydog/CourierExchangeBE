/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bidding;

import common.bidding.Bid;
import common.bidding.BidCE;
import common.bidding.SortCriterion;
import common.bidding.SortOrder;
import common.delivery.DeliveryRequest;
import common.user.User;
import common.util.code.bidding.ExitCode;
import delivery.DeliveryRequestCE;
import delivery.DeliveryRequestGopher;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Iterator;
import java.util.List;

/**
 * This class provides access to database operations on <code>Bid</code> objects
 * and encapsulates any special business logic regarding those operations.
 * 
 * @author sedog
 */
public class BidAgent {
    
    /**
     * Inserts a new record into the bid table based on the given <code>Bid
     * </code>. Fails if there exists another <code>Bid</code> placed on the
     * same <code>DeliveryRequest</code> by the same <code>User</code>.
     * 
     * @param bid
     * @return <code>true</code> if the operation was successful
     */
    public ExitCode insert(final Bid bid) {
        // Enum indicating success of the requested database operation
        ExitCode code = ExitCode.FAILURE;
        // BidGopher that will interact with the database
        BidGopher gopher = new BidGopher();
        // List of all bids on the same delivery request
        List<Bid> bids = null;
        // Iterator over the list of bids
        Iterator<Bid> finder = null;
        // Flag indicating there is no matching bid
        boolean isUnique = true;
        // Stores each bid as it is evaluated for a match
        Bid listedBid = null;
        
        // Obtain a list of all bids on the same delivery request
        try {
            bids = gopher.getList(bid.getDeliveryRequestID());
        } catch (SQLException ex) {
            System.out.println("Unable to retrieve list from database");
            code = ExitCode.SQL_EXCEPTION;
        }
        
        // Determine if any bid was created by the same courier
        finder = bids.iterator();
        while (finder.hasNext() && isUnique) {
            listedBid = finder.next();
            isUnique = !(listedBid.getCourierID() == bid.getCourierID());
        }
        
        // Only insert the new bid of it is unique to the request/courier
        if (isUnique) {
            // Only insert the new bid if it is contextually valid
            if (isValidInsert(bid)) {
                try {
                gopher.insert(bid);
                code = ExitCode.SUCCESS;
                } catch (SQLException ex) {
                System.out.println("Unable to insert new bid");
                code = ExitCode.SQL_EXCEPTION;
                }
            } else {
                code = ExitCode.BID_INVALID;
            }
        } else {
            code = ExitCode.BID_DUPLICATE;
        }
        System.out.println("BidAgent: returns code " + code.toString());
        return code;
    }
    
    // DEBUG test data
    private static final DeliveryRequest TEST_DATA = new DeliveryRequestCE();
    static {
        TEST_DATA.setDeliveryRequestID(23);
        TEST_DATA.setPostTime(new Timestamp(System.currentTimeMillis() - 36000 * 3));
        TEST_DATA.setPickUpTime(new Timestamp(System.currentTimeMillis() - 36000 * 2));
        TEST_DATA.setDropOffTime(new Timestamp(System.currentTimeMillis() - 36000));
        TEST_DATA.setBidID(DeliveryRequestCE.DEFAULT_BID_ID);
    };
    
    public ExitCode update(final Bid bid) {
        // Enum indicating success of the requested database operation
        ExitCode code = ExitCode.FAILURE;
        // BidGopher that will interact with the database
        BidGopher gopher = new BidGopher();
        // Flag indicating the bid is valid as an insert
        boolean isValid = false;
        // The bid currently stored in the database
        Bid storedBid = null;
        // The update flag bid
        Bid flaggedBid = new BidCE();
        // The bid custodian to store the updated bid temporarily
        BidCustodian custodian = null;
        
        // Get the currently-stored bid from the database
        // If it is not accepted already... 
        // And the given bid is valid (as an insert)...
        // And the given bid is valid as an update... 
        // Create a shallow bid to set the update flag
        // Update the stored bid to be isPendingUpdate
        // Create a bid custodian with the new bid data
        // Return successful code
        System.out.println("DEBUG: BidAgent.update() called");
        try {
            storedBid = gopher.get(bid.getBidID());
            System.out.println("DEBUG: " + storedBid.toString());
        } catch (SQLException ex) {
            code = ExitCode.SQL_EXCEPTION;
            System.out.println("DEBUG: getting stale bid from DB failed");
        }
        
        // Verify the bid has not already been accepted
        if (!storedBid.isAccepted()) {
            // Verify the bid is valid for inserting and updating
            if (isValidUpdate(storedBid, bid)) {
                flaggedBid.setBidID(storedBid.getBidID());
                flaggedBid.setIsPendingUpdate(true);
            
                // Set the current bid as pending update
                try {
                    gopher.update(flaggedBid);
                } catch (SQLException ex) {
                    code = ExitCode.SQL_EXCEPTION;
                    System.out.println("DEBUG: flagging bid failed");
                } catch (Exception ex) { System.out.println("DEBUG: WTF"); } 
                
                Thread t = new Thread(new BidCustodian(bid));
                t.start();
                
                code = ExitCode.SUCCESS;
            //} else if (bid.getBidID() == 13) { // DEBUG REMOVE
              //  Thread t = new Thread(new BidCustodian(bid));
              //  t.start();
                //ExecutorService exec = Executors.newFixedThreadPool(1);
                //exec.execute(new BidCustodian(bid));
                //custodian.run();
              //  code = ExitCode.SUCCESS;
              //  System.out.println("DEBUG: forced update of 13");
                
            } else {
                code = ExitCode.BID_INVALID;
            }
        } else {
            code = ExitCode.BID_ACCEPTED;
        }
        System.out.println("DEBUG: BidAgent returning " + code.toString());
        return code;
    }
    
    /**
     * Helper method to insert() that checks the state of the given <code>Bid
     * </code> is conditionally valid.
     * 
     * @param bid   the <code>Bid</code> containing the record to insert
     * @return      <code>true</code> if <code>bid</code> is valid to insert
     */
    private boolean isValidInsert(final Bid bid) {
        // Flag indicating the given bid is valid for an insert op
        boolean isValid = false;
        // Flag indicating the bid contains all needed data
        boolean isPopulated = false;
        // Empty delivery request for agent query
        DeliveryRequest delivery = new DeliveryRequestCE();
        // Agent responsible for delivery request access
        /*
        DeliveryRequestAgent reqAgent = new DeliveryRequestAGent();
        */
        
        if (null != bid 
                && null != bid.getDropOffTime() 
                && null != bid.getPickUpTime() 
                && bid.getFee() == bid.getFee()
                && BidCE.DEFAULT_BID_ID == bid.getBidID()
                && BidCE.DEFAULT_COURIER_ID != bid.getCourierID()
                && BidCE.DEFAULT_REQ_ID != bid.getDeliveryRequestID()) {
            isPopulated = true;
        }
        
        if (isPopulated) {
            delivery.setDeliveryRequestID(bid.getDeliveryRequestID());
            //delivery = reqAgent.get(delivery);
            delivery = TEST_DATA; // Dummy data for testing
            isValid = (null != delivery 
                    //&& DeliveryRequestCE.DEFAULT_BID_ID == delivery.getBidID()
                    && delivery.getPostTime().before(bid.getPickUpTime())
                    && delivery.getPostTime().before(bid.getDropOffTime())
                    && bid.getPickUpTime().before(bid.getDropOffTime())
                    && 0 <= bid.getFee());
        }
        System.out.println("DEBUG: Bid valid for insert? " + isValid);
        return isValid;
    }
    
    /**
     * Helper method that checks that the updated bid information provides a 
     * higher level of service than the original. This allows for either a 
     * faster pick up or drop off time and/or lower fee.
     * 
     * @param original  the original <code>Bid</code>
     * @param update    the updated <code>Bid</code>
     * @return boolean
     */
    private boolean isValidUpdate(Bid original, Bid update) {
        // Validity flag
        boolean isValidUpdate = false;
        
        if (((update.getPickUpTime().before(original.getPickUpTime()))
                || update.getPickUpTime().equals(original.getPickUpTime()))
                && (update.getDropOffTime().before(original.getDropOffTime())
                || update.getDropOffTime().equals(original.getDropOffTime()))
                && (update.getFee() <= original.getFee())) {
            isValidUpdate = true;  
        }
        
        return isValidUpdate;
    }
    
    /**
     * Obtains and returns the bid record matching the given <code>Bid</code>
     * @param bid   the <code>Bid</code> matching the record to retrieve
     * @return      a fresh copy of the requested bid as a <code>Bid</code>
     */
    public Bid get(final Bid bid) {
        // Flag indicating success of the requested database operation
        boolean isSuccessful = false;
        // BidGopher that will interact with the database
        BidGopher gopher = new BidGopher();
        // The retrieved bid record
        Bid record = null;
        
        try {
            record = gopher.get(bid.getBidID());
        } catch (SQLException ex) {
            System.out.println("Requested bid does not exist");
        }
        
        return record;
    }
    
    public List<Bid> getList(final DeliveryRequest request, 
            SortCriterion criterion, SortOrder order) {
        // List of Bids to return
        List<Bid> bids = null;
        // The gopher to interact with the database
        BidGopher gopher = new BidGopher();
        
        // We should check to see if the DR still shows as posted... 
        System.out.println("DEBUG: BidAgent: getList called");
        try {
            bids = gopher.getList(request.getDeliveryRequestID());
        } catch (SQLException ex) {
            System.out.println("Unable to get the bids list");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        return bids;
    }
    
    public List<Bid> getListByCourier(User courier) {
        List<Bid> bids = null;
        BidGopher gopher = new BidGopher();
        
        try {
            gopher.getListByUserID(courier.getUserID());
        } catch (SQLException ex) {
            // Nothing we can do
        }
        
        return bids;
    }
    
    public ExitCode accept(final DeliveryRequest delivery, final Bid bid) {
        // Default bad exit code
        ExitCode code = ExitCode.FAILURE;
        BidGopher bidGopher = new BidGopher();
        DeliveryRequestGopher drGopher = new DeliveryRequestGopher();
        Bid updatedBid = new BidCE();
        DeliveryRequest updatedDR = new DeliveryRequestCE();
        
        updatedBid.setBidID(bid.getBidID());
        updatedBid.setIsAccepted(true);
        updatedDR.setDeliveryRequestID(delivery.getDeliveryRequestID());
        updatedDR.setBidID(bid.getBidID());
        System.out.println("Trying");
        
        try {
            Bid dbBid = bidGopher.get(bid.getBidID());
            if ((null != dbBid) && !(dbBid.isAccepted())) {
                DeliveryRequest dbDR;
                dbDR = drGopher.get(delivery.getDeliveryRequestID());
                
                if (null != dbDR) {
                    if (DeliveryRequestCE.DEFAULT_BID_ID == dbDR.getDeliveryRequestID()) {
                        bidGopher.update(updatedBid);
                        drGopher.update(updatedDR);
                        code = ExitCode.SUCCESS;
                    } else {
                        code = ExitCode.REQ_INVALID;
                    }
                } else {
                    code = ExitCode.REQ_NULL;
                }
            } else {
                code = ExitCode.BID_ACCEPTED;
            }
            
        } catch (SQLException ex) {
            code = ExitCode.SQL_EXCEPTION;
            throw new RuntimeException("SQLException");
        }

        return code;
    }
}
