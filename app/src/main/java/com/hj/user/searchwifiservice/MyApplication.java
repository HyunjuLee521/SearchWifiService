package com.hj.user.searchwifiservice;

import android.app.Application;
import android.graphics.Typeface;

import com.hj.user.searchwifiservice.TypefaceManager;

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

        TypefaceManager.mKopubDotumBoldTypeface = Typeface.createFromAsset(getAssets(), "KoPubDotumBold.ttf");
        TypefaceManager.mKopubDotumMediumTypeface = Typeface.createFromAsset(getAssets(), "KoPubDotumMedium.ttf");
        TypefaceManager.mKopubDotumLightTypeface = Typeface.createFromAsset(getAssets(), "KoPubDotumLight.ttf");
    }
}
