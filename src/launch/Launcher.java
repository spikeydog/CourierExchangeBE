/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package launch;

import common.DeliveryRequest;
import common.Status;
import delivery.DeliveryRequestGopher;

/**
 * This class initializes necessary server objects within the application.
 * @author sedog
 */
public class Launcher {
    public static void main(String[] args) {
        // For now, this just instantiates things to test
        DeliveryRequestGopher gopher = new DeliveryRequestGopher();
        DeliveryRequest dr = gopher.get(1);
        System.out.println(dr.getDescription());
        
        for (DeliveryRequest r : gopher.getList(Status.COMPLETE)) {
            System.out.println(r.getStatus());
        }
    }
}
