package client.state.game;

import client.animation.Anim;
import client.buttons.Button;
import client.buttons.CardButton;
import client.models.Enemy;
import client.models.Player;
import client.models.cards.MinionCard;
import client.models.cards.card;
import client.models.entities.Entity;
import client.models.entities.Minion;
import client.state.Handler;
import client.state.game.questReward.Quest;
import client.utils.Asset;
import client.utils.Constants;

import java.awt.*;
import java.util.Iterator;

import static client.input.MouseInput.*;
import static client.input.MouseInput.isDragged;

public class GameMapper {
    private GameLogic gameLogic;

    GameMapper(Handler handler) {
        gameLogic = new GameLogic(handler);
    }

    boolean isEnemy() {
        return gameLogic.getPlayer() instanceof Enemy;
    }

    void initGameLogic() {
        gameLogic.init();
    }

    void updateChooseCard() {
        for (int i = 0; i < 2; i++)
            if (gameLogic.getChooseCard()[i].isActive())
                gameLogic.getChooseCard()[i].update();
    }

    boolean chooseCardIsActive() {
        return gameLogic.getChooseCard()[0].isActive() || gameLogic.getChooseCard()[1].isActive();
    }

    int getSizeOfDeck() {
        return gameLogic.getPlayer().getCurrentDeck().getDeck().size();
    }

    void renderAnims(Graphics2D g) {
        Iterator iterator = gameLogic.getAnims().iterator();
        while (iterator.hasNext()) {
            Anim a = (Anim) iterator.next();
            if (a.isActive()) {
                a.paint(g, Constants.frame);
            }
            else {
                iterator.remove();
            }
        }
    }

    void renderQuests(Graphics2D g) {
        for (Quest quest : gameLogic.getPlayer().getQuests()) {
            quest.render(g, 20 + gameLogic.getPlayer().getQuests().indexOf(quest) * 40);
        }
    }

    void renderEntityInfo(Graphics2D g) {
        for (Player player : gameLogic.getPlayers()) {
            for (Entity entity : player.getEntities()) {
                if (entity.isHovered(getMouseX(), getMouseY()))
                    entity.showInfo(g);
            }
        }
    }

    void renderTimeRemained(Graphics2D g) {
        gameLogic.renderTimeRemained(g);
    }

    void renderGameEvent(Graphics2D g) {
        gameLogic.getGameEvent().render(g);
    }

    void renderChooseCard(Graphics2D g) {
        for (int i = 0; i < 2; i++) {
            if (gameLogic.getChooseCard()[i].isActive()) {
                g.setComposite(Asset.ac);
                g.drawImage(Asset.utils.get("info"), 0, 0, Constants.GameWidth, Constants.GameHeight, null);
                g.setComposite(Asset.ac2);
                gameLogic.getChooseCard()[i].render(g);
            }
        }
    }

    void play(card card) {
        gameLogic.playCard(card);
    }

    void renderCardButton(Graphics2D g, Button button, int cardsInHand, int cardSpacing) {
        if (button instanceof CardButton) {
            if (gameLogic.getPlayer().getHand().size() != 0) {
                int n = cardsInHand - 1 - (getMouseX() - 50) / cardSpacing;
                if (gameLogic.getPlayer().getHand().size() > n) {
                    if (n < 0)
                        n = 0;
                    ((CardButton) button).showCard(g, gameLogic.getPlayer().getHand().get(n), getMouseX());
                }
            }
        }
    }
    void renderCards(Graphics2D g, int cardWidth, int cardHeight, int cardSpacing, int cardsInHand) {
        if (gameLogic.getPlayer().getHand().size() == 0)
            return;
        for (int i = gameLogic.getPlayer().getHand().size() - 1; i > 0; i--) {
            g.drawImage(Asset.cards.get(gameLogic.getPlayer().getHand().get(i).getName()), (cardsInHand - 1 - i) * cardSpacing
                    + 40, 840, cardWidth, cardHeight, null);
        }
        g.drawImage(Asset.cards.get(gameLogic.getPlayer().getHand().get(0).getName()), (cardsInHand - 1) * cardSpacing + 40,
                840, cardWidth, cardHeight, null);
    }

    card getCard(int index) {
        if (gameLogic.getPlayer().getHand().size() == 0)
            return null;
        return gameLogic.getPlayer().getHand().get(index);
    }

