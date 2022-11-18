package game;

import javax.swing.*;
import java.awt.*;

public class BaseGame extends JFrame {
    public static final int WIDTH = 1366;
    public static final int HEIGHT = 768;
    public static final String TITLE = "打砖块";
    private Color bgColor;
    private int fps;
    

    public BaseGame() throws HeadlessException {
        setLocation(60, 60);
        setTitle(TITLE);
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(false);
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(bgColor);
        g.fillRect(0,0, WIDTH, HEIGHT);
    }

    public Color getBgColor() {
        return bgColor;
    }

    public void setBgColor(Color bgColor) {
        this.bgColor = bgColor;
    }

    public int getFps() {
        return fps;
    }

    public void setFps(int fps) {
        this.fps = fps;
    }

}
