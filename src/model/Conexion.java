/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ivn
 */
public class Conexion {
    
    private final String base="panaderia_db";
    private final String user="root";
    private final String password="root";
    private final String url="jdbc:mysql://localhost/"+base;
    private java.sql.Connection con= null; 
    
    
    
    public Connection getconexion(){
   
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con= DriverManager.getConnection(url,user,password);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex);
        }
        return con;
   }  
    
}
