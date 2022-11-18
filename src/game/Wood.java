package game;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Wood extends GameObject{
    public double speed = 12;
    private Image image;

    @Override
    public Image getImage() {
        return image;
    }

    @Override
    public void setImage(Image image) {
        this.image = image;
    }

    public int movingCondition() {
        return movingCondition;
    }
    private int movingCondition;

    public Wood() {}

    public Wood(int width, int height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(image, x, y,null);
    }

    @Override
    public void onTick() {
        if (Input.getKeyDown(KeyEvent.VK_LEFT)){
            movingCondition = -1;
            this.transferBy(-1 * speed, 0);
        }
        else if(Input.getKeyDown(KeyEvent.VK_RIGHT)){
            movingCondition = 1;
            this.transferBy(speed, 0);
        }else {
            movingCondition = 0;
        }

    }

    @Override
    public void transferBy(double offsetX, double offsetY) {
        super.transferBy(offsetX, offsetY);
        if (super.x <29 )
            super.x = 28;
        if (super.x > BaseGame.WIDTH - this.width-32)
            super.x = BaseGame.WIDTH - this.width-32;
    }

    public double getSpeed(){
        return speed;
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

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

}
