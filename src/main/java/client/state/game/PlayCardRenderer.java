package client.state.game;

import client.models.cards.card;
import client.utils.Asset;

import java.awt.*;

public class PlayCardRenderer {
    private double previousTime;
    private card card;
    private float c = 1;
    private AlphaComposite ac;

    void renderCard(Graphics2D g) {
        if (card == null)
            return;
        if (System.currentTimeMillis() - previousTime >= 100) {
            previousTime = System.currentTimeMillis();
            c -= 0.03;
            if (c <= 0) {
                c = 1;
                card = null;
                return;
            }
        }
        ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, c);
        g.setComposite(ac);
        g.drawImage(Asset.cards.get(card.getName()), 100, 40, 150, 200, null);
        g.setComposite(Asset.ac2);
    }

    public void setCard(client.models.cards.card card) {
        this.card = card;
    }

    public void setPreviousTime(double previousTime) {
        this.previousTime = previousTime;
    }
}
