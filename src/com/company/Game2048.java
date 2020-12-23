package com.company;

import susteam.sdk.SusteamSdk;

import java.awt.*;
import java.awt.event.*;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.time.Instant;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import javax.swing.*;

public class Game2048 implements KeyListener, ActionListener {
    String name;
    Boolean DLC;
    boolean upMovable = true, downMovable = true, rightMovable = true, leftMovable = true;
    JDialog jd;
    JFrame jf;
    JLabel jlb[] = new JLabel[16];
    JPanel jp[] = new JPanel[30];
    JButton jbt[] = new JButton[4];
    JLabel jlt = new JLabel("当前得分: 0");

    public Game2048(String name, boolean DLC) {
        this.name = name;
        this.DLC = DLC;
        jf = new JFrame("2048");
        jf.setSize(565, 815);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setLocationRelativeTo(null);
        jf.setVisible(true);
        jf.setLayout(null);
        jf.setResizable(false);
        jf.addKeyListener(this);
        jf.requestFocus();
        Container c = jf.getContentPane();
        c.setBackground(new Color(51, 51, 51));


        for (int i = 0; i < 16; i++) {
            jlb[i] = new JLabel("");
            jp[i] = new JPanel();
            jp[i].setLayout(new GridBagLayout());
            jp[i].setOpaque(true);
            jp[i].setBackground(new Color(75, 75, 75));
            jf.add(jp[i]);
        }

        for (int i = 16; i < 21; i++) {
            jp[i] = new JPanel();
            jp[i].setLayout(new GridBagLayout());
            jp[i].setOpaque(true);
            jp[i].setBackground(new Color(51, 51, 51));
            jp[i].requestFocus();
            jf.add(jp[i]);
        }
        jp[0].setBounds(15, 115, 120, 120);
        jp[1].setBounds(150, 115, 120, 120);
        jp[2].setBounds(285, 115, 120, 120);
        jp[3].setBounds(420, 115, 120, 120);

        jp[4].setBounds(15, 250, 120, 120);
        jp[5].setBounds(150, 250, 120, 120);
        jp[6].setBounds(285, 250, 120, 120);
        jp[7].setBounds(420, 250, 120, 120);

        jp[8].setBounds(15, 385, 120, 120);
        jp[9].setBounds(150, 385, 120, 120);
        jp[10].setBounds(285, 385, 120, 120);
        jp[11].setBounds(420, 385, 120, 120);

        jp[12].setBounds(15, 520, 120, 120);
        jp[13].setBounds(150, 520, 120, 120);
        jp[14].setBounds(285, 520, 120, 120);
        jp[15].setBounds(420, 520, 120, 120);

        jp[16].setBounds(15, 655, 120, 120);
        jp[17].setBounds(150, 655, 120, 120);
        jp[18].setBounds(285, 655, 120, 120);
        jp[19].setBounds(420, 655, 120, 120);
        jp[20].setBounds(100, 0, 480, 100);
        jp[20].setLayout(new GridLayout());

        Font f = new Font("Serif", Font.BOLD, 40);
        Font f2 = new Font("宋体", Font.BOLD, 40);


        jlt.setForeground(Color.YELLOW);
        jlt.setFont(f2);
        jp[20].add(jlt);

        for (int i = 0; i < 16; i++) {
            jlb[i].setForeground(Color.black);
            jlb[i].setFont(f);
            jp[i].add(jlb[i]);
        }

        for (int i = 0; i < 4; i++) {
            jbt[i] = new JButton();
            jbt[i].setSize(100, 100);
            jbt[i].setForeground(Color.BLACK);
            jbt[i].setFont(f2);
        }

        jbt[0].setText("重开");
        jbt[0].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                initialize();
                jf.requestFocus();
            }
        });

        jbt[1].setText("返回");
        jbt[1].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jf.dispose();
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        new HomePage(name, DLC);
                    }
                });
            }
        });

        jbt[2].setText("保存");
        jbt[2].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] savedBoard = Save();
                jf.requestFocus();
            }
        });

        for (int i = 0; i < 4; i++) {
            jp[i + 16].add(jbt[i]);
        }
        Random r = new Random();
        int i = r.nextInt(16);
        if (Math.random() < 0.9) jlb[i].setText("2");
        else jlb[i].setText("4");
        int j;
        do {
            j = r.nextInt(16);
        } while (i == j);
        if (Math.random() < 0.9) jlb[j].setText("2");
        else jlb[j].setText("4");
        colorTiles();


    }

    @Override
    public void keyPressed(KeyEvent e) {
        int pre_score = getScore();
        if (e.getKeyCode() == KeyEvent.VK_UP && upMovable) {
            upMove();
            colorTiles();
            next();
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN && downMovable) {
            downMove();
            colorTiles();
            next();
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT && rightMovable) {
            rightMove();
            colorTiles();
            next();
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT && leftMovable) {
            leftMove();
            colorTiles();
            next();
        }

        int score = getScore();
        if (pre_score < 20 && score >= 20) {
            AtomicInteger progress = new AtomicInteger();
            SusteamSdk.getUserAchievementProcess("小试牛刀").onComplete(it -> {
                if (it.result() != null) {
                    progress.set(it.result());
                    SusteamSdk.updateUserAchievementProcess("小试牛刀", progress.get() + 1);
                }
            });
        } else if (pre_score < 100 && score >= 100) {
            AtomicInteger progress = new AtomicInteger();
            SusteamSdk.getUserAchievementProcess("初露锋芒").onComplete(it -> {
                if (it.result() != null) {
                    progress.set(it.result());
                    SusteamSdk.updateUserAchievementProcess("初露锋芒", progress.get() + 1);
                }
            });
        } else if (pre_score < 1000 && score >= 1000) {
            AtomicInteger progress = new AtomicInteger();
            SusteamSdk.getUserAchievementProcess("大展身手").onComplete(it -> {
                if (it.result() != null) {
                    progress.set(it.result());
                    SusteamSdk.updateUserAchievementProcess("大展身手", progress.get() + 1);
                }
            });
        } else if (pre_score < 5000 && score >= 5000) {
            AtomicInteger progress = new AtomicInteger();
            SusteamSdk.getUserAchievementProcess("炉火纯青").onComplete(it -> {
                if (it.result() != null) {
                    progress.set(it.result());
                    SusteamSdk.updateUserAchievementProcess("炉火纯青", progress.get() + 1);
                }
            });

            AtomicInteger progress1 = new AtomicInteger();
            SusteamSdk.getUserAchievementProcess("登峰造极").onComplete(it -> {
                if (it.result() != null) {
                    progress1.set(it.result());
                    SusteamSdk.updateUserAchievementProcess("登峰造极", progress1.get() + 1);
                }
            });
        }
        jlt.setText("当前得分: " + score);
        if (!leftMovable && !rightMovable && !upMovable && !downMovable) {
            JOptionPane.showMessageDialog(
                    jf,
                    "游戏结束！\n得分：" + score,
                    "game over",
                    JOptionPane.INFORMATION_MESSAGE
            );
            recordScore(score);
            jf.dispose();
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    new HomePage(name, DLC);
                }
            });
        }
    }

    private void movable() {
        upMovable = false;
        downMovable = false;
        rightMovable = false;
        leftMovable = false;
        int fir, sec;
        x:
        {
            for (int i = 3; i <= 15; i = i + 4) {
                for (int k = 3; k > 0; k--) {
                    for (int l = i; l > i - k; l--) {
                        if (jlb[l].getText().equals("")) {
                            if (!jlb[l - 1].getText().equals("")) {
                                rightMovable = true;
                                break x;
                            }
                        }
                    }
                }
                for (int j = i; j > i - 3; j--) {
                    if (jlb[j].getText().equals(""))
                        fir = 0;
                    else fir = Integer.parseInt(jlb[j].getText());
                    if (jlb[j - 1].getText().equals(""))
                        sec = 0;
                    else sec = Integer.parseInt(jlb[j - 1].getText());
                    if (fir == sec) {
                        if (fir != 0) {
                            rightMovable = true;
                            break x;
                        }
                    }
                }
            }
        }
        //to know about left move
        y:
        {
            for (int i = 0; i <= 12; i = i + 4) {
                for (int k = 3; k > 0; k--) {
                    for (int l = i; l < i + k; l++) {
                        if (jlb[l].getText().equals("")) {
                            if (!jlb[l + 1].getText().equals("")) {
                                leftMovable = true;
                                break y;
                            }
                        }
                    }
                }
                for (int j = i; j < i + 3; j++) {
                    if (jlb[j].getText().equals(""))
                        fir = 0;
                    else fir = Integer.parseInt(jlb[j].getText());
                    if (jlb[j + 1].getText().equals(""))
                        sec = 0;
                    else sec = Integer.parseInt(jlb[j + 1].getText());
                    if (fir == sec) {
                        if (fir != 0) {
                            leftMovable = true;
                            break y;
                        }
                    }
                }
            }
        }
        //to know about down move
        z:
        {
            for (int i = 12; i <= 15; i++) {
                for (int k = 12; k > 0; k = k - 4) {
                    for (int l = i; l > i - k; l = l - 4) {
                        if (jlb[l].getText().equals("")) {
                            if (!jlb[l - 4].getText().equals("")) {
                                downMovable = true;
                                break z;
                            }
                        }
                    }
                }
                for (int j = i; j > i - 12; j = j - 4) {
                    if (jlb[j].getText().equals(""))
                        fir = 0;
                    else fir = Integer.parseInt(jlb[j].getText());
                    if (jlb[j - 4].getText().equals(""))
                        sec = 0;
                    else sec = Integer.parseInt(jlb[j - 4].getText());
                    if (fir == sec) {
                        if (fir != 0) {
                            downMovable = true;
                            break z;
                        }
                    }
                }
            }
        }
        //to know about up move
        u:
        {
            for (int i = 0; i <= 3; i++) {
                for (int k = 12; k > 0; k = k - 4) {
                    for (int l = i; l < i + k; l = l + 4) {
                        if (jlb[l].getText().equals("")) {
                            if (!jlb[l + 4].getText().equals("")) {
                                upMovable = true;
                                break u;
                            }
                        }
                    }
                }
                for (int j = i; j < i + 12; j = j + 4) {
                    if (jlb[j].getText().equals(""))
                        fir = 0;
                    else fir = Integer.parseInt(jlb[j].getText());
                    if (jlb[j + 4].getText().equals(""))
                        sec = 0;
                    else sec = Integer.parseInt(jlb[j + 4].getText());
                    if (fir == sec) {
                        if (fir != 0) {
                            upMovable = true;
                            break u;
                        }
                    }
                }
            }
        }
    }

    private void next() {
        Random r = new Random();
        boolean flag = false;
        for (int i = 0; i < 16; i++) {
            if (jlb[i].getText().equals("")) {
                flag = true;
                break;
            }
        }
        int i;
        if (flag) {
            do {
                i = r.nextInt(16);
            } while (!jlb[i].getText().equals(""));
            Random random = new Random();
            int possibilityOfTwo = 90;
            if (random.nextInt(100) < possibilityOfTwo) {
                jlb[i].setText("2");
                colorTiles();
            } else {
                jlb[i].setText("4");
                colorTiles();
            }

        }
        movable();
        if (!flag && !upMovable && !downMovable && !rightMovable && !leftMovable) {
            jd.setVisible(true);
        }
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getActionCommand().equals("ok")) {
            initialize();
            jd.setVisible(false);
        }
    }

    private void initialize() {
        leftMovable = true;
        rightMovable = true;
        upMovable = true;
        downMovable = true;
        for (int i = 0; i < 16; i++) {
            jlb[i].setText("");
        }
        int score = getScore();
        jlt.setText("当前得分: " + score);
        colorTiles();
        Random r = new Random();
        int i = r.nextInt(16);
        if (Math.random() < 0.9) jlb[i].setText("2");
        else jlb[i].setText("4");
        int j;
        do {
            j = r.nextInt(16);
        } while (i == j);
        if (Math.random() < 0.9) jlb[j].setText("2");
        else jlb[j].setText("4");
        colorTiles();
    }

    private void rightMove() {
        int fir, sec, fin;
        for (int i = 3; i <= 15; i = i + 4) {
            rightify(i);
            //code to add consecutive same terms
            for (int j = i; j > i - 3; j--) {
                if (jlb[j].getText().equals(""))
                    fir = 0;
                else fir = Integer.parseInt(jlb[j].getText());
                if (jlb[j - 1].getText().equals(""))
                    sec = 0;
                else sec = Integer.parseInt(jlb[j - 1].getText());
                if (fir == sec) {
                    fin = fir + sec;
                    if (fin != 0)
                        jlb[j].setText("" + fin);
                    else jlb[j].setText("");
                    jlb[j - 1].setText("");
                }
            }
            rightify(i);
        }
    }

    private void rightify(int i) {
        //code to righify
        for (int k = 3; k > 0; k--) {
            for (int l = i; l > i - k; l--) {
                if (jlb[l].getText().equals("")) {
                    if (!jlb[l - 1].getText().equals("")) {
                        jlb[l].setText(jlb[l - 1].getText());
                        jlb[l - 1].setText("");
                    }
                }
            }
        }
    }

    private void leftMove() {
        int fir, sec, fin;
        for (int i = 0; i <= 12; i = i + 4) {
            leftify(i);
            //code to add consecutive same terms
            for (int j = i; j < i + 3; j++) {
                if (jlb[j].getText().equals(""))
                    fir = 0;
                else fir = Integer.parseInt(jlb[j].getText());
                if (jlb[j + 1].getText().equals(""))
                    sec = 0;
                else sec = Integer.parseInt(jlb[j + 1].getText());
                if (fir == sec) {
                    fin = fir + sec;
                    if (fin != 0)
                        jlb[j].setText("" + fin);
                    else jlb[j].setText("");
                    jlb[j + 1].setText("");
                }
            }
            leftify(i);
        }
    }

    private void leftify(int i) {
        //code to leftify
        for (int k = 3; k > 0; k--) {
            for (int l = i; l < i + k; l++) {
                if (jlb[l].getText().equals("")) {
                    if (!jlb[l + 1].getText().equals("")) {
                        jlb[l].setText(jlb[l + 1].getText());
                        jlb[l + 1].setText("");
                    }
                }
            }
        }
    }

    private void downMove() {
        int fir, sec, fin;
        for (int i = 12; i <= 15; i++) {
            downify(i);
            //code to add consecutive same terms
            for (int j = i; j > i - 12; j = j - 4) {
                if (jlb[j].getText().equals(""))
                    fir = 0;
                else fir = Integer.parseInt(jlb[j].getText());
                if (jlb[j - 4].getText().equals(""))
                    sec = 0;
                else sec = Integer.parseInt(jlb[j - 4].getText());
                if (fir == sec) {
                    fin = fir + sec;
                    if (fin != 0)
                        jlb[j].setText("" + fin);
                    else jlb[j].setText("");
                    jlb[j - 4].setText("");
                }
            }
            downify(i);
        }
    }

    private void downify(int i) {
        //code to downify
        for (int k = 12; k > 0; k = k - 4) {
            for (int l = i; l > i - k; l = l - 4) {
                if (jlb[l].getText().equals("")) {
                    if (!jlb[l - 4].getText().equals("")) {
                        jlb[l].setText(jlb[l - 4].getText());
                        jlb[l - 4].setText("");
                    }
                }
            }
        }
    }

    private void upMove() {
        int fir, sec, fin;
        for (int i = 0; i <= 3; i++) {
            upify(i);
            //code to add consecutive same terms
            for (int j = i; j < i + 12; j = j + 4) {
                if (jlb[j].getText().equals(""))
                    fir = 0;
                else fir = Integer.parseInt(jlb[j].getText());
                if (jlb[j + 4].getText().equals(""))
                    sec = 0;
                else sec = Integer.parseInt(jlb[j + 4].getText());
                if (fir == sec) {
                    fin = fir + sec;
                    if (fin != 0)
                        jlb[j].setText("" + fin);
                    else jlb[j].setText("");
                    jlb[j + 4].setText("");
                }
            }
            upify(i);
        }
    }

    private void upify(int i) {
        //code to upify
        for (int k = 12; k > 0; k = k - 4) {
            for (int l = i; l < i + k; l = l + 4) {
                if (jlb[l].getText().equals("")) {
                    if (!jlb[l + 4].getText().equals("")) {
                        jlb[l].setText(jlb[l + 4].getText());
                        jlb[l + 4].setText("");
                    }
                }
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    public void colorTiles() {
        for (int i = 0; i < 16; i++) {
            Color c = new Color(75, 75, 75);
            String s = jlb[i].getText();
            if (s.equals("2"))
                c = Color.WHITE;
            if (s.equals("4"))
                c = Color.WHITE;
            if (s.equals("8"))
                c = new Color(255, 255, 170);
            if (s.equals("16"))
                c = new Color(255, 255, 128);
            if (s.equals("32"))
                c = new Color(255, 255, 85);
            if (s.equals("64"))
                c = new Color(255, 255, 43);
            if (s.equals("128"))
                c = new Color(255, 255, 0);
            if (s.equals("256"))
                c = new Color(213, 213, 0);
            if (s.equals("512"))
                c = new Color(170, 170, 0);
            if (s.equals("1024"))
                c = new Color(128, 128, 0);
            if (s.equals("2048"))
                c = new Color(85, 85, 0);
            jp[i].setBackground(c);
        }
    }

    public int getScore() {
        int res = 0;
        for (int i = 0; i < 16; i++) {
            String temp = jlb[i].getText();
            if (!temp.equals("")) {
                int scor = Integer.parseInt(temp);
                int tem = scor;
                int n = 0;
                while (tem > 1) {
                    n++;
                    tem /= 2;
                }
                res += (n - 1) * scor;
            }
        }
        return res;
    }

    public void Load(String[] board, String name) {
        leftMovable = true;
        rightMovable = true;
        upMovable = true;
        downMovable = true;
        for (int i = 0; i < 16; i++) {
            jlb[i].setText(board[i]);
        }
        int score = getScore();
        jlt.setText("当前得分: " + score);
        colorTiles();
    }

    public String[] Save() {
        String[] result = new String[16];
        String storeStuff = "";
        for (int i = 0; i < 16; i++) {
            result[i] = jlb[i].getText();
            if (result[i].equals("")) result[i] = "0";
            if (i != 0) storeStuff = storeStuff + " ";
            storeStuff = storeStuff + result[i];
        }
        try {
            String filename =
                    Instant.now().plusSeconds(8 * 60 * 60).toString()
                            .replace(".", "T")
                            .replace(":", "-") + "_" + getScore() + ".txt";
            File file = new File(filename);
            if (!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(file);
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            bos.write(storeStuff.getBytes(), 0, storeStuff.getBytes().length);
            bos.flush();
            bos.close();
            SusteamSdk.save(file);
            notifySaved();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static Player getPlayer(String name) {
        //TODO
        return new Player(name, 0);
    }

    public void recordScore(int score) {
        SusteamSdk.addRecord(score);
    }

    public void notifySaved() {
        JFrame jframe = new JFrame("提示");
        jframe.setSize(300, 150);
        jframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        jframe.setLocationRelativeTo(null);
        jframe.setVisible(true);
        jframe.setLayout(null);

        JPanel jPanel = new JPanel();
        jPanel.setSize(300, 150);
        jPanel.setLayout(null);
        jPanel.setBackground(new Color(51, 51, 51));
        jPanel.setOpaque(true);
        jframe.add(jPanel);

        Font f1 = new Font("宋体", Font.BOLD, 30);
        Font f2 = new Font("宋体", Font.BOLD, 10);

        JLabel jLabel = new JLabel("保存成功");
        jLabel.setVisible(true);
        jLabel.setBounds(70, 15, 150, 40);
        jLabel.setForeground(Color.WHITE);
        jLabel.setFont(f1);
        jPanel.add(jLabel);

        JButton jButton = new JButton("确定");
        jButton.setVisible(true);
        jButton.setBounds(100, 85, 60, 20);
        jButton.setForeground(Color.BLACK);
        jButton.setFont(f2);
        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jframe.dispose();
            }
        });
        jPanel.add(jButton);

    }

}
