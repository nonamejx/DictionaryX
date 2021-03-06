package edu.sfsu.cs.orange.ocr;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class DictionaryXApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // Configure Realm for the application
        Realm.init(getApplicationContext());
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder().deleteRealmIfMigrationNeeded().build();
        // Realm.deleteRealm(realmConfiguration); // Clean the old realm
        Realm.setDefaultConfiguration(realmConfiguration);
    }
}