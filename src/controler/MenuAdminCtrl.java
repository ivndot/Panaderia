/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controler;

import java.awt.Component;
import model.ButtonColumn;
import model.ReportesVentas;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.plaf.basic.BasicComboBoxRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import model.ControlHoras;
import model.Empleado;
import model.ItemMes;
import model.Sucursal;
import model.SucursalVentas;
import model.VentaAdmin;
import model.sql.SqlControlHoras;
import model.sql.SqlEmpleado;
import model.sql.SqlSucursal;
import model.sql.SqlVenta;
import rojerusan.RSPanelsSlider;
import view.LoginView;
import view.MenuAdminView;
import view.NuevaSucursalView;
import view.NuevoEmpleadoView;

/**
 *
 * @author ivn
 */
public class MenuAdminCtrl implements ActionListener {

    private final MenuAdminView view;
    private final SqlSucursal sqlsucursal = new SqlSucursal();
    private final SqlEmpleado sqlempleado = new SqlEmpleado();

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
        //botones empleado
        view.btn_nuevoEmpleado.addActionListener(this);
        view.btn_buscarEmpleado.addActionListener(this);
        //boton ventas
        view.btn_generarReporteMensual.addActionListener(this);
        //combo box ventas
        view.cbox_anio.addActionListener(this);
        view.cbox_mes.addActionListener(this);
        //combo box salario
        view.cbox_mesSalario.addActionListener(this);
        view.cbox_anioSalario.addActionListener(this);

        //combobox mes ventas
        comboBoxMes(view.cbox_mes);
        //combobox mes salario
        comboBoxMes(view.cbox_mesSalario);

        //tablas
        //sucursal
        consultarSucursal(tablaSucursal());
        //empleado
        consultarEmpleado(tablaEmpleado());
        //ventas
        consultarVentasSucursales(tablaVentas(), generarVentasSucursales(sumarVentasSucursal(getMes(view.cbox_mes), getAnio(view.cbox_anio))));
        //salario
        consultarSalarioEmpleado(tablaSalario(), generarSalarioEmpleado(sumarSalarioEmpleado(getMes(view.cbox_mesSalario), getAnio(view.cbox_anioSalario))));
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        //mover entre vistas
        moverVistas(e);

        //todos los botones de la vista sucursal
        sucursal(e);

        //todos los botones de la vista empleado
        empleado(e);

        //todos los botones de la vista ventas
        ventas(e);

        //todos los botones de la vista salario
        salario(e);

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
                    int idSucursal = (int) table.getModel().getValueAt(row, 0);

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
                int idSucursal = (int) table.getModel().getValueAt(row, 0);

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
            sucursal.setId_sucursal(Integer.parseInt(view.txt_buscarSucursal.getText()));
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

    private void empleado(ActionEvent e) {

        //agregar nuevo empleado
        if (e.getSource() == view.btn_nuevoEmpleado) {
            //se crea un objeto view de la vista empleado
            NuevoEmpleadoView empleadoview = new NuevoEmpleadoView();
            //se crea un objeto controlador de la vista empleado
            NuevoEmpleadoCtrl empleadoctrl = new NuevoEmpleadoCtrl(empleadoview, MenuAdminCtrl.this);
            //se envia el nombre del admin a la otra vista
            empleadoview.lb_user.setText(view.lb_user.getText());
            empleadoview.setVisible(true);
        }

        //buscar un empleado
        if (e.getSource() == view.btn_buscarEmpleado) {
            buscarEmpleado(tablaEmpleado());
        }

    }

    public DefaultTableModel tablaEmpleado() {
        //Empleado
        DefaultTableModel tablaempleado = new DefaultTableModel();
        view.jtable_empleado.setModel(tablaempleado);
        tablaempleado.addColumn("Id");
        tablaempleado.addColumn("Nombre");
        tablaempleado.addColumn("Apellido Paterno");
        tablaempleado.addColumn("Apellido Materno");
        tablaempleado.addColumn("Dirección");
        tablaempleado.addColumn("Teléfono");
        tablaempleado.addColumn("Rol");
        tablaempleado.addColumn("Sucursal");
        tablaempleado.addColumn(" ");
        tablaempleado.addColumn(" ");

        //tamaño de las columnas 
        tamanioColumnas(view.jtable_empleado, 32);
        //tamaño de las filas
        view.jtable_empleado.setRowHeight(32);

        return tablaempleado;
    }

