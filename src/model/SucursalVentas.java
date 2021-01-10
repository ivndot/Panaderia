/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author ivn
 */
public class SucursalVentas {
    
    private int id_sucursal;
    private String sucursal;
    private double ventas_totales;

    public int getId_sucursal() {
        return id_sucursal;
    }

    public void setId_sucursal(int id_sucursal) {
        this.id_sucursal = id_sucursal;
    }

    public String getSucursal() {
        return sucursal;
    }

    public void setSucursal(String sucursal) {
        this.sucursal = sucursal;
    }

    public double getVentas_totales() {
        return ventas_totales;
    }

    public void setVentas_totales(double ventas_totales) {
        this.ventas_totales = ventas_totales;
    }
    
    
    
}
