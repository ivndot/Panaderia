/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.Connection;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import model.Conexion;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author ivn
 */
public class ReportesVentas extends Conexion {

    public void generarReporteSucursales(int mes, int anio) {

        try {
            Connection con = getconexion();

            JasperReport report = null;
            String path = "src//reports//ReporteSucursales.jasper";

            report = (JasperReport) JRLoader.loadObjectFromFile(path);

            HashMap variables = new HashMap();
            variables.put("mes", mes);
            variables.put("anio", anio);

            JasperPrint print = JasperFillManager.fillReport(report, variables, con);

            JasperViewer view = new JasperViewer(print, false);

            view.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            view.setVisible(true);

        } catch (JRException ex) {
            Logger.getLogger(ReportesVentas.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void generarReporteSucursal(int mes, int anio, int idSucursal) {
        try {
            Connection con = getconexion();

            JasperReport report = null;
            String path = "src//reports//ReporteSucursal.jasper";

            report = (JasperReport) JRLoader.loadObjectFromFile(path);

            HashMap variables = new HashMap();
            variables.put("mes", mes);
            variables.put("anio", anio);
            variables.put("idSucursal", idSucursal);

            JasperPrint print = JasperFillManager.fillReport(report, variables, con);

            JasperViewer view = new JasperViewer(print, false);

            view.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            view.setVisible(true);

        } catch (JRException ex) {
            Logger.getLogger(ReportesVentas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
