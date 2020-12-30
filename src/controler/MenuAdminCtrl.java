/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controler;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import model.Sucursal;
import model.sql.SqlSucursal;
import rojerusan.RSPanelsSlider;
import view.LoginView;
import view.MenuAdminView;
import view.NuevaSucursalView;

/**
 *
 * @author ivn
 */
public class MenuAdminCtrl implements ActionListener {

    private final MenuAdminView view;
    private final SqlSucursal sqlsucursal = new SqlSucursal();

    public MenuAdminCtrl(MenuAdminView view) {
        this.view = view;

        //objetos de la vista
        //botones menu
        view.btn_logout.addActionListener(this);
        view.btn_home.addActionListener(this);
        view.btn_empleado.addActionListener(this);
        view.btn_salario.addActionListener(this);
        view.btn_sucursal.addActionListener(this);
        view.btn_ventas.addActionListener(this);
        //botones sucursal
        view.btn_nuevaSucursal.addActionListener(this);
        view.btn_buscarSucursal.addActionListener(this);

        //tablas
        consultarSucursal(tablaSucursal());
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        //mover entre vistas
        moverVistas(e);

        //todos los botones de la vista sucursal
        sucursal(e);

        // boton de logout
        if (e.getSource() == view.btn_logout) {
            //salir de la aplicacion de admin
            int x = JOptionPane.showConfirmDialog(null, "¿Estas seguro que deseas salir?", "ATENCIÓN", JOptionPane.INFORMATION_MESSAGE);
            //Opciones
            if (x == 0) {
                //el usuario quiere salir de la aplicacion
                LoginView loginview = new LoginView();
                LoginCtrl loginctrl = new LoginCtrl(loginview);
                loginview.setVisible(true);
                view.setVisible(false);
                view.dispose();

            }
        }
    }

    private void moverPanel(JButton mainbutton, JButton button1, JButton button2, JButton button3, JButton button4, JPanel panel) {

        if (!mainbutton.isSelected()) {

            mainbutton.setSelected(true);
            button1.setSelected(false);
            button2.setSelected(false);
            button3.setSelected(false);
            button4.setSelected(false);

            view.panelSlider.setPanelSlider(10, panel, RSPanelsSlider.DIRECT.DOWN);
        }
    }

    private void moverVistas(ActionEvent e) {
        // boton home
        if (e.getSource() == view.btn_home) {

            moverPanel(view.btn_home, view.btn_empleado, view.btn_salario, view.btn_sucursal, view.btn_ventas, view.jp_home);

        }

        // boton sucursal
        if (e.getSource() == view.btn_sucursal) {

            moverPanel(view.btn_sucursal, view.btn_empleado, view.btn_salario, view.btn_home, view.btn_ventas, view.jp_sucursal);
        }

        // boton empleado
        if (e.getSource() == view.btn_empleado) {

            moverPanel(view.btn_empleado, view.btn_home, view.btn_salario, view.btn_sucursal, view.btn_ventas, view.jp_empleado);
        }

        // boton ventas
        if (e.getSource() == view.btn_ventas) {

            moverPanel(view.btn_ventas, view.btn_empleado, view.btn_salario, view.btn_sucursal, view.btn_home, view.jp_ventas);
        }

        // boton salario
        if (e.getSource() == view.btn_salario) {

            moverPanel(view.btn_salario, view.btn_empleado, view.btn_home, view.btn_sucursal, view.btn_ventas, view.jp_salario);
        }
    }

    private void sucursal(ActionEvent e) {

        //agregar nueva sucursal
        if (e.getSource() == view.btn_nuevaSucursal) {
            //creacion de objeto vista tipo Nueva Sucursal
            NuevaSucursalView nuevasucursalview = new NuevaSucursalView();
            //se manda el usuario al label de la pantalla 
            nuevasucursalview.lb_user.setText(view.lb_user.getText());
            NuevaSucursalCtrl nuevasucursalctrl = new NuevaSucursalCtrl(nuevasucursalview, this);
            nuevasucursalview.setVisible(true);
        }

        //buscar una sucursal
        if (e.getSource() == view.btn_buscarSucursal) {
            buscarSucursal(tablaSucursal());
        }
    }

