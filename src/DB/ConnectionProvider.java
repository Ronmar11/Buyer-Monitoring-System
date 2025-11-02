/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DB;

import java.sql.*;
import javax.swing.JOptionPane;
/**
 *
 * @author Ronmar abalos
 */
public class ConnectionProvider {
    public static Connection getCon(){
    
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localHost:3306//bms?useSSL=false", "root","rrabalos");
            return con;
            
        } catch (Exception e) {
            return null;
        }
    }
    
}
