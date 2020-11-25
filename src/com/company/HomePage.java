package com.company;

import susteam.sdk.SusteamSdk;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.FileInputStream;

public class HomePage {
    String name;
    JFrame jf;
    JPanel jp[] = new JPanel[10];
    JLabel jlb[] = new JLabel[10];
    JButton jbt[] = new JButton[4];

    public HomePage(String username) {
        this.name = username;
        Player user = Game2048.getPlayer(username);
        jf = new JFrame("2048");
        jf.setSize(565, 815);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setLocationRelativeTo(null);
        jf.setVisible(true);
        jf.setLayout(null);
        jf.setResizable(false);
        Container c = jf.getContentPane();
        c.setBackground(new Color(51,51,51));

        for( int i = 0; i < 10; i++ ) {
            jp[i]=new JPanel();
            jp[i].setLayout(new GridBagLayout());
            jp[i].setOpaque(true);
            jp[i].setBackground(new Color(51,51,51));
            jp[i].requestFocus();
            jf.add(jp[i]);
        }

        jp[0].setBounds(15, 420, 120, 120);
        jp[1].setBounds(150, 420, 120, 120);
        jp[2].setBounds(285, 420, 120, 120);
        jp[3].setBounds(420, 420, 120, 120);

        jp[4].setBounds(0,100,565,100);
        jlb[4] =new JLabel("2048",JLabel.CENTER);
        Font f = new Font("Serif",Font.BOLD,80);
        jlb[4].setForeground(Color.YELLOW);
        jlb[4].setFont(f);
        jp[4].add(jlb[4]);

        jp[5].setBounds(0,200,565,100);
        jlb[5] =new JLabel("欢迎! "+user.name, JLabel.CENTER);
        f = new Font("Serif",Font.BOLD,40);
        jlb[5].setForeground(Color.YELLOW);
        jlb[5].setFont(f);
        jp[5].add(jlb[5]);

        jp[6].setBounds(0,300,565,100);
        jlb[6] =new JLabel("最高得分: "+user.score, JLabel.CENTER);
        f = new Font("宋体",Font.BOLD,40);
        jlb[6].setForeground(Color.YELLOW);
        jlb[6].setFont(f);
        jp[6].add(jlb[6]);


        Font f2 = new Font("宋体",Font.BOLD,40);
        for( int i = 0; i < 4; i++ ) {
            jbt[i] = new JButton();
            jbt[i].setSize(100, 100);
            jbt[i].setForeground(Color.BLACK);
            jbt[i].setFont(f2);
        }

        jbt[0].setText("开始");
        jbt[0].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jf.dispose();
                SwingUtilities.invokeLater(new Runnable(){
                    public void run()
                    {
                        new Game2048(name);
                    }
                });
            }
        });

        jbt[1].setText("加载");
        jbt[1].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jf.dispose();
                SusteamSdk.getAllGameSaveName().onComplete( it -> {
                        jf.dispose();
                        SwingUtilities.invokeLater(new Runnable(){
                            public void run()
                            {
                                new showSaves(name,it.result(),0);
                            }
                        });
                });
            }
        });

        jbt[2].setText("排行");
        jbt[2].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(new Runnable(){
                    public void run()
                    {
                        new Rank(name);
                    }
                });
            }
        });


        for( int i = 0; i < 4; i++ ) {
            jp[i].add(jbt[i]);
        }
    }
}
