package game;

import java.awt.*;
import java.io.IOException;

//在BricksGame里实现该接口中的方法
public interface RunGameable {
    //初始化游戏界面
    void initGame(int width, int height);

    //绘制游戏内容
    void paint(Graphics g);

    void exit();

    void clear(Graphics g);

    void drawWin() throws IOException;





}
