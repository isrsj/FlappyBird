package ventana;

import javax.swing.JFrame;
import javax.swing.JPanel;
import paneles.PanelInicio;

/**
 *
 * @author jacob
 */
public class MainInicio extends JFrame {

    public MainInicio(JPanel panel) {
        Thread thread = new Thread((Runnable) panel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(500, 700);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.add(panel);
        this.setVisible(true);
        thread.start();
    }

    public static void main(String[] args) {
        MainInicio inicio = new MainInicio(new PanelInicio());
        inicio.setVisible(true);

    }

}
