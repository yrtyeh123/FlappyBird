package game;

import gamesframework.Objects;
import gamesframework.SoundPlayer;

import java.awt.*;
import java.io.File;

public class Bird extends Objects {

    private float vt = 0; // toc do roi  hien tai = 0.

    private boolean isFlying = false;

    private Rectangle rect;

    private boolean isLive = true;

    public SoundPlayer flySound, fallSound, getpointSound;

    public Bird(int x, int y, int w, int h) {
        super(x,y,w,h);
        rect = new Rectangle(x,y,w,h);

        flySound = new SoundPlayer(new File("Assets/fap.wav"));
        fallSound = new SoundPlayer(new File("Assets/fall.wav"));
        getpointSound = new SoundPlayer(new File("Assets/getpoint.wav"));
    }

    public void setLive(boolean b) {
        isLive = b;
    }
    public boolean getLive() {
        return isLive;
    }

    public Rectangle getRect() {
        return rect;
    }

    public void setVt(float vt) {
        this.vt = vt;
    }

    public void update(long deltaTime) {

        vt += FlappyBird.Gravity;
        this.setPosY(this.getPosY()+vt);
        this.rect.setLocation((int)this.getPosX(),(int) this.getPosY());

        if (vt < 0) {
            isFlying = true;
        } else isFlying = false;

    }

    public void fly() {
        vt = -3;
        flySound.play();
    }

    public boolean getIsFlying() {
        return isFlying;
    }
}
