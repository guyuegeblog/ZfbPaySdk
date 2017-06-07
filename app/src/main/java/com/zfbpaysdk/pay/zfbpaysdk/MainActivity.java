package com.zfbpaysdk.pay.zfbpaysdk;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.zfbpaysdk.pay.starzfbsdk.zfbpay.StarPay;
import com.zfbpaysdk.pay.starzfbsdk.zfbpay.StarPayResult;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements StarPayResult {

    private Button zfbPay;
    private Activity mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        zfbPay = (Button) findViewById(R.id.zfbpay);
        zfbPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new StarPay(mContext).startZfbPay("VIP会员", "0.01", "http://www.baidu.com", getOutTradeNo());
            }
        });
    }


    @Override
    public void payResult(String result) {
        Log.i("payresult", result);
        AlertDialog.Builder normalDialog = new AlertDialog.Builder(mContext);
        normalDialog.setTitle("支付提示");
        normalDialog.setMessage(result);
        normalDialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).show();
    }

    private String getOutTradeNo() {
        SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss", Locale.getDefault());
        Date date = new Date();
        String key = format.format(date);

        Random r = new Random();
        key = key + r.nextInt();
        key = key.substring(0, 15);
        return key;
    }
}
