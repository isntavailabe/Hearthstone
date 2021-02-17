package client.state.game.actions.cardActions;

import client.models.Attribute;
import client.models.Player;
import client.models.entities.Entity;
import client.models.entities.Minion;
import client.state.game.GameLogic;
import client.state.game.actions.Action;

import java.util.ArrayList;

public class GiveAttribute extends Action {
    private ArrayList<String> arrayList;
    private String condition;

    public GiveAttribute(ArrayList arrayList, Player player, Entity actor, GameLogic gameLogic) {
        super(gameLogic, actor, player);
        condition = (String) arrayList.get(0);
        arrayList.remove(0);
        this.arrayList = arrayList;
    }

    @Override
    public void execute() {
        super.execute();
        switch (condition) {
            case "choose_one":
                gameLogic.getActionLogic().addActionToWait(this);
                condition = "default";
                break;
            case "Minions":
                fireAction(player.getMinions());
                break;
            case "default":
                fireAction();
        }
    }

    private void fireAction(ArrayList<Minion> arrayList) {
        for (Minion minion : arrayList) {
            for (String attribute : this.arrayList)
                minion.getAttributes().add(Attribute.valueOf(attribute));
        }
    }

    private void fireAction() {
        for (String attribute : arrayList) {
            gameLogic.getActionLogic().getTargetSelected().getAttributes().add(Attribute.valueOf(attribute));
        }
    }
}
