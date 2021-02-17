package client.state.infopassive;

import client.audio.Audio;
import client.buttons.Action;
import client.buttons.Button;
import client.buttons.RectButton;
import client.state.game.GameState;
import client.state.Handler;
import client.state.State;
import client.utils.Asset;
import client.utils.Constants;
import client.utils.config.InfoPassiveConfig;

import java.awt.*;
import java.util.ArrayList;

import static client.input.MouseInput.getMouseX;
import static client.state.infopassive.infoPassiveLogic.*;
import static client.utils.Logger.logger;

public class InfoPassiveState extends State {
    private ArrayList<Button> buttons;
    private infoPassiveLogic infoPassiveLogic;
    static InfoPassiveConfig config = new InfoPassiveConfig("INFOPASSIVESTATE_CONFIGFILE");

    private int spacing = config.getCardSpacing();
    private int cardWidth = config.getcardWidth();
    private int cardHeight = config.getcardHeight();

    public InfoPassiveState(Handler handler) {
        super(handler);
        infoPassiveLogic = getInstance();
        buttons = new ArrayList<>();

        creatButtons();
    }

    static InfoPassiveConfig getConfig(){
        return config;
    }

    private void creatButtons() {
        for (int i = 0; i < infoPassiveLogic.getToChooseP(); i++) {
            buttons.add(new RectButton(250 + i * spacing, 500, cardWidth, cardHeight, new Action() {
                public void doAction() {
                    int x = (getMouseX() - 250) / spacing;
                    infoPassiveLogic.setPassive(x);
                    setCurrentState(handler.getGame().getGameState());
                    ((GameState) handler.getGame().getGameState()).start();
                    handler.getGame().getAudio().setFile(Asset.sound.get("back2"));
                    Audio.play();
                    logger(handler.getGame().getEnvironment().getKey(), "info passive state : select passive");
                }
            }));
        }
    }

    private void renderPassive(Graphics2D g) {
        for (int i = 0; i < infoPassiveLogic.getToChooseP(); i++) {
            g.drawImage(Asset.passives.get(infoPassiveLogic.passiveName(i)), 250 + i * spacing, 500, cardWidth, cardHeight, null);
        }
    }

    @Override
    public void update() {
        for (Button button : buttons) {
            button.update();
        }
    }

    @Override
    public void render(Graphics2D g) {
        g.drawImage(Asset.backgrounds.get("infopassiveback"), 0, 0, Constants.GameWidth, Constants.GameHeight, null);
        renderButtons(g);
        renderPassive(g);
    }

    private void renderButtons(Graphics2D g) {
        for (Button button : buttons) {
            button.render(g);
        }
    }

}
