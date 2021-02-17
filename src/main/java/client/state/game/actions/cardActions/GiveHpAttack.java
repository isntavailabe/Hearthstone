package client.state.game.actions.cardActions;

import client.models.Player;
import client.models.entities.Entity;
import client.state.game.GameLogic;
import client.state.game.actions.Action;

import java.util.ArrayList;

public class GiveHpAttack extends Action {
    private double hp;
    private double attack;
    private String condition;

    public GiveHpAttack(ArrayList arrayList, Player player, Entity actor, GameLogic gameLogic) {
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
                condition = "default";
                break;
            case "actor":
                gameLogic.getActionLogic().getDrawCardActions().add(this);
                condition = "draw_card";
                break;
            case "this_turn":
                gameLogic.getActionLogic().getOneTurnActions().add(this);
                fireAction();
                break;
            case "draw_card":
                fireAction(gameLogic.getPlayer());
                break;
            case "hero":
                restore(player.getHero());
                break;
            case "default":
                fireAction(gameLogic.getActionLogic().getTargetSelected());
        }
    }

    private void fireAction(Entity actor) {
        if (actor == null)
            return;
        actor.addHp(hp);
        actor.addAttack(attack);
    }

    private void fireAction(Player player) {
        if (actor == null)
            return;
        if (!player.equals(this.player))
            return;
        actor.addHp(hp);
        actor.addAttack(attack);
    }

    private void restore(Entity entity) {
        entity.addHp(player.getHero().getHeroPower().getRestoreFactor() * hp);
        entity.addAttack(attack);
    }

    private void fireAction() {
        for (Entity entity : player.getEntities()) {
            entity.addAttack(attack);
        }
    }

    public void deActive() {
        for (Entity entity : player.getEntities()) {
            entity.addAttack(-attack);
        }
    }
}
