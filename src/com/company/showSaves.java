package com.company;

import susteam.sdk.GameSave;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.Instant;

public class showSaves {
    String name;
    JFrame jf;
    JLabel jlb[] = new JLabel[30];
    JPanel jp[] = new JPanel[30];
    JButton jbt[] = new JButton[30];

    public static void main(String[] args) {
        GameSave[] saves = new GameSave[13];
        for( int i = 0; i < saves.length; i++ ) {
            saves[i] = new GameSave("test00"+i,10,"test"+i+".txt", Instant.now());
        }
        showSaves s = new showSaves("admin", saves, 0);
    }
    public showSaves(String name, GameSave[] saves, int index) {
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
        c.setBackground(new Color(51,51,51));

        for(int i=0;i<30;i++)
        {
            jp[i]=new JPanel();
            jp[i].setLayout(new GridBagLayout());
            jp[i].setOpaque(true);
            jp[i].setBackground(new Color(75,75,75));
            jp[i].requestFocus();
            jf.add(jp[i]);
        }

        Font f = new Font("Serif",Font.BOLD,40);
        Font f2 = new Font("宋体",Font.BOLD,32);
        for( int i = index; i < Math.min(index+5, saves.length); i++ ) {
            int cur = i-index;
            jp[cur*3].setBounds(15,100+cur*115,255,100);
            jp[cur*3+1].setBounds(285,100+cur*115,120,100);
            jp[cur*3+2].setBounds(420, 100+cur*115, 120, 100);
            jlb[i] = new JLabel("");
            jlb[i].setLayout(new GridBagLayout());
            jlb[i].setForeground(Color.YELLOW);
            jlb[i].setFont(f);
            jp[cur*3].add(jlb[i]);
            jlb[i].setText(saves[i].getSaveName());

            jbt[i*2] = new JButton("删除");
            jbt[i*2].setSize(100, 100);
            jbt[i*2].setForeground(Color.BLACK);
            jbt[i*2].setFont(f2);
            jbt[i*2].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                }
            });
            jp[cur*3+1].add(jbt[i*2]);
            jbt[i*2+1] = new JButton("选择");
            jbt[i*2+1].setSize(100, 100);
            jbt[i*2+1].setForeground(Color.BLACK);
            jbt[i*2+1].setFont(f2);
            jbt[i*2+1].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                }
            });
            jp[cur*3+2].add(jbt[i*2+1]);
        }

        jp[21].setBounds(5,675,180,100);
        jp[21].setBackground(new Color(51,51,51));
        jbt[21] = new JButton("上一页");
        jbt[21].setSize(100, 100);
        jbt[21].setForeground(Color.BLACK);
        jbt[21].setFont(f2);
        jbt[21].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jf.dispose();
                SwingUtilities.invokeLater(new Runnable(){
                    public void run()
                    {
                        new showSaves(name, saves, index-5);
                    }
                });
            }
        });
        if( index != 0 ) jp[21].add(jbt[21]);


        jp[22].setBounds(190,675,180,100);
        jp[22].setBackground(new Color(51,51,51));
        jbt[22] = new JButton("返回");
        jbt[22].setSize(100, 100);
        jbt[22].setForeground(Color.BLACK);
        jbt[22].setFont(f2);
        jbt[22].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jf.dispose();
                SwingUtilities.invokeLater(new Runnable(){
                    public void run()
                    {
                        new HomePage(name);
                    }
                });
            }
        });
        jp[22].add(jbt[22]);

        jp[23].setBounds(375,675,180,100);
        jp[23].setBackground(new Color(51,51,51));
        jbt[23] = new JButton("下一页");
        jbt[23].setSize(100, 100);
        jbt[23].setForeground(Color.BLACK);
        jbt[23].setFont(f2);
        jbt[23].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jf.dispose();
                SwingUtilities.invokeLater(new Runnable(){
                    public void run()
                    {
                        new showSaves(name, saves, index+5);
                    }
                });
            }
        });
        if( index+5 < saves.length ) jp[23].add(jbt[23]);



        jp[24].setBounds(150,0,255,100);
        jp[24].setBackground(new Color(51,51,51));
        jlb[24] =new JLabel("",JLabel.CENTER);
        Font f3 = new Font("Serif",Font.BOLD,60);
        jlb[24].setForeground(Color.YELLOW);
        jlb[24].setFont(f3);
        jp[24].add(jlb[24]);
        jlb[24].setText("游戏存档");
    }
}
