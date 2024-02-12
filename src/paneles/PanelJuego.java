package paneles;

import figuras.Figura;
import imagenes.Audio;
import imagenes.Imagen;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.TexturePaint;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JPanel;
import javax.swing.Timer;
import teclado.Teclado;

/**
 *
 * @author jacob
 */
public class PanelJuego extends JPanel {

    Imagen imagenes = new Imagen();
    Figura figuras = new Figura();
    Teclado teclado = new Teclado();
    Audio audio = new Audio();
    PerdidaJuego perdidaJuego = new PerdidaJuego();

    private int avancePajaro, tiempoSubida, movimientoPaisaje, movimientoPiso, contUnidades, contDecenas;
    private Boolean añadido, tuberiaCompletada, puntajeContado, estaPerdido;
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
        puntajeContado = false;
        estaPerdido = false;
        tuberiasPiso = new ArrayList<Rectangle2D>();
        tuberiasTecho = new ArrayList<Rectangle2D>();
        animacion();
    }

    public void animacion() {
        timer = new Timer(7, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pararAnimacion();
                // bird
                caidaPajaro();
                saltoPajaro();
                // puntaje
                aumentarUnidades();
                aumentarDecenas();
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
        if (perdidaJuego.estaFueraDelPanel(caidaPajaro) || caidaPajaro >= 510) {
            timer.stop();
            audio.playAudio(audio.fileCrashAudio());
            estaPerdido = true;
        }
        for (int i = 0; i < tuberiasTecho.size() && i < tuberiasTecho.size(); i++) {
            if (perdidaJuego.choqueConTuberia(figuras.getCuerpoPajaro(), tuberiasTecho.get(i)) || perdidaJuego.choqueConTuberia(figuras.getCuerpoPajaro(), tuberiasPiso.get(i))) {
                timer.stop();
                audio.playAudio(audio.fileCrashAudio());
                estaPerdido = true;
            }
        }
    }

    public void caidaPajaro() {
        avancePajaro = 100;
        if (!teclado.getSpace() && caidaPajaro < 510) {
            caidaPajaro += 4;
        }
    }

    public void saltoPajaro() {
        if (tiempoSubida > 0 && tiempoSubida < 2) {
            audio.playAudio(audio.fileJumpAudio());
        }
        if (teclado.getSpace() && caidaPajaro < 510) {
            caidaPajaro -= tiempoSubida;
            tiempoSubida += 1;
        }
        if (tiempoSubida == 13) {
            tiempoSubida = 0;
            teclado.setSpace(false);
        }
    }

    public void aumentarUnidades() {
        for (int i = 0; i < tuberiasPiso.size(); i++) {
            if (tuberiasPiso.get(i).getX() < figuras.getCuerpoPajaro().getX() && !puntajeContado) {
                contUnidades++;
                audio.playAudio(audio.filePointAudio());
                puntajeContado = true;
            }
        }
    }

    public void aumentarDecenas() {
        if (contUnidades == 10) {
            contUnidades = 0;
            contDecenas++;
        }
    }

    public void moverFondo() {
        movimientoPaisaje -= 3;
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
                tuberiasPiso.add(i, generarTuberiaPiso(tuberiasPiso.get(i - 1).getX() + 110 + imagenes.tuberiaPiso().getWidth()));
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
            tuberiasPiso.set(i, figuras.rectangulo(rInicial.getX() - 5, y, w, h));
            reemplazarTuberiaPiso(i);
        }
    }

    public void reemplazarTuberiaPiso(int i) {
        if (tuberiasPiso.get(i).getX() + tuberiasPiso.get(i).getWidth() < 0) {
            tuberiasPiso.remove(i);
            buscarCoordenadaMayorPiso();
            tuberiasPiso.add(i, generarTuberiaPiso(coordMayorPiso + 110 + imagenes.tuberiaPiso().getWidth()));
            puntajeContado = false;
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
            tuberiasTecho.set(i, figuras.rectangulo(rInicial.getX() - 5, y, w, h));
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
        dibujarPuntaje(graphics2D);
        dibujarGameOver(graphics2D);
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

    public void dibujarPuntaje(Graphics2D graphics2D) {
        Rectangle2D rUnidades = figuras.rectangulo((this.getWidth() / 2) + 15, 30, 30, 45);
        Rectangle2D rDecenas = figuras.rectangulo((this.getWidth() / 2) - 15, 30, 30, 45);
        graphics2D.setPaint(texturePaintNumero(contDecenas, rDecenas));
        graphics2D.fill(rDecenas);
        graphics2D.setPaint(texturePaintNumero(contUnidades, rUnidades));
        graphics2D.fill(rUnidades);
    }

    public TexturePaint texturePaintNumero(int numero, Rectangle2D rectangle) {
        switch (numero) {
            case 0 -> {
                return imagenes.crearTexturePaint(imagenes.numero("0.png"), rectangle);
            }
            case 1 -> {
                return imagenes.crearTexturePaint(imagenes.numero("1.png"), rectangle);
            }
            case 2 -> {
                return imagenes.crearTexturePaint(imagenes.numero("2.png"), rectangle);
            }
            case 3 -> {
                return imagenes.crearTexturePaint(imagenes.numero("3.png"), rectangle);
            }
            case 4 -> {
                return imagenes.crearTexturePaint(imagenes.numero("4.png"), rectangle);
            }
            case 5 -> {
                return imagenes.crearTexturePaint(imagenes.numero("5.png"), rectangle);
            }
            case 6 -> {
                return imagenes.crearTexturePaint(imagenes.numero("6.png"), rectangle);
            }
            case 7 -> {
                return imagenes.crearTexturePaint(imagenes.numero("7.png"), rectangle);
            }
            case 8 -> {
                return imagenes.crearTexturePaint(imagenes.numero("8.png"), rectangle);
            }
            case 9 -> {
                return imagenes.crearTexturePaint(imagenes.numero("9.png"), rectangle);
            }
        }
        return null;
    }

    public void dibujarGameOver(Graphics2D graphics2D) {
        if (estaPerdido) {
            graphics2D.drawImage(imagenes.gameOver(), 20, this.getHeight() / 2 - 80, 450, 100, this);
        }
    }

}
