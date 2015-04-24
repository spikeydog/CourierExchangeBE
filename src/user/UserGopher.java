/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package user;

import common.user.UserCE;
import common.user.User;
import gopher.AbstractGopher;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
/**
 *
 * @author pooja
 */
public class UserGopher extends AbstractGopher{
 // Names for each database column so thtey only need to be set once
    public final static String TABLE_NAME = "user";
    public final static String USER_ID="user_id";
     public final static String USER_NAME="user_name";
    public final static String PASS="password";
    public final static String USER_TYPE="user_type";
    public final static String ADDR_1="street_address_1";
    public final static String ADDR_2="stree_address_2";
    public final static String CITY="city";
    public final static String STATE="state";
    public final static String COUNTRY="country";
    public final static String ZIP="zip_code";
    public final static String PHNO="phone_no";
    public final static String EMAIL="email_address";
    public final static String NULL = "NULL";
    public final static String DEFAULT = "DEFAULT";
    
    @Override
    protected User parseResult(ResultSet results) throws SQLException {
       User user = new UserCE(); 

        try
        {
            user.setUserID(results.getInt(USER_ID));
            
        }
        catch (SQLException ex) {
            System.out.println("Unable to read from resultSet");
            throw ex;
        }
        return user;
}
    
    public void insert(User user) throws SQLException
    {
         StringBuilder query = new StringBuilder();
        // List of all of the data values to insert
        List<String> values = new LinkedList<>();
        query.append("INSERT INTO ").append(TABLE_NAME).append(" VALUES(");
        values.add(DEFAULT);
        values.add(String.valueOf(user.getUserID()));
        values.add(String.valueOf(user.getUsername()));
        values.add(String.valueOf(user.getPassword()));
        values.add(String.valueOf(user.getUserType()));
        values.add(String.valueOf(user.getStreetAddress1()));
        values.add(String.valueOf(user.getStreetAddress2()));
        values.add(String.valueOf(user.getCity()));
        values.add(String.valueOf(user.getState()));
        values.add("USA");
        values.add(String.valueOf(user.getZipCode()));
        values.add(String.valueOf(user.getPhoneNo()));
        values.add(String.valueOf(user.getEmail()));
        query.append(values.toString().substring(1,values.toString().length() - 1));
        query.append(")");
        
        System.out.println(query.toString());
        super.executeQuery(query.toString());
    }
    
    public User getUserDetails(String uName,String uEmail) throws SQLException {
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + USER_NAME + "="
                + uName + "AND" + EMAIL + "=" +uEmail;
                // The raw list of results
        List<Object> rawList = null;
        // The converted list of results
        List<User> castList = null;
        User request = null;
        rawList = super.executeQuery(query);
        castList = convert(rawList);
       request = (0 < castList.size())? castList.get(0) : null;  
        
        return request;
    }

    private List<User> convert(List<Object> list) {
        List<User> converted = new ArrayList<User>(
                list.size());
        for (Object each : list) {
            if (each instanceof User) {
                converted.add((User) each);
            } 
        }
        
        return converted;
    }
    
    
    public void deleteUser(String uName) throws SQLException {
        String query = "DELETE FROM " + TABLE_NAME + " WHERE " + USER_NAME
                + "=" + uName;
        
        super.executeQuery(query);
    }
    
    public void updateUser(User user) throws SQLException
    {
        StringBuilder query = new StringBuilder();
        List<String> params = new LinkedList<>();
        int UserID = user.getUserID();
        String addr1=user.getStreetAddress1();
        String addr2=user.getStreetAddress2();
        String city=user.getCity();
        String state=user.getState();
        String zip=user.getZipCode();
        String phno=user.getPhoneNo();
        String email = user.getEmail();
        query.append("UPDATE ").append(UserGopher.TABLE_NAME).append(" SET ");
        params.add(UserGopher.ADDR_1 + "=" + addr1);
        params.add(UserGopher.ADDR_2+"="+addr2);
        params.add(UserGopher.CITY+"="+city);
        params.add(UserGopher.STATE+"="+state);
        params.add(UserGopher.ZIP+"="+zip);
        params.add(UserGopher.PHNO+"="+phno);
        params.add(UserGopher.EMAIL+"="+email);
        query.append(params.toString().substring(1,params.toString().length() - 1));
        query.append(" WHERE ").append(UserGopher.USER_ID).append("=").append(UserID);
        super.executeQuery(query.toString());
    }
    }
