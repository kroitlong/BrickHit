//用于显示背景
package game;

import java.awt.*;

public class Background extends GameObject {
    private Image image;

    public void setImage(Image image){
        this.image=image;
    }
    @Override
    public void draw(Graphics g) {
        g.drawImage(image, x, y, null);
    }

    @Override
    public void onTick(){}
    
}
