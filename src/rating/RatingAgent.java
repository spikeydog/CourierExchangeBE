/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rating;
import common.rating.Rating;
import delivery.DeliveryRequestGopher;
import java.sql.SQLException;
import common.util.code.rating.ExitCode;

/**
 *
 * @author Chiru
 */
public class RatingAgent {   
    
    public ExitCode insert(Rating rating) throws SQLException
    {
        ExitCode exitCode = ExitCode.SUCCESS;
        boolean statusValidation = checkForRange(rating);
        System.out.println("Status validation is "+statusValidation);
        if(statusValidation)
        {        
           boolean isDelReqeustValid = isDelReqeustValid(rating.getDeliveryRequestID());
           System.out.println("DelRequest validation is "+isDelReqeustValid);
           if(isDelReqeustValid)
           {
               //Do nothing
           }
           else
           {
                exitCode = ExitCode.RATING_DEL_REQ_NOT_EXIST;
           }
        }
        else
        {
            exitCode = ExitCode.RATING_RANGE_ERR;
        }
       
        if(exitCode == ExitCode.SUCCESS)
        {
           RatingGopher ratingGopher = new RatingGopher();
           ratingGopher.insert(rating); 
        } 
        
        return exitCode;
    }
    
    public Rating getRating(int delReqID) throws SQLException
    {
         ExitCode exitCode = ExitCode.SUCCESS; 
         Rating rating = null;
         
          boolean isDelReqeustValid = isDelReqeustValid(delReqID);
          System.out.println("DelRequest validation is "+isDelReqeustValid);
           if(isDelReqeustValid)
           {
               RatingGopher ratingGopher = new RatingGopher();
               rating = ratingGopher.get(delReqID);
           }
           else
           {
                exitCode = ExitCode.RATING_DEL_REQ_NOT_EXIST;
           }           
           return rating;
    }

    public boolean checkForRange(Rating rating)
    {
     int overallRating = rating.getCustomerOverallRating();
     int ratingType1= rating.getCustomerDeliveryPersonRating();
     int ratingType2=rating.getCustomerProfesionalismRating();
     if( ratingType1 > 5 || ratingType1 < 1 )
        return false;
     else if(ratingType2 > 5 || ratingType2 < 1)
         return false;
     else if(overallRating > 5 || overallRating < 1)
          return false;
     else
         return true;
    }
    
    //To check if delivery request is is database or not
    public boolean isDelReqeustValid(int delReqID) throws SQLException
    {
        boolean isValid = false;
        DeliveryRequestGopher dg = new DeliveryRequestGopher();
        if(dg.get(delReqID) != null)
        {
            isValid = true;
        }
        return isValid;
    }    
}
