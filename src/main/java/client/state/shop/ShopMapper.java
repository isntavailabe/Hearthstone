package client.state.shop;



import client.buttons.CardButton;
import client.state.Handler;

import java.awt.*;

public class ShopMapper {
    private ShopLogic logic;

    ShopMapper(Handler handler) {
        logic = new ShopLogic(handler);
    }

    void renderCard(Graphics2D g, int num, int i, int height, int l, int cardX, int Spacing, int cardWidth, int cardHeight, int lockWidth, int lockHeight) {
        logic.renderCard(g, num, i, height, l, cardX, Spacing, cardWidth, cardHeight, lockWidth, lockHeight);
    }

    void showInfo(Graphics2D g, CardButton b, int index) {
        logic.showInfo(g, b, index);
    }

    void buySellCard(int x, int y) {
        logic.buySellCard(x, y);
    }

    boolean hasReachedLimit(int a) {
        return logic.hasReachedLimit(a);
    }

    void setNeutral() {
        logic.setNeutral();
    }

    public ShopLogic getLogic() {
        return logic;
    }
}
