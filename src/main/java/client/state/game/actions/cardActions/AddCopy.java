package client.state.game.actions.cardActions;

import client.models.Player;
import client.models.cards.MinionCard;
import client.models.cards.card;
import client.models.entities.Entity;
import client.models.entities.Minion;
import client.state.game.GameLogic;
import client.state.game.actions.Action;

import java.util.ArrayList;
import java.util.Random;

import static client.utils.RandomNumber.getRandom;

public class AddCopy extends Action {
    private double count;
    private String condition;

    public AddCopy(ArrayList arrayList, Player player, Entity actor, GameLogic gameLogic) {
        super(gameLogic, actor, player);
        this.count = (double) arrayList.get(0);
        this.condition = (String) arrayList.get(1);
    }

    @Override
    public void execute() {
        super.execute();
        switch (condition) {
            case "choose_one":
                gameLogic.getActionLogic().addActionToWait(this);
                condition = "default";
                break;
            case "opponent's_deck":
                selectCard();
                break;
            case "default":
                fireAction(gameLogic.getActionLogic().getTargetSelected());
        }
    }

    private void selectCard() {
        if (gameLogic.getTheOtherPlayer().getCurrentDeck().getDeck().size() == 0)
            return;
        for (int i = 0; i < count; i++) {
            int r = getRandom(gameLogic.getTheOtherPlayer().getCurrentDeck().getDeck().size());
            fireAction(gameLogic.getTheOtherPlayer().getCurrentDeck().getDeck().get(r));
        }
    }

    private void fireAction(card card) {
        player.getHand().add(card);
    }

    private void fireAction(Entity minion) {
        if (minion == null || !gameLogic.getPlayer().getEntities().contains(minion))
            return;
        if (minion instanceof Minion) {
            MinionCard minionCard = ((Minion) minion).getMinionCard();
            player.getCurrentDeck().getDeck().add(minionCard);
            player.getHand().add(minionCard);
            gameLogic.summon(minionCard);
        }
    }
}
