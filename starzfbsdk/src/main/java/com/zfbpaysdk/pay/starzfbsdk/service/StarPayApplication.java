package com.zfbpaysdk.pay.starzfbsdk.service;

import android.app.Activity;
import android.app.Application;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.zfbpaysdk.pay.starzfbsdk.zfbpay.CrashHandler;

public class StarPayApplication extends Application {

    private static final String TAG = "starpay";
    private static StarHandler handler;
    public static Activity demoActivity;
    public static StringBuilder payloadData = new StringBuilder();

    @Override
    public void onCreate() {
        super.onCreate();
        if (handler == null) {
            handler = new StarHandler();
        }
//        CrashHandler crashHandler = CrashHandler.getInstance();
//        crashHandler.init(getApplicationContext());
    }

    public static void sendMessage(Message msg) {
        handler.sendMessage(msg);
    }

    public static class StarHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    //透传消息
                    if (demoActivity != null) {
                        payloadData.append((String) msg.obj);
                        Log.i("getuistr", "返回case0: " + ((String) msg.obj));
//                        payloadData.append("\n");
//                        if (GetuiSdkDemoActivity.tLogView != null) {
//                            GetuiSdkDemoActivity.tLogView.append(msg.obj + "\n");
//                        }
                    }
                    break;

                case 1:
                    //通知
                    if (demoActivity != null) {
//                        if (GetuiSdkDemoActivity.tLogView != null) {
//                            Log.i("getuistr", "返回case1: " + ((String) msg.obj));
//                            GetuiSdkDemoActivity.tView.setText((String) msg.obj);
//                        }
                    }
                    break;
            }
        }
    }
}
