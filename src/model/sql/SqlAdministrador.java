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
import model.Administrador;
import model.Conexion;

/**
 *
 * @author ivn
 */
public class SqlAdministrador extends Conexion {

    public boolean consultarCredencialesAdministrador(Administrador admin) {

        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = getconexion();

        String sql = "SELECT usuario, contrasenia, nombre FROM administrador where usuario=?";

        try {
            //Se prepara la sentencia
            ps = con.prepareStatement(sql);
            //Se obtiene el usuario que enviamos desde el controler
            ps.setString(1, admin.getUsuario());
            //Se ejecuta el query
            rs = ps.executeQuery();

            //iteramos por los resultados
            if(rs.next()){
                
                //Mandamos lo resultados al POJO
                admin.setUsuario(rs.getString("usuario"));
                admin.setContrasenia(rs.getString("contrasenia"));
                admin.setNombre(rs.getString("nombre"));
              
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
