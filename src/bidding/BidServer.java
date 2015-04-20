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
        // Default, mysterious exit code to be overwritten
        ExitCode code = ExitCode.FAILURE;
        // The agent to handle this request
        BidAgent agent = new BidAgent();
        
        code = agent.update(bid);
        System.out.println("DEBUG:BidServer.updateBid():" + code.toString());
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
    
    /**
     * This method obtains a list of all of the bids placed upon the given <code>
     * DeliveryRequest</code>, sorted according to the given <code>SortCriterion</code>
     * and <code>SortOrder</code>. If either <code>criterion</code> or <code>order
     * </code> are <code>null</code>, then defaults are used.
     * 
     * @param request       The <code>DeliveryRequest</code> to list <code>Bids</code> for
     * @param criterion     The <code>SortCriterion</code> to use for sorting
     * @param order         The <ocde>SortOrder</code> to use for sorting
     * @return  <code>List&#60Bid&#62</code>
     * @throws RemoteException 
     */
    public List<Bid> listBids(DeliveryRequest request, SortCriterion criterion, 
            SortOrder order) throws RemoteException {
        // The list of bids to return
        List<Bid> bids = null;
        // The bid agent to retrieve the bids
        BidAgent agent = new BidAgent();
        // The bid sorter that will sort the list of bids
        //BidSorter sorter = new BidSorter();
        
        bids = agent.getList(request, criterion, order);
        
        return bids;
    }
    
    public Bid getBid(final Bid bid) {
        BidAgent agent = new BidAgent();
        return agent.get(bid);
    }
}