    void changeTurn() {
        gameLogic.endTurn();
        gameLogic.startTurn();
    }

    void exit() {
        resetPlayer();
        gameLogic.reset();
    }

    public void resetPlayer() {
        gameLogic.resetPlayer();
    }

    void pauseTimer() {
        gameLogic.pauseTimer();
    }

    void arrowUp(int gameEventRecent) {
        gameLogic.getGameEvent().setSelected(gameLogic.getGameEvent().getSelected() + 1);
        if (gameLogic.getGameEvent().getSelected() > gameLogic.getGameEvent().getEventSize() - gameEventRecent)
            gameLogic.getGameEvent().setSelected(gameLogic.getGameEvent().getEventSize() - gameEventRecent);
    }

    void arrowDown() {
        gameLogic.getGameEvent().setSelected(gameLogic.getGameEvent().getSelected() - 1);
        if (gameLogic.getGameEvent().getSelected() < 0)
            gameLogic.getGameEvent().setSelected(0);
    }

    void handleAction() {
        gameLogic.handleAction();
    }

    void renderWeapon(Graphics2D g) {
        if (gameLogic.getPlayers()[0].getWeapon() != null)
            gameLogic.getPlayers()[0].getWeapon().render(g, 500, 660);
        if (gameLogic.getPlayers()[1].getWeapon() != null)
            gameLogic.getPlayers()[1].getWeapon().render(g, 500, 80);
    }

    void renderHero(Graphics2D g) {
        if (gameLogic.getPlayers()[0].getHero() != null)
            gameLogic.getPlayers()[0].getHero().render(g, 665, 655);
        if (gameLogic.getPlayers()[1].getHero() != null)
            gameLogic.getPlayers()[1].getHero().render(g, 665, 85);
    }

    void renderActor(Graphics2D g) {
        if (gameLogic.getActionLogic().getActor() == null || !(gameLogic.getActionLogic().getActor() instanceof Minion))
            return;
        int x = gameLogic.getActionLogic().getActor().getX();
        int y = gameLogic.getActionLogic().getActor().getY();
        g.drawImage(Asset.utils.get("selected_minion"), x, y, 110, 130, null);
    }

    void renderMana(Graphics2D g) {
        for (int j = 0; j < gameLogic.getPlayer().getMaxMana(); j++) {
            g.drawImage(Asset.utils.get("maxmana"), 1088 + 32 * j, 885, 32, 32, null);
        }
        for (int i = 0; i < gameLogic.getPlayer().getMana(); i++) {
            g.drawImage(Asset.utils.get("mana"), 1088 + 32 * i, 885, 32, 32, null);
        }
    }

    void renderMinions(Graphics2D g, card card, int minionSpacing) {
        int j = 0;
        for (int i = 0; i < gameLogic.getPlayers()[0].getMinions().size(); i++, j++) {
            if (isDragged() && getMouseX() > 320 + j * minionSpacing && getMouseX() < 320 + (j + 1) * minionSpacing && card
                    instanceof MinionCard && getMouseY() > 470 && getMouseY() < 600 && gameLogic.getPlayer().equals(gameLogic.getPlayers()[0])) {
                i--;
                continue;
            }
            gameLogic.getPlayers()[0].getMinions().get(i).render(g, 320 + j * minionSpacing, 470);
        }
        j = 0;
        for (int i = 0; i < gameLogic.getPlayers()[1].getMinions().size(); i++, j++) {
            if (isDragged() && getMouseX() > 320 + j * minionSpacing && getMouseX() < 320 + (j + 1) * minionSpacing && card
                    instanceof MinionCard && getMouseY() > 290 && getMouseY() < 420 && gameLogic.getPlayer().equals(gameLogic.getPlayers()[1])) {
                i--;
                continue;
            }
            gameLogic.getPlayers()[1].getMinions().get(i).render(g, 320 + j * minionSpacing, 290);
        }
    }

    int minionsSize() {
        return gameLogic.getPlayer().getMinions().size();
    }

    boolean isEqualTo() {
        return gameLogic.getPlayer().equals(gameLogic.getPlayers()[0]);
    }

    public GameLogic getGameLogic() {
        return gameLogic;
    }
}
