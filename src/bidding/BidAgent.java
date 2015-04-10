/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bidding;

import common.bidding.Bid;
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
    public boolean insert(final Bid bid) {
        // Flag indicating success of the requested database operation
        boolean isSuccessful = false;
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
        bids = gopher.getList(bid.getDeliveryRequestID());
        // Determine if any bid was created by the same courier
        while (finder.hasNext() && isUnique) {
            listedBid = finder.next();
            isUnique = !(listedBid.getCourierID() == bid.getCourierID());
        }
        
        // Only insert the new bid of it is unique to the request/courier
        if (isUnique) {
            gopher.insert(bid);
        }
        
        return isSuccessful;
    }
}
