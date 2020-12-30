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
import java.util.ArrayList;
import model.Conexion;
import model.Sucursal;

/**
 *
 * @author ivn
 */
public class SqlSucursal extends Conexion {

    public boolean consultarCredencialesSucursal(Sucursal sucursal) {

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
            if (rs.next()) {

                //Mandamos lo resultados al POJO
                sucursal.setUsuario(rs.getString("usuario"));
                sucursal.setContrasenia(rs.getString("contrasenia"));
                sucursal.setDescripcion(rs.getString("descripcion"));

            }

            return true;

        } catch (Exception e) {
            System.out.println("Error: " + e);
            return false;
        } finally {
            try {
                con.close();
                rs.close();
                ps.close();
            } catch (SQLException e) {
                System.out.println(e);
            }
        }
    }

    public boolean insertarSucursal(Sucursal sucursal) {
        PreparedStatement ps = null;
        Connection con = getconexion();

        //query
        String sql = "INSERT INTO sucursal (id_sucursal, descripcion, direccion, telefono, usuario, contrasenia) VALUES (?,?,?,?,?,?)";

        try {
            //Se prepara la sentencia
            ps = con.prepareStatement(sql);
            //Se obtienen los datos desde el POJO
            ps.setString(1, sucursal.getId_sucursal());
            ps.setString(2, sucursal.getDescripcion());
            ps.setString(3, sucursal.getDireccion());
            ps.setString(4, sucursal.getTelefono());
            ps.setString(5, sucursal.getUsuario());
            ps.setString(6, sucursal.getContrasenia());
            //Se ejecuta el query
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.out.println(e);
            return false;
        } finally {
            try {
                con.close();
                ps.close();
            } catch (SQLException e) {
                System.out.println(e);
            }
        }
    }

    public ArrayList<Sucursal> consultarSucursal() {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = getconexion();
        ArrayList<Sucursal> sucursalList = new ArrayList<>(); // lista de objetos sucursal

        //query
        String sql = "SELECT id_sucursal, descripcion, direccion, telefono FROM sucursal";

        try {
            //se prepara la sentencia
            ps = con.prepareStatement(sql);
            //se ejecuta el query
            rs = ps.executeQuery();

            while (rs.next()) {
                //creamos un objeto sucursal
                Sucursal sucursal = new Sucursal();
                //se envian datos al POJO
                sucursal.setId_sucursal(rs.getString("id_sucursal"));
                sucursal.setDescripcion(rs.getString("descripcion"));
                sucursal.setDireccion(rs.getString("direccion"));
                sucursal.setTelefono(rs.getString("telefono"));

                //se guarda el objeto en la lista
                sucursalList.add(sucursal);
            }
            //se regresa la lista de sucursales
            return sucursalList;
            //return true;
        } catch (SQLException e) {
            System.out.println(e);
            //return false;
            return null;
        } finally {
            try {
                con.close();
                rs.close();
                ps.close();
            } catch (SQLException e) {
                System.out.println(e);
            }
        }

    }

    public Sucursal consultarRegistroSucursal(Sucursal sucursal) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = getconexion();
        Sucursal nuevaSucursal= new Sucursal();

        //query
        String sql = "SELECT id_sucursal, descripcion, direccion, telefono FROM sucursal WHERE id_sucursal=?";

        try {
            //se prepara la sentencia
            ps = con.prepareStatement(sql);
            // se envia el id de la sucursal que estamos buscando
            ps.setString(1, sucursal.getId_sucursal());
            //se ejecuta el query
            rs = ps.executeQuery();
            
            if(rs.next()){
                //se encontro un registro
                
                nuevaSucursal.setId_sucursal(rs.getString("id_sucursal"));
                nuevaSucursal.setDescripcion(rs.getString("descripcion"));
                nuevaSucursal.setDireccion(rs.getString("direccion"));
                nuevaSucursal.setTelefono(rs.getString("telefono"));
            }else{
                return null;
            }
            
            return nuevaSucursal;
        } catch (SQLException e) {
            System.out.println(e);
            return null;
        } finally {
            try {
                con.close();
                rs.close();
                ps.close();
            } catch (SQLException e) {
                System.out.println(e);
            }
        }

    }

    public boolean eliminarSucursal(Sucursal sucursal) {
        PreparedStatement ps = null;
        Connection con = getconexion();

        //query
        String sql = "DELETE FROM sucursal where id_sucursal=?";

        try {
            //Se prepara la sentencia
            ps = con.prepareStatement(sql);
            //Se obtiene el id del POJO
            ps.setString(1, sucursal.getId_sucursal());
            //Se ejecuta el query
            ps.execute();

            return true;
        } catch (SQLException e) {
            System.out.println(e);
            return false;
        } finally {
            try {
                con.close();
                ps.close();
            } catch (SQLException e) {
                System.out.println(e);
            }
        }
    }

    public boolean editarSucursal(Sucursal sucursal) {
        PreparedStatement ps = null;
        Connection con = getconexion();

        //query
        String sql = "UPDATE sucursal SET descripcion=?, direccion=?, telefono=?, usuario=?, contrasenia=? WHERE id_sucursal=?";

        try {
            //Se prepara la sentencia
            ps = con.prepareStatement(sql);
            // se obtiene la informacion del POJO y se envia al statment
            ps.setString(1, sucursal.getDescripcion());
            ps.setString(2, sucursal.getDireccion());
            ps.setString(3, sucursal.getTelefono());
            ps.setString(4, sucursal.getUsuario());
            ps.setString(5, sucursal.getContrasenia());
            ps.setString(6, sucursal.getId_sucursal());
            //Se ejecuta el query
            ps.execute();
            return true;
        } catch (Exception e) {
            System.out.println("Error: " + e);
            return false;
        } finally {
            try {
                con.close();
                ps.close();
            } catch (SQLException e) {
                System.out.println(e);
            }
        }
    }

    public boolean buscarSucursal(Sucursal sucursal) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = getconexion();
        //query
        String sql = "SELECT * FROM sucursal where id_sucursal=?";

        try {
            //Se prepara la sentencia
            ps = con.prepareStatement(sql);
            //Se obtiene el id del POJO
            ps.setString(1, sucursal.getId_sucursal());
            //Se ejecuta el query
            rs = ps.executeQuery();

            if (rs.next()) {
                //se obtuvo un registro 
                //se manda la informacion al POJO
                sucursal.setId_sucursal(rs.getString("id_sucursal"));
                sucursal.setDescripcion(rs.getString("descripcion"));
                sucursal.setDireccion(rs.getString("direccion"));
                sucursal.setTelefono(rs.getString("telefono"));
                sucursal.setUsuario(rs.getString("usuario"));
                sucursal.setContrasenia(rs.getString("contrasenia"));
            }
            return true;
        } catch (Exception e) {
            System.out.println("Error: " + e);
            return false;
        } finally {
            try {
                con.close();
                rs.close();
                ps.close();
            } catch (SQLException e) {
                System.out.println(e);
            }
        }
    }
}
