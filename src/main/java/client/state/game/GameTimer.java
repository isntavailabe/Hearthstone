package client.state.game;

import client.utils.Timer;

public class GameTimer extends Thread {
    private Timer timer = new Timer();
    private GameLogic gameLogic;
    private boolean pause;

    GameTimer(GameLogic gameLogic) {
        this.gameLogic = gameLogic;
    }

    @Override
    public void run() {
        timer.resetTimer();
        while (!gameLogic.isGameEnded()) {
            if (!pause)
                if (timer.timeEvent(60000)) {
                    gameLogic.endTurn();
                    gameLogic.startTurn();
                }
        }
    }

    Timer getTimer() {
        return timer;
    }

    void setPause(boolean pause) {
        this.pause = pause;
    }
}
