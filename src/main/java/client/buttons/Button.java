package client.buttons;

import client.input.MouseInput;
import client.utils.Asset;

import java.awt.*;

import static client.audio.SoundEffect.*;
import static client.input.MouseInput.isLeftPressed;
import static client.input.MouseInput.setLeftPressed;

public abstract class Button {
    int XPos;
    int YPos;
    Shape boundingBox;
    protected Action action;
    protected String sound = Asset.sound.get("button_pressed");
    boolean hover;

    public Button(Action action) {
        this.action = action;
    }

    public void update() {
        hover = boundingBox.contains(MouseInput.getMouseX(), MouseInput.getMouseY());
        if (hover && isLeftPressed()) {
            action.doAction();
            setFile(sound);
            effectPlay();
            setLeftPressed(false);
        }
    }

    public abstract void render(Graphics2D g);
}
