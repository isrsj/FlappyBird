package paneles;

import mecanicas.Constante;
import multimedia.Imagen;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JPanel;
import mecanicas.Teclado;

/**
 *
 * @author jacob
 */
public class PanelInicio extends JPanel implements Runnable {

    Imagen imagenes = new Imagen();
    Constante constante = new Constante();
    Teclado teclado = new Teclado();

    private int size;
    private JFrame frame;
    private Boolean imagenMaximizada;

    public PanelInicio(JFrame frame) {
        this.setLayout(null);
        this.frame = frame;
        size = 200;
        imagenMaximizada = false;
        this.addKeyListener(teclado);
        this.setFocusable(true);
    }

    @Override
    public void run() {
        while (!teclado.getEnter()) {
            delay(10);
            animarImagen();
            repaint();
        }
        cargarPanel();
    }

    public void cargarPanel() {
        PanelJuego juego = new PanelJuego(frame);
        Thread thread = new Thread(juego);
        frame.add(juego);
        frame.remove(this);
        frame.setVisible(true);
        thread.start();
    }

    public void delay(int delay) {
        try {
            Thread.sleep(delay);
        } catch (InterruptedException ex) {
            Logger.getLogger(PanelInicio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void animarImagen() {
        if (size > 150 && !imagenMaximizada) {
            size--;
        } else {
            imagenMaximizada = true;
        }
        if (size < 200 && imagenMaximizada) {
            size++;
        } else {
            imagenMaximizada = false;
        }
    }

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D graphics2D = (Graphics2D) graphics;
        dibujarFondo(graphics2D);
        dibujarPiso(graphics2D);
        dibujarMensajeInicio(graphics2D);
    }

    public void dibujarFondo(Graphics2D graphics2D) {
        graphics2D.drawImage(imagenes.fondo(), 0, 0, constante.ANCHO_PANEL, constante.ALTO_PANEL, this);
    }

    public void dibujarPiso(Graphics2D graphics2D) {
        graphics2D.drawImage(imagenes.piso(), 0, 530, constante.ANCHO_PANEL, 200, this);
    }

    public void dibujarMensajeInicio(Graphics2D graphics2D) {
        graphics2D.drawImage(imagenes.mensajeInicio(), size / 2, size / 2, constante.ANCHO_PANEL - size, constante.ALTO_PANEL - size, this);
    }

}
