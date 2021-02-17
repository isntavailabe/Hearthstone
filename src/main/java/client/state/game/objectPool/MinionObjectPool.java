package client.state.game.objectPool;

import client.models.cards.MinionCard;
import client.models.entities.Minion;

import java.awt.geom.Ellipse2D;

public class MinionObjectPool extends ObjectPool<Minion> {


    @Override
    protected Minion create() {
        return new Minion();
    }

    public Minion getMinion(MinionCard minionCard, int x, int y) {
        Minion m = checkOut();
        m.setMinionCard(minionCard);
        m.setX(x);
        m.setY(y);
        m.setBounding(new Ellipse2D.Double(x, y, 110, 130));
        m.setHp(minionCard.getHP());
        m.setAttack(minionCard.getAttack());
        m.getAttributes().addAll(minionCard.getAttributes());
        return m;
    }
}
