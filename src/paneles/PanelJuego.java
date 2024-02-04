package paneles;

import calculos.Calculos;
import figuras.Figuras;
import imagenes.Imagenes;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.TexturePaint;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author jacob
 */
public class PanelJuego extends JPanel {

    Imagenes imagenes = new Imagenes();
    Figuras figuras = new Figuras();
    Calculos calculos = new Calculos();

    private int avancePajaro;
    private double caidaPajaro;

    public PanelJuego() {
        this.setLayout(null);
        caidaPajaro = 30;
        avancePajaro = 0;
        animacion();
    }

    public void animacion() {
        Timer timer = new Timer(10, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                caidaPajaro();
            }

        });
        timer.start();
    }

    public void caidaPajaro() {
        avancePajaro = 100;
        caidaPajaro += 15;
    }

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D graphics2D = (Graphics2D) graphics;
        dibujarFondo(graphics2D);
        dibujarPiso(graphics2D);
        dibujarPajaro(graphics2D);
        repaint();
    }

    public void dibujarFondo(Graphics2D graphics2D) {
        graphics2D.drawImage(imagenes.fondo(), 0, 0, this.getWidth(), this.getHeight(), this);
    }

    public void dibujarPiso(Graphics2D graphics2D) {
        graphics2D.drawImage(imagenes.piso(), 0, 530, this.getWidth(), 200, this);
    }

    public void dibujarPajaro(Graphics2D graphics2D) {
        graphics2D.setPaint(texturaPajaro());
        graphics2D.fill(figuras.getCuerpoPajaro());
    }

    public TexturePaint texturaPajaro() {
        figuras.setCuerpoPajaro(avancePajaro, caidaPajaro, 34, 24);
        return imagenes.crearTexturePaint(imagenes.pajaro(), figuras.getCuerpoPajaro());
    }

}