    public void consultarEmpleado(DefaultTableModel table) {
        //lista de objetos empleados
        ArrayList<Empleado> empleadoList = new ArrayList<>(); // lista de objetos empleados
        //se ejecuta el metodo consultar y devuelve una lista de empleados
        empleadoList = sqlempleado.consultarEmpleado();

        //no hay ningun empleado
        if (empleadoList == null) {
            JOptionPane.showMessageDialog(null, "No hay nigun empleado", "ATENCIÓN", JOptionPane.INFORMATION_MESSAGE);
        } else {
            //iteramos por todos los objetos
            for (Empleado empleado : empleadoList) {
                //se crea un arreglo de tipo object en donde se le asigna a cada posicion la informacion de cada sucursal
                Object[] empleadoObject = new Object[10];
                empleadoObject[0] = empleado.getId_empleado();
                empleadoObject[1] = empleado.getNombre();
                empleadoObject[2] = empleado.getApe_pat();
                empleadoObject[3] = empleado.getApe_mat();
                empleadoObject[4] = empleado.getDireccion();
                empleadoObject[5] = empleado.getTelefono();
                empleadoObject[6] = empleado.getRol();
                empleadoObject[7] = empleado.getSucursal();

                empleadoObject[8] = new ImageIcon(getClass().getResource("/icons/edit.png")); //icono actualizar
                empleadoObject[9] = new ImageIcon(getClass().getResource("/icons/delete.png")); //icono eliminar

                //se agrega la fila con la informacion
                table.addRow(empleadoObject);
            }
        }

        //se crean los botones en la tabla
        //boton de actualizar
        ButtonColumn btnUpdate = new ButtonColumn(view.jtable_empleado, actualizarEmpleado(), 8);
        //boton de eliminar
        ButtonColumn btnDelete = new ButtonColumn(view.jtable_empleado, eliminarEmpleado(), 9);

    }

    private Action eliminarEmpleado() {
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
                    //se obtiene el id del empleado
                    int idEmpleado = (int) table.getModel().getValueAt(row, 0);

                    //se crea un objeto de tipo Empleado
                    Empleado empleado = new Empleado();
                    //se envia el id del empleado al POJO
                    empleado.setId_empleado(idEmpleado);

                    //se ejecuta el método de eliminar
                    if (sqlempleado.eliminarEmpleado(empleado)) {
                        // se puedo ejecutar el método eliminar
                        //se consulta la tabla para actualizar los resultados
                        consultarEmpleado(tablaEmpleado());
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

    private Action actualizarEmpleado() {
        //se crea la logica para el boton actualizar y se manda el resultado al boton
        Action update = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTable table = (JTable) e.getSource();
                int row = table.getSelectedRow();
                //se obtiene el id del empleado
                int idEmpleado = (int) table.getModel().getValueAt(row, 0);

                //se crea un objeto de tipo empleado
                Empleado empleado = new Empleado();
                //se envia el id del empleado al POJO
                empleado.setId_empleado(idEmpleado);

                //se ejecuta el metodo para buscar la informacion del registro seleccionado
                if (sqlempleado.buscarEmpleado(empleado)) {
                    //se ejecuto el metodo buscar y se tiene la informacion
                    //se hace un objeto de tipo nuevo empleado view
                    NuevoEmpleadoView editarempleadoview = new NuevoEmpleadoView();
                    //se hace un objeto de tipo empleado ctrl con el constructor que tiene un parametro para enviar la informacion recibida
                    NuevoEmpleadoCtrl editarsucursalctrl = new NuevoEmpleadoCtrl(editarempleadoview, MenuAdminCtrl.this, empleado);
                    //se envia el usuario administrador
                    editarempleadoview.lb_user.setText(view.lb_user.getText());
                    editarempleadoview.setVisible(true);
                } else {
                    //no se pudo hacer la consulta
                    JOptionPane.showMessageDialog(null, "Algo falló", "ATENCIÓN", JOptionPane.ERROR_MESSAGE);
                }
            }
        };
        return update;
    }

