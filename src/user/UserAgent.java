/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package user;

import common.user.User;
import common.util.code.user.ExitCode;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author pooja
 */
public class UserAgent {
    public ExitCode registerUser(User user) throws SQLException 
    {
        ExitCode code = null;
        UserGopher ug=new UserGopher();
        
        if(isValidInsert(user))
        {
            try
            {
                ug.insert(user);
                 code = ExitCode.SUCCESS;
            }catch (SQLException ex) {
                System.out.println("Unable to insert new user");
                code = ExitCode.SQL_EXCEPTION;
                }
        }
        else
        {
            code=ExitCode.FAILURE;
        }
         System.out.println("UserAgent: returns code " + code.toString());
        return code;
    }
    
    public ExitCode updateUser(final User user)
    {
          ExitCode code = null;
          UserGopher ug = new UserGopher();
          try {
                  ug.updateUser(user);
                    code = ExitCode.SUCCESS;
                } catch (SQLException ex) {
                    code = ExitCode.SQL_EXCEPTION;
                }
           return code;  
    }
    public User LoginUser(final User user)
    {
          UserGopher ug = new UserGopher();
          User authUser = null;
          Iterator<User> finder = null;
          try
          {
      
             authUser = ug.getUserDetails(user.getUsername(),user.getEmail());
           
             
          }
          catch(Exception e)
          {
              System.out.println(e.getStackTrace());
          }
               
        return authUser;
    }
    
    
    
    
    //Helper Method
    private boolean isValidInsert(final User user) throws SQLException
    {
         // Flag indicating the given user is valid for an insert op
        boolean isValid = false;
       // Flag indicating the user contains all needed data
        boolean isPopulated = false;
             
        if (null != user 
                && null != user.getEmail()
                && null != user.getUsername()
                && null != user.getPassword()
           ) {
            isPopulated = true;
        }
        System.out.println("isPopulated"+isPopulated);
          if (isPopulated) {
                User uDets = null;
               
                uDets=chkUserTab(user);
                if(null != uDets)
                {
                    isValid=true;
                }
    }
          System.out.println("---"+isValid);
          return isValid;
    }
    //check user table if same username/email already exists
    public User chkUserTab(User user) throws SQLException
    {
        User uDets = null;
        UserGopher ug = new UserGopher();
        System.out.println("UserAgent: is called");
        try
        {
         uDets= ug.getUserDetails(user.getUsername(),user.getEmail());
        } catch (SQLException ex) {
            System.out.println("Unable to get the uDets list");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        System.out.println("Matching user" + user.toString());
        
        return uDets;
    }
}
