package ventana;

import paneles.PanelJuego;
import javax.swing.JFrame;

/**
 *
 * @author jacob
 */
public class Main {

    public static void main(String[] args) {
        
        JFrame ventana = new JFrame("FLAPPY BIRD");
        PanelJuego panelJuego = new PanelJuego();
        
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setSize(500, 700);
        ventana.setResizable(false);
        ventana.setLocationRelativeTo(null);
        ventana.add(panelJuego);
        ventana.setVisible(true);
        
    }

}
