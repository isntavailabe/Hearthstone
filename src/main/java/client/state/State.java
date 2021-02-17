package client.state;


import java.awt.*;


public abstract class State {
    protected static State currentState;
    protected Handler handler;

    public State(Handler handler) {
        this.handler = handler;
    }

    public static State getCurrentState() {
        return currentState;
    }

    public abstract void update();

    public abstract void render(Graphics2D g);

    public static void setCurrentState(State state) {
        currentState = state;
    }
}
