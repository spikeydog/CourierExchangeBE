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
import java.util.Iterator;
import java.util.List;
import java.util.SortedMap;
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
        Timestamp time = null;
        Iterator<Timestamp> timeIter = null;
        
        // Map the bids according to the chosen sort criteria
        switch (c) {
            case DROP_TIME :
                SortedMap<Timestamp, Bid> mapD = new TreeMap<Timestamp, Bid>();
                for (Bid bid : bids) {
                    mapD.put(bid.getDropOffTime(), bid);
                }
                
                TreeSet<Timestamp> keysD = new TreeSet<Timestamp>(mapD.keySet());
                
                
                if (SortOrder.DESC == o) {
                    timeIter = keysD.descendingIterator();
                } else {
                    timeIter = keysD.iterator();
                }
                
                while (timeIter.hasNext()) {
                    time = timeIter.next();
                    sortedBids.add(mapD.get(time));
                } break;
                
            case PICKUP_TIME :
                SortedMap<Timestamp, Bid> mapP = new TreeMap<Timestamp, Bid>();
                for (Bid bid : bids) {
                    mapP.put(bid.getPickUpTime(), bid);
                }
                
                TreeSet<Timestamp> keysP = new TreeSet<Timestamp>(mapP.keySet());
                
                if (SortOrder.DESC == o) {
                    timeIter = keysP.descendingIterator();
                } else {
                    timeIter = keysP.iterator();
                }
                
                while (timeIter.hasNext()) {
                    time = timeIter.next();
                    sortedBids.add(mapP.get(time));
                } break;
            default :
                SortedMap<Float, Bid> mapF = new TreeMap<Float, Bid>();
                
                for (Bid bid : bids) {
                    mapF.put(bid.getFee(), bid);
                }
                
                TreeSet<Float> keysF = new TreeSet<Float>(mapF.keySet());
                Iterator<Float> floatIter = null;
                Float f = null;
                
                if (SortOrder.DESC == o) {
                    floatIter = keysF.descendingIterator();
                } else {
                    floatIter = keysF.descendingIterator();
                }
                
                while (floatIter.hasNext()) {
                    f = floatIter.next();
                    sortedBids.add(mapF.get(f));
                } break;
        }
        
        return sortedBids;
    }
}
