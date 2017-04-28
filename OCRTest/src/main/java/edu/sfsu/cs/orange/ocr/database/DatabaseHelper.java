package edu.sfsu.cs.orange.ocr.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;
import edu.sfsu.cs.orange.ocr.entity.Word;

public class DatabaseHelper extends SQLiteAssetHelper {
    private static final String DATABASE_NAME = "dictionary.sqlite";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public Word search(String key) {
        Word word = null;
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        String[] sqlSelect = {"dict_id", "new_word", "mean"};
        String sqlTables = "dict_tbl";
        qb.setTables(sqlTables);
        Cursor c = qb.query(db, sqlSelect, sqlSelect[1] + " LIKE ? ", new String[] {key.trim().toLowerCase() + "%"}, null, null, null);
        if (c != null && c.moveToFirst()) {
            word = new Word(c.getInt(0), c.getString(1), c.getString(2));
        }
        return word;
    }
}