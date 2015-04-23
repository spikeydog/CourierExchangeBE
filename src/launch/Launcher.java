/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package launch;

import bidding.BidServer;
import  common.rating.Server;
import common.bidding.BiddingServer;
import common.rating.RatingServer;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import metrics.MetricsAgent;
import rating.RatingServerFinal;


/**
 * This class initializes necessary server objects within the application.
 * @author sedog
 */
public class Launcher {
    private static final int RMI_PORT = 2222;
    
        public static void main(String[] args) throws SQLException {
        // The RMI registry that allows clients to obtain server stubs
        Registry registry;
        
        /*
            Rating rating;
            RatingAgent ra = new RatingAgent();
            RatingGopher rg = new RatingGopher();
            RatingCE rate = new RatingCE();
            rate.setRatingID(1);
            rate.setDeliveryRequestID(1);
            rate.setCustomerOverallRating(1);
            rate.setCustomerProfesionalismRating(1);
            rate.setCustomerDeliveryPersonRating(1);
            
            //rg.insert(rate);
            ExitCode code = ra.insert(rate);
            System.out.println("ExistCode after RA insert is "+code);
            
            rating = ra.getRating(1000);
            System.out.println("The rating recived from dtabase is "+rating.getRatingID()+" "+rating.getDeliveryRequestID());
         */
        
        /*
        TravellingTimeAgent pta = new TravellingTimeAgent();
        System.out.println(pta.getPersonalTravellingTime(1,"UT","UNCC"));
        System.out.println(pta.getOverallTravellingTime("UT","UNCC"));
        */
        /*
        MetricsAgent ma = new MetricsAgent();
        System.out.println(ma.getAverageOverallRating(1));
        System.out.println(ma.getAverageProfesionalismRating(1));
         System.out.println(ma.getAverageDeliveryPersonRating(1));
         System.out.println("No of bids placed "+ma.getNoOfBidsPlaced(1));
         System.out.println("No of deleveries completed "+ma.getNoOfDeliveriesCompleted(1));         
         System.out.println("Percentage of good deleveries "+ma.getPercentageRequestsDeliveredOnTime(1));
        */
       // pta.getPersonalTravellingTime(1,"UT","UNCC");

            
           // RatingServerFinal rs = new RatingServerFinal();
        /* This will fail if the RMI service is not running */
       try {
            registry = LocateRegistry.createRegistry(RMI_PORT);
            //launchUserServer(registry);
            //launchDeliveryServer(registry);
            launchBidServer(registry);
            //launchTrackingServer(registry);
           // launchRatingServer(registry);
            
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
