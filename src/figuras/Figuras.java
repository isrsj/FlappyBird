/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package figuras;

import java.awt.geom.Rectangle2D;

/**
 *
 * @author jacob
 */
public class Figuras {
    
    private Rectangle2D cuerpoPajaro;
    
    public Figuras (){
        cuerpoPajaro = new Rectangle2D.Double();
    }
    
    public void setCuerpoPajaro (double x, double y, double w, double h){
        cuerpoPajaro = new Rectangle2D.Double(x,y,w,h);
    }
    
    public Rectangle2D getCuerpoPajaro() {
        return cuerpoPajaro;
    }
    
}
