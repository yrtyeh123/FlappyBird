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
        super(x, y, w, h);
        rect = new Rectangle(x, y, w, h); // Lấy toạ độ của con chim như cách lấy toạ độ của mặt đất

        flySound = new SoundPlayer(new File("Assets/fap.wav")); // tiếng chim lúc bay
        fallSound = new SoundPlayer(new File("Assets/fall.wav")); // tiếng chim lúc chạm cột hoặc rơi xuống đất
        getpointSound = new SoundPlayer(new File("Assets/getpoint.wav")); // tiếng kêu mỗi khi ghi được thêm điểm số
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
        /**
         * ngoài vận tốc mặc định khi bay chim còn chịu trọng lực G của Trái Đất nên:
         * vận tốc theo trục Oy được biểu diễn bằng Gravity
         */
        vt += FlappyBird.Gravity; //
        this.setPosY(this.getPosY() + vt);
        this.rect.setLocation((int) this.getPosX(), (int) this.getPosY());

        if (vt < 0) {
            isFlying = true;
        } else isFlying = false;

    }

    public void fly() {
        vt = -3; //  vận tốc theo trục hoành của con chim.
        flySound.play();
    }

    public boolean getIsFlying() {
        return isFlying;
    }
}
