package client.state.game.actions.cardActions;

import client.models.Player;
import client.models.cards.card;
import client.models.entities.Entity;
import client.state.game.GameLogic;
import client.state.game.actions.Action;

import java.util.ArrayList;
import java.util.Random;

import static client.utils.RandomNumber.getRandom;


public class DrawCardAction extends Action {
    private double count;
    private String type;
    private String discard;
    private String condition;


    public DrawCardAction(ArrayList arrayList, Player player, Entity actor, GameLogic gameLogic) {
        super(gameLogic, actor, player);
        this.count = (double) arrayList.get(0);
        this.type = (String) arrayList.get(1);
        this.discard = (String) arrayList.get(2);
        this.condition = (String) arrayList.get(3);
    }

    @Override
    public void execute() {
        super.execute();
        switch (condition) {
            case "end_of_turn":
                gameLogic.getActionLogic().getEndTurnActions().add(this);
                condition = "default";
                break;
            case "just_this":
                fireAction(type);
                break;
            case "default":
                fireAction();
        }
    }

    private void fireAction(String type) {
        card c = null;
        for (card card : player.getCurrentDeck().getDeck()) {
            if (card.getType().equals(type)) {
                c = card;
            }
        }
        if (c == null)
            return;
        gameLogic.drawThis(c, player);
    }

    private void fireAction() {
        if (gameLogic.getPlayer() != player)
            return;
        for (int i = 0; i < count; i++) {
            if (player.getCurrentDeck().getDeck().size() > 0) {
                int c = getRandom(player.getCurrentDeck().getDeck().size());
                if ((player.getCurrentDeck().getDeck().get(c).getType().equals(type) || type.equals("none"))) {
                    if (player.getHand().size() < 12 && !player.getCurrentDeck().getDeck().get(c).getType().equals(discard))
                        gameLogic.drawCardAnimation(player.getCurrentDeck().getDeck().get(c), player);
                    player.getCurrentDeck().getDeck().remove(player.getCurrentDeck().getDeck().get(c));
                }
            }
        }
        gameLogic.getActionLogic().executeDrawCardActions();
    }
}
