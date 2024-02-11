package paneles;

import figuras.Figuras;
import imagenes.Imagenes;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.TexturePaint;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
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
    PerdidaJuego perdidaJuego = new PerdidaJuego();

    private int avancePajaro, tiempoSubida, movimientoPaisaje, movimientoPiso;
    private Boolean añadido, tuberiaCompletada;
    private double caidaPajaro, coordMayorPiso, coordMayorTecho;
    private ArrayList<Rectangle2D> tuberiasPiso, tuberiasTecho;
    private Timer timer;

    public PanelJuego() {
        this.setLayout(null);
        this.addKeyListener(teclado);
        this.setFocusable(true);
        caidaPajaro = 30;
        avancePajaro = 0;
        tiempoSubida = 0;
        movimientoPaisaje = 0;
        movimientoPiso = 0;
        coordMayorPiso = 0;
        coordMayorTecho = 0;
        añadido = false;
        tuberiaCompletada = false;
        tuberiasPiso = new ArrayList<Rectangle2D>();
        tuberiasTecho = new ArrayList<Rectangle2D>();
        animacion();
    }

    public void animacion() {
        timer = new Timer(10, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pararAnimacion();
                // bird
                caidaPajaro();
                saltoPajaro();
                // background
                moverFondo();
                moverPiso();
                // pipe
                añadirTuberiaPiso();
                añadirTuberiaTecho();
                moverTuberiasPiso();
                moverTuberiasTecho();
                // to paint
                repaint();
            }
        });
        timer.start();
    }

    public void pararAnimacion() {
        if (perdidaJuego.estaFueraDelPanel(caidaPajaro)) {
            timer.stop();
        }
        for (int i = 0; i < tuberiasTecho.size() && i < tuberiasTecho.size(); i++) {
            if (perdidaJuego.choqueConTuberia(figuras.getCuerpoPajaro(), tuberiasTecho.get(i)) || perdidaJuego.choqueConTuberia(figuras.getCuerpoPajaro(), tuberiasPiso.get(i))) {
                timer.stop();
            }
        }
    }

    public void caidaPajaro() {
        avancePajaro = 100;
        if (!teclado.getSpace() && caidaPajaro < 510) {
            caidaPajaro += 10;
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

    public Rectangle2D generarTuberiaPiso(double x) {
        int pipeHeight = (int) (Math.random() * (300 - 130 + 1) + 130);
        double pipeWidth = 80;
        double y = 530 - pipeHeight;
        return figuras.rectangulo(x, y, pipeWidth, pipeHeight);
    }

    public void añadirTuberiaPiso() {
        for (int i = 0; i < 5 && !añadido; i++) {
            if (tuberiasPiso.isEmpty()) {
                tuberiasPiso.add(i, generarTuberiaPiso(486));
            } else {
                tuberiasPiso.add(i, generarTuberiaPiso(tuberiasPiso.get(i - 1).getX() + 90 + imagenes.tuberiaPiso().getWidth()));
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
            buscarCoordenadaMayorPiso();
            tuberiasPiso.add(i, generarTuberiaPiso(coordMayorPiso + 90 + imagenes.tuberiaPiso().getWidth()));

            System.out.println(tuberiasPiso.get(i).getY());
        }
    }

    public void buscarCoordenadaMayorPiso() {
        for (int i = 0; i < tuberiasPiso.size(); i++) {
            if (coordMayorPiso == 0) {
                coordMayorPiso = tuberiasPiso.get(i).getX();
            } else {
                if (coordMayorPiso < tuberiasPiso.get(i).getX()) {
                    coordMayorPiso = tuberiasPiso.get(i).getX();
                }
            }
        }
    }

    public Rectangle2D generarTuberiaTecho(int i) {
        double pipeHeight = 530 - 150 - tuberiasPiso.get(i).getHeight();
        double pipeWidth = 80;
        return figuras.rectangulo(tuberiasPiso.get(i).getX(), 0, pipeWidth, pipeHeight);
    }

    public void añadirTuberiaTecho() {
        for (int i = 0; i < tuberiasPiso.size() && !tuberiaCompletada; i++) {
            tuberiasTecho.add(generarTuberiaTecho(i));
        }
        tuberiaCompletada = true;
    }

    public void moverTuberiasTecho() {
        for (int i = 0; i < tuberiasTecho.size(); i++) {
            Rectangle2D rInicial = tuberiasTecho.get(i);
            double y = rInicial.getY();
            double w = rInicial.getWidth();
            double h = rInicial.getHeight();
            tuberiasTecho.set(i, figuras.rectangulo(rInicial.getX() - 4, y, w, h));
            reemplazarTuberiaTecho(i);
        }
    }

    public void reemplazarTuberiaTecho(int i) {
        if (tuberiasTecho.get(i).getX() + tuberiasTecho.get(i).getWidth() < 0) {
            tuberiasTecho.remove(i);
            buscarCoordenadaMayorTecho();
            tuberiasTecho.add(i, generarTuberiaTecho(i));
        }
    }

    public void buscarCoordenadaMayorTecho() {
        for (int i = 0; i < tuberiasTecho.size(); i++) {
            if (coordMayorTecho == 0) {
                coordMayorTecho = tuberiasTecho.get(i).getX();
            } else {
                if (coordMayorTecho < tuberiasTecho.get(i).getX()) {
                    coordMayorTecho = tuberiasTecho.get(i).getX();
                }
            }
        }
    }

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D graphics2D = (Graphics2D) graphics;
        dibujarFondo(graphics2D);
        dibujarTuberias(graphics2D);
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

    public void dibujarTuberias(Graphics2D graphics2D) {
        try {
            for (int i = 0; i < tuberiasPiso.size() && i < tuberiasTecho.size(); i++) {
                graphics2D.setPaint(texturaTuberiaPiso(tuberiasPiso.get(i), imagenes.tuberiaPiso()));
                graphics2D.fill(tuberiasPiso.get(i));
                graphics2D.setPaint(texturaTuberiaTecho(tuberiasTecho.get(i), imagenes.tuberiaTecho()));
                graphics2D.fill(tuberiasTecho.get(i));
            }
        } catch (Exception e) {

        }
    }

    public TexturePaint texturaTuberiaPiso(Rectangle2D r, BufferedImage image) {
        return imagenes.crearTexturePaint(image, figuras.rectangulo(r.getX(), r.getY(), 80, 400));
    }

    public TexturePaint texturaTuberiaTecho(Rectangle2D r, BufferedImage image) {
        return imagenes.crearTexturePaint(image, figuras.rectangulo(r.getX(), r.getHeight() - 400, 80, 400));
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
