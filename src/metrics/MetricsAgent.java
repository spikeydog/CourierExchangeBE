/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metrics;

import bidding.BidAgent;
import bidding.BidGopher;
import common.bidding.Bid;
import delivery.DeliveryRequestGopher;
import java.util.List;
import rating.RatingAgent;
import rating.RatingGopher;
import common.delivery.*;
import common.rating.Rating;
import common.rating.metrics.Metrics;
import common.rating.metrics.MetricsCE;
import common.user.User;
import java.sql.SQLException;
import java.util.ArrayList;
import user.UserGopher;

/**
 *
 * @author Chiru
 */
public class MetricsAgent {
    
    public Metrics getMetrics(int courierID) throws SQLException
    {
        Metrics metrics = new MetricsCE();   
        metrics.setCourierID(courierID);
        metrics.setAverageDeliveryPersonRating(getAverageDeliveryPersonRating(courierID));
        metrics.setAverageOverallRating(getAverageOverallRating(courierID));
        metrics.setAverageProfesionalismRating(getAverageProfesionalismRating(courierID));
        metrics.setNoOfBidsPlaced(getNoOfBidsPlaced(courierID));
        metrics.setPercentageRequestsDeliveredOnTime(getPercentageRequestsDeliveredOnTime(courierID));
        metrics.setTotalRequestsDelivered(getNoOfDeliveriesCompleted(courierID));        
        return metrics;
    }
    
    public List<Metrics> getMetricsForAllCouriers() throws SQLException
    {
        UserGopher userGopher = new UserGopher();
        List<User> courierList = userGopher.getAllCouriers();
        List<Metrics> metricsList = new ArrayList<Metrics>();
         
        for(User tempUser : courierList)
        {
            metricsList.add(getMetrics(tempUser.getUserID()));
        }
        
        return metricsList;
    }
    
    public float getAverageOverallRating(int courierID) throws SQLException
    {
        float averageOverallRating = 0;
        float totalOverallRating=0;
        float totalNoOfDeliveries=0;
       // int user_id;
        
        RatingGopher ratingGopher = new RatingGopher();  
        RatingAgent ratingAgent = new RatingAgent();
        DeliveryRequestGopher deliveryRequestGopher = new DeliveryRequestGopher();
        BidGopher  bidGopher = new BidGopher();
        BidAgent bidAgent = new BidAgent();
        
        List<DeliveryRequest> deliveryRequestList =  deliveryRequestGopher.getList(0);
        
        for(DeliveryRequest deliveryRequest : deliveryRequestList )
        {
         Bid bid = bidGopher.get(deliveryRequest.getBidID());
         if(bid.getCourierID() == courierID)
         {
         System.out.println("Del Request id is"+deliveryRequest.getDeliveryRequestID());
         Rating rating = ratingAgent.getRating(deliveryRequest.getDeliveryRequestID());
         
         totalNoOfDeliveries+=1;
         System.out.println("The rating for one of the customer is "+rating.getCustomerOverallRating());
         totalOverallRating = totalOverallRating + rating.getCustomerOverallRating(); 
         }
        }
       
        
        System.out.println("Total overall rating is "+totalOverallRating);
        System.out.println("Total no of deleveries  is "+totalNoOfDeliveries);
        averageOverallRating = (totalOverallRating/totalNoOfDeliveries);
        System.out.println("The average overall rating is "+averageOverallRating);
        return averageOverallRating;        
    }
    
