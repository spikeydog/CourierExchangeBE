/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package launch;

import bidding.BidAgent;
import bidding.BidCE;
import common.bidding.Bid;
import common.delivery.DeliveryRequest;
import delivery.DeliveryRequestCE;
import java.sql.Timestamp;

/**
 * This is a freely-mutable tester class for anyone to use. 
 * 
 * When you write your own tests, simply do not commit any changes to the repo,
 * so other team member's tests will not be adversely affected.
 * 
 * @author sedog
 */
public class Tester {
    public final static void main(String[] args) {
        /*
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
        
        try {
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

            if (0 < gopher.getList(Status.SAVED.value).size()) {
                gopher.delete(gopher.getList(Status.SAVED.value).get(0).getDeliveryRequestID());


            BidGopher g2 = new BidGopher();
            Bid bid = new BidCE(23,23,new Timestamp(System.currentTimeMillis()), 
                    new Timestamp(System.currentTimeMillis()), (float) 10.0, false, false);
            g2.insert(bid);
            System.out.println(g2.get(1));

            System.out.println(g2.getList(23).get(0));
            System.out.println(g2.getListByUserID(23).get(0).getFee());

            Bid copy = g2.getList(23).get(0).clone();
            System.out.println(copy.getFee());
            copy.setFee((float) 100.00);
            g2.update(copy);
            System.out.println(g2.getListByUserID(23).get(0).getFee());

            }
        } catch (SQLException ex) {}
        */
        BidAgent bidAgent = new BidAgent();
        Bid bid1 = new BidCE();
        bid1.setDeliveryRequestID(17);
        bid1.setCourierID(2);
        bid1.setPickUpTime(new Timestamp(System.currentTimeMillis()));
        bid1.setDropOffTime(new Timestamp(System.currentTimeMillis() + 60000));
        bid1.setFee((float) 10.00);
        System.out.println("First insert: " + bidAgent.insert(bid1)); 
        System.out.println("Second insert: " + bidAgent.insert(bid1)); 
        Bid bid2 = new BidCE();
        bid2.setBidID(11);
        System.out.println("Get inserted bid? " + bidAgent.get(bid2).equals(bid1));
        DeliveryRequest req1 = new DeliveryRequestCE(); req1.setDeliveryRequestID(23);
        for (Bid bid : bidAgent.getList(req1)) {
            System.out.println(bid.getBidID());
        }
    }
}
