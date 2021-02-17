package client.state.shop;

import client.buttons.CardButton;
import client.models.cards.card;
import client.state.Handler;
import client.state.ShowErrors;
import client.utils.Asset;

import java.awt.*;
import java.util.ArrayList;

import static client.utils.Logger.logger;

public class ShopLogic {
    private Handler handler;
    private ArrayList<card> cards;
    private boolean buysell;
    private int buysellCard;


    ShopLogic(Handler handler) {
        this.handler = handler;
        cards = new ArrayList<>();
        cards.addAll(handler.getGame().getEnvironment().getNeutralCards());
    }

    boolean hasReachedLimit(int a) {
        return a > cards.size();
    }

    void setNeutral() {
        cards.clear();
        cards.addAll(handler.getGame().getEnvironment().getNeutralCards());
    }

    private boolean haveCard(card card) {
        for (client.models.cards.card c : handler.getGame().getEnvironment().getCurrentPlayer().getCollection()) {
            if (card.equals(c))
                return true;
        }
        return false;
    }

    private void sell(card card) {
        handler.getGame().getEnvironment().getCurrentPlayer().getCollection().remove(card);
        handler.getGame().getEnvironment().getCurrentPlayer().setDiamonds(handler.getGame().getEnvironment()
                .getCurrentPlayer().getDiamonds() + card.getPrice());
        showMessage("SOLD! " + "MANACOST : +" + String.valueOf(card.getPrice()), 2000);
        logger(handler.getGame().getEnvironment().getKey(), "shop state : sell card  : " + card.getName());
    }

    private void buy(card card) {
        handler.getGame().getEnvironment().getCurrentPlayer().getCollection().add(card);
        handler.getGame().getEnvironment().getCurrentPlayer().setDiamonds(handler.getGame().getEnvironment()
                .getCurrentPlayer().getDiamonds() - card.getPrice());
        showMessage("PURCHASED! " + "MANACOST : -" + String.valueOf(card.getPrice()), 2000);
        logger(handler.getGame().getEnvironment().getKey(), "shop state : buy card  : " + card.getName());
    }

    void buySellCard(int x, int y) {
        BuySell(x);
        buysellCard = y;
    }

    public void setCards(ArrayList<card> cards) {
        this.cards.clear();
        this.cards.addAll(cards);
    }

    void showInfo(Graphics2D g, CardButton b, int index) {
        b.showInfo(g, cards.get(index));
    }

    private void BuySell(int x) {
        if (x != buysellCard) {
            buysell = true;
            return;
        }
        card c = cards.get(buysellCard);
        if (buysell) {
            if (haveCard(c))
                sell(c);
            else
                buy(c);
            buysell = false;
        } else {
            buysell = true;
        }
    }

    public void setCards(card card) {
        cards.clear();
        cards.add(card);
    }

    private void showMessage(String message, int time) {
        ((ShowErrors) handler.getGame().getShowErrors()).setActive(true);
        ((ShowErrors) handler.getGame().getShowErrors()).setMessage(message, time);
    }

    void renderCard(Graphics2D g, int num, int i, int height, int l, int cardX, int Spacing, int cardWidth, int cardHeight, int lockWidth, int lockHeight) {
        int size = cards.size();
        if (num < size) {
            g.drawImage(Asset.cards.get(cards.get(num).getName()), cardX + (i - l) * Spacing, height, cardWidth, cardHeight, null);
            if (!haveCard(cards.get(num)))
                g.drawImage(Asset.utils.get("lock"), cardX + cardWidth + (i - l) * Spacing - 40, height + 20, lockWidth, lockHeight, null);
        }
    }




}
