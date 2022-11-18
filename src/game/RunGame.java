
package game;

import javax.swing.*;

import java.io.IOException;


public class RunGame {
    @SuppressWarnings("unused")
	public static void main(String[] args) throws IOException, FileException {
        JFrame frame = new JFrame("弹弹球");
        frame.setBounds(500, 150, 474, 474);
        frame.setResizable(false);

        LeaderBoard board = new LeaderBoard();
        board.loadLb("LeaderBoard/LeaderBoard.txt");

        JButton b0 = new JButton("查看排名");
        b0.setBounds(180, 240, 95, 30);
        b0.addActionListener(e -> {
            try {
                board.show("LeaderBoard/LeaderBoard.txt");
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });

        JButton b1 = new JButton("开始游戏");
        b1.setBounds(180, 340, 95, 30);
        b1.addActionListener(e -> {
            try {
                new BricksGame(BaseGame.WIDTH, BaseGame.HEIGHT, "弹弹球");
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            frame.setVisible(false);
        });

        JButton b2 = new JButton("退出游戏");
        b2.setBounds(180, 390, 95, 30);
        b2.addActionListener(e -> System.exit(0));

        JButton b3 = new JButton("查看规则");
        b3.setBounds(180, 290, 95, 30);
        b3.addActionListener(e -> {
            JOptionPane.showMessageDialog(frame, "按方向键上键开始游戏\n通过方向键控制板子左右移动使小球反弹，撞碎所有砖块即可胜利", "游戏规则", 1, null);
        });

        ImageIcon bg = new ImageIcon("2dimage/初始背景.png");
        try{
            if(bg == null){
                throw new FileException();
            }
        }catch (FileException e){
            System.out.println(e.getMessage());
        }

        frame.add(b0);
        frame.add(b1);
        frame.add(b2);
        frame.add(b3);
        JLabel label = new JLabel(bg);
        label.setSize(bg.getIconWidth(), bg.getIconHeight());
        frame.getLayeredPane().add(label, Integer.valueOf(Integer.MIN_VALUE));
        JPanel jp=(JPanel)frame.getContentPane();
        jp.setOpaque(false);//设置透明
        frame.setLayout(null);
        frame.setVisible(true);
        frame.validate();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

         // 背景音乐启动
         Music audioPlayWave_1 = new Music("audio/钟离，小子.wav");// 开音乐
         audioPlayWave_1.start();
         try {
             audioPlayWave_1.join();
         } catch (InterruptedException e) {
             e.printStackTrace();
         }
         Music audioPlayWave_2 = new Music("audio/若陀龙王，小子.wav");
         audioPlayWave_2.start();
        
    }
}
