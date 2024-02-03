package imagenes;

import java.awt.TexturePaint;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

/**
 *
 * @author jacob
 */
public class Imagenes {

    public Imagenes () {

    }

    public BufferedImage fondo () {
        return leerImagen("background.png");
    }

    public BufferedImage mensajeInicio () {
        return leerImagen("message.png");
    }
    
    public BufferedImage piso () {
        return leerImagen("floor.png");
    }
    
    public BufferedImage pajaro () {
        return leerImagen("red_bird.png");
    }
    
    public TexturePaint crearTexturePaint(BufferedImage bufferedImage, Rectangle2D rectangle) {
        return new TexturePaint(bufferedImage, rectangle);
    }
    
    public BufferedImage leerImagen (String url){
        try {
            return ImageIO.read(new File(url));
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "No se encontraron archivos");
            return null;
        }
    }
    
}
