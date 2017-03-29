package com.example.user.searchwifiservice;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by USER on 2017-03-28.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Realm.init(this);
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder().build();
        Realm.setDefaultConfiguration(realmConfiguration);
    }
}
