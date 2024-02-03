package calculos;

/**
 *
 * @author jacob
 */
public class MRU extends Movimiento{

    public MRU() {
        super();
    }

    @Override
    public double funcionAngulo() {
        return Math.cos(getAngulo());
    }

    @Override
    public double velocidadFinal() {
        return getVelocidadInicial() * funcionAngulo();
    }

    @Override
    public double posicionCoordenada() {
        return velocidadFinal() * getTiempo();
    }

}
