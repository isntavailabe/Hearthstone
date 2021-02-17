package client.state.game.actions.heroPowerActions;

import client.models.Player;
import client.models.cards.card;
import client.models.entities.Entity;
import client.models.entities.Minion;
import client.state.game.GameLogic;
import client.state.game.actions.HeroAction;

import static client.utils.RandomNumber.getRandom;

public class RoguePower extends HeroAction {

    public RoguePower(GameLogic gameLogic, Entity actor, Player player) {
        super(gameLogic, actor, player);
    }

    @Override
    public void execute() {
        super.execute();
        if (gameLogic.getPlayer().getHero().getHeroPower().getCount() >= gameLogic.getPassiveLogic().getHeropowerCounter())
            return;
        if (player.getWeapon() != null) {
            cardFromDeck();
            cardFromEnemy();
        } else {
            cardFromDeck();
        }
        player.getHero().getHeroPower().addCount();
    }

    @Override
    public void fireSpecialAction(String hero, card c, Entity minion) {
        if (c == null)
            return;
        int total = c.getManaCost() + gameLogic.getPassiveLogic().getOffcards();
        if (!c.getHeroClass().equals("neutral") && !c.getHeroClass().equals("rogue")) {
            total -= player.getHero().getHeroPower().getRogueOffCard();
        }
        if (total < 0)
            total = 0;
        gameLogic.setFinalMana(player.getMana() - total, c);
    }

    private void cardFromDeck() {
        if (player.getCurrentDeck().getDeck().size() == 0)
            return;
        int r = getRandom(player.getCurrentDeck().getDeck().size());
        if (player.getHand().size() < 12)
            player.getHand().add(player.getCurrentDeck().getDeck().get(r));
        player.getCurrentDeck().getDeck().remove(r);
    }

    private void cardFromEnemy() {
        int r = getRandom(player.getCurrentDeck().getDeck().size());
        if (player.getHand().size() < 12)
            player.getHand().add(gameLogic.getTheOtherPlayer().getCurrentDeck().getDeck().get(r));
        gameLogic.getTheOtherPlayer().getCurrentDeck().getDeck().remove(r);
    }
}
