package ventana;

import javax.swing.JFrame;
import paneles.PanelInicio;

/**
 *
 * @author jacob
 */
public class Ventana extends JFrame {

    
    public static void main(String[] args) {
        JFrame ventana = new JFrame();
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setSize(500, 700);
        ventana.setResizable(false);
        ventana.setLocationRelativeTo(null);
        
        PanelInicio inicio = new PanelInicio(ventana);
        ventana.add(inicio);
        ventana.setVisible(true);
        
        Thread thread = new Thread(inicio);
        thread.start();

    }

}
