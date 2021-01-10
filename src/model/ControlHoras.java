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
public class ControlHoras {
    
    private int id_empleado;
    private String nombre_empleado;
    private String ape_pat_empleado;
    private String ape_mat_empleado;
    private int horas_normales;
    private int horas_extra;
    private float salario;

    public int getId_empleado() {
        return id_empleado;
    }

    public void setId_empleado(int id_empleado) {
        this.id_empleado = id_empleado;
    }

    public String getNombre_empleado() {
        return nombre_empleado;
    }

    public void setNombre_empleado(String nombre_empleado) {
        this.nombre_empleado = nombre_empleado;
    }

    public String getApe_pat_empleado() {
        return ape_pat_empleado;
    }

    public void setApe_pat_empleado(String ape_pat_empleado) {
        this.ape_pat_empleado = ape_pat_empleado;
    }

    public String getApe_mat_empleado() {
        return ape_mat_empleado;
    }

    public void setApe_mat_empleado(String ape_mat_empleado) {
        this.ape_mat_empleado = ape_mat_empleado;
    }

    public int getHoras_normales() {
        return horas_normales;
    }

    public void setHoras_normales(int horas_normales) {
        this.horas_normales = horas_normales;
    }

    public int getHoras_extra() {
        return horas_extra;
    }

    public void setHoras_extra(int horas_extra) {
        this.horas_extra = horas_extra;
    }

    public float getSalario() {
        return salario;
    }

    public void setSalario(float salario) {
        this.salario = salario;
    }
    
    
    
}
