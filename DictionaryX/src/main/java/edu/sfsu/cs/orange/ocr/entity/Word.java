package edu.sfsu.cs.orange.ocr.entity;

import com.google.gson.annotations.SerializedName;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Word extends RealmObject {
    @PrimaryKey
    @SerializedName("dict_id")
    private int dict_id;
    @SerializedName("new_word")
    private String new_word;
    @SerializedName("mean")
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

    public Word clone() {
        Word word = new Word();
        word.setDict_id(this.dict_id);
        word.setNew_word(new String(this.getNew_word()));
        word.setMean(new String(this.getMean()));
        return word;
    }
}