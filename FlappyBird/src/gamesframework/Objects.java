package gamesframework;

public class Objects {
    
    private float posX, posY;
    private float w, h;
    
    public Objects(){
         posX = posY = w = h = 0;
    }

    /**
     * hàm Object định nghĩa đối tượng lấy các toạ độ (x,y) , w, h;
     * @param x
     * @param y
     * @param w
     * @param h
     */
    public Objects(float x, float y, float w, float h){
        this.posX = x;
        this.posY = y;
        this.w = w;
        this.h = h;
    }
    public void setPos(float x, float y){
        posX = x;
        posY = y;
    }
    public void setPosX(float x){
        posX = x;
    }
    public void setPosY(float y){
        posY = y;
    }
    public float getPosX(){
        return posX;
    }
    public float getPosY(){
        return posY;
    }
    public float getH(){
        return h;
    }
}
