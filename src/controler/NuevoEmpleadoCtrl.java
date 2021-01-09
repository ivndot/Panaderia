/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controler;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;
import javax.swing.plaf.basic.BasicComboBoxRenderer;
import model.Empleado;
import model.ItemSucursal;
import model.sql.SqlEmpleado;
import view.NuevoEmpleadoView;

/**
 *
 * @author ivn
 */
public class NuevoEmpleadoCtrl implements ActionListener {

    private NuevoEmpleadoView view;
    private SqlEmpleado sqlempleado = new SqlEmpleado();
    private MenuAdminCtrl menuadminctrl;
    private Empleado empleado = new Empleado();
    private Empleado editEmpleado;

    //constructor para un nuevo registro
    public NuevoEmpleadoCtrl(NuevoEmpleadoView view, MenuAdminCtrl menuadminctrl) {
        this.view = view;
        this.menuadminctrl = menuadminctrl;

        //se pone el listener al boton de enviar
        view.btn_enviar.addActionListener(this);

        //listener que nada mas cierra la ventana actual
        view.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        llenarComboBoxSucursal();

        //se oculta el txt del id del empleado
        view.txt_idEmpleado.setVisible(false);
    }

    //constructor para editar un registro que ya existe
    public NuevoEmpleadoCtrl(NuevoEmpleadoView view, MenuAdminCtrl menuadminctrl, Empleado editEmpleado) {
        this.view = view;
        this.menuadminctrl = menuadminctrl;
        this.editEmpleado = editEmpleado;

        //se pone el listener al boton de enviar
        view.btn_enviar.addActionListener(this);

        //listener que nada mas cierra la ventana actual
        view.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        llenarComboBoxSucursal();

        //se llenan los campos con la informacion que se obtuvo
        mostrarDatosEmpleado();

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //se pulsa el boton de enviar
        if (e.getSource() == view.btn_enviar) {

            if (verificarTextFields()) {
                //se escoge si se quiere editar o insertar

                // si editSucursal es NULL quiere decir que se quiere insertar
                if (editEmpleado == null) {
                    //se quiere insertar una nueva sucursal
                    insertarEmpleado();
                } else {
                    //se quiere editar una sucursal
                    editarEmpleado();
                }

            } else {
                //campos vacios
                JOptionPane.showMessageDialog(null, "Por favor, llena todos los campos", "ATENCIÓN", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    private boolean verificarTextFields() {
        if (!view.txt_nombreEmpleado.getText().isEmpty() && !view.txt_apePatEmpleado.getText().isEmpty()
                && !view.txt_apeMatEmpleado.getText().isEmpty() && !view.txt_direccionEmpleado.getText().isEmpty() && !view.txt_telefonoEmpleado.getText().isEmpty()) {
            return true;
        } else {
            return false;
        }

    }

    private void insertarEmpleado() {

        //el numero de telefono solo debe contener numeros
        if (telefonoesInt(view.txt_telefonoEmpleado.getText())) {

            //el numero de telefono debe tener 10 caracteres de longitud
            if (view.txt_telefonoEmpleado.getText().length() == 10) {

                //enviamos los datos al POJO del objeto de tipo empleado creado
                empleado.setNombre(view.txt_nombreEmpleado.getText().toUpperCase());
                empleado.setApe_pat(view.txt_apePatEmpleado.getText().toUpperCase());
                empleado.setApe_mat(view.txt_apeMatEmpleado.getText().toUpperCase());
                empleado.setDireccion(view.txt_direccionEmpleado.getText().toUpperCase());
                empleado.setTelefono(view.txt_telefonoEmpleado.getText());
                empleado.setRol((String) view.cbox_rolEmpleado.getSelectedItem());
                //se crea una variable para extraer el id de la sucursal
                ItemSucursal item = (ItemSucursal) view.cbox_sucursalEmpleado.getSelectedItem();
                empleado.setSucursal(item.getId_sucursal());
                //se ejecuta el metodo insertar
                if (sqlempleado.insertarEmpleado(empleado)) {
                    JOptionPane.showMessageDialog(null, "Se registró correctamente", "ATENCIÓN", JOptionPane.INFORMATION_MESSAGE);
                    limpiarTextFields();
                    //se hace la consulta despues de insertar el nuevo registro
                    menuadminctrl.consultarEmpleado(menuadminctrl.tablaEmpleado());
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

    }

    private void editarEmpleado() {

        //el numero de telefono solo debe contener numeros
        if (telefonoesInt(view.txt_telefonoEmpleado.getText())) {

            //el numero de telefono debe tener 10 caracteres de longitud
            if (view.txt_telefonoEmpleado.getText().length() == 10) {

                //enviamos los datos al POJO del objeto de tipo empleado creado
                editEmpleado.setNombre(view.txt_nombreEmpleado.getText().toUpperCase());
                editEmpleado.setApe_pat(view.txt_apePatEmpleado.getText().toUpperCase());
                editEmpleado.setApe_mat(view.txt_apeMatEmpleado.getText().toUpperCase());
                editEmpleado.setDireccion(view.txt_direccionEmpleado.getText().toUpperCase());
                editEmpleado.setTelefono(view.txt_telefonoEmpleado.getText());
                editEmpleado.setRol((String) view.cbox_rolEmpleado.getSelectedItem());
                //se crea una variable para extraer el id de la sucursal
                ItemSucursal item = (ItemSucursal) view.cbox_sucursalEmpleado.getSelectedItem();
                editEmpleado.setSucursal(item.getId_sucursal());
                //se ejecuta el metodo insertar
                if (sqlempleado.editarEmpleado(editEmpleado)) {
                    JOptionPane.showMessageDialog(null, "Se actualizó correctamente la información", "ATENCIÓN", JOptionPane.INFORMATION_MESSAGE);
                    limpiarTextFields();
                    //se hace la consulta despues de editar el nuevo registro
                    menuadminctrl.consultarEmpleado(menuadminctrl.tablaEmpleado());
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

    private void limpiarTextFields() {
        view.txt_idEmpleado.setText("");
        view.txt_nombreEmpleado.setText("");
        view.txt_apePatEmpleado.setText("");
        view.txt_apeMatEmpleado.setText("");
        view.txt_direccionEmpleado.setText("");
        view.txt_telefonoEmpleado.setText("");
    }

    private boolean telefonoesInt(String tel) {
        try {
            BigInteger bigIntegerStr = new BigInteger(tel);
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    private void llenarComboBoxSucursal() {
        //objeto de tipo arraylist 
        ArrayList<ItemSucursal> itemSucursal = new ArrayList<>();
        //se iguala a la lista que regresa el metodo obtener sucursal item
        itemSucursal = sqlempleado.obtenerListaSucursales();
        //objeto de tipo vector
        Vector model = new Vector();

        //iteramos por toda la lista
        for (ItemSucursal item : itemSucursal) {
            //se llena el combo box
            model.addElement(new ItemSucursal(item.getId_sucursal(), item.getSucursal()));
        }
        //se envia el modelo al combo box
        view.cbox_sucursalEmpleado.setModel(new DefaultComboBoxModel(model));
        //se envia un renderer de los datos para que se pueda visualizar el objeto en el combo box
        view.cbox_sucursalEmpleado.setRenderer(new ItemRenderer());

    }

    private void mostrarDatosEmpleado() {
        view.txt_idEmpleado.setText(String.valueOf(editEmpleado.getId_empleado()));
        view.txt_idEmpleado.setEnabled(false);
        view.txt_nombreEmpleado.setText(editEmpleado.getNombre());
        view.txt_apePatEmpleado.setText(editEmpleado.getApe_pat());
        view.txt_apeMatEmpleado.setText(editEmpleado.getApe_mat());
        view.txt_direccionEmpleado.setText(editEmpleado.getDireccion());
        view.txt_telefonoEmpleado.setText(editEmpleado.getTelefono());
        view.cbox_rolEmpleado.setSelectedItem(editEmpleado.getRol());
        view.cbox_sucursalEmpleado.setSelectedItem(editEmpleado.getSucursal());
    }

    // clase que permite hacer un render de los valores key value 
    class ItemRenderer extends BasicComboBoxRenderer {

        public Component getListCellRendererComponent(
                JList list, Object value, int index,
                boolean isSelected, boolean cellHasFocus) {
            super.getListCellRendererComponent(list, value, index,
                    isSelected, cellHasFocus);

            if (value != null) {
                ItemSucursal item = (ItemSucursal) value;
                setText(item.getSucursal().toUpperCase());
            }

            if (index == -1) {
                ItemSucursal item = (ItemSucursal) value;
                setText("" + item.getId_sucursal());
            }

            return this;
        }
    }

}
