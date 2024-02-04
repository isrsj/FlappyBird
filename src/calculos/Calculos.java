package calculos;

/**
 *
 * @author jacob
 */
public class Calculos {
    
    public Calculos() {
        
    }
    
    public double coordenadaVertical(int distancia, int xInicial, int yInicial, int xFinal) {
        return Math.sqrt( distancia^2 - (xFinal-xInicial)^2 ) + yInicial;
    }
    
}
