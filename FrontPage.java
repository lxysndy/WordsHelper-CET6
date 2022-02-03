package com.lxy.CET;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class FrontPage{

    public static void main(String[] args) throws Exception {
        ArrayList<Word> words = new ArrayList<>();
        ArrayList<Word> mastered = new ArrayList<>();
        ArrayList<Word> notMaster = new ArrayList<>();
        System_1.showMain1(words,mastered,notMaster);//把单词读进去了
        JFrame jf = new JFrame("六级词汇速记程序");
        jf.setLayout(new FlowLayout(FlowLayout.LEFT));
        jf.setBounds(450,300,450,300);
        jf.setLayout(new GridLayout(3,1,10,10));
        JButton jButton1 = new JButton("“英-中”挑战模式");
        JButton jButton2 = new JButton("“中-英”挑战模式");
        JButton jButton3 = new JButton("成绩记忆");
        jf.add(jButton1);
        jf.add(jButton2);
        jf.add(jButton3);
        jf.setResizable(false);
        jf.setVisible(true);
        countNum countNum = new countNum(0,0,0,0);
        System_1.initArr(countNum.arrRight1);
        System_1.initArr(countNum.arrRight2);
        System_1.initArr(countNum.arrError1);
        System_1.initArr(countNum.arrError2);
        mastered.clear();
        notMaster.clear();
        jButton1.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System_1.enTochs(words,countNum,mastered,notMaster);
            }
        });
        jButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    System_2.chToens(words,countNum,countNum.arrRight2,countNum.arrError2,mastered,notMaster);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        jButton3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BufferedWriter bw1 = null;
                try {
                    bw1 = new BufferedWriter(new FileWriter("D:\\javacode\\javasepro\\hello-app\\src\\cet6\\已掌握单词.txt",true));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                BufferedWriter bw2 = null;
                try {
                    bw2 = new BufferedWriter(new FileWriter("D:\\javacode\\javasepro\\hello-app\\src\\cet6\\未掌握单词.txt",true));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                try {
                    System_1.Memory(words,countNum.arrRight1,countNum.arrError1,
                            countNum.arrRight2,countNum.arrError2,mastered,notMaster,bw1,bw2);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}
