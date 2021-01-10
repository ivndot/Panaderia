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
public class ItemMes {
    
    private int id_mes;
    private String mes;

    public ItemMes(int id_mes, String mes) {
        this.id_mes = id_mes;
        this.mes = mes;
    }

    public int getId_mes() {
        return id_mes;
    }

    public void setId_mes(int id_mes) {
        this.id_mes = id_mes;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }
    
    
}
