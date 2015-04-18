/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rating;

import common.bidding.Bid;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Chiru
 */
public class RatingAgent {
    
    public boolean insert(Rating rating) throws SQLException
    {
        RatingGopher ratingGopher = new RatingGopher();
        ratingGopher.insert(rating);                
        return true;
    }
    
}