    private void buscarEmpleado(DefaultTableModel table) {
        //el campo txt de buscar esta vacio 
        if (!view.txt_buscarEmpleado.getText().isEmpty() && view.txt_buscarEmpleado.getText() != null) {
            //el campo tiene informacion
            //creamos un objeto de tipo empleado y le enviamos 
            Empleado empleado = new Empleado();
            Empleado nuevoEmpleado = new Empleado();
            empleado.setId_empleado(Integer.parseInt(view.txt_buscarEmpleado.getText()));
            //se ejecuta el metodo de busqueda y se iguala al objeto anteriormente hecho
            nuevoEmpleado = sqlempleado.consultarRegistroEmpleado(empleado);
            //si es null no se encontro ningun registro con ese id
            if (nuevoEmpleado != null) {
                //se encontro un registro
                Object[] empleadoObject = new Object[10];
                empleadoObject[0] = nuevoEmpleado.getId_empleado();
                empleadoObject[1] = nuevoEmpleado.getNombre();
                empleadoObject[2] = nuevoEmpleado.getApe_pat();
                empleadoObject[3] = nuevoEmpleado.getApe_mat();
                empleadoObject[4] = nuevoEmpleado.getDireccion();
                empleadoObject[5] = nuevoEmpleado.getTelefono();
                empleadoObject[6] = nuevoEmpleado.getRol();
                empleadoObject[7] = nuevoEmpleado.getSucursal();

                empleadoObject[8] = new ImageIcon(getClass().getResource("/icons/edit.png")); //icono actualizar
                empleadoObject[9] = new ImageIcon(getClass().getResource("/icons/delete.png")); //icono eliminar

                //se agrega la fila con la informacion
                table.addRow(empleadoObject);

                //se crean los botones en la tabla
                //boton de actualizar
                ButtonColumn btnUpdate = new ButtonColumn(view.jtable_empleado, actualizarEmpleado(), 8);
                //boton de eliminar
                ButtonColumn btnDelete = new ButtonColumn(view.jtable_empleado, eliminarEmpleado(), 9);
                view.txt_buscarSucursal.setText("");
            } else {
                //no se encontro el registro
                JOptionPane.showMessageDialog(null, "No se encontró nigun empleado", "ATENCIÓN", JOptionPane.INFORMATION_MESSAGE);
                //se ejecuta consultar
                consultarEmpleado(tablaEmpleado());
            }
        } else {
            //el campo esta vacio
            //se ejecuta consultar
            consultarEmpleado(tablaEmpleado());
            view.txt_buscarEmpleado.setText("");
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

    private void ventas(ActionEvent e) {

        //generar reporte de todas las sucursales
        if (e.getSource() == view.btn_generarReporteMensual) {
            ReportesVentas reporteSucursal = new ReportesVentas();
            reporteSucursal.generarReporteSucursales(getMes(view.cbox_mes), getAnio(view.cbox_anio));
        }

        //cbox año y mes
        if (e.getSource() == view.cbox_anio || e.getSource() == view.cbox_mes) {
            //consulta de las ventas
            consultarVentasSucursales(tablaVentas(), generarVentasSucursales(sumarVentasSucursal(getMes(view.cbox_mes), getAnio(view.cbox_anio))));
            //System.out.println("entro");
        }
    }

    public DefaultTableModel tablaVentas() {
        //Ventas
        DefaultTableModel tablaventas = new DefaultTableModel();
        view.jtable_ventas.setModel(tablaventas);
        tablaventas.addColumn("Id sucursal");
        tablaventas.addColumn("Ventas totales");
        tablaventas.addColumn(" ");

        //tamaño de las columnas 
        //tamanioColumnas(view.jtable_ventas, 32);
        //tamaño de las filas
        view.jtable_ventas.setRowHeight(32);

        return tablaventas;
    }

    private HashMap<Integer, Float> sumarVentasSucursal(int mes, int anio) {
        SqlVenta sqlventa = new SqlVenta();
        ArrayList<VentaAdmin> ventas = sqlventa.obtenerVentas(mes, anio);
        //id sucursal | ventas totales
        HashMap<Integer, Float> hashMap = new HashMap<>();

        //iteracion por el resulset de todas las ventas que se hicieron en el año y mes indicados
        for (VentaAdmin venta : ventas) {

            //si el hashmap contiene el id de la sucursal
            if (hashMap.containsKey(venta.getId_sucursal())) {
                //ya se tenia la sucursal registrada
                float total = venta.getPrecio() * venta.getCantidad() + hashMap.get(venta.getId_sucursal());
                //se suma al total el resultado de la venta al valor del hashmap
                hashMap.replace(venta.getId_sucursal(), total);
            } else {
                //es la primera vez que se encuentra con esa sucursal
                hashMap.put(venta.getId_sucursal(), venta.getPrecio() * venta.getCantidad());
            }

        }

        return hashMap;

    }

    private ArrayList<SucursalVentas> generarVentasSucursales(HashMap<Integer, Float> hashMap) {
        //se crea el objeto de tipo VentaAdmin, que es el que se va a mostrar en la tabla
        SucursalVentas sucursalVentas = null;
        ArrayList<SucursalVentas> ventasList = new ArrayList<>();
        for (int id_sucursal : hashMap.keySet()) {
            sucursalVentas = new SucursalVentas();
            sucursalVentas.setId_sucursal(id_sucursal);
            sucursalVentas.setVentas_totales(hashMap.get(id_sucursal));

            ventasList.add(sucursalVentas);
        }

        return ventasList;
    }

    private void consultarVentasSucursales(DefaultTableModel table, ArrayList<SucursalVentas> ventas) {
        //lista de objetos empleados
        ArrayList<SucursalVentas> sucursalVentasList = ventas;

        //no hay ningun empleado
        if (sucursalVentasList == null) {
            JOptionPane.showMessageDialog(null, "Aún no hay ventas", "ATENCIÓN", JOptionPane.INFORMATION_MESSAGE);
        } else {
            //iteramos por todos los objetos
            for (SucursalVentas sucursalVentas : sucursalVentasList) {
                //se crea un arreglo de tipo object en donde se le asigna a cada posicion la informacion de cada sucursal ventas
                Object[] sucursalVentasObject = new Object[3];
                sucursalVentasObject[0] = sucursalVentas.getId_sucursal();
                sucursalVentasObject[1] = sucursalVentas.getVentas_totales();

                sucursalVentasObject[2] = new String("Generar Reporte");

                //se agrega la fila con la informacion
                table.addRow(sucursalVentasObject);
            }
        }

        //se crean los botones en la tabla
        //boton de actualizar
        ButtonColumn btnGenerarReporte = new ButtonColumn(view.jtable_ventas, generarReporteSucursal(), 2);
    }

    private Action generarReporteSucursal() {
        Action reporte = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ReportesVentas reporte = new ReportesVentas();

                JTable table = (JTable) e.getSource();
                int row = table.getSelectedRow();
                //se obtiene el id de la sucursal
                int idSucursal = (int) table.getModel().getValueAt(row, 0);

                reporte.generarReporteSucursal(getMes(view.cbox_mes), getAnio(view.cbox_anio), idSucursal);
            }
        };
        return reporte;
    }

