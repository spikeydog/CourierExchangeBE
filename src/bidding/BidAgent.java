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
import common.util.code.bidding.ExitCode;
import delivery.DeliveryRequestCE;
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
                && BidCE.DEFAULT_COURIER_ID != bid.getCourierID()
                && BidCE.DEFAULT_REQ_ID != bid.getDeliveryRequestID()) {
            isPopulated = true;
        }
        
        if (isPopulated) {
            delivery.setDeliveryRequestID(bid.getDeliveryRequestID());
            //delivery = reqAgent.get(delivery);
            delivery = TEST_DATA; // Dummy data for testing
            isValid = (null != delivery 
                    && DeliveryRequestCE.DEFAULT_BID_ID == delivery.getBidID()
                    && delivery.getPostTime().before(bid.getPickUpTime())
                    && delivery.getPostTime().before(bid.getDropOffTime())
                    && bid.getPickUpTime().before(bid.getDropOffTime())
                    && 0 <= bid.getFee());
        }
        System.out.println("DEBUG: Bid valid for insert? " + isValid);
        return isValid;
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
        System.out.println(null==record? "Null!!" : "OK!");
        return record;
    }
    
    public List<Bid> getList(final DeliveryRequest request, 
            SortCriterion criterion, SortOrder order) {
        // List of Bids to return
        List<Bid> bids = null;
        // The gopher to interact with the database
        BidGopher gopher = new BidGopher();
        
        // We should check to see if the DR still shows as posted... 
        System.out.println("BidAgent: getList called");
        try {
            bids = gopher.getList(request.getDeliveryRequestID());
        } catch (SQLException ex) {
            System.out.println("Unable to get the bids list");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        System.out.println("Matching bids: " + bids.size());
        
        return bids;
    }
}