    public DefaultTableModel tablaSucursal() {
        //Sucursal
        DefaultTableModel tablasucursal = new DefaultTableModel();
        view.jtable_sucursal.setModel(tablasucursal);
        tablasucursal.addColumn("Id");
        tablasucursal.addColumn("Descripción");
        tablasucursal.addColumn("Dirección");
        tablasucursal.addColumn("Teléfono");
        tablasucursal.addColumn(" ");
        tablasucursal.addColumn(" ");

        //tamaño de las columnas 
        tamanioColumnas(view.jtable_sucursal, 32);
        //tamaño de las filas
        view.jtable_sucursal.setRowHeight(32);

        return tablasucursal;
    }

    public void consultarSucursal(DefaultTableModel table) {
        //lista de objetos sucursal
        ArrayList<Sucursal> sucursalList = new ArrayList<>(); // lista de objetos sucursal
        //se ejecuta el metodo consultar y devuelve una lista de sucursales
        sucursalList = sqlsucursal.consultarSucursal();

        //no hay ninguna sucursal
        if (sucursalList == null) {
            JOptionPane.showMessageDialog(null, "No hay niguna sucursal", "ATENCIÓN", JOptionPane.INFORMATION_MESSAGE);
        } else {
            //iteramos por todos los objetos
            for (Sucursal sucursal : sucursalList) {
                //se crea un arreglo de tipo object en donde se le asigna a cada posicion la informacion de cada sucursal
                Object[] sucursalObject = new Object[6];
                sucursalObject[0] = sucursal.getId_sucursal();
                sucursalObject[1] = sucursal.getDescripcion();
                sucursalObject[2] = sucursal.getDireccion();
                sucursalObject[3] = sucursal.getTelefono();
                sucursalObject[4] = new ImageIcon(getClass().getResource("/icons/edit.png")); //icono actualizar
                sucursalObject[5] = new ImageIcon(getClass().getResource("/icons/delete.png")); //icono eliminar

                //se agrega la fila con la informacion
                table.addRow(sucursalObject);
            }
        }

        //se crean los botones en la tabla
        //boton de actualizar
        ButtonColumn btnUpdate = new ButtonColumn(view.jtable_sucursal, actualizarSucursal(), 4);
        //boton de eliminar
        ButtonColumn btnDelete = new ButtonColumn(view.jtable_sucursal, eliminarSucursal(), 5);

    }

