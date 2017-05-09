package edu.sfsu.cs.orange.ocr.database;

import android.content.Context;
import java.util.List;
import edu.sfsu.cs.orange.ocr.entity.Dictionary;
import edu.sfsu.cs.orange.ocr.entity.Word;
import io.realm.Realm;

public class RealmHelper {
    private Realm mRealm;
    private Context mContext;

    public RealmHelper(Context context) {
        this.mContext = context;
        this.mRealm = Realm.getDefaultInstance();
    }

    public Realm getRealm() {
        return mRealm;
    }

    public List<Word> getWords() {
        return mRealm.where(Word.class).findAll();
    }

    public Word getWord(String keyWord) {
        Word word = null;
        word = mRealm.where(Word.class).equalTo("new_word", keyWord.trim().toLowerCase()).findFirst();
        return word;
    }

    public boolean isMisspelled(String keyword) {
        if (keyword.trim().length() == 0) return true;
        else {
            return mRealm.where(Word.class).equalTo("new_word", keyword.trim().toLowerCase()).findFirst() == null ? true : false;
        }
    }

    public List<Word> getRecentWords() {
        Dictionary d = mRealm.where(Dictionary.class).findFirst();
        return d.getRecentWords();
    }

    public void addWordToHistory(Word word) {
        Dictionary d = mRealm.where(Dictionary.class).findFirst();
        mRealm.beginTransaction();
        d.getRecentWords().add(word);
        mRealm.commitTransaction();
    }

    public void removeWordFromHistory(Word word) {
        Dictionary d = mRealm.where(Dictionary.class).findFirst();
        mRealm.beginTransaction();
        d.getRecentWords().remove(word);
        mRealm.commitTransaction();
    }
}