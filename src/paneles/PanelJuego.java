package paneles;

import figuras.Figuras;
import imagenes.Imagenes;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.TexturePaint;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.Timer;
import teclado.Teclado;

/**
 *
 * @author jacob
 */
public class PanelJuego extends JPanel {

    Imagenes imagenes = new Imagenes();
    Figuras figuras = new Figuras();
    Teclado teclado = new Teclado();

    private int avancePajaro, tiempoSubida, movimientoPaisaje, movimientoPiso, xTuberiaPiso;
    private Boolean añadido;
    private double caidaPajaro, coordMayor;
    private ArrayList<Rectangle2D> tuberiasPiso, tuberiasTecho;

    public PanelJuego() {
        this.setLayout(null);
        this.addKeyListener(teclado);
        this.setFocusable(true);
        caidaPajaro = 30;
        avancePajaro = 0;
        tiempoSubida = 0;
        movimientoPaisaje = 0;
        movimientoPiso = 0;
        xTuberiaPiso = 0;
        coordMayor = 0;
        añadido = false;
        tuberiasPiso = new ArrayList<Rectangle2D>();
        tuberiasTecho = new ArrayList<Rectangle2D>();
        animacion();
    }

    public void animacion() {
        Timer timer = new Timer(10, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                caidaPajaro();
                saltoPajaro();
                moverFondo();
                moverPiso();
                añadirTuberiaPiso();
                moverTuberiasPiso();
                xTuberiaPiso++;
                reiniciarContador();
                repaint();
            }
        });
        timer.start();
    }

    public void caidaPajaro() {
        avancePajaro = 100;
        if (!teclado.getSpace() && caidaPajaro < 510) {
            caidaPajaro += 7;
        }
    }

    public void saltoPajaro() {
        if (teclado.getSpace() && caidaPajaro < 510 && tiempoSubida < 25) {
            caidaPajaro -= 2.5;
            tiempoSubida++;
        } else {
            teclado.setSpace(false);
            tiempoSubida = 0;
        }
    }

    public void moverFondo() {
        movimientoPaisaje -= 2;
        reiniciarFondo();
    }

    public void reiniciarFondo() {
        if (this.getWidth() < Math.abs(movimientoPaisaje)) {
            movimientoPaisaje = this.getWidth();
        }
    }

    public void moverPiso() {
        movimientoPiso -= 6;
        reiniciarPiso();
    }

    public void reiniciarPiso() {
        if (this.getWidth() < Math.abs(movimientoPiso)) {
            movimientoPiso = this.getWidth();
        }
    }

    public void añadirTuberiaPiso() {
        for (int i = 0; i < 5 && !añadido; i++) {
            if (tuberiasPiso.isEmpty()) {
                tuberiasPiso.add(i, generarTuberia(486));
            } else {
                tuberiasPiso.add(i, generarTuberia(tuberiasPiso.get(i - 1).getX() + 150 + imagenes.tuberia().getWidth()));
            }
        }
        añadido = true;
    }

    public void moverTuberiasPiso() {
        for (int i = 0; i < tuberiasPiso.size(); i++) {
            Rectangle2D rInicial = tuberiasPiso.get(i);
            double y = rInicial.getY();
            double w = rInicial.getWidth();
            double h = rInicial.getHeight();
            tuberiasPiso.set(i, figuras.rectangulo(rInicial.getX() - 4, y, w, h));
            reemplazarTuberiaPiso(i);
        }
    }

    public void reemplazarTuberiaPiso(int i) {
        if (tuberiasPiso.get(i).getX() + tuberiasPiso.get(i).getWidth() < 0) {
            tuberiasPiso.remove(i);
            buscarCoordenadaMayor();
            tuberiasPiso.add(i, generarTuberia(coordMayor + 150 + imagenes.tuberia().getWidth()));
        }
    }

    public void buscarCoordenadaMayor() {
        for (int i = 0; i < tuberiasPiso.size(); i++) {
            if (coordMayor == 0) {
                coordMayor = tuberiasPiso.get(i).getX();
            } else {
                if (coordMayor < tuberiasPiso.get(i).getX()) {
                    coordMayor = tuberiasPiso.get(i).getX();
                }
            }
        }
    }

    public void reiniciarContador() {
        if (xTuberiaPiso < 0) {
            xTuberiaPiso = 486;
        }
    }

    public Rectangle2D generarTuberia(double x) {
        double pipeHeight = Math.random() * (350 - 130 + 1) + 130;
        double pipeWidth = imagenes.tuberia().getWidth();
        double y = 530 - pipeHeight;
        return figuras.rectangulo(x, y, pipeWidth, pipeHeight);
    }
    
    public void añadirTuberiaTecho() {
        for (int i = 0; i < tuberiasPiso.size() && !añadido; i++) {
            generarTuberiaTecho(i);
        }
        añadido = true;
    }
    public Rectangle2D generarTuberiaTecho(int i) {
        double pipeHeight = 530-150-tuberiasPiso.get(i).getHeight();
        double pipeWidth = imagenes.tuberia().getWidth();
        return figuras.rectangulo(tuberiasPiso.get(i).getX(), 0, pipeWidth, pipeHeight);
    }

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D graphics2D = (Graphics2D) graphics;
        dibujarFondo(graphics2D);
        try {
            graphics2D.fill(tuberiasPiso.get(0));
            graphics2D.fill(tuberiasPiso.get(1));
            graphics2D.fill(tuberiasPiso.get(2));
            graphics2D.fill(tuberiasPiso.get(3));
            graphics2D.fill(tuberiasPiso.get(4));
        } catch (Exception e) {

        }
        dibujarPiso(graphics2D);
        dibujarPajaro(graphics2D);
    }

    public void dibujarFondo(Graphics2D graphics2D) {
        graphics2D.drawImage(imagenes.fondo(), movimientoPaisaje, 0, this.getWidth(), this.getHeight(), this);
        if (movimientoPaisaje < 0) {
            graphics2D.drawImage(imagenes.fondo(), movimientoPaisaje + this.getWidth(), 0, this.getWidth(), this.getHeight(), this);
        } else {
            graphics2D.drawImage(imagenes.fondo(), movimientoPaisaje - this.getWidth(), 0, this.getWidth(), this.getHeight(), this);
        }
    }

    public void dibujarPiso(Graphics2D graphics2D) {
        graphics2D.drawImage(imagenes.piso(), movimientoPiso, 530, this.getWidth(), 200, this);
        if (movimientoPiso < 0) {
            graphics2D.drawImage(imagenes.piso(), movimientoPiso + this.getWidth(), 530, this.getWidth(), 200, this);
        } else {
            graphics2D.drawImage(imagenes.piso(), movimientoPiso - this.getWidth(), 530, this.getWidth(), 200, this);
        }
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
