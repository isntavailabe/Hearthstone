package client.state.game.actions.heroPowerActions;

import client.models.Player;
import client.models.cards.card;
import client.models.entities.Entity;
import client.state.game.GameLogic;
import client.state.game.actions.HeroAction;

public class PriestPower extends HeroAction {

    public PriestPower(GameLogic gameLogic, Entity actor, Player player) {
        super(gameLogic, actor, player);
    }

    @Override
    public void execute() {
        super.execute();
        if (gameLogic.getPlayer().getHero().getHeroPower().getCount() >= gameLogic.getPassiveLogic().getHeropowerCounter())
            return;
        if (gameLogic.getPlayer().getEntities().contains(gameLogic.getActionLogic().getTargetSelected())) {
            gameLogic.getActionLogic().getTargetSelected().addHp(4);
            player.getHero().getHeroPower().addCount();
        }
    }

    @Override
    public void fireSpecialAction(String hero, card c, Entity minion) {
        if (c == null)
            return;
        gameLogic.setPlayerMana(c);
    }
}
