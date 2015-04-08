/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bidding;

import common.Bid;
import gopher.AbstractGopher;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import util.Formatter;

/**
 *
 * @author sedog
 */
public class BidGopher extends AbstractGopher {
    // Names for each database column so thtey only need to be set once
    public final static String TABLE_NAME = "bid";
    public final static String BID_ID = "bid_id";
    public final static String COURIER_ID = "courier_id";
    public final static String REQ_ID = "delivery_request_id";
    public final static String DROP_TIME = "drop_off_time";
    public final static String PICKUP_TIME = "pick_up_time";
    public final static String FEE = "fee";
    public final static String PENDING = "is_pending_update";
    public final static String ACCEPTED = "is_accepted";
    public final static String NULL = "NULL";
    public final static String DEFAULT = "DEFAULT";
    
    @Override
    protected Bid parseResult(ResultSet results) {
        Bid bid = new BidCE();
        
        try {
            bid.setBidID(results.getInt(BID_ID));
            bid.setCourierID(results.getInt(COURIER_ID));
            bid.setDeliveryRequestID(results.getInt(REQ_ID));
            bid.setDropOffTime(results.getTimestamp(DROP_TIME));
            bid.setPickUpTime(results.getTimestamp(PICKUP_TIME));
            bid.setFee(results.getFloat(FEE));
            bid.setIsPendingUpdate(results.getBoolean(PENDING));
            bid.setIsAccepted(results.getBoolean(ACCEPTED));
        } catch (SQLException ex) {
            System.out.println("Unable to read from resultSet");
            ex.printStackTrace();
        }
        
        return bid;
    }
    
    /**
     * Inserts a new record into the bid table using the data in the given Bid
     * 
     * @param bid   the bid containing the record data
     */
    public void insert(Bid bid) {
        // The query to execute
        StringBuilder query = new StringBuilder();
        // List of all of the data values to insert
        List<String> values = new LinkedList<>();
        
        query.append("INSERT INTO ").append(TABLE_NAME).append(" VALUES(");
        values.add(DEFAULT);
        values.add(String.valueOf(bid.getCourierID()));
        values.add(String.valueOf(bid.getDeliveryRequestID()));
        values.add(Formatter.formatTime(bid.getDropOffTime()));
        values.add(Formatter.formatTime(bid.getPickUpTime()));
        values.add(String.valueOf(bid.getFee()));
        values.add(String.valueOf(bid.isPendingUpdate()).toUpperCase());
        values.add(String.valueOf(bid.isAccepted()).toUpperCase());
        
        query.append(values.toString().substring(1,values.toString().length() - 1));
        query.append(")");
        
        System.out.println(query.toString());
        super.executeQuery(query.toString());
    }
    
    /**
     * Returns a <code>Bid</code> populated by the data from the bid record with
     * the given id
     * 
     * @param id    the bid_id of the record to return as a <code>Bid</code>
     * @return      a <code>Bid</code> representing the database record with the
     *              given id
     */
    public Bid get(int id) {
        // The query to execute
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + BID_ID + "="
                + id;
        // The raw list of results
        List<Object> rawList = null;
        // The converted list of results
        List<Bid> castList = null;
        // The Bid to return
        Bid request = null;
       
        rawList = super.executeQuery(query);
        castList = convert(rawList);
        request = castList.get(0);
        
        return request;
    }
    
    /**
     * Casts the list of objects into bids.
     * 
     * @param list  the list of bids that need to be cast to <code>Bid</code>
     * @return 
     */
    private List<Bid> convert(List<Object> list) {
        List<Bid> converted = new ArrayList<Bid>(
                list.size());
        for (Object each : list) {
            if (each instanceof Bid) {
                converted.add((Bid) each);
            } 
        }
        
        return converted;
    }
}
