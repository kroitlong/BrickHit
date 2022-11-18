//用于显示边框
package game;
import java.awt.*;
public class Borders extends GameObject {
    private Image image;

    Borders(){}

    public void setImage(Image image){
        this.image=image;
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(image, x, y, null);
    }

    @Override
    public void onTick() {}

}
