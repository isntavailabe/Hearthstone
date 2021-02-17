package client.state.setting;

import client.buttons.*;
import client.buttons.Button;
import client.state.Handler;
import client.state.State;
import client.utils.Asset;

import java.awt.*;
import java.util.ArrayList;

import static client.audio.Audio.decreaseSound;
import static client.audio.Audio.increaseSound;
import static client.input.MouseInput.getMouseX;
import static client.utils.Text.drawString;

public class Setting extends State {
    private ArrayList<Button> buttons;
    private boolean active;
    private ArrayList<String> names;
    private SettingLogic logic = new SettingLogic(handler);

    public Setting(Handler handler) {
        super(handler);
        buttons = new ArrayList<>();
        names = new ArrayList<>();
        names.add("gameback");
        names.add("gameback2");
        names.add("gameback3");

        createButton();
    }

    @Override
    public void update() {
        for (Button b : buttons) {
            b.update();
        }
    }

    @Override
    public void render(Graphics2D g) {
        g.setColor(Color.LIGHT_GRAY);
        g.drawRoundRect(350, 85, 800, 600, 10, 10);
        drawString(g, "SOUND VOLUME", 750, 400, true, Color.white, Asset.white40);
        drawString(g, "CHOOSE ONE", 750, 150, true, Color.white, Asset.white40);
        drawString(g, "DELETE PLAYER", 750, 580, true, Color.white, Asset.white40);
        renderBacks(g);
        renderButtons(g);
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isActive() {
        return active;
    }

    private void renderButtons(Graphics2D g) {
        for (Button b : buttons) {
            b.render(g);
            if (b instanceof CardButton) {
                if ((getMouseX() - 400) / 250 < names.size() && (getMouseX() - 400) / 250 >= 0)
                    ((CardButton) b).show(g, Asset.gamebacks.get(names.get((getMouseX() - 400) / 250)));
            }
        }
    }

    private void renderBacks(Graphics2D g) {
        for (int i = 0; i < 3; i++) {
            g.drawImage(Asset.gamebacks.get(names.get(i)), 400 + 250 * i, 200, 200, 140, null);
        }
    }

    private void createButton() {
        buttons.add(new CircleButton(730, 480, 40, new Action() {
            public void doAction() {
                decreaseSound();
            }
        }, Asset.utils.get("arrow_down")));
        buttons.add(new CircleButton(730, 430, 40, new Action() {
            public void doAction() {
                increaseSound();
            }
        }, Asset.utils.get("arrow_up")));
        buttons.add(new RectButton(360, 95, 40, 40, new Action() {
            public void doAction() {
                logic.backToMenu();
            }
        }, Asset.utils.get("close_button")));
        buttons.add(new CardButton(400, 200, 200, 140, new Action() {
            public void doAction() {
                Asset.setGameBack(Asset.gamebacks.get("gameback"));
            }
        }));
        buttons.add(new CardButton(650, 200, 200, 140, new Action() {
            public void doAction() {
                Asset.setGameBack(Asset.gamebacks.get("gameback2"));
            }
        }));
        buttons.add(new CardButton(900, 200, 200, 140, new Action() {
            public void doAction() {
                Asset.setGameBack(Asset.gamebacks.get("gameback3"));
            }
        }));
        buttons.add(new CircleButton(940, 560, 40, new Action() {
            public void doAction() {
                logic.deletePlayerButton();
            }
        }, Asset.utils.get("mana")));
    }
}
