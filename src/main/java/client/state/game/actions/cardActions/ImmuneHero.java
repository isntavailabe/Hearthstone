package client.state.game.actions.cardActions;

import client.models.Attribute;
import client.models.Player;
import client.models.entities.Entity;
import client.state.game.GameLogic;
import client.state.game.actions.Action;

import java.util.ArrayList;

public class ImmuneHero extends Action {
    private String condition;

    public ImmuneHero(ArrayList arrayList, Player player, Entity actor, GameLogic gameLogic) {
        super(gameLogic, actor, player);
        condition = (String) arrayList.get(0);
        actor.getActions().add(this);
    }

    @Override
    public void execute() {
        super.execute();
        switch (condition) {
            case "this_turn":
                gameLogic.getActionLogic().getOneTurnActions().add(this);
                fireAction();
                break;
            case "default":
                fireAction();
        }
    }

    public void deActive() {
        player.getHero().getAttributes().remove(Attribute.IMMUNE_WHILE_ATTACKING);
    }

    private void fireAction() {
        player.getHero().getAttributes().add(Attribute.IMMUNE_WHILE_ATTACKING);
    }


}
