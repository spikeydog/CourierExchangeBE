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
import common.util.code.rating.ExitCode;

/**
 *
 * @author Chiru
 */
public class RatingServerFinal implements RatingServer{

    public ExitCode insertRating(Rating rating) throws RemoteException {
        RatingAgent ratingAgent = new RatingAgent();
           ExitCode code = null;
        try {
            code = ratingAgent.insert(rating);
        } catch (SQLException ex) {
            Logger.getLogger(RatingServerFinal.class.getName()).log(Level.SEVERE, null, ex);
        }
       return code;
    }
    
    public Rating   getRating(int delReqID) throws RemoteException
    {
        Rating rating = null;
        RatingAgent ratingAgent = new RatingAgent();
        try {
            rating = ratingAgent.getRating(delReqID);
        } catch (SQLException ex) {
            Logger.getLogger(RatingServerFinal.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rating;
    }

}
 