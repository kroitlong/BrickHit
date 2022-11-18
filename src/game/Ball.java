package game;

import java.awt.*;
import java.awt.event.KeyEvent;


public class Ball extends GameObject {
    private int radius;
    private double speed = 6;// 小球移动速度
    private boolean moving;// 是否在移动
    private boolean gameOver;// 是否over
    private double horiMove;// 水平移动距离(正负号代表移动方向)
    private double vertMove;// 垂直移动距离(正负号代表移动方向)
    public Wood woodBar;//木头板
    private Image image;
    private Point center = new Point();
    private GameOverListener l;


    private int cnt = 0;


    public Ball(int radius_2) {
        this.radius = radius_2 / 2;
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(image, x, y, null);
    }

    @Override
    public void onTick() {
        //启动小球运动
        if (!moving) {
            if (Input.getKeyDown(KeyEvent.VK_UP) || Input.getKeyDown(KeyEvent.VK_W)) {
                int sign = (Math.random() * 10) <= 4 ? 1 : -1;
                double r1 = Math.random() * 0.1 * sign + 1;
                double r2 = Math.random() * 0.1 * sign + 1;
                moving = true;
                gameOver = false;
                horiMove = (Math.random() * 10) <= 4 ? speed * r2 : -speed * r2;
                vertMove = -speed * r1;
            }
        }

        if (moving) {
            if ((x <= 25 && flag == 1) || x >= BaseGame.WIDTH - radius * 2 - 13) {
                BricksGame.score++;
                Music wallhit = new Music("audio/撞击墙壁.wav");
                wallhit.start();
                if (x < 25) {
                    horiMove = -horiMove;
                    flag = 0;
                }
                if (x >= BaseGame.WIDTH - radius * 2 - 29)
                    horiMove = -horiMove;

            }
            if (x >= 25 && flag == 0) {
                flag = 1;
            }
            // 小球落到了上面，其中22为边距
            if (y <= 35 && vertMove < 0) {
                BricksGame.score++;
                Music wallhit = new Music("audio/撞击墙壁.wav");
                wallhit.start();
                vertMove = -vertMove;
            }

            // 小球落在了挡板上
            if (getCenter().x >= woodBar.getX()
                    && getCenter().x <= woodBar.getX() + woodBar.getWidth()
                    && Math.abs(y - woodBar.y) <= radius + 1
                    && vertMove > 0
            ) {
                Music woodhit = new Music("audio/撞击板子.wav");
                woodhit.start();
                BricksGame.score -= 4;
                vertMove = -vertMove;
                if (woodBar.movingCondition() == 1 && cnt <= 2) {
                    cnt++;
                    horiMove = horiMove > 0 ? horiMove * 1.05 : horiMove * 0.9;
                } else if (woodBar.movingCondition() == -1 && cnt >= -2) {
                    cnt--;
                    horiMove = horiMove > 0 ? horiMove * 0.9 : horiMove * 1.05;
                }

            }

            // 小球落在了窗口的底部，停住小球 GAME OVER
            if (y >= BaseGame.HEIGHT - radius * 2) {
                moving = false;
                gameOver = true;
                if (l != null)
                    l.over();
                return;
            }

            this.transferBy(horiMove, vertMove);
        }

    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public Wood getWoodBar() {
        return woodBar;
    }

    public void setWoodBar(Wood woodBar) {
        this.woodBar = woodBar;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public boolean isMoving() {
        return moving;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getHoriMove() {
        return horiMove;
    }

    public void setHoriMove(double horiMove) {
        this.horiMove = horiMove;
    }

    public double getVertMove() {
        return vertMove;
    }

    public void setVertMove(double vertMove) {
        this.vertMove = vertMove;
    }

    @Override
    public int getWidth() {
        return 2 * radius;
    }

    @Override
    public int getHeight() {
        return getWidth();
    }

    public Point getCenter() {
        center.x = x + radius;
        center.y = y + radius;
        return center;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public GameOverListener getGameOverListener() {
        return l;
    }

    public void setGameOverListener(GameOverListener l) {
        this.l = l;
    }

    public interface GameOverListener {
        void over();
    }
}
