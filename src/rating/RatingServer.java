/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rating;


import common.delivery.DeliveryRequest;
import java.rmi.RemoteException;
import java.util.List;

/**
 *
 * @author Chiru
 */
public interface RatingServer {
    
    //To show rating of a particula delivery request
    
    public Rating insertRating(Rating rating) throws RemoteException ;
    
}
