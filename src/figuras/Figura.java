package figuras;

import java.awt.geom.Rectangle2D;

/**
 *
 * @author jacob
 */
public class Figura {
    
    private Rectangle2D cuerpoPajaro;
    
    public Figura (){
        cuerpoPajaro = new Rectangle2D.Double();
    }
    
    public void setCuerpoPajaro (double x, double y, double w, double h){
        cuerpoPajaro = new Rectangle2D.Double(x,y,w,h);
    }
    
    public Rectangle2D getCuerpoPajaro() {
        return cuerpoPajaro;
    }
    
    public Rectangle2D rectangulo(double x, double y, double w, double h) {
        return new Rectangle2D.Double(x, y, w, h);
    }
    
}
