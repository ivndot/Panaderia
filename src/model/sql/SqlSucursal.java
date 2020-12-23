/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.Conexion;
import model.Sucursal;

/**
 *
 * @author ivn
 */
public class SqlSucursal extends Conexion{
    
    public boolean consultarCredencialesSucursal(Sucursal sucursal){
        
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = getconexion();

        String sql = "SELECT usuario, contrasenia, descripcion FROM sucursal where usuario=?";

        try {
            //Se prepara la sentencia
            ps = con.prepareStatement(sql);
            //Se obtiene el usuario que enviamos desde el controler
            ps.setString(1, sucursal.getUsuario());
            //Se ejecuta el query
            rs = ps.executeQuery();

            //iteramos por los resultados
            if(rs.next()){
                
                //Mandamos lo resultados al POJO
                sucursal.setUsuario(rs.getString("usuario"));
                sucursal.setContrasenia(rs.getString("contrasenia"));
                sucursal.setDescripcion(rs.getString("descripcion"));
              
            }
            
            return true;

        } catch (Exception e) {
            System.out.println("Error: " + e);
            return false;
        }finally{
            try{
                con.close();
                rs.close();
                ps.close();
            }catch(SQLException e){
                System.out.println(e);
            }
        }
    }
    
}
