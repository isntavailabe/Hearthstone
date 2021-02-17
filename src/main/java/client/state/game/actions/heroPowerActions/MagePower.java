package client.state.game.actions.heroPowerActions;

import client.models.Player;
import client.models.cards.card;
import client.models.entities.Entity;
import client.state.game.GameLogic;
import client.state.game.actions.HeroAction;

public class MagePower extends HeroAction {

    public MagePower(GameLogic gameLogic, Entity actor, Player player) {
        super(gameLogic, actor, player);
    }

    @Override
    public void execute() {
        super.execute();
        if (gameLogic.getPlayer().getHero().getHeroPower().getCount() >= gameLogic.getPassiveLogic().getHeropowerCounter())
            return;
        if (gameLogic.getTheOtherPlayer().getEntities().contains(gameLogic.getActionLogic().getTargetSelected())) {
            gameLogic.getActionLogic().getTargetSelected().takeDamage(1);
            gameLogic.getActionLogic().checkIfIsDead(gameLogic.getActionLogic().getTargetSelected(), gameLogic.getTheOtherPlayer());
            player.getHero().getHeroPower().addCount();
        }
    }

    @Override
    public void fireSpecialAction(String hero, card c, Entity minion) {
        if (c == null)
            return;
        int total = c.getManaCost() + gameLogic.getPassiveLogic().getOffcards();
        if (hero.contains("mage")) {
            total -= player.getHero().getHeroPower().getSpellManaCost();
        }
        if (total < 0)
            total = 0;
        gameLogic.setFinalMana(player.getMana() - total, c);
    }
}
