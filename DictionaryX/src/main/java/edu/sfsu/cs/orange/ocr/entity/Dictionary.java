package edu.sfsu.cs.orange.ocr.entity;

import java.util.UUID;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Dictionary extends RealmObject {
    @PrimaryKey
    private String dictId;
    private RealmList<Word> dictData;
    private RealmList<Word> recentWords;

    public Dictionary() {
        this.dictId = UUID.randomUUID().toString();
        this.dictData = new RealmList<>();
        this.recentWords = new RealmList<>();
    }

    public String getDictId() {
        return dictId;
    }

    public RealmList<Word> getDictData() {
        return dictData;
    }

    public void setDictData(RealmList<Word> dictData) {
        this.dictData = dictData;
    }

    public RealmList<Word> getRecentWords() {
        return recentWords;
    }

    public void setRecentWords(RealmList<Word> recentWords) {
        this.recentWords = recentWords;
    }
}