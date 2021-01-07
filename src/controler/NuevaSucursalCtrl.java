/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controler;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.math.BigInteger;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;
import model.Sucursal;
import model.sql.SqlSucursal;
import view.NuevaSucursalView;

/**
 *
 * @author ivn
 */
public class NuevaSucursalCtrl implements ActionListener {

    private NuevaSucursalView view;
    private SqlSucursal sqlsucursal = new SqlSucursal();
    private Sucursal sucursal = new Sucursal();
    private Sucursal editSucursal;
    private MenuAdminCtrl menuadminctrl;

    //constructor para insertar un nuevo registro
    public NuevaSucursalCtrl(NuevaSucursalView view, MenuAdminCtrl menuadminctrl) {
        this.view = view;
        this.menuadminctrl = menuadminctrl;

        //objetos de la vista
        view.btn_aceptarSucursal.addActionListener(this);

        //listener que nada mas cierra la ventana actual
        view.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }

    //constructor para editar o actualizar un registro existente
    public NuevaSucursalCtrl(NuevaSucursalView view, MenuAdminCtrl menuadminctrl, Sucursal editSucursal) {
        this.view = view;
        this.menuadminctrl = menuadminctrl;
        this.editSucursal = editSucursal;

        //se muestran los datos contenidos en el objeto sucursal
        mostrarDatosSucursal();

        //objetos de la vista
        view.btn_aceptarSucursal.addActionListener(this);

        //listener que nada mas cierra la ventana actual
        view.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == view.btn_aceptarSucursal) {
            if (verificarTextFields()) {
                //se escoge si se quiere editar o insertar

                // si editSucursal es NULL quiere decir que se quiere insertar
                if (editSucursal == null) {
                    //se quiere insertar una nueva sucursal
                    insertarSucursal();
                } else {
                    //se quiere editar una sucursal
                    editarSucursal();
                }

            } else {
                //campos vacios
                JOptionPane.showMessageDialog(null, "Por favor, llena todos los campos", "ATENCIÓN", JOptionPane.INFORMATION_MESSAGE);
            }

        }
    }

    private boolean verificarTextFields() {
        if (!view.txt_idSucursal.getText().isEmpty() && !view.txt_descripcionSucursal.getText().isEmpty() && !view.txt_direccionSucursal.getText().isEmpty()
                && !view.txt_telefonoSucursal.getText().isEmpty() && !view.txt_usuarioSucursal.getText().isEmpty() && !view.txt_contraseniaSucursal.getText().isEmpty()) {
            return true;
        } else {
            return false;
        }

    }

    private void limpiarTextFields() {
        view.txt_idSucursal.setText("");
        view.txt_descripcionSucursal.setText("");
        view.txt_direccionSucursal.setText("");
        view.txt_telefonoSucursal.setText("");
        view.txt_usuarioSucursal.setText("");
        view.txt_contraseniaSucursal.setText("");
    }

    private void insertarSucursal() {
        //id debe tener 4 caracteres de longitud
        if (view.txt_idSucursal.getText().length() == 4) {

            //el numero de telefono solo debe contener numeros
            if (telefonoesInt(view.txt_telefonoSucursal.getText())) {

                //el numero de telefono debe tener 10 caracteres de longitud
                if (view.txt_telefonoSucursal.getText().length() == 10) {

                    //enviamos los datos al POJO del objeto de tipo sucursal creado
                    sucursal.setId_sucursal(view.txt_idSucursal.getText());
                    sucursal.setDescripcion(view.txt_descripcionSucursal.getText().toUpperCase());
                    sucursal.setDireccion(view.txt_direccionSucursal.getText().toUpperCase());
                    sucursal.setTelefono(view.txt_telefonoSucursal.getText());
                    sucursal.setUsuario(view.txt_usuarioSucursal.getText());
                    sucursal.setContrasenia(view.txt_contraseniaSucursal.getText());

                    //se ejecuta el metodo insertar
                    if (sqlsucursal.insertarSucursal(sucursal)) {
                        JOptionPane.showMessageDialog(null, "Se registró correctamente", "ATENCIÓN", JOptionPane.INFORMATION_MESSAGE);
                        limpiarTextFields();
                        //se hace la consulta despues de insertar el nuevo registro
                        menuadminctrl.consultarSucursal(menuadminctrl.tablaSucursal());
                        view.setVisible(false);
                        view.dispose();
                    } else {
                        JOptionPane.showMessageDialog(null, "Hubo un error en el registro, intentalo más tarde", "ATENCIÓN", JOptionPane.ERROR_MESSAGE);
                    }

                } else {
                    JOptionPane.showMessageDialog(null, "El número de teléfono debe ser de 10 caracteres", "ATENCIÓN", JOptionPane.INFORMATION_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "El número de telefono contiene caracteres que no son permitidos", "ATENCIÓN", JOptionPane.INFORMATION_MESSAGE);
            }

        } else {
            JOptionPane.showMessageDialog(null, "El id de la sucursal debe tener 4 caracteres", "ATENCIÓN", JOptionPane.INFORMATION_MESSAGE);
        }

    }

    private void editarSucursal() {

        //el numero de telefono solo debe contener numeros
        if (telefonoesInt(view.txt_telefonoSucursal.getText())) {

            //el numero de telefono debe tener 10 caracteres de longitud
            if (view.txt_telefonoSucursal.getText().length() == 10) {

                //enviamos los datos al POJO del objeto de tipo sucursal creado
                sucursal.setId_sucursal(view.txt_idSucursal.getText());
                sucursal.setDescripcion(view.txt_descripcionSucursal.getText().toUpperCase());
                sucursal.setDireccion(view.txt_direccionSucursal.getText().toUpperCase());
                sucursal.setTelefono(view.txt_telefonoSucursal.getText());
                sucursal.setUsuario(view.txt_usuarioSucursal.getText());
                sucursal.setContrasenia(view.txt_contraseniaSucursal.getText());

                //se ejecuta el metodo editar
                if (sqlsucursal.editarSucursal(sucursal)) {
                    JOptionPane.showMessageDialog(null, "Se actualizó correctamente la información", "ATENCIÓN", JOptionPane.INFORMATION_MESSAGE);
                    limpiarTextFields();
                    //se hace la consulta despues de editar el registro
                    menuadminctrl.consultarSucursal(menuadminctrl.tablaSucursal());
                    view.setVisible(false);
                    view.dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Hubo un error en la actualización, intentalo más tarde", "ATENCIÓN", JOptionPane.ERROR_MESSAGE);
                }

            } else {
                JOptionPane.showMessageDialog(null, "El número de teléfono debe ser de 10 caracteres", "ATENCIÓN", JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "El número de telefono contiene caracteres que no son permitidos", "ATENCIÓN", JOptionPane.INFORMATION_MESSAGE);
        }

    }

    private void mostrarDatosSucursal() {
        //ponemos en disabled el txt del id ya que no se puede editar
        view.txt_idSucursal.setEnabled(false);

        //se vomitan los datos contenidos en editSucursal
        view.txt_idSucursal.setText(editSucursal.getId_sucursal());
        view.txt_descripcionSucursal.setText(editSucursal.getDescripcion());
        view.txt_direccionSucursal.setText(editSucursal.getDireccion());
        view.txt_telefonoSucursal.setText(editSucursal.getTelefono());
        view.txt_usuarioSucursal.setText(editSucursal.getUsuario());
        view.txt_contraseniaSucursal.setText(editSucursal.getContrasenia());

    }

    private boolean telefonoesInt(String tel) {
        try {
            BigInteger bigIntegerStr=new BigInteger(tel);
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

}
