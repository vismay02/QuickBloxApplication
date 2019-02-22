package com.example.quickBloxApplication;

import android.app.Application;

import com.quickblox.auth.session.QBSettings;
import com.quickblox.core.StoringMechanism;

public class CoreApp extends Application {
    static final String APP_ID = "75967";
    static final String AUTH_KEY = "YeCBfnJt3eg5ROK";
    static final String AUTH_SECRET = "aVx8Kd7VThqeR26";
    static final String ACCOUNT_KEY = "yxBkPNNiCj8HrEhyGVAX";

    @Override
    public void onCreate() {
        super.onCreate();

        QBSettings.getInstance().setStoringMehanism(StoringMechanism.UNSECURED);
        QBSettings.getInstance().init(getApplicationContext(), APP_ID, AUTH_KEY, AUTH_SECRET);
        QBSettings.getInstance().setAccountKey(ACCOUNT_KEY);
    }
}