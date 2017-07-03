package com.zfbpaysdk.pay.starzfbsdk.zfbpay;

import android.graphics.Bitmap;

import com.zfbpaysdk.pay.starzfbsdk.model.PayInfo;
import com.zfbpaysdk.pay.starzfbsdk.model.PayStatus;

/**
 * Created by ASUS on 2017/6/7.
 */
public interface StarPayResult {
    void aliPayResultCallBack(String result);

    void aliPayQueryCallBack(PayStatus serverResult);

    void wxPayResultCallBack(String result);

    void wxPayQueryCallBack(String serverResult);

    void wxPublicSignalPayQueryCallBack(String serverResult);

    void aliWapPayQueryCallBack(String serverResult);

    void payErrorCallBack(int errorCode);

    void wxCodePayResultCallBack(String result);

    void wxCodePayQueryCallBack(String serverResult);

    void wxCodePay2ResultCallBack(Bitmap bitmap);

    void wxCodePay2QueryCallBack(String serverResult);
}
