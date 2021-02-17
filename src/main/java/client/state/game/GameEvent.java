package client.state.game;

import client.utils.Asset;

import java.awt.*;
import java.util.ArrayList;

import static client.utils.Text.drawString;
import static client.state.game.GameState.*;

public class GameEvent {
    private ArrayList<String> events;
    private int selected = 0;
    private int x;
    private int y;

    private int gameEventSpacing = config.getGameEventSpacing();
    private int gameEventrecent = config.getGameEventrecent();

    GameEvent(int x, int y) {
        events = new ArrayList<>();
        this.x = x;
        this.y = y;
    }

    public void render(Graphics2D g) {
        for (int i = 0; i < gameEventrecent; i++) {
            if (selected + i < 0 || selected + i >= events.size())
                continue;
            if (events.get(i) != null)
                drawString(g, events.get(selected + i), x, y + gameEventSpacing * i, false, Color.white, Asset.font20);
        }
    }

    void reset() {
        events.clear();
        selected = 0;
    }

    int getEventSize() {
        return events.size();
    }

    void updateEvents(String ev) {
        events.add(ev);
        selected = events.size() - gameEventrecent;
        if (selected < 0)
            selected = 0;
    }

    void setSelected(int selected) {
        this.selected = selected;
    }

    int getSelected() {
        return selected;
    }

}
