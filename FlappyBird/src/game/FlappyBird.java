package game;

import gamesframework.AFrameOnImage;
import gamesframework.Animation;
import gamesframework.GameScreen;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class FlappyBird extends GameScreen {

    private BufferedImage birds;
    private BufferedImage beginImage;
    private BufferedImage endImage;
    private BufferedImage scorebroadImage;
    private BufferedImage bronzemedalImage;
    private BufferedImage silvermedalImage;
    private BufferedImage goldenmedalImage;

    private Animation bird_anim;

    public static float Gravity = 0.2f;

    private Bird bird;
    private Ground ground;
    private BackGround backGround;

    private ChimneyGroup chimneyGroup;

    private int SCORE = 0;
    private int BEST = 0;

    private int BEGIN_SCREEN = 0;
    private int GAMEPLAY_SCREEN = 1;
    private int GAMEOVER_SCREEN = 2;

    private int CurrentScreen = BEGIN_SCREEN;

    public FlappyBird() {
        super(800,600);

        try {
            birds = ImageIO.read(new File("Assets/bird_sprite.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }

        bird_anim = new Animation(70); // milis

        AFrameOnImage f;
        f = new AFrameOnImage(0,0,60,60);
        bird_anim.AddFrame(f);
        f = new AFrameOnImage(60,0,60,60);
        bird_anim.AddFrame(f);
        f = new AFrameOnImage(120,0,60,60);
        bird_anim.AddFrame(f);
        f = new AFrameOnImage(60,0,60,60);
        bird_anim.AddFrame(f);

        bird = new Bird(350,250,40,40);
        backGround = new BackGround();
        ground = new Ground();

        chimneyGroup = new ChimneyGroup();

        BeginGame();
    }

    public static void main(String[] args) {
        new FlappyBird();
    }

    /**
     * Khi chim chết ta đặt lại thông số cho chú chim và bắt đầu vòng chơi mới:
     * - Toạ độ ban đầu (350,250)
     * - Vận tốc ban đầu về 0
     * - Đặt lại trạng thái "Còn sống"
     * - Đặt lại điểm số về 0
     * - Chạy hàm resetChimneys để bắt đầu trò chơi.
     */
    private void resetGame() {
        bird.setPos(350,250);
        bird.setVt(0);
        bird.setLive(true);
        SCORE = 0;
        chimneyGroup.resetChimneys();
    }

    @Override
    public void GAME_UPDATE(long deltaTime) {
        backGround.Update();
        if (CurrentScreen == BEGIN_SCREEN) {
            resetGame();
        } else if (CurrentScreen == GAMEPLAY_SCREEN) {

            if (bird.getLive())bird_anim.Update_Me(deltaTime);
            bird.update(deltaTime);

            ground.Update();

            chimneyGroup.Update();

            for (int i=0; i < chimneyGroup.SIZE; i++) {
                if (bird.getRect().intersects(chimneyGroup.getChimney(i).getRect())) {
                    bird.setLive(false);
                    bird.fallSound.play();
                    CurrentScreen = GAMEOVER_SCREEN;
                }
            }
            /**
             * Xét vòng lặp cho các ống khói.
             * Nếu hoành độ của chim > hoành độ của ống khói và hàm chứng mình chim đã ở phía sau ống khói và chia 2 dư 1
             * Lý do chia 2 vì chỉ số cặp cột = SIZE/2.
             */
            for (int i = 0; i < ChimneyGroup.SIZE; i++) {
                if ((bird.getPosX() > chimneyGroup.getChimney(i).getPosX()+74) && (!chimneyGroup.getChimney(i).getIsBehindBird())  && (i % 2 == 0)) {
                    SCORE++;
                    bird.getpointSound.play();
                    chimneyGroup.getChimney(i).setIsBehindBird(true);
                }
                BEST = Math.max(BEST, SCORE);
            }
            /**
             * Khi mà tổng tung độ và độ dài hoạt ảnh chim > tung độ của mặt đất thì chim chạm đất -> chết
             */
            if (bird.getPosY() + bird.getH() >= ground.getYGround()) {
                bird.fallSound.play();
                CurrentScreen = GAMEOVER_SCREEN;
            }
        }
    }

    @Override
    public void GAME_PAINT(Graphics2D g2) {

        //g2.setColor(Color.decode("#b8daef")); //  bảng màu nền

        /**
         * ta xét tô màu toàn màn hình.
         * Lý do dùng MASTER_WIDTH và MASTER_HEIGHT để khi kéo mở độ lớn tab game ko bị tràn hình ảnh vì 2 biến đó cố định.
         * Còn CUSTOM_WIDTH vs CUSTOM_HEIGHT sẽ thay đổi theo cách ta kéo thả tab game.
         */
        g2.fillRect(0, 0, MASTER_WIDTH, MASTER_HEIGHT);
        backGround.Paint(g2);
        chimneyGroup.Paint(g2);
        ground.Paint(g2);

        /**
         * Vẽ hoạt ảnh cho chim.
         * Nếu đang bấm phim để bay thì quay lên trên ,về sau 45 độ còn nếu thả phím thì quay về hoạt ảnh ban đâu.
         */
        if (bird.getIsFlying()) {
            bird_anim.PaintAnims((int) bird.getPosX(), (int) bird.getPosY(), birds, g2, 0, -1);
        } else bird_anim.PaintAnims((int) bird.getPosX(), (int) bird.getPosY(), birds, g2, 0, 0);



        if (CurrentScreen == BEGIN_SCREEN) {
            try {
                beginImage = ImageIO.read(new File("Assets/Begin_Screen.png"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            g2.drawImage(beginImage, 0,100,null);
            g2.setFont(new Font("Arial", 1, 25));
            g2.setColor(Color.BLACK);
            g2.drawString("SPACE TO PLAY GAME",250,350);
        }
        if (CurrentScreen == GAMEPLAY_SCREEN) {
            g2.setFont(new Font("Arial", 1, 50)); // lấy phông chữ Arial, cỡ chỡ 25
            g2.setColor(Color.RED);
            g2.drawString(""+ SCORE, 390, 50);
        }
        if (CurrentScreen == GAMEOVER_SCREEN) {
            try {
                endImage = ImageIO.read(new File("Assets/Gameover.png"));
                scorebroadImage = ImageIO.read(new File("Assets/bangdiem.PNG"));
                bronzemedalImage = ImageIO.read(new File("Assets/huychuongdong.PNG"));
                silvermedalImage = ImageIO.read(new File("Assets/huychuongbac.PNG"));
                goldenmedalImage = ImageIO.read(new File("Assets/huychuongvang.PNG"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            g2.setFont(new Font("Arial", 1, 25)); // lấy phông chữ Arial, cỡ chỡ 25
            g2.drawImage(endImage,304,200,null);
            g2.drawImage(scorebroadImage,230,300,null);
            g2.setColor(Color.BLACK);
            g2.drawString(""+SCORE, 500, 375);
            g2.drawString(""+BEST,500,435);

            if (0 <= SCORE && SCORE <= 20) {
                g2.drawImage(bronzemedalImage,275,370,null);
            } else if (21<SCORE && SCORE <99) {
                g2.drawImage(silvermedalImage,275,370,null);
            } else g2.drawImage(goldenmedalImage,275,370,null);
            g2.setColor(Color.BLUE);
            g2.drawString("Press space to come back BEGIN_SCREEN",140,550);
        }


    }

    /**
     * Xét khi bấm phím :
     * - Nếu màn hình đang là bắt đầu thì chuyển sang màn hình chơi game.
     * - Nếu chim còn sống thì đặt mặc định bấm phím là chim bay.
     * - Nếu màn hình là kết thúc thì bấm phím để chuyển về màn hình bắt đầu game.
     */
    @Override
    public void KEY_ACTION(KeyEvent e, int Event) {
        if (Event == KEY_PRESSED) {
            if (CurrentScreen == BEGIN_SCREEN) {
                CurrentScreen = GAMEPLAY_SCREEN;
            } else if (CurrentScreen == GAMEPLAY_SCREEN) {
                if (bird.getLive())bird.fly();
            } else if (CurrentScreen == GAMEOVER_SCREEN) {
                CurrentScreen = BEGIN_SCREEN;
            }

        }
    }
}
