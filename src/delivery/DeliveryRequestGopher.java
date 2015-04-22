package delivery;

import common.delivery.DeliveryRequest;
import common.delivery.Status;
import gopher.AbstractGopher;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import util.Formatter;

/**
 * This class handles the creation and processing of database queries related to
 * <code>DeliveryRequest</code> objects.
 * 
 * @author sedog
 */
public class DeliveryRequestGopher extends AbstractGopher {
    // Names for each database column so thtey only need to be set once
    public final static String TABLE_NAME = "delivery_request";
    public final static String BID_ID = "bid_id";
    public final static String CUST_ID = "customer_id";
    public final static String REQ_ID = "delivery_id";
    public final static String DESCR = "description";
    public final static String DROP_ADDR = "drop_off_address";
    public final static String DROP_TIME = "drop_off_time";
    public final static String PICKUP_ADDR = "pick_up_address";
    public final static String PICKUP_TIME = "pick_up_time";
    public final static String POST_TIME = "post_time";
    public final static String REAL_DROP_TIME = "real_drop_off_time";
    public final static String REAL_PICKUP_TIME = "real_pick_up_time";
    public final static String STATUS = "status";
    public final static String WEIGHT = "weight";
    public final static String NULL = "NULL";
    public final static String DEFAULT = "DEFAULT";
    
    @Override
    protected DeliveryRequest parseResult(ResultSet results) {
        DeliveryRequest request = new common.delivery.DeliveryRequestCE();
        
        try {
            request.setBidID(results.getInt(BID_ID));
            request.setCustomerID(results.getInt(CUST_ID));
            request.setDeliveryRequestID(results.getInt(REQ_ID));
            request.setDescription(results.getString(DESCR));
            request.setDropOffAddress(results.getString(DROP_ADDR));
            request.setDropOffTime(results.getTimestamp(DROP_TIME));
            request.setPickUpAddress(results.getString(PICKUP_ADDR));
            request.setPickUpTime(results.getTimestamp(PICKUP_TIME));
            request.setRealDropOffTime(results.getTimestamp(REAL_DROP_TIME));
            request.setRealPickUpTime(results.getTimestamp(REAL_PICKUP_TIME));
            request.setStatus(Status.map(results.getInt(STATUS)));
            request.setWeight(results.getFloat(WEIGHT));
        } catch (SQLException ex) {
            System.out.println("Unable to read from resultSet");
            ex.printStackTrace();
        }
        
        return request;
    }
    
    /**
     * Retrieves and returns the <code>DeliveryRequest</code> identified by the
     * given primary key. Returns <code>null</code> if no such delivery_request
     * record exists.
     * 
     * @param id    the primary key of the record to retrieve
     * @return      a <code>DeliveryRequest</code> populated with data retrieved
     *              from the database
     * @throws SQLException if the database operation failed
     */
    public DeliveryRequest get(int id) throws SQLException {
        // The query to execute
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + REQ_ID + "="
                + id;
        // The raw list of results
        List<Object> rawList = null;
        // The converted list of results
        List<DeliveryRequest> castList = null;
        // The DeliveryRequest to return
        DeliveryRequest request = null;
       
        rawList = super.executeQuery(query);
        castList = convert(rawList);
        
        // Guarantee the list is not empty before trying to get element
        request = (0 < castList.size())? castList.get(0) : null;  
        
        return request;
    }
    
    /**
     * This method returns a list of <code>DeliveryRequest</code> objects, one
     * for each record in the database with the given status.
     * 
     * @param status    the status of the delivery requests to list
     * @return          a list of all matching <code>DeliveryRequest</code>s
     * @throws SQLException if the operation failed
     */
    public List<DeliveryRequest> getList(int status) throws SQLException {
        // The list to return
        List<DeliveryRequest> list = null;
        // The query to execute
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE status = " 
                + status;
        
        List<Object> rawList = super.executeQuery(query);
        list = convert(rawList);
        
        return list;
    }
    
    //Created function implemented by Teja, to get the delivery request for a particualr bid
    /**
     * This method returns a list of <code>DeliveryRequest</code> objects, one
     * for each record in the database with the given status.
     * 
     * @param status    the status of the delivery requests to list
     * @return          a list of all matching <code>DeliveryRequest</code>s
     * @throws SQLException if the operation failed
     */
    public DeliveryRequest getDelRequestForBid(int bidID) throws SQLException {
        // The list to return
        List<DeliveryRequest> list = null;
        // The query to execute
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE BID_ID = " 
                + bidID;
        
        // The raw list of results
        List<Object> rawList = null;
        // The converted list of results
        List<DeliveryRequest> castList = null;
        // The DeliveryRequest to return
        DeliveryRequest request = null;
       
        rawList = super.executeQuery(query);
        castList = convert(rawList);
        
        // Guarantee the list is not empty before trying to get element
        request = (0 < castList.size())? castList.get(0) : null;          
        return request;
    }
    
    /**
     * This method returns a list of <code>DeliveryRequest</code> objects, one
     * for each record in the database with the given status that is associated
     * with the given user id.
     * 
     * @param status    the status of the delivery requests to list
     * @param userID    the primary key of the associated user
     * @return          <code>List</code> of all matching <code>DeliveryRequest
     *                  </code>s
     * @throws SQLException if the operation failed
     */
    public List<DeliveryRequest> getListByUserId(Status status, int userID) 
            throws SQLException {
        // The list to return
        List<DeliveryRequest> list = null;
        // The query to execute
        String query = null; 
        
        // TODO ... not done, need bidding also
        
        List<Object> rawList = super.executeQuery(query);
        list = convert(rawList);
        
        return list;
    }
    
