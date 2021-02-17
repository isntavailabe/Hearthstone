package client.buttons;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;

public class CircleButton extends Button {
    private int r;
    private BufferedImage in;

    public CircleButton(int x, int y, int r, Action action) {
        super(action);
        boundingBox = new Ellipse2D.Double(x, y, r, r);
    }

    public CircleButton(int x, int y, int r, Action action, BufferedImage in) {
        super(action);
        XPos = x;
        YPos = y;
        this.in = in;
        this.r = r;
        boundingBox = new Ellipse2D.Double(x, y, r, r);
    }

    public void render(Graphics2D g){
        if(hover)
            g.drawImage(in, XPos, YPos, r + 10, r + 10, null);
        else
            g.drawImage(in, XPos, YPos, r, r, null);
    }
}
