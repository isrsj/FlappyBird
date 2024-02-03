package teclado;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 *
 * @author jacob
 */
public class Teclado implements KeyListener{
    
    private Boolean enter;
    
    public Teclado (){
        enter = false;
    }

    public Boolean getEnter() {
        return enter;
    }

    public void setEnter(Boolean enter) {
        this.enter = enter;
    }
    
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            enter = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
    
}
