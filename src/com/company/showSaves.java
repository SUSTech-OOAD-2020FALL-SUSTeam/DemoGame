package com.company;

import susteam.sdk.GameSave;
import susteam.sdk.SusteamSdk;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.time.Instant;

public class showSaves {
    String name;
    JFrame jf;
    JLabel jlb[] = new JLabel[30];
    JPanel jp[] = new JPanel[30];
    JButton jbt[] = new JButton[30];

    public showSaves(String name, GameSave[] saves, int index, boolean DLC) {
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

        Font f = new Font("Serif",Font.BOLD,27);
        Font f2 = new Font("宋体",Font.BOLD,32);
        for( int i = index; i < Math.min(index+5, saves.length); i++ ) {
            int cur = i-index;
            jp[cur*3].setBounds(15,100+cur*115,255,100);
            jp[cur*3+1].setBounds(285,100+cur*115,120,100);
            jp[cur*3+2].setBounds(420, 100+cur*115, 120, 100);
            jlb[cur] = new JLabel("");
            jlb[cur].setLayout(new GridBagLayout());
            jlb[cur].setForeground(Color.YELLOW);
            jlb[cur].setFont(f);
            jlb[cur+10] = new JLabel("");
            jlb[cur+10].setLayout(new GridBagLayout());
            jlb[cur+10].setForeground(Color.WHITE);
            jlb[cur+10].setFont(f);
            GridBagLayout layout = new GridBagLayout();
            GridBagConstraints constraints = new GridBagConstraints();
            constraints.gridx = 0;
            constraints.gridy = 0;
            constraints.gridwidth = 100;
            constraints.gridheight = 20;
            layout.setConstraints(jlb[cur], constraints);
            constraints.gridx = 0;
            constraints.gridy = 25;
            constraints.gridwidth = 100;
            constraints.gridheight = 20;
            layout.setConstraints(jlb[cur+10], constraints);
            jp[cur*3].setLayout(layout);
            jp[cur*3].add(jlb[cur]);
            jp[cur*3].add(jlb[cur+10]);

            String[] save  = saves[i].getSaveName().split("_");
            String score = save[1].split("\\.")[0];
            String[] saveTime = save[0].split("T");
            jlb[cur].setText(saveTime[0]+" "+ saveTime[1].replace("-",":"));
            jlb[cur+10].setText("得分: " + score);
            int finalI = i;

            jbt[cur*2] = new JButton("删除");
            jbt[cur*2].setSize(100, 100);
            jbt[cur*2].setForeground(Color.BLACK);
            jbt[cur*2].setFont(f2);
            jbt[cur*2].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    SusteamSdk.deleteSave(saves[finalI].getSaveName()).onComplete(it1 -> {
                        SusteamSdk.getAllGameSaveName().onComplete( it -> {
                            jf.dispose();
                            SwingUtilities.invokeLater(new Runnable(){
                                public void run()
                                {
                                    new showSaves(name,it.result(),0, DLC);
                                }
                            });
                        });
                    });
                }
            });
            jp[cur*3+1].add(jbt[cur*2]);
            jbt[cur*2+1] = new JButton("选择");
            jbt[cur*2+1].setSize(100, 100);
            jbt[cur*2+1].setForeground(Color.BLACK);
            jbt[cur*2+1].setFont(f2);
            jbt[cur*2+1].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    SusteamSdk.load(saves[finalI].getSaveName()).onComplete(it -> {
                        jf.dispose();
                        try {
                            String address = System.getProperty("java.io.tmpdir")+"susteam/sdk/10/"+saves[finalI].getSaveName();
                            FileInputStream fis=new FileInputStream(address);
                            BufferedInputStream bis=new BufferedInputStream(fis);
                            StringBuilder content= new StringBuilder();
                            //自定义缓冲区
                            byte[] buffer=new byte[10240];
                            int flag=0;
                            while((flag=bis.read(buffer))!=-1){
                                assert false;
                                content.append(new String(buffer, 0, flag));
                            }
                            bis.close();

                            String[] board = content.toString().split(" ");
                            for( int j = 0; j < 16; j++ ) {
                                if( board[j].equals("0") ) board[j] = "";
                            }
                            Game2048 game = new Game2048(name, DLC);
                            game.Load(board, name);
                        } catch (Exception exception) {
                            exception.printStackTrace();
                        }
                    });

                }
            });
            jp[cur*3+2].add(jbt[cur*2+1]);
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
                        new showSaves(name, saves, index-5, DLC);
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
                        new HomePage(name, DLC);
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
                        new showSaves(name, saves, index+5, DLC);
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
