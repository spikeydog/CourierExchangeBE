/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metrics;

import common.rating.metrics.Metrics;
import common.rating.metrics.MetricsServer;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Chiru
 */
public class MetricsServerFinal implements MetricsServer{

   
    public Metrics getMetrics(int courierID) throws RemoteException{
     MetricsAgent metricsAgent = new MetricsAgent();
     Metrics metrics = null;
        try {
            metrics = metricsAgent.getMetrics(courierID);
        } catch (SQLException ex) {
            Logger.getLogger(MetricsServerFinal.class.getName()).log(Level.SEVERE, null, ex);
        }
        return metrics;
    }

    @Override
    public List<Metrics> getMetricsForAllCouriers() throws RemoteException {
     
        MetricsAgent metricsAgent = new MetricsAgent();
        List<Metrics> metricsList = null;
        try {
            metricsList =  metricsAgent.getMetricsForAllCouriers();
        } catch (SQLException ex) {
            Logger.getLogger(MetricsServerFinal.class.getName()).log(Level.SEVERE, null, ex);
        }
        return metricsList;
    }
    
    
}
