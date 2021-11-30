package gamesframework;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;

public abstract class GameScreen extends JFrame implements KeyListener{

    public static int KEY_PRESSED = 1;
    public static int KEY_RELEASED = 0;
    
    public int CUSTOM_WIDTH  = 500;
    public int CUSTOM_HEIGHT = 500;
    
    private GameThread G_Thread;

    public static int MASTER_WIDTH = 500, MASTER_HEIGHT = 500;
    
    public GameScreen(){
        InitThread();
        InitScreen();
    }
    
    public GameScreen(int w, int h){
        this.CUSTOM_WIDTH = w;
        this.CUSTOM_HEIGHT = h;
        MASTER_WIDTH = CUSTOM_WIDTH;
        MASTER_HEIGHT = CUSTOM_HEIGHT;
        /**
         * Toolkit trong Java AWT dùng để lấy độ phân giải màn hình.
         * Bằng việc lấy được độ phần giải màn hình em có thể căn chỉnh
         * sao cho phần chơi game ở chính giữa của khung hình.
         */
        Toolkit toolkit = this.getToolkit();
        Dimension dimension = toolkit.getScreenSize();
        this.setBounds((dimension.width - CUSTOM_WIDTH)/2,(dimension.height-CUSTOM_HEIGHT)/2,CUSTOM_WIDTH,CUSTOM_HEIGHT);
        InitThread();
        InitScreen();
    }

    private void InitScreen(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // đóng trò chơi bằng cách bấm nút X
        this.addKeyListener(this);
        setSize(CUSTOM_WIDTH, CUSTOM_HEIGHT); // lấy kích thước của game = CUSTOM_WIDTH VÀ CUSTOM_HEIGHT
        setVisible(true);
        
    }
    
    public void BeginGame(){
        G_Thread.StartThread();
    }
    
    private void InitThread(){
        G_Thread = new GameThread(this);
        add(G_Thread);
    }
    
    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            KEY_ACTION(e, GameScreen.KEY_PRESSED);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        KEY_ACTION(e, GameScreen.KEY_RELEASED);
    }
    
    public abstract void GAME_UPDATE(long deltaTime);
    public abstract void GAME_PAINT(Graphics2D g2);
    public abstract void KEY_ACTION(KeyEvent e, int Event);
    
}
