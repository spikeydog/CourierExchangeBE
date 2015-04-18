
package rating;

import common.bidding.Bid;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;


public class RatingGopher extends gopher.AbstractGopher {
    
    public final static String TABLE_NAME = "rating";
    public final static String RATING_ID = "rating_id";
    public final static String DELIVERY_REQEUST_ID = "delivery_request_id";
    public final static String OVERALL_RATING = "overall_rating";
    public final static String PROFESSIONALISM_RATING = "professionalism_rating";
    public final static String DELIVERY_PERSON_RATING = "delivery_person_rating";
    public final static String NULL = "NULL";
    public final static String DEFAULT = "DEFAULT";
    
     public void insert(Rating rating) throws SQLException {
        // The query to execute
        StringBuilder query = new StringBuilder();
        // List of all of the data values to insert
        List<String> values = new LinkedList<>();
        
        query.append("INSERT INTO ").append(TABLE_NAME).append(" VALUES(");
        values.add(DEFAULT);
        values.add(String.valueOf(rating.getRatingID()));
        values.add(String.valueOf(rating.getDeliveryRequestID()));
        values.add(String.valueOf(rating.getCustomerOverallRating()));
        values.add(String.valueOf(rating.getCustomerProfesionalismRating()));
        values.add(String.valueOf(rating.getCustomerDeliveryPersonRating())); 
       //To append the , between.... by converting the list to string and removing the final square brackets.
        query.append(values.toString().substring(1,values.toString().length() - 1));
        query.append(")");        
        System.out.println(query.toString());
        super.executeQuery(query.toString());
    }
    
     protected Rating parseResult(ResultSet results) throws SQLException {
        Rating rating = new RatingCE();
        
        try {
            rating.setRatingID(results.getInt(RATING_ID));//(results.getInt(RATING_ID));
            rating.setDeliveryRequestID(results.getInt(DELIVERY_REQEUST_ID));
            rating.setCustomerOverallRating(results.getInt(OVERALL_RATING));
            rating.setCustomerProfesionalismRating(results.getInt(PROFESSIONALISM_RATING));
            rating.setCustomerDeliveryPersonRating(results.getInt(DELIVERY_PERSON_RATING));
        } catch (SQLException ex) {
            System.out.println("Unable to read from resultSet");
            throw ex;
        }
        
        return rating;
    }
}
