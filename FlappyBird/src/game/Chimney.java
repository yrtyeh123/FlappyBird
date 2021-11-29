package game;

import gamesframework.Objects;

import java.awt.*;

public class Chimney extends Objects {

    private Rectangle rect;

    private boolean isBehindBird = false;

    public Chimney(int x, int y, int w, int h) {
        super(x,y,w,h);
        rect = new Rectangle(x,y,w,h);
    }

    public void Update() {
        setPosX(getPosX()-2); // Toạ độ mới bằng toạ độ cũ trừ đi 2.
        rect.setLocation((int) this.getPosX(),(int) this.getPosY());
    }

    public Rectangle getRect() {
        return rect;
    }

    public void setIsBehindBird(boolean b) {
        isBehindBird = b;
    }

    public boolean getIsBehindBird() {
        return isBehindBird;
    }

}
