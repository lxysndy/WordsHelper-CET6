package com.lxy.CET;

public class Word {
    private int id;
    private String Chs;
    private String Ens;
    public int flag;//标记单词是否被掌握或是遗忘
    public int flagWrite;//标记单词是否被写入

    public Word(int id, String chs, String ens, int flag, int flagWrite) {
        this.id = id;
        Chs = chs;
        Ens = ens;
        this.flag = flag;
        this.flagWrite = flagWrite;
    }

    public Word() {
    }

    public int getId() {
        return id;
    }

    public int getFlag(){
        return flag;
    }

    public void setFlag(){
        this.flag = flag;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getChs() {
        return Chs;
    }

    public void setChs(String chs) {
        Chs = chs;
    }

    public String getEns() {
        return Ens;
    }

    public void setEns(String ens) {
        Ens = ens;
    }
}
