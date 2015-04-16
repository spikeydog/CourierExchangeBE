/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bidding;

import common.bidding.Bid;
import common.bidding.BiddingServer;
import common.bidding.SortCriterion;
import common.bidding.SortOrder;
import common.delivery.DeliveryRequest;
import common.util.code.bidding.ExitCode;
import java.rmi.RemoteException;
import java.util.List;

/**
 *
 * @author sedog
 */
public class BidServer implements BiddingServer {
    public ExitCode acceptBid(DeliveryRequest request, Bid bid) 
            throws RemoteException {
        ExitCode code = ExitCode.FAILURE;
        
        return code;
    }
    
    public ExitCode updateBid(Bid bid) throws RemoteException {
        ExitCode code = ExitCode.FAILURE;
        
        return code;
    }
    
    public ExitCode placeBid(Bid bid) throws RemoteException {
        // Default, mysterious exit code to be overwritten
        ExitCode code = ExitCode.FAILURE;
        // The agent to handle the request
        BidAgent agent = new BidAgent();
        
        code = agent.insert(bid);

        return code;
    }
    
    public List<Bid> listBids(DeliveryRequest request, SortCriterion criterion, 
            SortOrder order) throws RemoteException {
        List<Bid> bids = null;
        
        return bids;
    }
}
