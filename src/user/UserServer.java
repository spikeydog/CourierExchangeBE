/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package user;

import common.user.User;
import common.user.UserCommonServer;
import common.util.code.user.ExitCode;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author pooja
 */
public class UserServer implements  UserCommonServer
{
public ExitCode registerUser(User user) throws RemoteException {
     common.util.code.user.ExitCode code = ExitCode.FAILURE;
     
     UserAgent agent = new UserAgent();
    try {
        code = agent.registerUser(user);
    } catch (Exception ex) {
        Logger.getLogger(UserServer.class.getName()).log(Level.SEVERE, null, ex);
        System.out.println("Error occurred in register user method inside userServer");
    }
        return code;

  
}
public ExitCode updateUser(User user) throws RemoteException
{
     ExitCode code = ExitCode.FAILURE;
     UserAgent agent = new UserAgent();
     try
     {
     code=agent.updateUser(user);
     }catch (Exception ex) {
        Logger.getLogger(UserServer.class.getName()).log(Level.SEVERE, null, ex);
        System.out.println("Error occurred in update user method inside userServer");
    }
     return code;
}


    public ExitCode LoginUser(User user) throws RemoteException {
   ExitCode code = ExitCode.FAILURE;
     UserAgent agent = new UserAgent();
     try
     {
     code=agent.updateUser(user);
     }catch (Exception ex) {
        Logger.getLogger(UserServer.class.getName()).log(Level.SEVERE, null, ex);
        System.out.println("Error occurred in update user method inside userServer");
    }
     return code;
    }
}
