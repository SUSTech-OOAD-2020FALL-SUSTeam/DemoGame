package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class Rank {
    JFrame jf;
    String name;
    JPanel jp[] = new JPanel[40];
    JLabel jlb[] = new JLabel[40];
    JButton jbt[] = new JButton[4];

    public Rank(String name) {
        this.name = name;
        jf = new JFrame("2048");
        jf.setSize(565, 815);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setLocationRelativeTo(null);
        jf.setVisible(true);
        jf.setLayout(null);
        jf.setResizable(false);
        Container c = jf.getContentPane();
        c.setBackground(new Color(51, 51, 51));

        for (int i = 0; i < 40; i++) {
            jp[i] = new JPanel();
            jp[i].setLayout(new GridBagLayout());
            jp[i].setOpaque(true);
            jp[i].setBackground(new Color(51, 51, 51));
            jp[i].requestFocus();
            jf.add(jp[i]);
        }
        Font f2 = new Font("宋体", Font.BOLD, 40);
        Font f3 = new Font("Serif", Font.BOLD, 30);

        for( int i = 1; i <= 33; i++ ) {
            jlb[i] = new JLabel();
            jlb[i].setForeground(Color.YELLOW);
            jlb[i].setFont(f3);
            jp[i].add(jlb[i]);
        }

        jlb[1].setText("排名");
        jlb[2].setText("用户名");
        jlb[3].setText("最高得分");
        jlb[1].setFont(f2);
        jlb[2].setFont(f2);
        jlb[3].setFont(f2);

        for( int i = 1; i <= 33; i++ ) {
            jp[i].setBounds( ((i-1)%3)*170, 30+((i-1)/3)*45, 170,45);
        }

        Player[] p = getTop10();
        for( int i = 0; i < 10; i++ ) {
            jlb[(i+1)*3+1].setText(""+(i+1));
            jlb[(i+1)*3+2].setText(p[i].name);
            jlb[(i+1)*3+3].setText(""+p[i].score);

        }
        for (int i = 0; i < 4; i++) {
            jbt[i] = new JButton();
            jbt[i].setSize(100, 100);
            jbt[i].setForeground(Color.BLACK);
            jbt[i].setFont(f2);
        }
        jp[0].setBounds(15, 655, 120, 120);

        jbt[0].setText("返回");
        jbt[0].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jf.dispose();
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        new HomePage(name);
                    }
                });
            }
        });

        for (int i = 0; i < 1; i++) {
            jp[i].add(jbt[i]);
        }
    }

    public Player[] getTop10() {
        //TODO
        Player[] p = new Player[10];
        for( int i = 0; i < 10; i++ ) {
            p[i] = new Player("test"+i,i);
        }
        Arrays.sort(p);
        return p;
    }
}
