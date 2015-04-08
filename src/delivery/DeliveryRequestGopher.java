package delivery;

import common.DeliveryRequest;
import common.Status;
import gopher.AbstractGopher;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * This class handles the creation and processing of database queries related to
 * <code>DeliveryRequest</code> objects.
 * 
 * @author sedog
 */
public class DeliveryRequestGopher extends AbstractGopher {
    // Indeces for each database column so thtey only need to be set once
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
        DeliveryRequest request = new DeliveryRequestCE();
        
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
     * given primary key
     * 
     * @param id    the primary key of the record to retrieve
     * @return      a <code>DeliveryRequest</code> populated with data retrieved
     *              from the database
     */
    public DeliveryRequest get(int id) {
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
        request = castList.get(0);
        
        return request;
    }
    
    /**
     * This method returns a list of <code>DeliveryRequest</code> objects, one
     * for each record in the database with the given status.
     * 
     * @param status    the status of the delivery requests to list
     * @return          a list of all matching <code>DeliveryRequest</code>s
     */
    public List<DeliveryRequest> getList(int status) {
        // The list to return
        List<DeliveryRequest> list = null;
        // The query to execute
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE status = " 
                + status;
        
        List<Object> rawList = super.executeQuery(query);
        list = convert(rawList);
        
        return list;
    }
    
    /**
     * This method returns a list of <code>DeliveryRequest</code> objects, one
     * for each record in the database with the given status that is associated
     * with the given user id.
     * 
     * @param status    the status of the delivery requests to list
     * @param userID    the primary key of the associated user
     * @return 
     */
    public List<DeliveryRequest> getListByUserId(Status status, int userID) {
        // The list to return
        List<DeliveryRequest> list = null;
        // The query to execute
        String query = null; 
        
        // TODO ... not done, need bidding also
        
        List<Object> rawList = super.executeQuery(query);
        list = convert(rawList);
        
        return list;
    }
    
    public void insert(DeliveryRequest dr) {
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
                + quote(dr.getDescription()) + "," 
                + formatTime(dr.getDropOffTime()) + "," 
                + quote(dr.getDropOffAddress()) + ","
                + NULL + "," 
                + formatTime(dr.getPickUpTime()) + "," 
                + quote(dr.getPickUpAddress()) + ","
                + NULL + "," 
                + NULL + ","
                + dr.getStatus().value 
                + "," + dr.getWeight() 
                + ")";
        
        System.out.println(query);
        
        super.executeQuery(query);
    }
    
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
    
    private String quote(String string) {
        return "'" + string + "'";
    }
    
    private String formatTime(Timestamp time) {
        String string = time.toString();
        String formatted = string.substring(0,string.lastIndexOf('.',string.length() - 1));
        
        System.out.println(formatted);
        
        return "'" + formatted + "'";
    }
}
