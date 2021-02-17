package client.models;

import client.state.game.GameLogic;
import client.utils.Timer;

public class RandomTimer extends Thread {
    private Timer timer = new Timer();
    private GameLogic gameLogic;
    private Enemy enemy;

    public RandomTimer(GameLogic gameLogic, Enemy enemy) {
        this.enemy = enemy;
        this.gameLogic = gameLogic;
    }

    @Override
    public void run() {
        while (!gameLogic.isGameEnded()) {
            if (timer.timeEvent(4000) && enemy.isUrTurn()) {
                System.out.println("in the loop before do");
                enemy.doRandomAction();
                enemy.setUrTurn(false);
                gameLogic.endTurn();
                gameLogic.startTurn();
            }
        }
    }

    Timer getTimer() {
        return timer;
    }
}
