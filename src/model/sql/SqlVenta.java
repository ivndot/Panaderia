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
import model.VentaAdmin;

/**
 *
 * @author ivn
 */
public class SqlVenta extends Conexion {

    public ArrayList<VentaAdmin> obtenerVentas(int mes, int anio) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = getconexion();
        ArrayList<VentaAdmin> ventasList = new ArrayList<>();
        VentaAdmin ventaAdmin;

        //query
        String sql = "SELECT sucursal.descripcion, venta.id_venta, venta.id_sucursal, venta.fecha, producto.id_producto, producto.precio, ventas_producto.cantidad \n"
                + "FROM sucursal INNER JOIN venta ON sucursal.id_sucursal = venta.id_sucursal\n"
                + "INNER JOIN ventas_producto ON venta.id_venta = ventas_producto.id_venta \n"
                + "INNER JOIN producto ON ventas_producto.id_producto = producto.id_producto WHERE MONTH(venta.fecha) = ? AND YEAR(venta.fecha) = ?";

        try {
            //se prepara la sentencia
            ps = con.prepareStatement(sql);
            // se envia el mes 
            ps.setInt(1, mes);
            // se envia el año 
            ps.setInt(2, anio);
            //se ejecuta el query
            rs = ps.executeQuery();

            while (rs.next()) {
                //se encontro un registro
                ventaAdmin = new VentaAdmin();
                ventaAdmin.setId_venta(rs.getInt("id_venta"));
                ventaAdmin.setId_sucursal(rs.getInt("id_sucursal"));
                ventaAdmin.setSucursal(rs.getString("descripcion"));
                ventaAdmin.setFecha(rs.getDate("fecha").toLocalDate());
                ventaAdmin.setId_producto(rs.getInt("id_producto"));
                ventaAdmin.setPrecio(rs.getFloat("precio"));
                ventaAdmin.setCantidad(rs.getInt("cantidad"));

                ventasList.add(ventaAdmin);
            }

            return ventasList;
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

}
