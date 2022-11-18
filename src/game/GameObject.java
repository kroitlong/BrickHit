package game;

import java.awt.*;


public abstract class GameObject {
    protected int x;
    protected int y;
    protected int width;
    protected int height;
    protected Color color;
    //protected Scene env;// 游戏对象所在窗口的所有信息
    protected Image image;// 游戏对象的前景图
    private boolean gone;
    public int flag=1;

    public GameObject() {
    }

    public abstract void draw(Graphics g) ;

    public abstract void onTick();

    public int getLeft() {
        return x;
    }

    public int getRight() {
        return x + width;
    }

    public int getTop() {
        return y;
    }

    public int getBottom() {
        return y + height;
    }

    public void setCoordinator(int x, int y){
        this.x = x;
        this.y = y;
    }

    public void transferBy(double x, double y){
        this.x += x;
        this.y += y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public boolean isGone() {
        return gone;
    }

    public void setGone(boolean gone) {
        this.gone = gone;
    }

    protected boolean isSameQuadrant(Point center, Point objA, Point objB) {
        int cX = center.x;
        int cY = center.y;
        int xoA = objA.x, yoA = objA.y, xoB = objB.x, yoB = objB.y;

        int deltaACX = xoA - cX;
        int deltaBCX = xoB - cX;
        int deltaACY = yoA - cY;
        int deltaBCY = yoB - cY;
        if (deltaACX > 0 && deltaBCX > 0) {
            if ((deltaACY > 0 && deltaBCY > 0) || (deltaACY < 0 && deltaBCY < 0)) {
                return true;
            }
            return false;
        } else if (deltaACX < 0 && deltaBCX < 0) {
            if ((deltaACY > 0 && deltaBCY > 0) || (deltaACY < 0 && deltaBCY < 0)) {
                return true;
            }
            return false;
        } else {
            return false;
        }
    }
}
