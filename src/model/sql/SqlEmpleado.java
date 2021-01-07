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
import model.Empleado;
import model.ItemSucursal;

/**
 *
 * @author ivn
 */
public class SqlEmpleado extends Conexion {

    //metodo para llenar el combo box de la vista empleado 
    public ArrayList<ItemSucursal> obtenerListaSucursales() {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = getconexion();
        ArrayList<ItemSucursal> itemSucursalList = new ArrayList<>();

        //query
        String sql = "SELECT id_sucursal, descripcion FROM sucursal";

        try {
            //se prepara la sentencia
            ps = con.prepareStatement(sql);
            //se ejecuta el query
            rs = ps.executeQuery();

            while (rs.next()) {
                //creamos un objeto item sucursal
                //se le envían como parametros el id y la descripcion
                ItemSucursal item = new ItemSucursal(rs.getString("id_sucursal"), rs.getString("descripcion"));

                //se guarda el objeto en la lista
                itemSucursalList.add(item);
            }
            //se regresa la lista de sucursales
            return itemSucursalList;
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

    public boolean insertarEmpleado(Empleado empleado) {
        PreparedStatement ps = null;
        Connection con = getconexion();

        //query
        String sql = "INSERT INTO empleado (id_empleado, nombre, ape_pat, ape_mat, direccion, telefono, rol, id_sucursal) VALUES (?,?,?,?,?,?,?,?)";

        try {
            //Se prepara la sentencia
            ps = con.prepareStatement(sql);
            //Se obtienen los datos desde el POJO
            ps.setString(1, empleado.getId_empleado());
            ps.setString(2, empleado.getNombre());
            ps.setString(3, empleado.getApe_pat());
            ps.setString(4, empleado.getApe_mat());
            ps.setString(5, empleado.getDireccion());
            ps.setString(6, empleado.getTelefono());
            ps.setString(7, empleado.getRol());
            ps.setString(8, empleado.getSucursal());
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

    public ArrayList<Empleado> consultarEmpleado() {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = getconexion();
        ArrayList<Empleado> empleadoList = new ArrayList<>(); // lista de objetos empleado

        //query
        String sql = "SELECT * FROM empleado";

        try {
            //se prepara la sentencia
            ps = con.prepareStatement(sql);
            //se ejecuta el query
            rs = ps.executeQuery();

            while (rs.next()) {
                //creamos un objeto empleado
                Empleado empleado = new Empleado();
                //se envian datos al POJO
                empleado.setId_empleado(rs.getString("id_empleado"));
                empleado.setNombre(rs.getString("nombre"));
                empleado.setApe_pat(rs.getString("ape_pat"));
                empleado.setApe_mat(rs.getString("ape_mat"));
                empleado.setDireccion(rs.getString("direccion"));
                empleado.setTelefono(rs.getString("telefono"));
                empleado.setRol(rs.getString("rol"));
                empleado.setSucursal(rs.getString("id_sucursal"));

                //se guarda el objeto en la lista
                empleadoList.add(empleado);
            }
            //se regresa la lista de sucursales
            return empleadoList;
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

    public boolean eliminarEmpleado(Empleado empleado) {
        PreparedStatement ps = null;
        Connection con = getconexion();

        //query
        String sql = "DELETE FROM empleado where id_empleado=?";

        try {
            //Se prepara la sentencia
            ps = con.prepareStatement(sql);
            //Se obtiene el id del POJO
            ps.setString(1, empleado.getId_empleado());
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

    public Empleado consultarRegistroEmpleado(Empleado empleado) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = getconexion();
        Empleado nuevoEmpleado = new Empleado();

        //query
        String sql = "SELECT * FROM empleado WHERE id_empleado=?";

        try {
            //se prepara la sentencia
            ps = con.prepareStatement(sql);
            // se envia el id del empleado que estamos buscando
            ps.setString(1, empleado.getId_empleado());
            //se ejecuta el query
            rs = ps.executeQuery();

            if (rs.next()) {
                //se encontro un registro
                nuevoEmpleado.setId_empleado(rs.getString("id_empleado"));
                nuevoEmpleado.setNombre(rs.getString("nombre"));
                nuevoEmpleado.setApe_pat(rs.getString("ape_pat"));
                nuevoEmpleado.setApe_mat(rs.getString("ape_mat"));
                nuevoEmpleado.setDireccion(rs.getString("direccion"));
                nuevoEmpleado.setTelefono(rs.getString("telefono"));
                nuevoEmpleado.setRol(rs.getString("rol"));
                nuevoEmpleado.setSucursal(rs.getString("id_sucursal"));
            } else {
                return null;
            }

            return nuevoEmpleado;
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

    public boolean buscarEmpleado(Empleado empleado) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = getconexion();
        //query
        String sql = "SELECT * FROM empleado where id_empleado=?";

        try {
            //Se prepara la sentencia
            ps = con.prepareStatement(sql);
            //Se obtiene el id del POJO
            ps.setString(1, empleado.getId_empleado());
            //Se ejecuta el query
            rs = ps.executeQuery();

            if (rs.next()) {
                //se obtuvo un registro 
                //se manda la informacion al POJO
                empleado.setId_empleado(rs.getString("id_empleado"));
                empleado.setNombre(rs.getString("nombre"));
                empleado.setApe_pat(rs.getString("ape_pat"));
                empleado.setApe_mat(rs.getString("ape_mat"));
                empleado.setDireccion(rs.getString("direccion"));
                empleado.setTelefono(rs.getString("telefono"));
                empleado.setRol(rs.getString("rol"));
                empleado.setSucursal(rs.getString("id_sucursal"));
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
    
    public boolean editarEmpleado(Empleado empleado) {
        PreparedStatement ps = null;
        Connection con = getconexion();

        //query
        String sql = "UPDATE empleado SET nombre=?, ape_pat=?, ape_mat=?, direccion=?, telefono=?, rol=?, id_sucursal=? WHERE id_empleado=?";

        try {
            //Se prepara la sentencia
            ps = con.prepareStatement(sql);
            // se obtiene la informacion del POJO y se envia al statment
            ps.setString(1, empleado.getNombre());
            ps.setString(2, empleado.getApe_pat());
            ps.setString(3, empleado.getApe_mat());
            ps.setString(4, empleado.getDireccion());
            ps.setString(5, empleado.getTelefono());
            ps.setString(6, empleado.getRol());
            ps.setString(7, empleado.getSucursal());
            ps.setString(8, empleado.getId_empleado());
            
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
}