    private void comboBoxMes(JComboBox cbox) {
        Vector model = new Vector();
        String meses[] = {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};
        for (int i = 0; i < 12; i++) {
            model.addElement(new ItemMes(i + 1, meses[i]));
        }
        cbox.setModel(new DefaultComboBoxModel(model));
        cbox.setRenderer(new ItemRenderer());
        cbox.setSelectedItem("Enero");
    }

    private int getAnio(JComboBox cbox) {
        return Integer.parseInt(cbox.getSelectedItem().toString());
    }

    private int getMes(JComboBox cbox) {
        ItemMes mes = (ItemMes) cbox.getSelectedItem();
        return mes.getId_mes();
    }

    private DefaultTableModel tablaSalario() {
        //Salario
        DefaultTableModel tablasalario = new DefaultTableModel();
        view.jtable_salario.setModel(tablasalario);
        tablasalario.addColumn("Id Empleado");
        //tablasalario.addColumn("Empleado");
        //tablasalario.addColumn("Horas normales");
        //tablasalario.addColumn("Horas extra");
        tablasalario.addColumn("Total pago");

        //tamaño de las columnas 
        //tamanioColumnas(view.jtable_ventas, 32);
        //tamaño de las filas
        view.jtable_ventas.setRowHeight(32);

        return tablasalario;
    }

