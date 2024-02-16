package teclado;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 *
 * @author jacob
 */
public class Teclado implements KeyListener {

    private static Boolean enter;
    private Boolean space;

    public Teclado() {
        enter = false;
        space = false;
    }

    public Boolean getSpace() {
        return space;
    }

    public void setSpace(Boolean space) {
        this.space = space;
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
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            space = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        
    }

}
