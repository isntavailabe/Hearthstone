package client.buttons;

import client.utils.Asset;
import client.models.cards.card;

import java.awt.*;
import java.awt.image.BufferedImage;

import static client.utils.Text.drawString;

public class CardButton extends Button {

    public CardButton(int x, int y, int width, int height, Action action) {
        super(action);
        XPos = x;
        YPos = y;
        boundingBox = new Rectangle(x, y, width, height);
    }

    public CardButton(int x, int y, int width, int height, Action action, String sound) {
        super(action);
        XPos = x;
        YPos = y;
        boundingBox = new Rectangle(x, y, width, height);
        this.sound = sound;
    }

    public void showInfo(Graphics2D g, card card) {
        if (hover) {
            g.setComposite(Asset.ac);
            g.drawImage(Asset.utils.get("info"), XPos + 10, YPos + 10, 200, 290, null);
            g.setComposite(Asset.ac2);
            drawString(g, card.getName(), XPos + 15, YPos + 80, false, Color.white, Asset.font20);
            drawString(g, card.getHeroClass(), XPos + 15, YPos + 120, false, Color.white, Asset.font20);
            drawString(g, card.getType(), XPos + 15, YPos + 160, false, Color.white, Asset.font20);
            drawString(g, card.getRarity(), XPos + 15, YPos + 200, false, Color.white, Asset.font20);
            drawString(g, String.valueOf(card.getPrice()), XPos + 15, YPos + 240, false, Color.white, Asset.font20);
        }
    }

    public void showCard(Graphics2D g, card card, int x) {
        if (hover) {
            g.drawImage(Asset.cards.get(card.getName()), x, 500, 220, 300, null);
        }
    }

    public void show(Graphics2D g, BufferedImage bi) {
        if (hover)
            g.drawImage(bi, XPos, YPos, 500, 360, null);
    }

    @Override
    public void render(Graphics2D g) {

    }
}
