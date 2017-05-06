package edu.sfsu.cs.orange.ocr.entity;

public class Word {
    private int dict_id;
    private String new_word;
    private String mean;

    public Word() {
    }

    public Word(int dict_id, String new_word, String mean) {
        this.dict_id = dict_id;
        this.new_word = new_word;
        this.mean = mean;
    }

    public int getDict_id() {
        return dict_id;
    }

    public void setDict_id(int dict_id) {
        this.dict_id = dict_id;
    }

    public String getNew_word() {
        return new_word;
    }

    public void setNew_word(String new_word) {
        this.new_word = new_word;
    }

    public String getMean() {
        return mean;
    }

    public void setMean(String mean) {
        this.mean = mean;
    }
}