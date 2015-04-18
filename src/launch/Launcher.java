/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package launch;

import bidding.BidServer;
import common.bidding.BiddingServer;
import common.bidding.Server;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 * This class initializes necessary server objects within the application.
 * @author sedog
 */
public class Launcher {
    private static final int RMI_PORT = 2222;
    
        public static void main(String[] args) {
        // The RMI registry that allows clients to obtain server stubs
        Registry registry;
        
        /* This will fail if the RMI service is not running */
        try {
            registry = LocateRegistry.createRegistry(RMI_PORT);
            //launchUserServer(registry);
            //launchDeliveryServer(registry);
            launchBidServer(registry);
            //launchTrackingServer(registry);
            //launchRatingsServer(registry);
            
        } catch (RemoteException ex) {
            System.out.println("Unable to locate Registry");
        }

    }
    
    private static void launchBidServer(final Registry registry) {
        try {
            BiddingServer bidServer = new BidServer();
            BiddingServer bidStub = (BiddingServer) UnicastRemoteObject
                    .exportObject(bidServer, 0);
            registry.rebind(Server.RMI_BINDING.name, bidStub);
        } catch (RemoteException ex) {
            System.out.println("Unable to bind BiddingServer");
            ex.printStackTrace();
        }
        System.out.println("Bidding Server bound successfully");
    }
}
