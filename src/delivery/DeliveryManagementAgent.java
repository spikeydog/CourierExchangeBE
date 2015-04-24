/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package delivery;

import common.delivery.DeliveryRequest;
import common.delivery.Status;
import common.user.User;
import common.user.UserType;
import common.util.code.delivery.ExitCode;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Akshay
 */
public class DeliveryManagementAgent {
    
    public ExitCode insert_delivery(DeliveryRequest deliveryRequest)
{
    ExitCode e = ExitCode.FAILURE;
    DeliveryRequestGopher g = new DeliveryRequestGopher();
    try
    {
        g.insert(deliveryRequest);
        e = ExitCode.SUCCESS;
    }
    catch(SQLException ex)
    {
      e = ExitCode.SQL_EXCEPTION;
      ex.printStackTrace();
      System.err.println("SQLException trying to execute query");
       
    }
   return e;
   
}
    public ExitCode edit_delivery(DeliveryRequest deliveryRequest)
   {
     ExitCode e = ExitCode.FAILURE;
     DeliveryRequestGopher g = new DeliveryRequestGopher();
     try
     {
         g.update(deliveryRequest);
          e = ExitCode.SUCCESS;
     }
     catch(SQLException ex)
     {
      e = ExitCode.SQL_EXCEPTION; 
     }
    return e;
   }
    public ExitCode delete_delivery(DeliveryRequest deliveryRequest)
    {
        ExitCode e = ExitCode.FAILURE;
        DeliveryRequestGopher g = new DeliveryRequestGopher();
        try
        {
            g.delete(deliveryRequest.getDeliveryRequestID());
            e = ExitCode.SUCCESS;
        }
        catch(SQLException ex)
        {
         e = ExitCode.SQL_EXCEPTION;
        }
        return e;
       } 
    
    public ExitCode activate_delivery(DeliveryRequest deliveryRequest)
    {
    
        ExitCode e = ExitCode.FAILURE;
        DeliveryRequestGopher g = new DeliveryRequestGopher();
        try
        {
            g.update(deliveryRequest);
            e = ExitCode.SUCCESS;
            
        }
        catch(SQLException ex)
        {
         e = ExitCode.SQL_EXCEPTION;
        }
        return e;
    }
    
    public DeliveryRequest search_delivery(DeliveryRequest deliveryRequest)
    {
        DeliveryRequest dr = null;
        DeliveryRequestGopher g = new DeliveryRequestGopher();
        try
        {
            dr = g.get(deliveryRequest.getDeliveryRequestID());
        }
        catch(SQLException ex)
        {
            // Nothing we can do
        }
        return dr;
    }
    
    public List<DeliveryRequest> list_deliveries(final Status status, final User user) {
        List<DeliveryRequest> deliveries = null;
        UserType type = user.getUserType();
        int userID = user.getUserID();
        DeliveryRequestGopher gopher = new DeliveryRequestGopher();
        
        if (Status.POSTED == status && UserType.COURIER == type) {
            try {
                deliveries = gopher.getList(status.value);
            } catch (SQLException ex) {
                // Nothing we can do
            }
        } else {
            try {
                deliveries = gopher.getListByUserId(status, userID); 
            } catch (SQLException ex) {
                // Nothing we can do
            }
        }
        
        return deliveries;
    }
}
