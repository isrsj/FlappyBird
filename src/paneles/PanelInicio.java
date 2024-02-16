package paneles;

import imagenes.Constante;
import imagenes.Imagen;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import teclado.Teclado;
import ventana.MainInicio;
import ventana.MainJuego;

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

    public PanelInicio() {
        this.setLayout(null);
        size = 200;
        imagenMaximizada = false;
        this.addKeyListener(teclado);
        this.setFocusable(true);
    }
    
    public PanelInicio(JFrame frame) {
        this.setLayout(null);
        size = 200;
        imagenMaximizada = false;
        this.addKeyListener(teclado);
        this.setFocusable(true);
    }

    @Override
    public void run() {
        while (!teclado.getEnter()) {
            delay();
            animarImagen();
            repaint();
        }
        MainInicio inicio = new MainInicio(new PanelJuego());
        inicio.setVisible(true);
    }

    public void delay() {
        try {
            Thread.sleep(10);
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
