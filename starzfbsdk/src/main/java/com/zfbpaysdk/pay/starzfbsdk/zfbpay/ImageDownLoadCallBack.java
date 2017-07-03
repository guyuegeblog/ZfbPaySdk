package com.zfbpaysdk.pay.starzfbsdk.zfbpay;

import android.graphics.Bitmap;

import java.io.File;

/**
 * Created by ASUS on 2017/3/10.
 */
public interface ImageDownLoadCallBack {
    void onDownLoadSuccess(File file);
    void onDownLoadSuccess(Bitmap bitmap);
    void onDownLoadFailed();
}
