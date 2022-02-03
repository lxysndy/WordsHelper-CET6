package com.lxy.CET;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Random;

public class System_1 implements Runnable {
    static JLabel jlt = new JLabel();

    public void run() {
        int time = 300;
        countNum.runl2 = true;
        while (countNum.runl2) {
            time--;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (time <= 0) {
                break;
            }
            jlt.setText(String.valueOf(time));
        }
        if(time <= 0){
            JFrame jFrame = new JFrame("结果");
            jFrame.setLayout(new FlowLayout(FlowLayout.LEFT));
            jFrame.setBounds(450, 300, 450, 300);
            JLabel jLabel = new JLabel("超时了！");
            jFrame.add(jLabel);
            jFrame.setVisible(true);
            jFrame.setResizable(false);
        }


    }
    /**
     * 进行flag的初始化，默认为0,都被答对为1，都被答错为2
     */
    public static void initId(ArrayList<Word> words) {
        for (int i = 0; i < words.size(); i++) {
            words.get(i).flag = 0;
        }
    }

    /**
     * 展示是否导入单词的主页，并导入单词
     *
     * @param words
     */
    public static void showMain1(ArrayList<Word> words, ArrayList<Word> mastered, ArrayList<Word> notMaster) throws Exception {
        readWords(words, mastered, notMaster);
    }

