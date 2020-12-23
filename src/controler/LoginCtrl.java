/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controler;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import model.Administrador;
import model.Sucursal;
import model.sql.SqlAdministrador;
import model.sql.SqlSucursal;
import view.LoginView;
import view.MenuAdminView;
import view.MenuSucursalView;

/**
 *
 * @author ivn
 */
public class LoginCtrl implements ActionListener{

    private final LoginView view;
    private final Administrador admin = new Administrador(); //objeto
    private final Sucursal sucursal = new Sucursal(); //objeto
    private final SqlAdministrador sqladmin = new SqlAdministrador();
    private final SqlSucursal sqlsucursal = new SqlSucursal();

    //Constructor
    public LoginCtrl(LoginView vista) {
        this.view = vista;

        //Componentes (botones, text fields de la vista)
        view.btn_login.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        //Boton login
        if (e.getSource() == view.btn_login) {

            //obtener usuario
            String user = view.txt_user.getText();
            //obtener contraseña
            String passwd = new String(view.txt_passwd.getPassword());

            //validamos si no son vacias
            if (user.isEmpty() || passwd.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Por favor, llena todos los campos", "ATENCIÓN", JOptionPane.INFORMATION_MESSAGE);
            } else {
                //los dos campos tienen informacion
                login(user, passwd);

            }
        }
    }

    //login
    private void login(String user, String passwd) {

        //Enviar POJOs
        admin.setUsuario(user);
        sucursal.setUsuario(user);

        //se hace la consulta a la base de datos
        if (sqladmin.consultarCredencialesAdministrador(admin) && sqlsucursal.consultarCredencialesSucursal(sucursal)) {

            if (admin.getNombre() != null) {
                //el usuario ADMIN existe
                loginAdmin(user, passwd);
            } else if (sucursal.getDescripcion() != null) {
                //el usuario SUCURSAL existe
                loginSucursal(user, passwd);
            } else {
                //no existe el usuario ADMIN o SUCURSAL
                JOptionPane.showMessageDialog(null, "El usuarion no existe", "ATENCIÓN", JOptionPane.OK_CANCEL_OPTION);
                limpiarTxtPasswd();
            }

        } else {
            //no se pudo hacer la consulta
            JOptionPane.showMessageDialog(null, "Hubo un error en la consulta", "ERROR", JOptionPane.ERROR_MESSAGE);
            limpiarTxtPasswd();
        }

    }

    private void loginAdmin(String user, String passwd) {

        //obtenemos el usuario y la contraseña del POJO y lo comparamos con lo que el usuario introdujo
        if (user.equals(admin.getUsuario()) && passwd.equals(admin.getContrasenia())) {

            //el usuario y la contraseña coinciden, mandamos al usuario a la pantalla de inicio
            MenuAdminView menuadminview = new MenuAdminView();
            MenuAdminCtrl menuadminctrl = new MenuAdminCtrl(menuadminview);
            menuadminview.lb_userHome.setText(admin.getNombre());
            menuadminview.lb_user.setText(admin.getNombre());
            menuadminview.setVisible(true);
            view.setVisible(false);
            view.dispose();

            limpiarTxtPasswd();
            view.btn_login.setEnabled(false);

        } else {
            //la contraseña no coindice
            JOptionPane.showMessageDialog(null, "Contraseña incorrecta", "ATENCIÓN", JOptionPane.INFORMATION_MESSAGE);
            limpiarTxtPasswd();
        }

    }

    private void loginSucursal(String user, String passwd) {

        // obtenemos el usuario y la contraseña del POJO y lo comparamos con lo que el usuario introdujo
        if (user.equals(sucursal.getUsuario()) && passwd.equals(sucursal.getContrasenia())) {

            //el usuario y la contraseña coinciden, mandamos al usuario a la pantalla de inicio
            MenuSucursalView menusucuview = new MenuSucursalView();
            menusucuview.lb_nombreSucursal.setText(sucursal.getDescripcion());
            menusucuview.setVisible(true);
            view.setVisible(false);
            view.dispose();

            limpiarTxtPasswd();
            view.btn_login.setEnabled(false);

        } else {
            //la contraseña no coindice
            JOptionPane.showMessageDialog(null, "Contraseña incorrecta", "ATENCIÓN", JOptionPane.INFORMATION_MESSAGE);
            limpiarTxtPasswd();
        }
    }

    //limpiar
    private void limpiarTxtPasswd() {
        view.txt_user.setText("");
        view.txt_passwd.setText("");
        admin.setNombre(null);
        sucursal.setDescripcion(null);
    }


}
