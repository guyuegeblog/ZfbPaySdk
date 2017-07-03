package com.zfbpaysdk.pay.starzfbsdk.service;

import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.zfbpaysdk.pay.starzfbsdk.view.T;
import com.zfbpaysdk.pay.starzfbsdk.zfbpay.FileTool;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by ASUS on 2017/6/23.
 */
public class FileThread extends Thread {
    private static final int TAG_PROGERSS = 0;
    private String sd_Path = FileTool.getSDCardPath() + "/";
    private String filePath = sd_Path + "starfile/";
    private String saveFileAllName = filePath + "file.apk";
    private Handler handler;
    private String fileUrl = "";
    public static boolean execution = false;

    public FileThread(String url, Handler handler) {
        this.handler = handler;
        this.fileUrl = url;
    }

    @Override
    public synchronized void run() {
        super.run();
        int i=0;
        i++;
        Log.i("getuistr", "i:" + i);
        execution = true;
        FileOutputStream fileOutputStream = null;
        InputStream inputStream = null;
        try {
            URL url = new URL(fileUrl);
            //获取连接
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setRequestProperty("Connection", "Keep-Alive");
            connection.setRequestProperty("Charset", "UTF-8");
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setUseCaches(false);
            //打开连接
            connection.connect();
            //获取内容长度
            int contentLength = connection.getContentLength();
            if (new File(saveFileAllName).exists()) {
                new File(saveFileAllName).delete();
            }
            File file = new File(filePath);
            // 判断文件目录是否存在
            if (!file.exists()) {
                file.mkdir();
            }
            //输入流
            inputStream = connection.getInputStream();
            File myFile = new File(saveFileAllName);
            //输出流
            fileOutputStream = new FileOutputStream(myFile);
            byte[] bytes = new byte[1024];
            long totalReaded = 0;
            int temp_Len;
            while ((temp_Len = inputStream.read(bytes)) != -1) {
                totalReaded += temp_Len;
                long progress = totalReaded * 100 / contentLength;
                fileOutputStream.write(bytes, 0, temp_Len);
                Message msg = handler.obtainMessage();
                msg.what = TAG_PROGERSS;
                msg.arg1 = (int) progress;
                msg.arg2 = (int) totalReaded;
                handler.sendMessage(msg);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
            execution = false;
        } catch (ProtocolException e) {
            e.printStackTrace();
            execution = false;
        } catch (IOException e) {
            e.printStackTrace();
            execution = false;
        } finally {
            execution = false;
            try {
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
