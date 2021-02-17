package client.state.menu;

import client.buttons.Action;
import client.buttons.Button;
import client.buttons.CircleButton;
import client.state.Handler;
import client.state.State;
import client.utils.Asset;
import client.utils.Constants;

import java.awt.*;
import java.util.ArrayList;


public class MenuState extends State {
    private ArrayList<Button> buttons;
    private boolean active = true;
    private MenuMapper mapper = new MenuMapper(handler);

    public MenuState(Handler handler) {
        super(handler);
        buttons = new ArrayList<>();
        //PLAY
        buttons.add(new CircleButton(695, 700, 180, new Action() {
            @Override
            public void doAction() {
                mapper.play();
            }
        }, Asset.button.get("play_button")));
        //SHOP
        buttons.add(new CircleButton(495, 700, 160, new Action() {
            @Override
            public void doAction() {
                mapper.shop();
            }
        }, Asset.button.get("shop_button")));
        //COLLECTION
        buttons.add(new CircleButton(295, 700, 160, new Action() {
            @Override
            public void doAction() {
                mapper.collection();
            }
        }, Asset.button.get("collection_button")));
        //STATUS
        buttons.add(new CircleButton(895, 700, 160, new Action() {
            @Override
            public void doAction() {
                mapper.status();
            }
        }, Asset.button.get("status_button")));
        //SETTING
        buttons.add(new CircleButton(1095, 700, 160, new Action() {
            public void doAction() {
                mapper.setting();
            }
        }, Asset.button.get("setting_button")));
        //EXIT
        buttons.add(new CircleButton(10, 10, 60, new Action() {
            public void doAction() {
                System.exit(0);
            }
        }, Asset.utils.get("exit_button")));
    }

    @Override
    public void update() {
        if (!active)
            return;
        for (Button b : buttons) {
            b.update();
        }
    }

    @Override
    public void render(Graphics2D g) {
        g.drawImage(Asset.backgrounds.get("menuback"), 0, 0, Constants.GameWidth, Constants.GameHeight, null);
        for (Button b : buttons) {
            b.render(g);
        }
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
