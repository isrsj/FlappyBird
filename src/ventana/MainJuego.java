package ventana;

import paneles.PanelJuego;
import javax.swing.JFrame;

/**
 *
 * @author jacob
 */
public class MainJuego extends JFrame {

    PanelJuego panelJuego = new PanelJuego();
    Thread thread = new Thread(panelJuego);

    public MainJuego() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(500, 700);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.add(panelJuego);
        thread.start();
    }

    public static void main(String[] args) {
        
        MainJuego juego = new MainJuego();
        juego.setVisible(true);
    }

}
