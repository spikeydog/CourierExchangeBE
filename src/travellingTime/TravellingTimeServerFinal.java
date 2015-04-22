/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package travellingTime;

import common.rating.travellingTime.TravellingTime;
import common.rating.travellingTime.TravellingTimeServer;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Chiru
 */
public class TravellingTimeServerFinal implements TravellingTimeServer {
    
        
        public TravellingTime getTravellingTimes(int courier_id,String pickUpAdress,String dropOffAdress)
        {
            TravellingTimeAgent travellingTimeAgent = new TravellingTimeAgent();
            TravellingTime travellingTime;
            travellingTime = null;
        try {
            travellingTime = travellingTimeAgent.getTravelTimes(courier_id, pickUpAdress, dropOffAdress);
        } catch (SQLException ex) {
            Logger.getLogger(TravellingTimeServerFinal.class.getName()).log(Level.SEVERE, null, ex);
        }
            return travellingTime;
        }
    
    
}