    /**
     * 将文档中的单词读入数组
     *
     * @param words
     * @throws Exception
     */
    public static void readWords(ArrayList<Word> words, ArrayList<Word> mastered, ArrayList<Word> notMaster) throws Exception {
        BufferedReader br = null;
        Reader fr = null;
        try {
            fr = new FileReader("D:\\javacode\\javasepro\\hello-app\\src\\cet6\\CET6words.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        br = new BufferedReader(fr);
        String str;
        int count = 0;
        while ((str = br.readLine()) != null) {
            String Engstr;
            String Chsstr;
            String[] nowEntry;
            if (str.startsWith("﻿")) {
                str = str.substring(1);
            }//排除异常
            if (str.endsWith(" ")) {
                for (int i = str.length() - 1; i >= 0; i--) {
                    if (str.charAt(i) == ' ') {
                        str = str.substring(0, i);
                    } else {
                        break;
                    }
                }
            }
            nowEntry = str.split(" {2}", 2);
            Engstr = nowEntry[0];
            Chsstr = nowEntry[1];
            count++;
            Word word = new Word(count, Chsstr, Engstr, 0, 0);
            words.add(word);
        }
        System.out.println("单词已添加完毕！");
        initId(words);
    }


    /**
     * 初始化储存数组
     */
    public static void initArr(int arr[]) {
        for (int i = 0; i < arr.length; i++) {
            arr[i] = -1;
        }
    }


    /**
     * 成绩记忆功能
     *
     * @param words
     * @param arr1Right
     * @param arr1Error
     * @param arr2Right
     * @param arr2Error
     */
    public static void Memory(ArrayList<Word> words, int[] arr1Right, int[] arr1Error, int[] arr2Right, int[] arr2Error, ArrayList<Word> mastered, ArrayList<Word> notMaster, BufferedWriter bw1, BufferedWriter bw2) throws IOException {
        /*//两次答错
        for (int i = 0; i < arr1Error.length; i++) {
            for (int j = 0; j < arr2Error.length; j++) {
                if ((arr1Error[i] == arr2Error[j]) && (arr1Error[i] != -1)) {
                    words.get(arr1Error[i]).flag = 2;
                }
            }
        }*/
        for (int i = 0; i < arr1Error.length; i++) {
            if (arr1Error[i] != -1){
                words.get(arr1Error[i]).flag = 2;
            }
        }
        for (int i = 0; i < arr2Error.length; i++) {
            if (arr2Error[i] != -1){
                words.get(arr2Error[i]).flag = 2;
            }
        }
        //两次答对
        for (int i = 0; i < arr1Right.length; i++) {
            for (int j = 0; j < arr2Right.length; j++) {
                if ((arr1Right[i] == arr2Right[j]) && (arr1Right[i] != -1)) {
                    words.get(arr1Right[i]).flag = 1;
                }
            }
        }

        for (int i = 0; i < words.size(); i++) {
            if (words.get(i).flag == 1) {
                int flag = 1;
                Word word = new Word(words.get(i).getId(), words.get(i).getChs(), words.get(i).getEns(), words.get(i).flag, words.get(i).flagWrite);
                if (mastered.size() == 0) {
                    mastered.add(word);
                }
                for (int j = 0; j < mastered.size(); j++) {
                    if (mastered.get(j).getEns().equals(word.getEns())) {
                        flag = 0;
                    }
                }
                if(flag == 1){
                    mastered.add(word);
                }
                System.out.println("已掌握单词：" + words.get(i).getEns());//用于调试！！
            }
        }
        for (int i = 0; i < mastered.size(); i++) {
            if (mastered.get(i).flagWrite == 0) {
                bw1.write(mastered.get(i).getEns() + "  " + mastered.get(i).getChs());
                bw1.newLine();
            }
            mastered.get(i).flagWrite = 1;
        }
        for (int i = 0; i < words.size(); i++) {
            if (words.get(i).flag == 2) {
                int flag = 1;
                Word word = new Word(words.get(i).getId(), words.get(i).getChs(), words.get(i).getEns(), words.get(i).flag, words.get(i).flagWrite);
                if (notMaster.size() == 0) {
                    notMaster.add(word);
                }
                for (int j = 0; j < notMaster.size(); j++) {
                    if (notMaster.get(j).getEns().equals(word.getEns())) {
                        flag = 0;
                    }
                }
                if(flag == 1){
                    notMaster.add(word);
                }
                System.out.println("未掌握单词：" + words.get(i).getEns());//用于调试！！
            }
        }
        for (int i = 0; i < notMaster.size(); i++) {//也是调试
            System.out.println("未掌握单词（另一个集合" + notMaster.get(i).getEns());
        }
        for (int i = 0; i < notMaster.size(); i++) {
            if (notMaster.get(i).flagWrite == 0) {
                bw2.write(notMaster.get(i).getEns() + "  " + notMaster.get(i).getChs());
                bw2.newLine();
            }
            notMaster.get(i).flagWrite = 2;
        }
        bw1.close();
        bw2.close();
    }



    /**
     * 英中挑战
     * @param words
     */
    public static void enTochs(ArrayList<Word> words, countNum countNum,ArrayList<Word> mastered,ArrayList <Word> notMaster) {
        countNum.arrE1Num = 0;
        countNum.arrR1Num = 0;
        countNum.count1 = 0;
        JFrame jf1 = new JFrame("“英-中”挑战");
        jf1.setLayout(new FlowLayout(FlowLayout.LEFT));
        jf1.setBounds(450,300,450,300);
        jf1.add(jlt);
        Thread th=new Thread(new System_1());
            th.start();
            int id = getRandom(0,words.size()-1);
            while (words.get(id).flag == 1){
                id = getRandom(0,words.size()-1);
            }
            countNum.id = id;
            JPanel jPanel1 = new JPanel();

            JLabel jl = new JLabel("请选择单词的中文释义：" + words.get(id).getEns());
            jPanel1.add(jl);
            int wordId = getRandom(0,3);
            String[] chs = new String[4];
        for (int i = 0; i < 4; i++) {
            if(i == wordId){
                chs[i] = words.get(id).getChs();
            }else {
                chs[i] = words.get(getRandom(0, words.size()-1)).getChs();
            }
        }
            //添加按钮
        JRadioButton jb0 = new JRadioButton(chs[0]);
        JRadioButton jb1 = new JRadioButton(chs[1]);
        JRadioButton jb2 = new JRadioButton(chs[2]);
        JRadioButton jb3 = new JRadioButton(chs[3]);
            JButton jButton = new JButton("下一题");
            jButton.setSize(45,15);
            jPanel1.add(jButton);
            jPanel1.add(jb0);
            jPanel1.add(jb1);
            jPanel1.add(jb2);
            jPanel1.add(jb3);
            jPanel1.setLayout(new GridLayout(6,1,10,10));
        jf1.add(jPanel1);
        jf1.setResizable(false);
        jf1.setVisible(true);
        jf1.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                countNum.runl2 = false;
            }
        });
        jb0.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(jb0.getText().equals(words.get(countNum.id).getChs())){
                    jb0.setBackground(Color.green);
                    countNum.arrRight1[countNum.arrR1Num++] = countNum.id;
                }else {
                    jb0.setBackground(Color.red);
                    countNum.arrError1[countNum.arrE1Num++] = countNum.id;
                }
            }
        });
        jb1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(jb1.getText().equals(words.get(countNum.id).getChs())){
                    jb1.setBackground(Color.green);
                    countNum.arrRight1[countNum.arrR1Num++] = countNum.id;
                }else {
                    jb1.setBackground(Color.red);
                    countNum.arrError1[countNum.arrE1Num++] = countNum.id;
                }
            }
        });
        jb2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(jb2.getText().equals(words.get(countNum.id).getChs())){
                    jb2.setBackground(Color.green);
                    countNum.arrRight1[countNum.arrR1Num++] = countNum.id;
                }else {
                    jb2.setBackground(Color.red);
                    countNum.arrError1[countNum.arrE1Num++] = countNum.id;
                }
            }
        });
        jb3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(jb3.getText().equals(words.get(countNum.id).getChs())){
                    jb3.setBackground(Color.green);
                    countNum.arrRight1[countNum.arrR1Num++] = countNum.id;
                }else {
                    jb3.setBackground(Color.red);
                    countNum.arrError1[countNum.arrE1Num++] = countNum.id;
                }
            }

        });


