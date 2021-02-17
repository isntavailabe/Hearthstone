package client.state.game.actions;

import client.models.Player;
import client.models.entities.Entity;
import client.state.game.GameLogic;

public abstract class Action {
    protected GameLogic gameLogic;
    protected Entity actor;
    protected Player player;

    public Action(GameLogic gameLogic, Entity actor, Player player) {
        this.gameLogic = gameLogic;
        this.actor = actor;
        this.player = player;
    }

    public void execute() {
        if (!gameLogic.getPlayer().equals(player))
            return;
    }

    public Entity getActor() {
        return actor;
    }

    public Player getPlayer() {
        return player;
    }

    public void deActive() {
    }

    public void setGameLogic(GameLogic gameLogic) {
        this.gameLogic = gameLogic;
    }
}
