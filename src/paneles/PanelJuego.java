package paneles;

import imagenes.Figura;
import imagenes.Audio;
import imagenes.Constante;
import imagenes.Imagen;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.TexturePaint;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
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
public class PanelJuego extends JPanel implements Runnable {

    Imagen imagenes = new Imagen();
    Audio audio = new Audio();
    Teclado teclado = new Teclado();
    Constante constante = new Constante();
    Figura figuras = new Figura();
    PerdidaJuego perdidaJuego = new PerdidaJuego();

    private int avancePajaro, tiempoSubida, movimientoPaisaje, movimientoPiso, contUnidades, contDecenas, aleteo;
    private Boolean añadido, tuberiaCompletada, puntajeContado, estaPerdido;
    private static Boolean cerrarVentana;
    private double caidaPajaro;
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
        aleteo = 0;
        añadido = false;
        tuberiaCompletada = false;
        puntajeContado = false;
        estaPerdido = false;
        tuberiasPiso = new ArrayList<Rectangle2D>();
        tuberiasTecho = new ArrayList<Rectangle2D>();
    }

    @Override
    public void run() {
        while (!estaPerdido) {
            delay(16);
            pararAnimacion();
            // bird
            caidaPajaro();
            saltoPajaro();
            aleteo++;
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
        
        delay(1500);
        MainInicio inicio = new MainInicio(new PanelInicio());
        inicio.setVisible(true);
    }

    public void delay(int delay) {
        try {
            Thread.sleep(delay);
        } catch (InterruptedException ex) {
            Logger.getLogger(PanelJuego.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void pararAnimacion() {
        if (perdidaJuego.estaFueraDelPanel(caidaPajaro) || caidaPajaro >= 510) {
            //Poner para cerrarVentana
            audio.playAudio(audio.fileCrashAudio());
            estaPerdido = true;
        }
        for (int i = 0; i < tuberiasTecho.size() && i < tuberiasTecho.size(); i++) {
            if (perdidaJuego.choqueConTuberia(figuras.getCuerpoPajaro(), tuberiasTecho.get(i)) || perdidaJuego.choqueConTuberia(figuras.getCuerpoPajaro(), tuberiasPiso.get(i))) {
                //Poner para cerrarVentana
                audio.playAudio(audio.fileCrashAudio());
                estaPerdido = true;
            }
        }
    }

    public void caidaPajaro() {
        avancePajaro = 100;
        if (!teclado.getSpace() && caidaPajaro < 510) {
            caidaPajaro += 5;
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

    public void añadirTuberiaPiso() {
        for (int i = 0; i < 5 && !añadido; i++) {
            if (tuberiasPiso.isEmpty()) {
                tuberiasPiso.add(i, generarTuberiaPiso(376));
            } else {
                tuberiasPiso.add(i, generarTuberiaPiso(tuberiasPiso.get(i - 1).getX() + constante.ANCHO_TUBERIA));
            }
        }
        añadido = true;
    }

    public Rectangle2D generarTuberiaPiso(double x) {
        int pipeHeight = (int) (Math.random() * (300 - 130 + 1) + 130);
        double pipeWidth = 80;
        double y = 530 - pipeHeight;
        return figuras.rectangulo(x + 110, y, pipeWidth, pipeHeight);
    }

    public void añadirTuberiaTecho() {
        for (int i = 0; i < tuberiasPiso.size() && !tuberiaCompletada; i++) {
            tuberiasTecho.add(generarTuberiaTecho(i));
        }
        tuberiaCompletada = true;
    }

    public Rectangle2D generarTuberiaTecho(int i) {
        double pipeHeight = 530 - 150 - tuberiasPiso.get(i).getHeight();
        double pipeWidth = 80;
        return figuras.rectangulo(tuberiasPiso.get(i).getX(), 0, pipeWidth, pipeHeight);
    }

    public void moverTuberiasPiso() {
        for (int i = 0; i < tuberiasPiso.size(); i++) {
            tuberiasPiso.set(i, modificarTuberia(tuberiasPiso.get(i)));
            reemplazarTuberiaPiso(i);
        }
    }

    public void moverTuberiasTecho() {
        for (int i = 0; i < tuberiasTecho.size(); i++) {
            tuberiasTecho.set(i, modificarTuberia(tuberiasTecho.get(i)));
            reemplazarTuberiaTecho(i);
        }
    }

    public Rectangle2D modificarTuberia(Rectangle2D rectangleArray) {
        Rectangle2D rectangle = rectangleArray;
        double x = rectangle.getX() - 5;
        double y = rectangle.getY();
        double w = rectangle.getWidth();
        double h = rectangle.getHeight();
        return figuras.rectangulo(x, y, w, h);
    }

    public void reemplazarTuberiaPiso(int i) {
        if (tuberiasPiso.get(i).getX() + tuberiasPiso.get(i).getWidth() < 0) {
            tuberiasPiso.remove(i);
            tuberiasPiso.add(i, generarTuberiaPiso(buscarCoordenadaMayor(tuberiasPiso) + constante.ANCHO_TUBERIA));
            puntajeContado = false;
        }
    }

    public double buscarCoordenadaMayor(ArrayList<Rectangle2D> arrayList) {
        double coordMayor = 0;
        for (int i = 0; i < arrayList.size(); i++) {
            if (coordMayor == 0) {
                coordMayor = arrayList.get(i).getX();
            } else {
                if (coordMayor < arrayList.get(i).getX()) {
                    coordMayor = arrayList.get(i).getX();
                }
            }
        }
        return coordMayor;
    }

    public void reemplazarTuberiaTecho(int i) {
        if (tuberiasTecho.get(i).getX() + tuberiasTecho.get(i).getWidth() < 0) {
            tuberiasTecho.remove(i);
            tuberiasTecho.add(i, generarTuberiaTecho(i));
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
        if (aleteo >= 7 && aleteo <= 17) {
            return imagenes.crearTexturePaint(imagenes.pajaro("redbird-downflap.png"), figuras.getCuerpoPajaro());
        }
        if (aleteo >= 17 && aleteo <= 27) {
            return imagenes.crearTexturePaint(imagenes.pajaro("redbird-midflap.png"), figuras.getCuerpoPajaro());
        }
        if (aleteo >= 27) {
            aleteo = 0;
            return imagenes.crearTexturePaint(imagenes.pajaro("redbird-upflap.png"), figuras.getCuerpoPajaro());
        }
        return imagenes.crearTexturePaint(imagenes.pajaro("red_bird.png"), figuras.getCuerpoPajaro());
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
