/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package delivery;

import common.delivery.DeliveryRequest;
import common.delivery.DeliveryRequestCE;
import common.delivery.DeliveryServer;
import common.delivery.Status;
import common.user.User;
import common.util.code.delivery.ExitCode;
import java.rmi.RemoteException;
import java.util.List;

/**
 *
 * @author Akshay
 */
public class DeliveryRequestServer implements DeliveryServer{
    
    public ExitCode createDeliveryRequest(DeliveryRequest deliveryRequest)
        {
            ExitCode code = ExitCode.FAILURE;
         DeliveryManagementAgent agent = new DeliveryManagementAgent();         
         code = agent.insert_delivery(deliveryRequest);
        return code;
    }
    
    public ExitCode edit_delivery(DeliveryRequest deliveryRequest)
            throws RemoteException{
       ExitCode code = ExitCode.FAILURE;
         DeliveryManagementAgent agent = new DeliveryManagementAgent();         
         code = agent.edit_delivery(deliveryRequest);         
        return code;
    }
     public ExitCode delete_delivery(DeliveryRequest deliveryRequest)
            throws RemoteException{         
         ExitCode code = ExitCode.FAILURE;
         DeliveryManagementAgent agent = new DeliveryManagementAgent();         
         code = agent.delete_delivery(deliveryRequest);         
        return code;
    }
      public ExitCode activate_delivery(DeliveryRequest deliveryRequest)
              throws RemoteException{
           DeliveryManagementAgent agent = new DeliveryManagementAgent(); 
           DeliveryRequest dr = new DeliveryRequestCE();
           dr.setDeliveryRequestID(deliveryRequest.getDeliveryRequestID());
           dr.setStatus(Status.POSTED);
           ExitCode code = agent.activate_delivery(deliveryRequest);
           return code;
      }
      
       public DeliveryRequest search_delivery(DeliveryRequest deliveryRequest)
                   throws RemoteException{
           DeliveryManagementAgent agent = new DeliveryManagementAgent();
           DeliveryRequest sd = agent.search_delivery(deliveryRequest);
           return(sd);
      }
        public List<DeliveryRequest> list_deliveries(final Status status, final User user)
        {
            DeliveryManagementAgent agent = new DeliveryManagementAgent();
           List<DeliveryRequest> list = agent.list_deliveries(status, user);
            return(list);
            
        }
      
}

