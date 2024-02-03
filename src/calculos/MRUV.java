/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package calculos;


/**
 *
 * @author jacob
 */
public class MRUV extends Movimiento{
    
    Constantes constantes = new Constantes();

    public MRUV() {
        super();
    }

    @Override
    public double funcionAngulo() {
        return Math.sin(getAngulo());
    }
    
    @Override
    public double velocidadFinal() {
        return getVelocidadInicial() * funcionAngulo();
    }

    @Override
    public double posicionCoordenada() {
        return ( velocidadFinal()*getTiempo() ) - ( constantes.GRAVEDAD*getTiempo() );
    }
    
}
