/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controler;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import rojerusan.RSPanelsSlider;
import view.LoginView;
import view.MenuAdminView;

/**
 *
 * @author ivn
 */
public class MenuAdminCtrl implements ActionListener {

    private final MenuAdminView view;

    public MenuAdminCtrl(MenuAdminView view) {
        this.view = view;

        //objetos de la vista
        view.btn_logout.addActionListener(this);
        view.btn_home.addActionListener(this);
        view.btn_empleado.addActionListener(this);
        view.btn_salario.addActionListener(this);
        view.btn_sucursal.addActionListener(this);
        view.btn_ventas.addActionListener(this);

//        view.jpanel_home.addMouseListener(this);
//        view.jpanel_empleado.addMouseListener(this);
//        view.jpanel_salario.addMouseListener(this);
//        view.jpanel_sucursal.addMouseListener(this);
//        view.jpanel_ventas.addMouseListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

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

}
