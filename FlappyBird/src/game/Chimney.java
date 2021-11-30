package game;

import gamesframework.Objects;

import java.awt.*;

public class Chimney extends Objects {

    private Rectangle rect;

    private boolean isBehindBird = false;

    public Chimney(int x, int y, int w, int h) {
        super(x,y,w,h);
        rect = new Rectangle(x,y,w,h); // Lấy toạ độ của ống khói (toạ độ (x,y) chỉ nơi vẽ hình và (w,h) chỉ độ rộng và cao của hình vẽ
    }

    public void Update() {
        setPosX(getPosX()-2); // Up date toạ độ mới bằng toạ độ cũ -2 bằng với độ lớn vận tốc của mặt đất.
        rect.setLocation((int) this.getPosX(),(int) this.getPosY());
    }

    public Rectangle getRect() {
        return rect;
    }

    public void setIsBehindBird(boolean b) { // hàm này để check xem con chim đã ra sau cặp ống khói chưa nếu ra rồi thì trả về true.
        isBehindBird = b;
    }

    public boolean getIsBehindBird() {
        return isBehindBird;
    }

}
