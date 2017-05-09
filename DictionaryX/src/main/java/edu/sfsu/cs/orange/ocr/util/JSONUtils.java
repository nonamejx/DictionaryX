package edu.sfsu.cs.orange.ocr.util;

import android.content.Context;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import edu.sfsu.cs.orange.ocr.entity.Word;
import io.realm.RealmObject;

public class JSONUtils {
    private static JSONUtils mInstance;
    private Context mContext;

    private JSONUtils(Context context) {
        this.mContext = context;
    }

    public static JSONUtils getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new JSONUtils(context);
        }
        return mInstance;
    }

    public List<Word> parseWords(String fileName) {
        Gson gson = new GsonBuilder().setExclusionStrategies(new ExclusionStrategy() {
            @Override
            public boolean shouldSkipField(FieldAttributes f) {
                /*
                * You must remember getDeclaringClass() in order to avoid bullshit parsing
                * */
                return f.getDeclaringClass().equals(RealmObject.class);
            }

            @Override
            public boolean shouldSkipClass(Class<?> clazz) {
                return false;
            }
        }).create();

        Type listType = new TypeToken<ArrayList<Word>>() {
        }.getType();
        InputStream inputStream = null;
        List<Word> words = null;
        JsonReader jsonReader = null;
        try {
            // read file
            inputStream = mContext.getAssets().open("dictionary.json");
            // json reader
            jsonReader = new JsonReader(new InputStreamReader(inputStream, "UTF-8"));
            words = gson.fromJson(jsonReader, listType);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                jsonReader.close();
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return words;
    }
}