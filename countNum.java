package com.lxy.CET;

public class countNum {
    public int[] arrRight1 = new int[2500];
    public int[] arrRight2 = new int[2500];
    public int[] arrError1 = new int[2500];
    public int[] arrError2 = new int[2500];
    public int arrR1Num;
    public int arrR2Num;
    public int arrE1Num;
    public int arrE2Num;
    public int id;//接收之前随机数产生的单词序号
    public int count1;
    public int count2;
    public static boolean runl;
    public static boolean runl2;

    public countNum( int arrR1Num, int arrR2Num, int arrE1Num, int arrE2Num) {
        this.arrR1Num = arrR1Num;
        this.arrR2Num = arrR2Num;
        this.arrE1Num = arrE1Num;
        this.arrE2Num = arrE2Num;
    }

    public countNum() {
    }
}
