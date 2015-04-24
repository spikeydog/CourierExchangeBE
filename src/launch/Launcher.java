/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package launch;

import bidding.BidServer;
import common.bidding.BiddingServer;
import common.delivery.DeliveryServer;
import common.rating.RatingServer;
import common.rating.metrics.MetricsServer;
import common.rating.travellingTime.TravellingTimeServer;
import common.user.UserCommonServer;
import delivery.DeliveryRequestServer;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import metrics.MetricsServerFinal;
import rating.RatingServerFinal;
import travellingTime.TravellingTimeServerFinal;
import user.UserServer;


/**
 * This class initializes necessary server objects within the application.
 * @author sedog
 */
public class Launcher {
    private static final int RMI_PORT = 2222;
    
        public static void main(String[] args) throws SQLException {
        // The RMI registry that allows clients to obtain server stubs
        Registry registry;
        
       /* This will fail if the RMI service is not running */
       try {
            registry = LocateRegistry.createRegistry(RMI_PORT);
            //launchUserServer(registry);
            launchMetricsServer(registry);
            launchDeliveryServer(registry);
            launchBidServer(registry);
            //launchTrackingServer(registry);
            launchRatingServer(registry);
            launchTravellingTimeServer(registry);
            
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
    
    private static void launchUserServer(final Registry registry)
    {
         try {
        UserCommonServer server = new UserServer();
        UserCommonServer bidStub = (UserCommonServer) UnicastRemoteObject
              .exportObject(server, 0);
        registry.rebind(common.user.Server.RMI_BINDING.name, bidStub);
    } catch (RemoteException ex) {
        System.out.println("Unable to bind UserServer");
        ex.printStackTrace();
    }
    System.out.println("User Server bound successfully");
    }
    
    private static void launchMetricsServer(final Registry registry)
    {
         try {
        MetricsServer server = new MetricsServerFinal();
        MetricsServer metricsStub = (MetricsServer) UnicastRemoteObject
              .exportObject(server, 0);
        registry.rebind(common.rating.metrics.Server.RMI_BINDING.name, metricsStub);
    } catch (RemoteException ex) {
        System.out.println("Unable to bind MetricsServer");
        ex.printStackTrace();
    }
    System.out.println("Metrics Server bound successfully");
    }
    
    private static void launchTravellingTimeServer(final Registry registry) {
        try {
            TravellingTimeServer server = new TravellingTimeServerFinal();
            TravellingTimeServer timeStub = (TravellingTimeServer) UnicastRemoteObject
                    .exportObject(server, 0);
            registry.rebind(common.rating.travellingTime.Server.RMI_BINDING.name, timeStub);
        } catch (RemoteException ex) {
            System.out.println("Unable to bind TravelingTime Server");
            ex.printStackTrace();
        }
        System.out.println("TravellingTimeServer bound successfully!");
    }
    
    private static void launchDeliveryServer(final Registry registry) {
        try {
            DeliveryServer server = new DeliveryRequestServer();
            DeliveryServer timeStub = (DeliveryServer) UnicastRemoteObject
                    .exportObject(server, 0);
            registry.rebind(common.delivery.Server.RMI_BINDING.name, timeStub);
        } catch (RemoteException ex) {
            System.out.println("Unable to bind Delivery Server");
            ex.printStackTrace();
        }
        System.out.println("DeliveryServer bound successfully!");
    }
}