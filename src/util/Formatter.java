/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.sql.Timestamp;

/**
 *
 * @author sedog
 */
public class Formatter {
    public static String quote(String string) {
        return "'" + string + "'";
    }
    
    public static String formatTime(Timestamp time) {
        String string = time.toString();
        String formatted = string.substring(0,string
                .lastIndexOf('.',string.length() - 1));
        
        System.out.println(formatted);
        
        return "'" + formatted + "'";
    }
}