    private Action eliminarSucursal() {
        //se crea la logica para el boton eliminar y se manda el resultado al boton
        Action delete = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //se pregunta si se está seguro de querer eliminar el registro
                int x = JOptionPane.showConfirmDialog(null, "¿Estas seguro que deseas eliminar el registro?", "ATENCIÓN", JOptionPane.INFORMATION_MESSAGE);

                if (x == 0) {
                    // si se quiere eliminar
                    JTable table = (JTable) e.getSource();
                    int row = table.getSelectedRow();
                    //se obtiene el id de la sucursal
                    String idSucursal = (String) table.getModel().getValueAt(row, 0);

                    //se crea un objeto de tipo sucursal
                    Sucursal sucursal = new Sucursal();
                    //se envia el id de la sucursal al POJO
                    sucursal.setId_sucursal(idSucursal);

                    //se ejecuta el método de eliminar
                    if (sqlsucursal.eliminarSucursal(sucursal)) {
                        // se puedo ejecutar el método eliminar
                        //se consulta la tabla para actualizar los resultados
                        consultarSucursal(tablaSucursal());
                        JOptionPane.showMessageDialog(null, "Se eliminó el registro correctamente", "ATENCIÓN", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        //no se pudo ejecutar el método eliminar
                        JOptionPane.showMessageDialog(null, "No se pudo eliminar el registro", "ATENCIÓN", JOptionPane.ERROR_MESSAGE);
                    }
                }

            }
        };
        return delete;
    }

    private Action actualizarSucursal() {
        //se crea la logica para el boton actualizar y se manda el resultado al boton
        Action update = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTable table = (JTable) e.getSource();
                int row = table.getSelectedRow();
                //se obtiene el id de la sucursal
                String idSucursal = (String) table.getModel().getValueAt(row, 0);

                //se crea un objeto de tipo sucursal
                Sucursal sucursal = new Sucursal();
                //se envia el id de la sucursal al POJO
                sucursal.setId_sucursal(idSucursal);

                //se ejecuta el metodo para buscar la informacion del registro seleccionado
                if (sqlsucursal.buscarSucursal(sucursal)) {
                    //se ejecuto el metodo buscar y se tiene la informacion
                    //se hace un objeto de tipo nueva sucursal view
                    NuevaSucursalView editarsucursalview = new NuevaSucursalView();
                    //se hace un objeto de tipo sucursal ctrl con el constructor que tiene un parametro para enviar la informacion recibida
                    NuevaSucursalCtrl editarsucursalctrl = new NuevaSucursalCtrl(editarsucursalview, MenuAdminCtrl.this, sucursal);
                    //se envia el usuario administrador
                    editarsucursalview.lb_user.setText(view.lb_user.getText());
                    editarsucursalview.setVisible(true);
                } else {
                    //no se pudo hacer la consulta
                    JOptionPane.showMessageDialog(null, "Algo falló", "ATENCIÓN", JOptionPane.ERROR_MESSAGE);
                }
            }
        };
        return update;
    }

    private void buscarSucursal(DefaultTableModel table) {
        //el campo txt de buscar esta vacio 
        if (!view.txt_buscarSucursal.getText().isEmpty() && view.txt_buscarSucursal.getText() != null) {
            //el campo tiene informacion
            //creamos un objeto de tipo sucursal y le enviamos 
            Sucursal sucursal = new Sucursal();
            Sucursal nuevaSucursal = new Sucursal();
            sucursal.setId_sucursal(view.txt_buscarSucursal.getText());
            //se ejecuta el metodo de busqueda y se iguala al objeto anteriormente hecho
            nuevaSucursal = sqlsucursal.consultarRegistroSucursal(sucursal);
            //si es null no se encontro ningun registro con ese id
            if (nuevaSucursal != null) {
                //se encontro un registro
                //se crea un arreglo de tipo object en donde se le asigna a cada posicion la informacion de cada sucursal
                Object[] sucursalObject = new Object[6];
                sucursalObject[0] = nuevaSucursal.getId_sucursal();
                sucursalObject[1] = nuevaSucursal.getDescripcion();
                sucursalObject[2] = nuevaSucursal.getDireccion();
                sucursalObject[3] = nuevaSucursal.getTelefono();
                sucursalObject[4] = new ImageIcon(getClass().getResource("/icons/edit.png")); //icono actualizar
                sucursalObject[5] = new ImageIcon(getClass().getResource("/icons/delete.png")); //icono eliminar

                //se agrega la fila con la informacion
                table.addRow(sucursalObject);

                //se crean los botones en la tabla
                //boton de actualizar
                ButtonColumn btnUpdate = new ButtonColumn(view.jtable_sucursal, actualizarSucursal(), 4);
                //boton de eliminar
                ButtonColumn btnDelete = new ButtonColumn(view.jtable_sucursal, eliminarSucursal(), 5);
                view.txt_buscarSucursal.setText("");
            } else {
                //no se encontro el registro
                JOptionPane.showMessageDialog(null, "No se encontró niguna sucursal", "ATENCIÓN", JOptionPane.INFORMATION_MESSAGE);
                //se ejecuta consultar
                consultarSucursal(tablaSucursal());
            }
        } else {
            //el campo esta vacio
            //se ejecuta consultar
            consultarSucursal(tablaSucursal());
            view.txt_buscarSucursal.setText("");
        }
    }

    private TableColumnModel tamanioColumnas(JTable table, int widthButtons) {
        TableColumnModel tablecolumnmodel = table.getColumnModel();
        //cuanto mide la tabla quitandole el width de los botones (siempre son dos)
        int totalWidth = (table.getWidth() - widthButtons * 2);
        //numero de columnas normales, se le quitan las dos de los botones
        int normalColumns = table.getColumnCount() - 2;
        //iteramos por las columnas 
        for (int i = 0; i < table.getColumnCount(); i++) {
            //se calcula el largo de cada columna normal
            tablecolumnmodel.getColumn(i).setPreferredWidth(totalWidth / normalColumns);
            if (i >= table.getColumnCount() - 2) {
                tablecolumnmodel.getColumn(i).setPreferredWidth(widthButtons);
            }
        }

        return tablecolumnmodel;
    }

}
