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
public class ItemSucursal {

    private String id_sucursal;
    private String sucursal;

    public ItemSucursal(String id_sucursal, String sucursal) {
        this.id_sucursal = id_sucursal;
        this.sucursal = sucursal;
    }

    public String getId_sucursal() {
        return id_sucursal;
    }

    public String getSucursal() {
        return sucursal;
    }
    
    

}
