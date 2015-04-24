/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package travellingTime;
import bidding.BidGopher;
import common.bidding.Bid;
import common.delivery.DeliveryRequest;
import common.rating.travellingTime.TravellingTime;
import common.rating.travellingTime.TravellingTimeBE;
import delivery.DeliveryRequestGopher;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Chiru
 */
public class TravellingTimeAgent {
    
    
    public TravellingTime getTravelTimes(int courier_id,String pickUpAdress,String dropOffAdress) throws SQLException
    {
        TravellingTime travellingTime = new TravellingTimeBE();
        long personalTravellingTime = getPersonalTravellingTime(courier_id, pickUpAdress, dropOffAdress);
        long overallTravellingTime = getOverallTravellingTime(pickUpAdress, dropOffAdress);        
        travellingTime.setOverallTravellingTime(overallTravellingTime);
        travellingTime.setPersonalTravellingTime(personalTravellingTime);        
        return travellingTime;        
    }
    
    public  long getPersonalTravellingTime(int user_id,String pickUpAdressPin , String dropOffAdressPin) throws SQLException
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
                     System.out.println("Printing the BID "+bid.getBidID()+" "+deliveryRequest.getDeliveryRequestID()+" "
                             +deliveryRequest.getPickUpAddress()+" "+deliveryRequest.getDropOffAddress());
                    //If Bothe pickup and dropoff adress are same as mentoind by the user
                     
                     pickUpAdressPin = pickUpAdressPin.substring(pickUpAdressPin.length()-5);
                     dropOffAdressPin = dropOffAdressPin.substring(dropOffAdressPin.length()-5);
                     
                     System.out.println("The pickup passeed pin is"+pickUpAdressPin);
                     System.out.println("The drop off passed pin is"+dropOffAdressPin);
                     
                     String delReqPickUpPinCode = deliveryRequest.getPickUpAddress().substring(deliveryRequest.getPickUpAddress().length()-5);
                     String delReqDropOffPinCode = deliveryRequest.getDropOffAddress().substring(deliveryRequest.getDropOffAddress().length()-5);
                    if( delReqPickUpPinCode.equals(pickUpAdressPin) &&
                         delReqDropOffPinCode.equals(dropOffAdressPin))
                    {
                        
                        timeDifference = deliveryRequest.getRealPickUpTime().getTime() -  deliveryRequest.getRealDropOffTime().getTime();
                        aggreateTimeDifference += timeDifference;
                        noOfDeliveries += 1;
                    }
                }    
        }      
        System.out.println("Aggreate time is "+aggreateTimeDifference);
        System.out.println("Number of deliverires is"+noOfDeliveries);
        if(noOfDeliveries != 0)
        {
        averagePersonlTime = aggreateTimeDifference/noOfDeliveries; 
         averagePersonlTime /= (60*1000);
        }
        else
        {
            averagePersonlTime = 0;
        }
         System.out.println("Average Time time is "+averagePersonlTime+" Min");
        
        return averagePersonlTime;
    }
    
    public  long getOverallTravellingTime(String pickUpAdressPin , String dropOffAdressPin) throws SQLException
    {
        BidGopher bidGopher = new BidGopher();
        int noOfDeliveries = 0;
        long timeDifference = 0;
        long aggreateTimeDifference = 0;
        long averagePersonlTime=0;        
        DeliveryRequestGopher deliveryRequestGopher = new DeliveryRequestGopher();
        List<DeliveryRequest>  delReqList =  deliveryRequestGopher.getList(0);
       // DeliveryRequest deliveryRequest;
        for(DeliveryRequest deliveryRequest : delReqList)
        {        
            //System.out.println("Printing the BID "+bid.getBidID());
            //Get delivery requst associates with the bid ID... It may or may not be
               // deliveryRequest = deliveryRequestGopher.getDelRequestForBid(bid.getBidID()); 
                if(deliveryRequest != null)
                {
                    System.out.println("IN if");
                     System.out.println("Printing the BID "+" "+deliveryRequest.getDeliveryRequestID()+" "
                             +deliveryRequest.getPickUpAddress()+" "+deliveryRequest.getDropOffAddress());
                    //If Bothe pickup and dropoff adress are same as mentoind by the user
                     pickUpAdressPin = pickUpAdressPin.substring(pickUpAdressPin.length()-5);
                     dropOffAdressPin = dropOffAdressPin.substring(dropOffAdressPin.length()-5);
                     
                     
                     String delReqPickUpPinCode = deliveryRequest.getPickUpAddress().substring(deliveryRequest.getPickUpAddress().length()-5);
                     String delReqDropOffPinCode = deliveryRequest.getDropOffAddress().substring(deliveryRequest.getDropOffAddress().length()-5);
                   
                    if( delReqPickUpPinCode.equals(pickUpAdressPin) &&
                        delReqDropOffPinCode.equals(dropOffAdressPin))
                    {
                        
                        timeDifference = deliveryRequest.getRealPickUpTime().getTime() -  deliveryRequest.getRealDropOffTime().getTime();
                        aggreateTimeDifference += timeDifference;
                        noOfDeliveries += 1;
                    }
                }    
        }      
        System.out.println("Aggreate time is "+aggreateTimeDifference);
        System.out.println("Number of deliverires is"+noOfDeliveries);
        if(noOfDeliveries != 0)
        {
        averagePersonlTime = aggreateTimeDifference/noOfDeliveries; 
         averagePersonlTime /= (60*1000);
        }
        else
        {
            averagePersonlTime = 0;
        }
        // System.out.println("Average Time before divisoin is "+averagePersonlTime);       
         System.out.println("Average Time time is "+averagePersonlTime+" Min");
        
        return averagePersonlTime;
    }
}