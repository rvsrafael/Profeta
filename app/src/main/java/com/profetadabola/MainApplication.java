package com.profetadabola;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.orhanobut.hawk.Hawk;

public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        setupStetho();
        setupHawk();
//        setupCrashlytics();
    }

    private void setupStetho() {
        Hawk.init(this).build();
    }

    private void setupHawk() {
        Stetho.initializeWithDefaults(this);
    }

//    private void setupCrashlytics() {
//        Fabric.with(this, new Crashlytics());
//    }
}