//            char command = sc.next().charAt(0);
//            if(isIn[command - 'A']){
//                System.out.println("恭喜你选对了！");
//                arrRight[k++] = id;
//            }else{
//                System.out.println("答案错误或是输入不符合规则！");
//                errNum++;
//                arrError[j++] = id;

            jButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    //countNum.count1 = 1;

                    countNum.count1++;
                    jb0.setBackground(null);
                    jb1.setBackground(null);
                    jb2.setBackground(null);
                    jb3.setBackground(null);
                    jb0.setSelected(false);
                    jb1.setSelected(false);
                    jb2.setSelected(false);
                    jb3.setSelected(false);
                    int id = getRandom(0,words.size()-1);
                    while (words.get(id).flag == 1){
                        id = getRandom(0,words.size()-1);
                    }
                    countNum.id = id;
                    JPanel jPanel1 = new JPanel();
                    jl.setText("请选择单词的中文释义：" + words.get(id).getEns());
                    int wordId = getRandom(0,3);
                    String[] chs = new String[4];
                    for (int i = 0; i < 4; i++) {
                        if(i == wordId){
                            chs[i] = words.get(id).getChs();
                        }else {
                            chs[i] = words.get(getRandom(0, words.size()-1)).getChs();
                        }
                    }
                    jb0.setText(chs[0]);
                    jb1.setText(chs[1]);
                    jb2.setText(chs[2]);
                    jb3.setText(chs[3]);
                    jButton.setText("下一题");
                    jf1.add(jPanel1);
                    jf1.setResizable(false);
                    jf1.setVisible(true);
                    if(countNum.count1 == 20){
                        jf1.dispose();
                        JFrame j = new JFrame("结果");
                        j.setLayout(new FlowLayout(FlowLayout.LEFT));
                        j.setBounds(450,300,450,300);
                        JLabel l = new JLabel();
                        if((countNum.arrE1Num > 1)||((countNum.arrE1Num ==0)&&(countNum.arrR1Num==0))){
                            l.setText("挑战失败，错误：" + countNum.arrE1Num);
                            System.out.print("错误的id:");//用于调试
                            for (int u = 0; (u < countNum.arrError1.length)&&(countNum.arrError1[u]!=-1); u++) {
                                System.out.print(countNum.arrError1[u] + " ");
                            } System.out.println();
                        }else {
                            l.setText("挑战成功，错误：" + countNum.arrE1Num);
                            System.out.print("错误的id:");//用于调试
                            for (int u = 0; (u < countNum.arrError1.length)&&(countNum.arrError1[u]!=-1); u++) {
                                System.out.print(countNum.arrError1[u] + " ");
                            } System.out.println();
                        }
                        j.add(l);
                        j.setVisible(true);
                        j.setResizable(false);
                    }
                }
            });


    //}
//        if(countNum.arrE1Num > 3){
//            System.out.println("挑战失败！");
//        }else {
//            System.out.println("挑战成功！");
//        }
    //}

    }

    public static int getRandom(int start, int end) {
        Random rand = new Random();
        boolean[] bool = new boolean[end - start + 1];
        int[] number = new int[end - start + 1];
        int randINT = 0;
        for (int i = start; i < end; i++){
            do {
                randINT = rand.nextInt(end - start + 1) + start;
            }while (bool[randINT]);
            number[randINT] = randINT;
            bool[randINT] = true;
        }
        return randINT;
    }
}
