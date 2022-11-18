package game;

import java.io.*;

import javax.swing.*;

import java.awt.*;

public class LeaderBoard {
    static final int MAX_LENGTH = 20;
    public UserRecord[] lb = new UserRecord[MAX_LENGTH];
    public int length = 0;

    public LeaderBoard() {
    }

    public void add(UserRecord user){
        if(length == 0){
            lb[0] = user;
            lb[0].ranking = 1;
            length ++ ;
        }
        else {
            for (int i = 0; length <= MAX_LENGTH && i < length ; i++) {
                if (lb[i].gameScore < user.gameScore) {
                    int h;
                    if(length == MAX_LENGTH){
                        h = length - 2;
                    }
                    else {
                        h = length - 1;
                    }
                    for (int j = h; j >= i; j--) {
                        lb[j + 1] = lb[j];
                        lb[j + 1].ranking = j + 2;
                    }
                    lb[i] = user;
                    lb[i].ranking = i + 1;
                    if(length<MAX_LENGTH){
                        length ++ ;
                    }
                    break;
                }

            }
            if (length < MAX_LENGTH){
                lb[length] = user;
                lb[length].ranking = length + 1;
                length ++ ;
            }
        }
    }

    public void writeLb(String path) throws IOException {
        File f = new File(path);
        f.createNewFile();
        FileWriter writer = new FileWriter(f);
        for (int i = 0; i < length; i++) {
            String dateTime = lb[i].dateTime;
            String score = String.valueOf(lb[i].gameScore);
            String ranking = String.valueOf(lb[i].ranking);
            writer.write(dateTime + " " + score + " " + ranking + "\n");
        }
        writer.flush();
        writer.close();
    }

    public void loadLb(String path) throws IOException {
        File file = new File(path);
        if (!file.exists()) {
            file.createNewFile();
            System.out.println("排名文件已创建");
        } else {
            System.out.println("排名文件已存在");
        }

        FileReader fr = new FileReader(path);
        try (BufferedReader br = new BufferedReader(fr)) {
            String data = null;
            int i = 0;
            while ((data = br.readLine()) != null) {
                String[] word = data.split("\\s");
                UserRecord user = new UserRecord(Integer.parseInt(word[1]));
                lb[i] = user;
                lb[i].dateTime = word[0];
                lb[i].ranking = Integer.parseInt(word[2]);
                i++;
            }
            this.length = i;
        } catch (NumberFormatException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public String show(String path) throws IOException {
        FileReader fr = new FileReader(path);
        JFrame frame = new JFrame("排行榜");
        try (BufferedReader br = new BufferedReader(fr)) {
            String data = null;
            StringBuilder lines = new StringBuilder("排名\t      游戏时间\t\t得分\n");
            while ((data = br.readLine()) != null) {
                String[] word = data.split("\\s");
                String line = " " + word[2] + "\t" + word[0] + "\t" + word[1] + "\n";
                lines.append(line);
            }
            frame.setBounds(300, 400, 600, 400);
            frame.setLocationRelativeTo(null);
            JTextArea jt = new JTextArea(lines.toString());
            jt.setBounds(50, 60, 300, 60);
            jt.setFont(new Font("宋体", Font.BOLD, 22));
            jt.setEditable(false);
            JScrollPane js = new JScrollPane(jt);
            js.setBounds(37, 13, 292, 443);
            frame.add(js);
            frame.setVisible(true);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return path;
    }

    public static void main(String[] args) throws IOException {
        LeaderBoard ld = new LeaderBoard();
        ld.loadLb("LeaderBoard/LeaderBoard.txt");
        UserRecord u1 = new UserRecord(2);
        UserRecord u2 = new UserRecord(12);
        UserRecord u3 = new UserRecord(18);
        ld.add(u1);
        ld.add(u2);
        ld.add(u3);
        ld.writeLb("LeaderBoard/LeaderBoard.txt");

        //System.out.println(ld.lb[2].dateTime);
    }
}

