/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package travellingTime;

import bidding.BidGopher;
import common.bidding.Bid;
import common.delivery.DeliveryRequest;
import delivery.DeliveryRequestGopher;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Chiru
 */
public class TravellingTimeAgent {
    
    public  long getPersonalTravellingTime(int user_id,String pickUpAdress , String dropOffAdress) throws SQLException
    {
        BidGopher bidGopher = new BidGopher();
        int noOfDeliveries = 0;
        long timeDifference = 0;
        long aggreateTimeDifference = 0;
        long averagePersonlTime=0;
        
        System.out.println("The provided userID is "+user_id);
        
        //Get all the bids placed by a particular courier.
        List<Bid> bidList =bidGopher.getListByUserID(user_id);
        
      
        
        DeliveryRequestGopher deliveryRequestGopher = new DeliveryRequestGopher();
        DeliveryRequest deliveryRequest;
        for(Bid bid : bidList)
        {        
            System.out.println("Printing the BID "+bid.getBidID());
            //Get delivery requst associates with the bid ID... It may or may not be
                deliveryRequest = deliveryRequestGopher.getDelRequestForBid(bid.getBidID()); 
                if(deliveryRequest != null)
                {
                    System.out.println("IN if");
                     System.out.println("Printing the BID "+bid.getBidID()+" "
                             +deliveryRequest.getPickUpAddress()+" "+pickUpAdress);
                    //If Bothe pickup and dropoff adress are same as mentoind by the user
                     
                    if( deliveryRequest.getPickUpAddress().equals(pickUpAdress) &&
                         deliveryRequest.getDropOffAddress().equals(dropOffAdress))
                    {
                        
                        timeDifference = deliveryRequest.getRealPickUpTime().getTime() -  deliveryRequest.getRealDropOffTime().getTime();
                        aggreateTimeDifference += timeDifference;
                        noOfDeliveries += 1;
                    }
                }    
        }      
        System.out.print("Aggreate time is "+aggreateTimeDifference);
        averagePersonlTime = aggreateTimeDifference/noOfDeliveries;  
        
        return averagePersonlTime;
    }
    
}
