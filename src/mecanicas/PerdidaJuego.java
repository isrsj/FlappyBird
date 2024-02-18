package mecanicas;

import java.awt.geom.Rectangle2D;

/**
 *
 * @author compi
 */
public class PerdidaJuego {
    
    public Boolean choqueConTuberia(Rectangle2D bird, Rectangle2D pipe) {
        return bird.intersects(pipe);
    }
    
    public Boolean estaFueraDelPanel(double y) {
        return y < 0;
    }
    
}
