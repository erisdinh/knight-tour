package quynh;

public class Knight {
    private int xPosition;
    private int yPosition;
    
    public Knight() {
        this.xPosition = 0;
        this.yPosition = 0;
    }
    
    public Knight(int x, int y) {
        this.xPosition = x;
        this.yPosition = y;
    }

    public int getxPosition() {
        return xPosition;
    }

    public void setxPosition(int xPosition) {
        this.xPosition = xPosition;
    }

    public int getyPosition() {
        return yPosition;
    }

    public void setyPosition(int yPosition) {
        this.yPosition = yPosition;
    }
}
