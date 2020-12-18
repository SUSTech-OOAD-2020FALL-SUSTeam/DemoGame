package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.Instant;

class moreChoose {
    String name;
    JFrame jf;
    JLabel jlb[] = new JLabel[30];
    JPanel jp[] = new JPanel[30];
    JButton jbt[] = new JButton[30];

    public moreChoose(String name, boolean have, boolean DLC) {
        this.name = name;
        jf = new JFrame("2048");
        jf.setSize(565, 815);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setLocationRelativeTo(null);
        jf.setVisible(true);
        jf.setLayout(null);
        jf.setResizable(false);
        jf.requestFocus();
        Container c = jf.getContentPane();
        c.setBackground(new Color(51, 51, 51));

        for (int i = 0; i < 30; i++) {
            jp[i] = new JPanel();
            jp[i].setLayout(new GridBagLayout());
            jp[i].setOpaque(true);
            jp[i].setBackground(new Color(75, 75, 75));
            jp[i].requestFocus();
            jf.add(jp[i]);
        }

        Font f = new Font("Serif", Font.BOLD, 27);
        Font f2 = new Font("宋体", Font.BOLD, 32);
        Font f3 = new Font("Serif", Font.BOLD, 60);

        jp[22].setBounds(190, 675, 180, 100);
        jp[22].setBackground(new Color(51, 51, 51));
        jbt[22] = new JButton("返回");
        jbt[22].setSize(100, 100);
        jbt[22].setForeground(Color.BLACK);
        jbt[22].setFont(f2);
        jbt[22].addActionListener(new ActionListener() {
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
        jp[22].add(jbt[22]);

        jp[0].setBounds(15, 120, 360, 120);
        jp[1].setBounds(15, 240, 360, 100);
        jp[2].setBounds(15, 360, 360, 120);
        jp[3].setBounds(15, 480, 360, 100);
        jp[4].setBounds(375, 120, 150, 220);
        jp[5].setBounds(375, 360, 150, 220);

        jlb[0] = new JLabel("2048");
        jlb[0].setFont(f3);
        jlb[0].setForeground(Color.YELLOW);
        jp[0].add(jlb[0]);

        jlb[1] = new JLabel("已拥有");
        jlb[1].setFont(f2);
        jlb[1].setForeground(Color.GREEN);
        jp[1].add(jlb[1]);

        jlb[2] = new JLabel("2048++(DLC)");
        jlb[2].setFont(f3);
        jlb[2].setForeground(Color.CYAN);
        jp[2].add(jlb[2]);

        jlb[3] = new JLabel("已拥有");
        jlb[3].setFont(f2);
        jlb[3].setForeground(Color.GREEN);
        if( !have ) {
            jlb[3].setText("未购买");
            jlb[3].setForeground(Color.red);
        }
        jp[3].add(jlb[3]);

        jbt[4] = new JButton("选择");
        jbt[4].setSize(100, 100);
        jbt[4].setForeground(Color.BLACK);
        jbt[4].setFont(f2);
        jbt[4].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jf.dispose();
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        new HomePage(name, false);
                    }
                });
            }
        });
        jp[4].add(jbt[4]);

        jbt[5] = new JButton("选择");
        jbt[5].setSize(100, 100);
        jbt[5].setForeground(Color.BLACK);
        jbt[5].setFont(f2);
        jbt[5].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jf.dispose();
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        new HomePage(name, true);
                    }
                });
            }
        });
        if( have )
            jp[5].add(jbt[5]);

        jp[24].setBounds(150, 0, 255, 100);
        jp[24].setBackground(new Color(51, 51, 51));
        jlb[24] = new JLabel("", JLabel.CENTER);
        jlb[24].setForeground(Color.YELLOW);
        jlb[24].setFont(f3);
        jp[24].add(jlb[24]);
        jlb[24].setText("更多版本");


    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new moreChoose("111", false, false);
            }
        });
    }
}
