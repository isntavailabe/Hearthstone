package client.state.game.actions.heroPowerActions;

import client.models.Player;
import client.models.cards.card;
import client.models.entities.Entity;
import client.state.game.GameLogic;
import client.state.game.actions.HeroAction;

import java.util.Random;

import static client.utils.RandomNumber.getRandom;

public class WarlockPower extends HeroAction {
    private Random random = new Random(System.nanoTime());

    public WarlockPower(GameLogic gameLogic, Entity actor, Player player) {
        super(gameLogic, actor, player);
    }

    @Override
    public void execute() {
        super.execute();
        if (gameLogic.getPlayer().getHero().getHeroPower().getCount() >= gameLogic.getPassiveLogic().getHeropowerCounter())
            return;
        player.getHero().takeDamage(2);
        int r = getRandom(2);
        if (r == 1) {
            if (player.getMinions().size() == 0)
                return;
            int m = getRandom(player.getMinions().size());
            player.getMinions().get(m).addHp(1);
            player.getMinions().get(m).addAttack(1);
        } else {
            gameLogic.drawCard(1, player);
        }
        player.getHero().getHeroPower().addCount();
    }

    @Override
    public void fireSpecialAction(String hero, card c, Entity minion) {
        if (c == null)
            return;
        gameLogic.setPlayerMana(c);
    }
}
