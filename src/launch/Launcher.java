/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package launch;

import bidding.BidServer;
import common.bidding.BiddingServer;
import common.rating.RatingServer;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import rating.RatingServerFinal;
import rating.Server;

/**
 * This class initializes necessary server objects within the application.
 * @author sedog
 */
public class Launcher {
    private static final int RMI_PORT = 2222;
    
        public static void main(String[] args) throws SQLException {
        // The RMI registry that allows clients to obtain server stubs
        Registry registry;
        
            //RatingAgent ra = new RatingAgent();
            //RatingGopher rg = new RatingGopher();
            //RatingCE rate = new RatingCE();
            //rate.setRatingID(1);
            //rate.setDeliveryRequestID(1);
            //rate.setCustomerOverallRating(1);
            //rate.setCustomerProfesionalismRating(1);
            //rate.setCustomerDeliveryPersonRating(1);
            
            //rg.insert(rate);
            //ra.insert(rate);
            

            
           // RatingServerFinal rs = new RatingServerFinal();
        /* This will fail if the RMI service is not running */
       try {
            registry = LocateRegistry.createRegistry(RMI_PORT);
            //launchUserServer(registry);
            //launchDeliveryServer(registry);
            launchBidServer(registry);
            //launchTrackingServer(registry);
            //launchRatingServer(registry);
            
        } catch (RemoteException ex) {
            System.out.println("Unable to locate Registry");
            ex.printStackTrace();
        } 
    }
        
    private static void launchRatingServer(final Registry registry)
    {
         try {
        RatingServer rs = new RatingServerFinal();
        RatingServer rateStub = (RatingServer) UnicastRemoteObject
                .exportObject(rs, 0);
        registry.rebind(common.rating.Server.RMI_BINDING.name, rateStub);
    } catch (RemoteException ex) {
        System.out.println("Unable to bind BiddingServer");
        ex.printStackTrace();
    }
    System.out.println("Rating Server bound successfully");
    }

    private static void launchBidServer(final Registry registry)
    {
         try {
        BiddingServer server = new BidServer();
        BiddingServer bidStub = (BiddingServer) UnicastRemoteObject
              .exportObject(server, 0);
        registry.rebind("biddingServer", bidStub);
    } catch (RemoteException ex) {
        System.out.println("Unable to bind BiddingServer");
        ex.printStackTrace();
    }
    System.out.println("Bidding Server bound successfully");
    }
}
