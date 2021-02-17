package client.state.game.actions.cardActions;

import client.models.Player;
import client.models.cards.MinionCard;
import client.models.entities.Entity;
import client.models.entities.Minion;
import client.state.game.GameLogic;
import client.state.game.actions.Action;

import java.util.ArrayList;

import static client.utils.RandomNumber.getRandom;

public class Summon extends Action {
    private double count;
    private String condition;
    private String minionCard;
    private MinionCard minion;

    public Summon(ArrayList arrayList, Player player, Entity actor, GameLogic gameLogic) {
        super(gameLogic, actor, player);
        this.count = (double) arrayList.get(0);
        this.condition = (String) arrayList.get(1);
        this.minionCard = (String) arrayList.get(2);
        if (gameLogic.getRewardsInModeOne().containsKey("strength in numbers") && condition == "random_from_deck") {
            condition = "default";
            minionCard = gameLogic.getRewardsInModeOne().get("strength in numbers");
        }
    }

    @Override
    public void execute() {
        super.execute();
        switch (condition) {
            case "default":
                MinionCard minion = (MinionCard) gameLogic.getHandler().getGame().getEnvironment().getCard(minionCard);
                fireAction(minion);
                break;
            case "add_to_default":
                condition = "default";
                break;
            case "hand":
                gameLogic.getActionLogic().getStartTurnActions().add(this);
                condition = "just_fire";
                break;
            case "random_from_deck":
                MinionCard m = player.getMinionCard();
                if (m == null) {
                    return;
                }
                fireAction(m);
                break;
            case "just_fire":
                this.minion = player.getMinionFromHand();
                if (this.minion == null)
                    return;
                fireAction(this.minion);
                break;
            case "actor":
                MinionCard minion1 = (MinionCard) gameLogic.getHandler().getGame().getEnvironment()
                        .getCard(((Minion)actor).getMinionCard().getName());
                minion1.getBattleCry().clear();
                fireAction(minion1);
                break;
        }
    }

    private void fireAction(MinionCard minionCard) {
        if (gameLogic.getPlayer().equals(player))
            for (int i = 0; i < count; i++) {
                if (gameLogic.canSummonMoreMinions(gameLogic.getPlayer()))
                    gameLogic.summon(minionCard);
            }
    }
}
