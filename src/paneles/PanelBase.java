package paneles;

import java.awt.CardLayout;
import javax.swing.JPanel;
import teclado.Teclado;

/**
 *
 * @author compi
 */
public class PanelBase extends JPanel {

//    PanelInicio panelInicio = new PanelInicio();
//    PanelJuego panelJuego = new PanelJuego();
    Teclado teclado = new Teclado();

    private CardLayout cardLayout;

    public PanelBase() {
//        cardLayout = new CardLayout();
//        this.setLayout(cardLayout);
//        this.addKeyListener(teclado);
//        this.setFocusable(true);
//        this.add(panelInicio);
//        this.add(panelJuego);
    }

    public CardLayout getCardLayout() {
        return cardLayout;
    }

    public void setCardLayout(CardLayout cardLayout) {
        this.cardLayout = cardLayout;
    }

}
