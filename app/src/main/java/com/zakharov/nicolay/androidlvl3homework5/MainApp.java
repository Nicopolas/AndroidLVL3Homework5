package com.zakharov.nicolay.androidlvl3homework5;


import android.app.Application;

import com.orm.SugarContext;

import io.realm.Realm;
import io.realm.RealmConfiguration;


public class MainApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        SugarContext.init(this);

        Realm.init(this);
        RealmConfiguration configuration = new RealmConfiguration.Builder().build();
        Realm.setDefaultConfiguration(configuration);
    }
}

