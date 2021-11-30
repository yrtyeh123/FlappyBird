package gamesframework;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;


public class Animation {
    
    private long beginTime = 0;
    
    private long mesure;
    
    private AFrameOnImage[] frames;
    private int NumOfFrame = 0;
    private int CurrentFrame = 0;
    
    public Animation(long mesure){
        this.mesure = mesure;
    }

    /**
     * nếu số lượng frame nhập vào và vẫn còn đợi > 0.
     *     nếu tổng thời gian - thời gian ban đầu > thời gian hàng chờ thì cộng hàng đợi current_frame
     *         đến khi current-frame bằng số lượng frame nhập vào thì
     *         reset lại bằng 0 và đặt lại thời gian ban đầu = tổng thời gian để kết thúc vòng lăp.
     * @param deltaTime
     */
    public void Update_Me(long deltaTime){
        if(NumOfFrame>0){
            if(deltaTime - beginTime > mesure){
                CurrentFrame++;
                if(CurrentFrame>=NumOfFrame) 
                    CurrentFrame = 0;
                beginTime = deltaTime;
            }
        }
    }

    /**
     * Nhập vào từng frame ảnh vào mảng và tăng biến đếm frames
     * @param sprite
     */
    public void AddFrame(AFrameOnImage sprite){
        AFrameOnImage[] bufferSprites = frames;
        frames = new AFrameOnImage[NumOfFrame+1];
        for(int i = 0;i<NumOfFrame;i++) frames[i] = bufferSprites[i];
        frames[NumOfFrame] = sprite;
        NumOfFrame++;
    }
    // In ra hoạt ảnh của frame hiện tại
    public void PaintAnims(int x, int y, BufferedImage image, Graphics2D g2, int anchor, float rotation){
        frames[CurrentFrame].Paint(x, y, image, g2, anchor, rotation);
    }
}
