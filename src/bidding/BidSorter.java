/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bidding;

import common.bidding.Bid;
import common.bidding.SortCriterion;
import common.bidding.SortOrder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 *
 * @author sedog
 */
public class BidSorter {
    private final static SortCriterion DEFAULT_CRITERION = SortCriterion.FEE;
    private final static SortOrder DEFAULT_ORDER = SortOrder.ASC;
    
    public static List<Bid> sortBids(final List<Bid> bids, SortCriterion c, SortOrder o) {
        List<Bid> sortedBids = new ArrayList<Bid>(bids.size());
        switch (c) {
            case DROP_TIME :
                SortedMap<Timestamp, Bid> mapD = new TreeMap<Timestamp, Bid>();
                for (Bid bid : bids) {
                    mapD.put(bid.getDropOffTime(), bid);
                }
                
                SortedSet<Timestamp> keysD = new TreeSet<Timestamp>(mapD.keySet());
                
                for (Timestamp keyD : keysD) {
                    sortedBids.add(mapD.get(keyD));
                } break;
            case PICKUP_TIME :
                SortedMap<Timestamp, Bid> mapP = new TreeMap<Timestamp, Bid>();
                for (Bid bid : bids) {
                    mapP.put(bid.getPickUpTime(), bid);
                }
                
                SortedSet<Timestamp> keysP = new TreeSet<Timestamp>(mapP.keySet());
                
                for (Timestamp keyP : keysP) {
                    sortedBids.add(mapP.get(keyP));
                } break;
            default :
                SortedMap<Float, Bid> mapF = new TreeMap<Float, Bid>();
                
                for (Bid bid : bids) {
                    mapF.put(bid.getFee(), bid);
                }
                
                SortedSet<Float> keysF = new TreeSet<Float>(mapF.keySet());
                
                for (Float keyF : keysF) {
                    sortedBids.add(mapF.get(keyF));
                } break;
        }
        return null;
    }
}
