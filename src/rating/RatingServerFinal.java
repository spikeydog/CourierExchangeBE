/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rating;

import common.rating.Rating;
import common.rating.RatingServer;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Chiru
 */
public class RatingServerFinal implements RatingServer{

    public Rating insertRating(Rating rating) throws RemoteException {
        RatingAgent ratingAgent = new RatingAgent();
        try {
            ratingAgent.insert(rating);
        } catch (SQLException ex) {
            Logger.getLogger(RatingServerFinal.class.getName()).log(Level.SEVERE, null, ex);
        }
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
 