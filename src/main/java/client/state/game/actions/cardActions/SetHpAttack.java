package client.state.game.actions.cardActions;

import client.models.Player;
import client.models.entities.Entity;
import client.state.game.GameLogic;
import client.state.game.actions.Action;

import java.util.ArrayList;

public class SetHpAttack extends Action {
    private double hp;
    private double attack;
    private String condition;

    public SetHpAttack(ArrayList arrayList, Player player, Entity actor, GameLogic gameLogic) {
        super(gameLogic, actor, player);
        this.hp = (double) arrayList.get(0);
        this.attack = (double) arrayList.get(1);
        this.condition = (String) arrayList.get(2);
    }

    @Override
    public void execute() {
        super.execute();
        switch (condition) {
            case "choose_one":
                gameLogic.getActionLogic().addActionToWait(this);
                condition = "selected";
                break;
            case "specific_num":
                gameLogic.getActionLogic().getSummonActions().add(this);
                condition = "summoned";
                break;
            case "summoned":
                fireAction(gameLogic.getActionLogic().getSummonedMinion(), actor.getHp(), actor.getAttack());
                break;
            case "selected":
                fireAction(gameLogic.getActionLogic().getTargetSelected(), hp, attack);
        }
    }

    public void fireAction(Entity target, double a, double b) {
        //JUST FOR MINIONS
        if (target == null || gameLogic.getPlayer().getEntities().contains(target))
            return;
        target.setHp(a);
        target.setAttack(b);
    }


}
