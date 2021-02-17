package client.state.game.actions.cardActions;

import client.models.Player;
import client.models.entities.Entity;
import client.models.entities.Minion;
import client.state.game.GameLogic;
import client.state.game.actions.Action;

import java.util.ArrayList;

import static client.utils.RandomNumber.getRandom;

public class Damage extends Action {
    private double count;
    private String condition;
    private ArrayList<Entity> enemyTarget;
    private ArrayList<Entity> friendlyTarget;

    public Damage(ArrayList arrayList, Entity actor, Player player, GameLogic gameLogic) {
        super(gameLogic, actor, player);
        this.count = (double) arrayList.get(0);
        this.condition = (String) arrayList.get(1);
        enemyTarget = new ArrayList<>();
        friendlyTarget = new ArrayList<>();
    }

    @Override
    public void execute() {
        super.execute();
        switch (condition) {
            case "choose_one":
                gameLogic.getActionLogic().addActionToWait(this);
                enemyTarget.addAll(gameLogic.getTheOtherPlayer().getMinions());
                condition = "enemy_Minions";
                break;
            case "enemy_Minions":
                fireAction();
                break;
            case "all_other_enemies":
                fireAction(getRandomEnemy(), gameLogic.getTheOtherPlayer());
                count = 1;
                enemyTarget.add(gameLogic.getTheOtherPlayer().getHero());
                enemyTarget.addAll(gameLogic.getTheOtherPlayer().getMinions());
                fireAction(enemyTarget, friendlyTarget);
                break;
            case "all_enemy_minions":
                enemyTarget.addAll(gameLogic.getTheOtherPlayer().getMinions());
                fireAction(enemyTarget, friendlyTarget);
                break;
            case "all_other_minions":
                enemyTarget.addAll(gameLogic.getTheOtherPlayer().getMinions());
                friendlyTarget.addAll(gameLogic.getPlayer().getMinions());
                friendlyTarget.remove(actor);
                gameLogic.getActionLogic().getEndTurnActions().add(this);
                condition = "check_added";
                break;
            case "all_characters":
                enemyTarget.addAll(gameLogic.getTheOtherPlayer().getMinions());
                friendlyTarget.addAll(gameLogic.getPlayer().getMinions());
                friendlyTarget.add(gameLogic.getPlayer().getHero());
                enemyTarget.add(gameLogic.getTheOtherPlayer().getHero());
                fireAction(enemyTarget, friendlyTarget);
                break;
            case "random_friendly_minion":
                gameLogic.getActionLogic().getEndTurnActions().add(this);
                condition = "random";
                break;
            case "random":
                int r = getRandom(player.getMinions().size());
                fireAction(player.getMinions().get(r), player);
                break;
            case "check_added":
                fireAction(gameLogic.getTheOtherPlayer(), player);
                break;
            case "default":
                fireAction(enemyTarget, friendlyTarget);
                break;
            case "all_minions":
                enemyTarget.addAll(gameLogic.getTheOtherPlayer().getMinions());
                friendlyTarget.addAll(gameLogic.getPlayer().getMinions());
                fireAction(enemyTarget, friendlyTarget);
        }
    }

    private void fireAction(Player player1, Player player2) {
        if (gameLogic.getPlayer() != player)
            return;
        for (Minion minion : player1.getMinions()) {
            if (!enemyTarget.contains(minion))
                enemyTarget.add(minion);
        }
        for (Minion minion : player2.getMinions()) {
            if (!friendlyTarget.contains(minion)) {
                friendlyTarget.add(minion);
            }
        }
        friendlyTarget.remove(actor);
        fireAction(enemyTarget, friendlyTarget);
    }

    private Entity getRandomEnemy() {
        Player enemy = gameLogic.getTheOtherPlayer();
        int bound = enemy.getMinions().size() + 1;
        int r = getRandom(bound);

        if (enemy.getMinions().size() != 0) {
            int index = r - 1;
            if (index < 0)
                index = 0;
            return enemy.getMinions().get(index);
        } else
            return enemy.getHero();
    }

    private void fireAction() {
        Entity entity = gameLogic.getActionLogic().getTargetSelected();
        if (entity == null || !player.getEntities().contains(entity))
            return;
        for (Entity minion : enemyTarget) {
            gameLogic.getActionLogic().takeDamage(minion, gameLogic.getActionLogic().getTargetSelected().getAttack(), gameLogic.getTheOtherPlayer());
            gameLogic.getActionLogic().checkIfIsDead(minion, gameLogic.getTheOtherPlayer());
        }
        gameLogic.getActionLogic().remove(entity, player);
    }

    private void fireAction(ArrayList<Entity> enemyTarget, ArrayList<Entity> friendlyTarget) {
        for (Entity entity : enemyTarget) {
            gameLogic.getActionLogic().takeDamage(entity, (int) count, gameLogic.getTheOtherPlayer());
            gameLogic.getActionLogic().checkIfIsDead(entity, gameLogic.getTheOtherPlayer());
        }
        for (Entity entity : friendlyTarget) {
            gameLogic.getActionLogic().takeDamage(entity, (int) count,player);
            gameLogic.getActionLogic().checkIfIsDead(entity, player);
        }
    }

    private void fireAction(Entity entity, Player player) {
        gameLogic.getActionLogic().takeDamage(entity, (int) count, player);
        gameLogic.getActionLogic().checkIfIsDead(entity, player);
    }
}
