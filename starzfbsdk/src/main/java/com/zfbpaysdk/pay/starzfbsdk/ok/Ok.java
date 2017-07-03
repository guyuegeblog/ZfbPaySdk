package com.zfbpaysdk.pay.starzfbsdk.ok;

import java.util.concurrent.TimeUnit;



/**
 * Created by ASUS on 2017/6/9.
 */
public class Ok {
    //饿汉式单例类.在类初始化时，已经自行实例化
    private Ok() {
    }

//    private static final OkHttpClient okHttpClient = new OkHttpClient().newBuilder().connectTimeout(NetConfig.CONN_TIMEOUT, TimeUnit.MILLISECONDS)
//            .readTimeout(NetConfig.READ_TIMEOUT, TimeUnit.MILLISECONDS)
//            .writeTimeout(NetConfig.WRITE_TIMEOUT, TimeUnit.MILLISECONDS).build();
//
//    //静态工厂方法
//    public static OkHttpClient getInstance() {
//        return okHttpClient;
//    }
}
