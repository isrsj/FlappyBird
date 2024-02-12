/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package paneles;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ContainerListener;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import teclado.Teclado;

/**
 *
 * @author compi
 */
public class PanelBase extends JInternalFrame {

    Teclado teclado = new Teclado();
    PanelInicio i = new PanelInicio();

    public PanelBase() {
        this.setLayout(null);
        this.addKeyListener(teclado);
        this.setFocusable(true);
        this.setSize(80, 80);
        this.setBackground(Color.red);
        removeAll();
        revalidate();
        repaint();
        this.setContentPane(i);
//        System.exit(0);
    }


}
