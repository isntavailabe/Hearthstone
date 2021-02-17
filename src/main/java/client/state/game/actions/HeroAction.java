package client.state.game.actions;

import client.models.Player;
import client.models.cards.card;
import client.models.entities.Entity;
import client.models.entities.Minion;
import client.state.game.GameLogic;

public abstract class HeroAction extends Action {

    public HeroAction(GameLogic gameLogic, Entity actor, Player player) {
        super(gameLogic, actor, player);
    }

    public abstract void fireSpecialAction(String hero, card c, Entity minion);


}
