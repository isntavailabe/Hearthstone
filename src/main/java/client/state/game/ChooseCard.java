package client.state.game;

import client.buttons.Action;
import client.buttons.Button;
import client.buttons.CircleButton;
import client.buttons.RectButton;
import client.models.Player;
import client.state.Handler;
import client.state.State;
import client.utils.Asset;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

import static client.input.MouseInput.getMouseX;

public class ChooseCard extends State {
    private ArrayList<Button> buttons;
    private Random random;
    private boolean[] hasChanged;
    private Player player;
    private boolean active;


    ChooseCard(Player player, Handler handler) {
        super(handler);
        this.player = player;
        init();
    }

    private void init() {
        hasChanged = new boolean[4];
        random = new Random(System.nanoTime());
        buttons = new ArrayList<>();
        createButtons();
    }

    public void update() {
        for (Button button : buttons) {
            button.update();
        }
    }

    private void createButtons() {
        for (int i = 0; i < 4; i++) {
            buttons.add(new RectButton(200 + 300 * i, 300, 220, 300, new Action() {
                @Override
                public void doAction() {
                    int s = (getMouseX() - 200) / 300;
                    if (!hasChanged[s]) {
                        changeCard(s);
                    }
                }
            }));
        }

        buttons.add(new CircleButton(200, 200, 80, new Action() {
            @Override
            public void doAction() {
                if (((GameState) handler.getGame().getGameState()).getMapper().getGameLogic().getChooseCard()[0].isActive()) {
                    ((GameState) handler.getGame().getGameState()).getMapper().getGameLogic().getChooseCard()[0].setActive(false);
                    ((GameState) handler.getGame().getGameState()).getMapper().getGameLogic().getChooseCard()[1].setActive(true);
                } else
                    ((GameState) handler.getGame().getGameState()).getMapper().getGameLogic().getChooseCard()[1].setActive(false);

                if (!((GameState) handler.getGame().getGameState()).getMapper().getGameLogic().getChooseCard()[0].isActive() &&
                        !((GameState) handler.getGame().getGameState()).getMapper().getGameLogic().getChooseCard()[1].isActive()) {
                    try {
                        ((GameState) handler.getGame().getGameState()).getMapper().getGameLogic().startTimer();
                    } catch (Exception e) {
                        ((GameState) handler.getGame().getGameState()).getMapper().getGameLogic().restartTimer();
                    }
                }
            }
        }, Asset.button.get("play_button")));
    }

    public void render(Graphics2D g) {
        renderCards(g);
        renderButtons(g);
    }

    private void renderButtons(Graphics2D g) {
        for (Button button : buttons) {
            button.render(g);
        }
    }

    private void renderCards(Graphics2D g) {
        if (player.getHand().size() == 0)
            return;
        for (int i = 0; i < 4; i++) {
            g.drawImage(Asset.cards.get(player.getHand().get(i).getName()),
                    200 + 300 * i, 300, 220, 300, null);
        }
    }

    private void changeCard(int s) {
        int c = random.nextInt(player.getCurrentDeck().getDeck().size());
        player.getCurrentDeck().getDeck().add(player.getHand().get(s));
        player.getHand().remove(s);
        player.getHand().add(s, player.getCurrentDeck().getDeck().get(c));
        player.getCurrentDeck().getDeck().remove(player.getCurrentDeck().getDeck().get(c));
        hasChanged[s] = true;
    }

    boolean isActive() {
        return active;
    }

    void setActive(boolean active) {
        this.active = active;
    }
}
