package myfirstapkas.com.qrcode;

import android.app.Application;
import  com.pushbots.push.Pushbots;

public class MainApplication extends Application {
    public void onCreate() {
        super.onCreate();
        // Initialize Pushbots Library
        Pushbots.sharedInstance().init(this);
    }
}
