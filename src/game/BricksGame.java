package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class BricksGame extends BaseGame implements RunGameable{
    private ArrayList<GameObject> list = new ArrayList<>();
    private RenderTask render;
    private boolean over;
    private Image memImage;
    private Graphics memG;// 双缓冲的画布

    public static int score = 0;
    int num = 0;
    LeaderBoard leaderBoard = new LeaderBoard();

    public BricksGame(int width, int height, String title) throws HeadlessException, IOException {
        super.setBgColor(new Color(33, 34, 63, 202));
        initGame(width, height);
        this.leaderBoard.loadLb("LeaderBoard/LeaderBoard.txt");
    }

    @Override
    public void initGame(int width, int height) {
        score = 0;
        super.setFps(60);
        over = false;
        // 设置图片路径，注意不要乱调顺序，否则可能发生图片覆盖问题
        ImageIcon iconbg = new ImageIcon("2dimage/zhongli_background.png");// 背景图片路径
        Background bg = new Background();
        bg.setX(15);
        bg.setY(0);
        bg.setImage(iconbg.getImage());
        list.add(bg);

        ImageIcon woodIcon = new ImageIcon("2dimage/wood1.png");// 板子图片路径
        int w = woodIcon.getIconWidth();
        int h = woodIcon.getIconHeight();
        Wood wood = new Wood(w, h);
        wood.setImage(woodIcon.getImage());
        wood.setX(getWidth() / 2 - w / 2);
        wood.setY(getHeight() - 100);
        wood.setImage(woodIcon.getImage());
        list.add(wood);

        ImageIcon iconBall = new ImageIcon("2dimage/rockball.png");// 球的图片路径
        Ball ball = new Ball(iconBall.getIconWidth());
        ball.setImage(iconBall.getImage());
        ball.setCoordinator(width / 2 - ball.getRadius(), wood.getY() - ball.getRadius() * 2);
        ball.setWoodBar(wood);
        list.add(ball);

        ImageIcon iconBorder = new ImageIcon("2dimage/boder2.png");// 边框图片路径
        Borders border = new Borders();
        border.setX(5);
        border.setY(30);
        border.setImage(iconBorder.getImage());
        list.add(border);

        Image[] images = new Image[5];
        for (int i = 0; i < 5; i++) {
            images[i] = new ImageIcon("2dimage/brick_" + i + ".png").getImage();// 砖块图片路径
        }

        int brW = 86;
        int brH = 34;
        int start = 100;
        int brickX = start;
        int brickY = 100;
        int brickAreaWidth = getWidth() - start * 2;
        int count = brickAreaWidth / brW - 1;
        int intervalHort = brW + 3;
        int intervalVert = brH + 3;
        int count_j1 = 6;
        // 砖块生成
        HitBrick hitBrick = new HitBrick();
        Random rand_j = new Random();
        int count_j = rand_j.nextInt(2) + count_j1;

        for (int j = 0; j < count_j; j++) {// brick line
            Random rand = new Random();
            int count_1 = rand.nextInt(count) % (count + 1);
            if (count_1 <= 4)
                count_1 = count_1 * 3;// 保证一排的数目不要太少
            start = brickX = (getWidth() - intervalHort * (count_1)) / 2;
            for (int i = 0; i < count_1; i++) {// brick columns
                num++;
                Brick brick = new Brick();
                brick.setImage(images[i % images.length]);
                switch (i % images.length) {
                    case 1:
                        brick.flag = 1;// 水
                        break;
                    case 2:
                        brick.flag = 2;// 冰
                        break;
                    case 3:
                        brick.flag = 3;// 火
                        break;
                    case 4:
                        brick.flag = 4;// 雷
                        break;
                    default:
                        break;
                }
                brick.setWidth(brW);
                brick.setHeight(brH);
                brick.setX(brickX);
                brick.setY(brickY);
                brick.setBall(ball);
                brick.setHitListener(hitBrick);
                brickX += intervalHort;
                list.add(brick);
            }
            brickX = start;
            brickY += intervalVert;
        }
        // 双缓冲，在内存里面创建一个和窗口JFrame一样大小的Image
        memImage = createImage(getWidth(), getHeight());
        memG = memImage.getGraphics();
        GameOver gameOver = new GameOver(memG);
        ball.setGameOverListener(gameOver);

        // 键盘事件的监听
        Input input = new Input();
        input.init();
        addKeyListener(input);

        // 重新渲染画面任务
        render=new RenderTask(this);
        render.start();
    }

    @Override
    public void paint(Graphics g) {
        clear(memG);// 将画布清空为背景色

        for (int i = 0; i < list.size(); i++) {
            list.get(i).onTick();
            list.get(i).draw(memG);
        }

        if (list.size() == 4) {// 只剩下小球和挡板，则通关成功！
            list.clear();
            try {
                drawWin();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        if (over) {
            try {
                new GameOver().drawOver();
                Music gameover = new Music("audio/失败音效_1.wav");
                gameover.start();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            over = false;
        }

        // 将内存Image的内容复制到窗口上
        g.drawImage(memImage, 0, 0, null);

        // 耗性能的轮询判断，一个对象是否要消失
        for (int i = 4; i < list.size(); i++) {// 0,1位置是挡板和小球，不能消失
            GameObject gameObject = list.get(i);
            if (gameObject.isGone()) {
                list.remove(i);
                --i;
            }
        }
    }
    @Override
    public void exit() {
    }
    @Override
    public void clear(Graphics g) {
        if (g != null) {
            g.setColor(getBgColor());
            g.fillRect(0, 0, getWidth(), getHeight());
        }
    }

    public void drawWin() throws IOException {
        JFrame f = new JFrame("恭喜通关！");
        JButton b1 = new JButton("再玩一次");
        JButton b2 = new JButton("查看排名");
        JButton b3 = new JButton("退出游戏");
        int lastScore = (int) (100 * score * 1.2 / Math.log10(num * 1.0));
        UserRecord user = new UserRecord(lastScore);
        leaderBoard.add(user);
        leaderBoard.writeLb("LeaderBoard/LeaderBoard.txt");
        JTextField jt = new JTextField("您的得分为：" + lastScore);
        jt.setBounds(50, 60, 180, 40);
        jt.setFont(new Font("宋体", Font.BOLD, 22));
        jt.setEditable(false);
        b1.setBounds(90, 100, 95, 30);
        b2.setBounds(90, 150, 95, 30);
        b3.setBounds(90, 200, 95, 30);
        b1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                render.setExitd(true);
                render = null;
                memImage = null;
                memG = null;
                list.clear();

                initGame(BaseGame.WIDTH, BaseGame.HEIGHT);
                f.setVisible(false);
            }

        });
        b2.addActionListener(e -> {
            try {
                leaderBoard.show("LeaderBoard/LeaderBoard.txt");
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        });
        b3.addActionListener(e -> System.exit(1));
        f.add(jt);
        f.add(b1);
        f.add(b2);
        f.add(b3);
        f.setBounds(600, 300, 350, 350);
        f.setLayout(null);
        f.setVisible(true);
    }

    // 小球碰到砖块的回调
    class HitBrick implements Brick.HitListener {
        @Override
        public void hit(Brick brick) {
            ExplodeObject eo = new ExplodeObject();
            eo.x = brick.x;
            eo.y = brick.y;
            eo.width = brick.width;
            eo.height = brick.height;
            eo.color = brick.color;
            list.add(eo);
            score += 5;
            System.out.println("score = " + score);
        }

    }

    // 游戏结束内容的绘制
    class GameOver implements Ball.GameOverListener {

        public GameOver(Graphics g) {
        }

        public GameOver() {

        }

        @Override
        public void over() {
            over = true;
        }

        public void drawOver() throws IOException {
            JFrame f = new JFrame("游戏结束！");
            JButton b1 = new JButton("重新开始");
            JButton b2 = new JButton("查看排名");
            JButton b3 = new JButton("退出游戏");
            int lastScore = (int) (100 * score / Math.log10(num * 1.0));
            UserRecord user = new UserRecord(lastScore);
            leaderBoard.add(user);
            leaderBoard.writeLb("LeaderBoard/LeaderBoard.txt");
            JTextField jt = new JTextField("您的得分为：" + lastScore);
            jt.setBounds(50, 60, 180, 40);
            jt.setFont(new Font("宋体", Font.BOLD, 22));
            jt.setEditable(false);
            b1.setBounds(90, 100, 95, 30);
            b2.setBounds(90, 150, 95, 30);
            b3.setBounds(90, 200, 95, 30);
            b1.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    render.setExitd(true);
                    render = null;
                    memImage = null;
                    memG = null;
                    // removeKeyListener(i);
                    list.clear();
                    f.setVisible(false);
                    initGame(BaseGame.WIDTH, BaseGame.HEIGHT);
                }

            });
            b2.addActionListener(e -> {
                try {
                    leaderBoard.show("LeaderBoard/LeaderBoard.txt");
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            });
            b3.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.exit(1);
                }
            });
            f.add(b1);
            f.add(b2);
            f.add(b3);
            f.add(jt);
            f.setBounds(600, 300, 350, 350);
            f.setLayout(null);
            f.setVisible(true);
        }
    }
}
