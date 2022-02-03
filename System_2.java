package com.lxy.CET;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class System_2 implements Runnable{
    static JLabel jlt = new JLabel();
    public void run(){
        int time = 600;
        countNum.runl = true;
        while (countNum.runl) {
            time--;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.interrupted();
            }
            if (time <= 0) {
                break;
            }
            jlt.setText(String.valueOf(time));
        }
        if(time <=0){
        JFrame jFrame = new JFrame("结果");
        jFrame.setLayout(new FlowLayout(FlowLayout.LEFT));
        jFrame.setBounds(450, 300, 450, 300);
        JLabel jLabel = new JLabel("超时了！");
        jFrame.add(jLabel);
        jFrame.setVisible(true);
        jFrame.setResizable(false);}
    }

    public static void chToens(ArrayList<Word> words, countNum countNum, int[] arrRight, int[] arrError, ArrayList<Word> mastered, ArrayList<Word> notMaster) throws IOException {
        //调试
       // System.out.print("未掌握单词：二");
        for (int i = 0; i < notMaster.size(); i++) {
            System.out.print(notMaster.get(i).getEns());
        }
        System.out.println();
        countNum.count2 = 0;
        JFrame jf2 = new JFrame("“中-英”挑战");
        jf2.setLayout(new FlowLayout(FlowLayout.LEFT));
        jf2.setBounds(450,300,450,300);
        countNum.count2++;
        //留出来开线程
        Thread th=new Thread(new System_2());
        th.start();
        int id = System_1.getRandom(0, words.size()-1);
        jf2.add(jlt);
        while (words.get(id).flag == 1) {
            id = System_1.getRandom(0,words.size()-1);
        }
        countNum.id = id;
        JPanel jPanel2 = new JPanel();
        JLabel jl = new JLabel("请根据提示补全下列单词：");
        String sign;
        sign = words.get(id).getChs();
        if (words.get(id).flag == 0) {
            sign = sign + words.get(id).getEns().substring(0,2);
        }
        JLabel jlsign = new JLabel(sign);
        jPanel2.add(jl);
        jPanel2.add(jlsign);
        JLabel jLabel1 = new JLabel("请输入：");
        JTextField wordt = new JTextField("",30);
        JTextField out = new JTextField("结果：",30);
        JButton button = new JButton("下一题");
        jPanel2.add(jLabel1);
        jPanel2.add(wordt);
        jPanel2.add(out);
        jPanel2.add(button);
        jf2.add(jPanel2);
        jPanel2.setLayout(new GridLayout(6,1,10,10));
        jf2.setVisible(true);
        jf2.setResizable(false);
        jf2.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                countNum.runl = false;
            }
        });
        button.setSize(40,20);
        countNum.arrE2Num = 0;
        countNum.arrR2Num = 0;
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //countNum.count2 = 1;
//                countNum.arrE2Num = 0;
//                countNum.arrR2Num = 0;
                String getwords = wordt.getText();
                if ((((words.get(countNum.id).getEns().substring(0, 2) + getwords)
                        .equals(words.get(countNum.id).getEns())) && words.get(countNum.id).
                        flag == 0) || ((words.get(countNum.id).getEns().equals(getwords)) && words.get(countNum.id).flag == 2)) {
                    out.setText("上一个正确！");
                    countNum.count2++;
                    countNum.arrRight2[countNum.arrR2Num++] = countNum.id;
                    if(words.get(countNum.id).flag == 2) {
                        words.get(countNum.id).flag = 1;
                        try {
                            moveWords(mastered,notMaster,words.get(countNum.id).getId());
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                    int id = System_1.getRandom(0, words.size()-1);
                    while (words.get(id).flag == 1) {
                        id = System_1.getRandom(0, words.size()-1);
                    }
                    countNum.id = id;
                    jl.setText("请根据提示补全下列单词");
                    String sign;
                    sign = words.get(id).getChs();
                    if (words.get(id).flag == 0) {
                        sign = sign + words.get(id).getEns().substring(0, 2);
                    }
                    jlsign.setText(sign);
                    jLabel1.setText("请输入：");
                    wordt.setText("");
                } else {
                    out.setText("上一个错误！");
                    countNum.count2++;
                    countNum.arrError2[countNum.arrE2Num++] = countNum.id;
                    int id = System_1.getRandom(0, 10);
                    while (words.get(id).flag == 1) {
                        id = System_1.getRandom(0, 10);
                    }
                    countNum.id = id;
                    jl.setText("请根据提示补全下列单词");
                    String sign;
                    sign = words.get(id).getChs();
                    if (words.get(id).flag == 0) {
                        sign = sign + words.get(id).getEns().substring(0, 2);
                    }
                    jlsign.setText(sign);
                    jLabel1.setText("请输入：");
                    wordt.setText("");
                }
                if (countNum.count2 == 21) {
                    jf2.dispose();
                    JFrame j = new JFrame("结果");
                    j.setLayout(new FlowLayout(FlowLayout.LEFT));
                    j.setBounds(450, 300, 450, 300);
                    JLabel l = new JLabel();
                    if ((countNum.arrE2Num > 1)||((countNum.arrE2Num==0)&&(countNum.arrR2Num==0))) {
                        l.setText("挑战失败，错误：" + countNum.arrE2Num);
                        System.out.print("错误的id:");//用于调试
                        for (int u = 0; (u < countNum.arrError2.length)&&(countNum.arrError2[u]!=-1); u++) {
                            System.out.print(countNum.arrError2[u] + " ");
                        } System.out.println();

                    } else {
                        l.setText("挑战成功，错误：" + countNum.arrE2Num);
                        System.out.print("错误的id:");//用于调试
                        for (int u = 0; (u < countNum.arrError2.length) && (countNum.arrError1[u] != -1); u++) {
                            System.out.print(countNum.arrError2[u] + " ");
                        }
                        System.out.println();
                    }
                    j.add(l);
                    j.setVisible(true);
                    j.setResizable(false);
                }
            }

        });

    }

    /**
     * 将未掌握的单词移动到已经掌握的部分
     * @param mastered
     * @param notMaster
     */
    public static void moveWords(ArrayList<Word> mastered, ArrayList<Word> notMaster, int id) throws IOException {
        Word word = notMaster.remove(getIndex(notMaster,id));
        mastered.add(word);
        BufferedWriter bw1 = new BufferedWriter(new FileWriter("D:\\javacode\\javasepro\\hello-app\\src\\cet6\\已掌握单词.txt"));
        BufferedWriter bw2 = new BufferedWriter(new FileWriter("D:\\javacode\\javasepro\\hello-app\\src\\cet6\\未掌握单词.txt"
        ));
        for (int i = 0; i < mastered.size(); i++) {
            bw1.write(mastered.get(i).getEns() + "  " + mastered.get(i).getChs());
            bw1.newLine();
        }
        for (int i = 0; i < notMaster.size(); i++) {
            bw2.write(notMaster.get(i).getEns() + "  " + notMaster.get(i).getChs());
            bw2.newLine();
        }
        bw1.close();
        bw2.close();
    }

    private static int getIndex(ArrayList<Word> notMaster, int id) {
        int index = 0;
        for (int i = 0; i < notMaster.size(); i++) {
            if(id == notMaster.get(i).getId()){
                index++;
            }
        }
        return index-1;
    }
}
