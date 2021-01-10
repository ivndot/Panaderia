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
import model.ControlHoras;

/**
 *
 * @author ivn
 */
public class SqlControlHoras extends Conexion {

    public ArrayList<ControlHoras> obtenerControlHoras(int mes, int anio) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = getconexion();
        ArrayList<ControlHoras> controlHorasList = new ArrayList<>();
        ControlHoras controlHoras;

        //query
        String sql = "SELECT empleado.id_empleado, empleado.nombre, empleado.ape_pat, empleado.ape_mat, control_horas.horas_normales, control_horas.horas_extra, control_horas.salario\n"
                + "FROM empleado INNER JOIN control_horas ON empleado.id_empleado = control_horas.id_empleado WHERE MONTH(control_horas.fecha) = ? AND YEAR(control_horas.fecha) = ?";

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
                controlHoras = new ControlHoras();
                controlHoras.setId_empleado(rs.getInt("id_empleado"));
                controlHoras.setNombre_empleado(rs.getString("nombre"));
                controlHoras.setApe_pat_empleado(rs.getString("ape_pat"));
                controlHoras.setApe_mat_empleado(rs.getString("ape_mat"));
                controlHoras.setHoras_normales(rs.getInt("horas_normales"));
                controlHoras.setHoras_extra(rs.getInt("horas_extra"));
                controlHoras.setSalario(rs.getFloat("salario"));

                controlHorasList.add(controlHoras);
            }

            return controlHorasList;
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
