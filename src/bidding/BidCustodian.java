/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bidding;

import common.bidding.Bid;
import java.sql.SQLException;

/**
 *
 * @author sedog
 */
public class BidCustodian extends Thread {
    // Constant wait duration
    private final static long SLEEP_DURATION = 30000;
    
    public BidCustodian(final Bid bid) {
        go(bid);  
    }
    
    private void go(final Bid bid) {
        // The gopher to use to access the database
        BidGopher gopher = new BidGopher();
        
        try {
            Thread.currentThread().sleep(SLEEP_DURATION);
            // Verify the stored bid is not already accepted
            if (!gopher.get(bid.getBidID()).isAccepted()) {
                gopher.update(bid);
            } 
        } catch (InterruptedException ex) {
            // Nothing can be done about this
        } catch (SQLException ex) {
            // Nothing we can do here, either
        }
    }
}
