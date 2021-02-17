package client.state;

import client.utils.Asset;
import client.utils.Timer;

import java.awt.*;

import static client.utils.Text.drawString;

public class ShowErrors extends State {
    private boolean active;
    private Timer timer;
    private String message;
    private int time;

    public ShowErrors(Handler handler) {
        super(handler);
        timer = new Timer();
    }

    @Override
    public void update() {

    }

    @Override
    public void render(Graphics2D g) {
        if (!timer.timeEvent(time)) {
            g.drawImage(Asset.utils.get("showerror"), 400, 400, 500, 100, null);
            drawString(g, message, 660, 440, true, Color.black, Asset.font20);
        } else {
            active = false;
        }
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setMessage(String message, int time) {
        this.message = message;
        this.time = time;
        timer.resetTimer();
    }
}