    /**
     * This method inserts the data associated with the given DeliveryRequest
     * into the database
     * 
     * @param dr    the <code>DeliveryRequest</code> to insert into the database
     * @throws SQLException if the operation failed
     */
    public void insert(DeliveryRequest dr) throws SQLException {
        // The query to execute
        String query = " INSERT INTO delivery_request "
                /*
                + BID_ID + "," + CUST_ID + "," + REQ_ID + "," + DESCR + ","
                + DROP_ADDR + "," + DROP_TIME + "," + PICKUP_ADDR + ","
                + PICKUP_TIME + "," + POST_TIME + "," + REAL_DROP_TIME + "," 
                + REAL_PICKUP_TIME + "," + STATUS + "," + WEIGHT 
                */
                + " VALUES("
                + DEFAULT + ","
                + NULL + "," 
                + dr.getCustomerID() + "," 
                + Formatter.quote(dr.getDescription()) + "," 
                + Formatter.formatTime(dr.getDropOffTime()) + "," 
                + Formatter.quote(dr.getDropOffAddress()) + ","
                + NULL + "," 
                + Formatter.formatTime(dr.getPickUpTime()) + "," 
                + Formatter.quote(dr.getPickUpAddress()) + ","
                + NULL + "," 
                + NULL + ","
                + dr.getStatus().value 
                + "," + dr.getWeight() 
                + ")";
        
        System.out.println(query);
        
        super.executeQuery(query);
    }
    
    /**
     * Updates the delivery_request record with the data from the given <code>
     * DeliveryRequest</code>
     * @param dr    the <code>DeliveryRequest</code> containing the data to 
     *              update 
     * @throws SQLException 
     */
    public void update(DeliveryRequest dr) throws SQLException {
        // String builder used to construct the query 
        StringBuilder query = new StringBuilder();
        // Fields that might be updated by the query
        int deliveryID = dr.getDeliveryRequestID();
        int bidID = dr.getBidID();
        String description = dr.getDescription();
        Timestamp dropOffTime = dr.getDropOffTime();
        String dropOffAddress = dr.getDropOffAddress();
        Timestamp realDropOffTime = dr.getRealDropOffTime();
        Timestamp pickUpTime = dr.getPickUpTime();
        String pickUpAddress = dr.getPickUpAddress();
        Timestamp realPickUpTime = dr.getRealPickUpTime();
        Timestamp postTime = dr.getPostTime();
        Status status = dr.getStatus();
        float weight = dr.getWeight();
        // List containing each column=value pair for each updated attribute
        List<String> params = new LinkedList<>();
        
        query.append("UPDATE ").append(TABLE_NAME).append(" SET ");
        
        // Include a column=value pair for each existing, non-default attribute
        if (common.delivery.DeliveryRequestCE.DEFAULT_BID_ID != bidID) {
            params.add(BID_ID + "=" + bidID);
        }
        if (null != description) {
            params.add(DESCR + "=" + description);
        }
        if (null != dropOffTime) {
            params.add(DROP_TIME + "=" + dropOffTime);
        }
        if (null != dropOffAddress) {
            params.add(DROP_ADDR + "=" + dropOffAddress);
        }
        if (null != realDropOffTime) {
            params.add(REAL_DROP_TIME + "=" + realDropOffTime);
        }
        if (null != pickUpTime) {
            params.add(PICKUP_TIME + "=" + pickUpTime);
        }
        if (null != pickUpAddress) {
            params.add(PICKUP_ADDR + "=" + pickUpAddress);
        }
        if (null != realPickUpTime) {
            params.add(REAL_PICKUP_TIME + "=" + realPickUpTime);
        }
        if (null != postTime) {
            params.add(POST_TIME + "=" + postTime);
        }
        if (null != status) {
            params.add(STATUS + "=" + String.valueOf(status.value));
        }
        
        if (weight == weight) {
            params.add(WEIGHT + "=" + weight);
        }
        
        query.append(params.toString().substring(1,params.toString().length() - 1));
        query.append(" WHERE ").append(REQ_ID).append("=").append(deliveryID);
        super.executeQuery(query.toString());
    }
    
    /**
     * This method deletes the delivery request record with the given id
     * @param dr 
     * @throws SQLException if the operation failed
     */
    public void delete(int id) throws SQLException {
        String query = "DELETE FROM " + TABLE_NAME + " WHERE " + REQ_ID
                + "=" + id;
        
        super.executeQuery(query);
    }
    
    /**
     * Helper method that converts the list of <code>Object</code> returned by a
     * query to a list of <code>DeliveryRequest</code>.
     * 
     * @param list  the <code>List</code> of <code>Object</code> to convert
     * @return      <code>List</code> of <code>DeliveryRequest</code> as cast
     *              from <code>list</code>
     */
    private List<DeliveryRequest> convert(List<Object> list) {
        List<DeliveryRequest> converted = new ArrayList<DeliveryRequest>(
                list.size());
        for (Object each : list) {
            if (each instanceof DeliveryRequest) {
                converted.add((DeliveryRequest) each);
            } 
        }
        
        return converted;
    }
}