    private void salario(ActionEvent e) {
        //combo box mes del salario
        if (e.getSource() == view.cbox_mesSalario || e.getSource() == view.cbox_anioSalario) {
            //salario
            consultarSalarioEmpleado(tablaSalario(), generarSalarioEmpleado(sumarSalarioEmpleado(getMes(view.cbox_mesSalario), getAnio(view.cbox_anioSalario))));
        }
    }

    private HashMap<Integer, Float> sumarSalarioEmpleado(int mes, int anio) {
        SqlControlHoras sqlcontrolhoras = new SqlControlHoras();
        ArrayList<ControlHoras> salario = sqlcontrolhoras.obtenerControlHoras(mes, anio);
        //id empleado | salario total
        HashMap<Integer, Float> hashMap = new HashMap<>();

        //iteracion por el resulset de todas las ventas que se hicieron en el año y mes indicados
        for (ControlHoras controlHoras : salario) {

            //si el hashmap contiene el id del empleado
            if (hashMap.containsKey(controlHoras.getId_empleado())) {
                //ya se tenia al empleado registrado en el hashmap
                float salario_total = controlHoras.getSalario() + hashMap.get(controlHoras.getId_empleado());
                //se suma al total el resultado de la venta al valor del hashmap
                hashMap.replace(controlHoras.getId_empleado(), salario_total);
            } else {
                //es la primera vez que se encuentra con ese empleado
                hashMap.put(controlHoras.getId_empleado(), controlHoras.getSalario());
            }

        }

        return hashMap;
    }

    private ArrayList<ControlHoras> generarSalarioEmpleado(HashMap<Integer, Float> hashMap) {
        //se crea el objeto de tipo VentaAdmin, que es el que se va a mostrar en la tabla
        ControlHoras controlHoras = null;
        ArrayList<ControlHoras> controlHorasList = new ArrayList<>();
        for (int id_empleado : hashMap.keySet()) {
            controlHoras = new ControlHoras();
            controlHoras.setId_empleado(id_empleado);
            controlHoras.setSalario(hashMap.get(id_empleado));

            controlHorasList.add(controlHoras);
        }

        return controlHorasList;
    }

    private void consultarSalarioEmpleado(DefaultTableModel table, ArrayList<ControlHoras> controlHoras) {
        //lista de objetos empleados
        ArrayList<ControlHoras> controlHorasList = controlHoras;

        //no hay ningun empleado
        if (controlHoras == null) {
            JOptionPane.showMessageDialog(null, "Aún no hay salarios", "ATENCIÓN", JOptionPane.INFORMATION_MESSAGE);
        } else {
            //iteramos por todos los objetos
            for (ControlHoras controlhoras : controlHorasList) {
                //se crea un arreglo de tipo object en donde se le asigna a cada posicion la informacion de cada empleado
                Object[] controlHorasObject = new Object[2];
                controlHorasObject[0] = controlhoras.getId_empleado();
                controlHorasObject[1] = controlhoras.getSalario();

                //se agrega la fila con la informacion
                table.addRow(controlHorasObject);
            }
        }
    }

    // clase que permite hacer un render de los valores key value 
    class ItemRenderer extends BasicComboBoxRenderer {

        public Component getListCellRendererComponent(
                JList list, Object value, int index,
                boolean isSelected, boolean cellHasFocus) {
            super.getListCellRendererComponent(list, value, index,
                    isSelected, cellHasFocus);

            if (value != null) {
                ItemMes item = (ItemMes) value;
                setText(item.getMes());
            }

            if (index == -1) {
                ItemMes item = (ItemMes) value;
                setText("" + item.getId_mes());
            }

            return this;
        }
    }

}
