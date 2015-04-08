/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package launch;

import bidding.BidCE;
import bidding.BidGopher;
import common.Bid;
import common.DeliveryRequest;
import common.Status;
import delivery.DeliveryRequestCE;
import delivery.DeliveryRequestGopher;
import java.sql.Timestamp;

/**
 * This class initializes necessary server objects within the application.
 * @author sedog
 */
public class Launcher {
    public static void main(String[] args) {
        // For now, this just instantiates things to test
        DeliveryRequestGopher gopher = new DeliveryRequestGopher();
        
        DeliveryRequest dr = new DeliveryRequestCE(); 
        dr.setCustomerID(23);
        dr.setDescription("test delivery reqeust");
        dr.setDropOffAddress("drop off addr");
        dr.setDropOffTime(new Timestamp(System.currentTimeMillis()));
        dr.setPickUpAddress("pick up addr");
        dr.setPickUpTime(new Timestamp(System.currentTimeMillis()));
        dr.setStatus(Status.SAVED);
        dr.setWeight((float) 10.0);
        
        gopher.insert(dr);
        
        
        for (DeliveryRequest r : gopher.getList(Status.SAVED.value)) {
            System.out.println(r.getStatus());
        }
        
        dr = gopher.get(gopher.getList(Status.SAVED.value).get(0).getDeliveryRequestID());
        System.out.println(dr.getDescription());
        
        dr = new DeliveryRequestCE();
        dr.setDeliveryRequestID(gopher.getList(Status.SAVED.value).get(0).getDeliveryRequestID());
        dr.setStatus(Status.IN_PROGRESS);
        
        gopher.update(dr);
        for (DeliveryRequest r : gopher.getList(Status.IN_PROGRESS.value)) {
            System.out.println(r.getStatus());
        }
        
        gopher.delete(gopher.getList(Status.SAVED.value).get(0).getDeliveryRequestID());
        
        BidGopher g2 = new BidGopher();
        Bid bid = new BidCE(23,23,new Timestamp(System.currentTimeMillis()), 
                new Timestamp(System.currentTimeMillis()), (float) 10.0, false, false);
        g2.insert(bid);
        System.out.println(g2.get(1)); 
    }
}
