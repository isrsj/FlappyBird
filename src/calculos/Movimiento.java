/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package calculos;

/**
 *
 * @author jacob
 */
public abstract class Movimiento {
    
    private static int tiempo;
    private static double velocidadInicial;
    private static double angulo;
    
    public Movimiento() {
        tiempo =5;
        velocidadInicial = 10;
        angulo = 46;
    }

    public static int getTiempo() {
        return tiempo;
    }

    public static double getVelocidadInicial() {
        return velocidadInicial;
    }

    public static double getAngulo() {
        return angulo;
    }
    
    public abstract double funcionAngulo();
    
    public abstract double velocidadFinal();
    
    public abstract double posicionCoordenada();
}
