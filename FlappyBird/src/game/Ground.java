package game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Ground {


    private BufferedImage groundImage;

    private int x1,y1,x2,y2;

    public Ground() {
        try {

            groundImage = ImageIO.read(new File("Assets/ground.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        x1 = 0;
        y1 = 500; // tung độ nơi bắt đầu vẽ mặt đất
        x2 = x1+830; // 830 la chieu dai cua tam anh
        y2 = 500;

    }

    /**
     * Ý tưởng tạo mặt đất là đặt 2 tấm ảnh giống nhau ở cạnh nhau.
     * Ngay sau khi tấm thứ 2 ở vi trí ban đầu của tấm 1 thì di chuyển tấm 1 ra sau tấm thứ 2.
     * vận tốc di chuyển là âm vì đi từ phải sang trái nhưng có độ lớn bằng vận tốc của ống khói.
     */

    public void Update() {
        x1 -= 2;
        x2 -= 2;

        if (x2 < 0) x1 = x2 + 830;
        if (x1 < 0) x2 = x1 + 830;
    }

    // Vẽ ra hình ảnh mặt đất
    public void Paint(Graphics2D g2) {
        g2.drawImage(groundImage,x1,y1,null);
        g2.drawImage(groundImage,x2,y2,null);
    }

    public int getYGround() {
        return y1;
    }

}
