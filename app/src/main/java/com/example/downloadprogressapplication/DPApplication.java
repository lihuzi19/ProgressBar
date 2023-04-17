package com.example.downloadprogressapplication;

import android.app.Application;
import android.content.Context;
import android.util.Log;

public class DPApplication extends Application {

    private final static String TAG = "DPApplication";


    @Override
    public void onCreate() {
        super.onCreate();
        recordOnCreate();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        recordAttachBaseContext();
    }

    private void recordAttachBaseContext() {
        Log.e(TAG, "attachBaseContext");
    }

    private void recordOnCreate() {
        Log.e(TAG, "onCreate");
    }
}
