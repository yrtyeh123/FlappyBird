package game;

import gamesframework.QueueList;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class ChimneyGroup {

    private QueueList<Chimney> chimneys;

    private BufferedImage chimneyImage, chimneyImage2;

    public static int SIZE = 6;

    /**
     * Vì theo lý thuyết ta vẽ từ trên xuống dưới từ trái qua phải(tức trục tung y dương hướng xuống dưới)
     * nên khi vẽ hình ta tuân thủ 2 chú ý :
     *  1; Vẽ hình ống ngược : tung độ vẽ ông ngược + min của deltaY(= 0) + 400 > 0
     *  2. Vẽ hình xuôi: tung độ vẽ ống ngược + max của deltaY(=300) < 500
     *  (vì độ cao màn hình 625 đã mất 125 đơn vị để vẽ animation cho mặt đất) nếu vượt 500 thì có thể ko thấy ống)
     */

    private int topChimneyY = -350;
    private int bottomChimneyY = 180;

    public Chimney getChimney(int i) {
        return chimneys.get(i);
    }

    public int getRandomY(){
        Random random = new Random();
        int a;
        a = random.nextInt(10);

        return a*30;
    }

    public ChimneyGroup() {

        try {
            chimneyImage = ImageIO.read(new File("Assets/chimney.png"));
            chimneyImage2 = ImageIO.read(new File("Assets/chimney_.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void resetChimneys(){

        chimneys = new QueueList<>();

        Chimney cn;
        /**
         * Hàm này để lấy toạ độ in của các cặp ống khói rồi chuyển vào trong hàng đợi QueueList<>
         * deltaY được biến số ngẫu nhiên bởi hàm getRandom để thay đổi vị trí của các cột khói.
         */

        for(int i = 0; i< SIZE/2;i++){

            int deltaY = getRandomY();

            cn = new Chimney(830+i*300, bottomChimneyY + deltaY, 74, 400);
            chimneys.push(cn);

            cn = new Chimney(830+i*300, topChimneyY + deltaY, 74, 400);
            chimneys.push(cn);
        }
    }

    public void Update() {
        /**
         * Đây là vòng lặp để xét vận tốc của những cái cột ống khói bằng hàm Update trong class Chimney
         */
        for (int i = 0; i < SIZE; i++) {
            chimneys.get(i).Update();
        }
        /**
         * Vì độ rộng của cột bằng 74 nên khi hoành độ của cột(0) thứ nhất < -74 thì cột thứ nhất đã khuất phía sau màn hình game.
         * Khi đó ta chuyển hình ảnh của cột thứ nhất ra phía sau cột thứ(4) thứ năm để sinh cột mới và dựa vào hoành độ ở địa điểm đó để vẽ hoàn thành cặp trụ.
         * Đồng thời đặt hàm setIsBehindBird về 0 vì ở cột mới xây dựng con chim chưa vượt qua.
         * Cuối cùng là đưa vào hàng chờ chimneys của class QueueList.
         */
        if (chimneys.get(0).getPosX() < -74) {

            int deltaY = getRandomY();

            Chimney cn;

            cn = chimneys.pop();
            cn.setPosX(chimneys.get(4).getPosX() + 300);
            cn.setPosY(bottomChimneyY + deltaY);
            cn.setIsBehindBird(false);
            chimneys.push(cn);

            cn = chimneys.pop();
            cn.setPosX(chimneys.get(4).getPosX());
            cn.setPosY(topChimneyY + deltaY);
            cn.setIsBehindBird(false);
            chimneys.push(cn);
        }
    }

    public void Paint(Graphics2D g2) {
        /**
         * Vòng lặp để in ra ảnh cột ống khói . nếu chẵn thì in cột xuôi, nếu lẻ thì in cột ngược.
         */
        for (int i = 0; i < SIZE; i++) {
            if (i % 2 == 0) {
                g2.drawImage(chimneyImage, (int) chimneys.get(i).getPosX(), (int) chimneys.get(i).getPosY(), null);
            } else {
                g2.drawImage(chimneyImage2, (int) chimneys.get(i).getPosX(), (int) chimneys.get(i).getPosY(), null);

            }
        }
    }

}
