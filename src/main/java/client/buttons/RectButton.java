package client.buttons;

import java.awt.*;
import java.awt.image.BufferedImage;

public class RectButton extends Button {
    private int width;
    private int height;
    private BufferedImage in;

    public RectButton(int x, int y, int width, int height, Action action) {
        super(action);
        XPos = x;
        YPos = y;
        boundingBox = new Rectangle(x, y, width, height);
    }

    public RectButton(int x, int y, int width, int height, Action action, BufferedImage in) {
        super(action);
        XPos = x;
        YPos = y;
        this.in = in;
        this.width = width;
        this.height = height;
        boundingBox = new Rectangle(x, y, width, height);
    }

    public void render(Graphics2D g){
        if(hover)
            g.drawImage(in, XPos - 10, YPos - 10, width + 10, height + 10, null);
        else
            g.drawImage(in, XPos - 5, YPos - 5, width, height, null);
    }
}
