/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package panaderia;

import controler.LoginCtrl;
import model.Administrador;
import model.sql.SqlAdministrador;
import view.LoginView;

/**
 *
 * @author ivn
 */
public class Panaderia {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here

        //Abre la vista login
        LoginView vista = new LoginView();
        LoginCtrl controlador = new LoginCtrl(vista);
        vista.setVisible(true);

    }

}
