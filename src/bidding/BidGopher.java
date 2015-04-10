/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bidding;

import common.bidding.Bid;
import common.delivery.DeliveryRequest;
import gopher.AbstractGopher;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
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
    
    /**
     * 
     * @param results
     * @return
     * @throws SQLException 
     */
    @Override
    protected Bid parseResult(ResultSet results) throws SQLException {
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
            throw ex;
        }
        
        return bid;
    }
    
    /**
     * Inserts a new record into the bid table using the data in the given Bid
     * 
     * @param bid   the bid containing the record data
     * @throws SQLException if the operation failed
     */
    public void insert(Bid bid) throws SQLException {
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
     * the given id. Returns <code>null</code> if no such bid exists.
     * 
     * @param id    the bid_id of the record to return as a <code>Bid</code>
     * @return      a <code>Bid</code> representing the database record with the
     *              given id
     * @throws SQLException if the operation failed
     */
    public Bid get(int id) throws SQLException {
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
        
        // Guarantee the list is not empty before trying to get element
        request = (0 < castList.size())? castList.get(0) : null;  
        
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
    
    /**
     * This method deletes the bid record with the given id
     * @param id    the primary key of the record to delete 
     * @throws SQLException if the operation failed
     */
    public void delete(int id) throws SQLException {
        String query = "DELETE FROM " + TABLE_NAME + " WHERE " + BID_ID
                + "=" + id;
        
        super.executeQuery(query);
    }
    
    /**
     * This method returns a list of <code>Bid</code> objects, one
     * for each record in the database with the given status that is associated
     * with the given delivery request id.
     * 
     * @param requestID primary key of the <code>DeliveryRequest</code> to 
     *                  obtain a list of <code>Bid</code> objects for
     * @return          a list of all of <code>Bid</code> objects placed on the
     *                  given <code>DeliveryRequest</code> id
     * @throws SQLException if the operation failed
     */
    public List<Bid> getList(int requestID) throws SQLException {
        // The list to return
        List<Bid> list = null;
        // The query to execute
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + REQ_ID 
                + "=" + requestID; 
        
        List<Object> rawList = super.executeQuery(query);
        list = convert(rawList);
        
        return list;
    }
    
    /**
     * Returns a list of <code>Bid</code> associated with the given user id.
     * 
     * @param userID    the primary key of the user for which bids will be listed
     * @return          a list of all <code>Bid</code> objects created by the
     *                  user with the given userID
     * @throws SQLException if the operation failed
     */
    public List<Bid> getListByUserID(int userID) throws SQLException {
        // The list to return
        List<Bid> list = null;
        // The query to execute
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COURIER_ID 
                + "=" + userID; 
        
        List<Object> rawList = super.executeQuery(query);
        list = convert(rawList);
        
        return list;
    }
    
    /**
     * Updates an exiting bid record with data extracted from the
     * given <code>Bid</code>.
     * 
     * @param bid   <code>Bid</code> containing updates
     * @throws SQLException if the operation failed
     */
    public void update(Bid bid) throws SQLException {
        // String builder used to construct the query 
        StringBuilder query = new StringBuilder();
        // Fields that might be updated by the query
        int bidID = bid.getBidID();
        Timestamp dropOffTime = bid.getDropOffTime();
        Timestamp pickUpTime = bid.getPickUpTime();
        float fee = bid.getFee();
        boolean isPendingUpdate = bid.isPendingUpdate();
        boolean isAccepted = bid.isAccepted();
        // List containing each column=value pair for each updated attribute
        List<String> params = new LinkedList<>();
        
        query.append("UPDATE ").append(BidGopher.TABLE_NAME).append(" SET ");
        
        // Include a column=value pair for each existing, non-default attribute
        if (null != dropOffTime) {
            params.add(BidGopher.DROP_TIME + "=" + Formatter.formatTime(dropOffTime));
        }
        if (null != pickUpTime) {
            params.add(BidGopher.PICKUP_TIME + "=" + Formatter.formatTime(pickUpTime));
        }
        // These are always going to be set since testing for a default or null
        // is not possible without using Boolean instead, which is unnecessary
        params.add(BidGopher.PENDING + "=" + isPendingUpdate);
        params.add(BidGopher.ACCEPTED + "=" + isAccepted);
        
        if (fee == fee) {
            params.add(BidGopher.FEE + "=" + String.valueOf(fee));
        }
        
        query.append(params.toString().substring(1,params.toString().length() - 1));
        query.append(" WHERE ").append(BidGopher.BID_ID).append("=").append(bidID);
        super.executeQuery(query.toString());
    }
}
