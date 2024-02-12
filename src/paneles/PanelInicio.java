package paneles;

import imagenes.Imagen;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author jacob
 */
public class PanelInicio extends JPanel {

    Imagen imagenes = new Imagen();

    private Timer timer;
    private int size;
    private Boolean imagenMaximizada;

    public PanelInicio() {
        this.setLayout(null);
        size = 200;
        imagenMaximizada = false;
        animacion();
    }

    public void animacion() {
        timer = new Timer(20, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                animarImagen();
                repaint();
            }
        });
        timer.start();
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
        graphics2D.drawImage(imagenes.fondo(), 0, 0, this.getWidth(), this.getHeight(), this);
    }

    public void dibujarPiso(Graphics2D graphics2D) {
        graphics2D.drawImage(imagenes.piso(), 0, 530, this.getWidth(), 200, this);
    }

    public void dibujarMensajeInicio(Graphics2D graphics2D) {
        graphics2D.drawImage(imagenes.mensajeInicio(), size / 2, size / 2, this.getWidth() - size, this.getHeight() - size, this);
    }

}
