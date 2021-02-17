package client.state.game.actions.heroPowerActions;

import client.models.Attribute;
import client.models.Player;
import client.models.cards.card;
import client.models.entities.Entity;
import client.state.game.GameLogic;
import client.state.game.actions.HeroAction;

public class HunterPower extends HeroAction {

    public HunterPower(GameLogic gameLogic, Entity actor, Player player) {
        super(gameLogic, actor, player);
    }

    @Override
    public void execute() {
        super.execute();
        if (gameLogic.getPlayer().getHero().getHeroPower().getCount() >= gameLogic.getPassiveLogic().getHeropowerCounter())
            return;
        if (gameLogic.getActionLogic().getHunterTarget() == null)
            return;
        gameLogic.getActionLogic().getHunterTarget().takeDamage(1);
        gameLogic.getActionLogic().checkIfIsDead(gameLogic.getActionLogic().getHunterTarget(), player);
        player.getHero().getHeroPower().addCount();
    }

    @Override
    public void fireSpecialAction(String hero, card c, Entity minion) {
        if (hero.contains("hunter")) {
            if (minion != null)
            minion.getAttributes().add(Attribute.RUSH);
        }
        if (c == null)
            return;
        gameLogic.setPlayerMana(c);
    }
}