     public float getAverageProfesionalismRating(int courierID) throws SQLException
    {
        float averageProfesionalismRating = 0;
        float totalProfesionalismRating=0;
        float totalNoOfDeliveries=0;
       // int user_id;
        
        RatingAgent ratingAgent = new RatingAgent();
        DeliveryRequestGopher deliveryRequestGopher = new DeliveryRequestGopher();
        BidGopher  bidGopher = new BidGopher();
        
        List<DeliveryRequest> deliveryRequestList =  deliveryRequestGopher.getList(0);
        
        for(DeliveryRequest deliveryRequest : deliveryRequestList )
        {
         Bid bid = bidGopher.get(deliveryRequest.getBidID());
         if(bid.getCourierID() == courierID)
         {
         System.out.println("Del Request id is"+deliveryRequest.getDeliveryRequestID());
         Rating rating = ratingAgent.getRating(deliveryRequest.getDeliveryRequestID());
         
         totalNoOfDeliveries+=1;
         System.out.println("The rating for one of the customer is "+rating.getCustomerProfesionalismRating());
         totalProfesionalismRating = totalProfesionalismRating + rating.getCustomerProfesionalismRating(); 
         }
        }
       
        
        System.out.println("Total professionalism rating is "+totalProfesionalismRating);
        System.out.println("Total no of deleveries  is "+totalNoOfDeliveries);
        averageProfesionalismRating = (totalProfesionalismRating/totalNoOfDeliveries);
        System.out.println("The average professionalism rating is "+averageProfesionalismRating);
        return averageProfesionalismRating;        
    }
   
     
    public float getAverageDeliveryPersonRating(int courierID) throws SQLException
    {
        float averageDeliveryPersonRating = 0;
        float totalDeliveryPersonRating=0;
        float totalNoOfDeliveries=0;
       // int user_id;
        
        RatingGopher ratingGopher = new RatingGopher();  
        RatingAgent ratingAgent = new RatingAgent();
        DeliveryRequestGopher deliveryRequestGopher = new DeliveryRequestGopher();
        BidGopher  bidGopher = new BidGopher();
        BidAgent bidAgent = new BidAgent();
        
        List<DeliveryRequest> deliveryRequestList =  deliveryRequestGopher.getList(0);
        
        for(DeliveryRequest deliveryRequest : deliveryRequestList )
        {
         Bid bid = bidGopher.get(deliveryRequest.getBidID());
         if(bid.getCourierID() == courierID)
         {
         System.out.println("Del Request id is"+deliveryRequest.getDeliveryRequestID());
         Rating rating = ratingAgent.getRating(deliveryRequest.getDeliveryRequestID());
         
         totalNoOfDeliveries+=1;
         System.out.println("The rating for one of the customer is "+rating.getCustomerDeliveryPersonRating());
         totalDeliveryPersonRating = totalDeliveryPersonRating + rating.getCustomerDeliveryPersonRating(); 
         }
        }
       
        
        System.out.println("Total DeliveryPerson rating is "+totalDeliveryPersonRating);
        System.out.println("Total no of deleveries  is "+totalNoOfDeliveries);
        averageDeliveryPersonRating = (totalDeliveryPersonRating/totalNoOfDeliveries);
        System.out.println("The average DeliveryPerson rating is "+averageDeliveryPersonRating);
        return averageDeliveryPersonRating;        
    }
    
    public int getNoOfBidsPlaced(int courier_id) throws SQLException
    {
     int noOfBids = 0;
     BidGopher  bidGopher = new BidGopher();
     List<Bid> bidsList = bidGopher.getListByUserID(courier_id);
     noOfBids = bidsList.size();
     return noOfBids;
    }
    
    public int getNoOfDeliveriesCompleted(int courier_id) throws SQLException
    {
     int noOfDeleveriesCompleted = 0;
     DeliveryRequestGopher  deliveryRequestGopher = new DeliveryRequestGopher();
     BidGopher  bidGopher = new BidGopher();
     List<Bid> bidsList = bidGopher.getListByUserID(courier_id);
     DeliveryRequest deliveryRequest;
     
     for(Bid bid : bidsList)
     {
       deliveryRequest = deliveryRequestGopher.getDelRequestForBid(bid.getBidID());
       if(deliveryRequest != null)
       {
           noOfDeleveriesCompleted++;
       }
     }
     return noOfDeleveriesCompleted;
    }
      
    public int getPercentageRequestsDeliveredOnTime(int courier_id) throws SQLException
    {
        int percentageRequest = 0;
        int delayedDeliveries = 0 ;
        
     int noOfDeleveriesCompleted = 0;
     DeliveryRequestGopher  deliveryRequestGopher = new DeliveryRequestGopher();
     BidGopher  bidGopher = new BidGopher();
     List<Bid> bidsList = bidGopher.getListByUserID(courier_id);
     DeliveryRequest deliveryRequest;
     
     for(Bid bid : bidsList)
     {
       deliveryRequest = deliveryRequestGopher.getDelRequestForBid(bid.getBidID());
       if(deliveryRequest != null)
       {
           noOfDeleveriesCompleted++;
            if(bid.getDropOffTime().after(deliveryRequest.getRealDropOffTime()))
            {
                delayedDeliveries++;
            }
        }    
           
      }

     if(noOfDeleveriesCompleted != 0)
     {
     percentageRequest = (int)(delayedDeliveries/noOfDeleveriesCompleted)*100;
     }
     return percentageRequest;        
    }
}




