package com.zfbpaysdk.pay.starzfbsdk.push;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.zfbpaysdk.pay.starzfbsdk.R;
//import com.zfbpaysdk.pay.starzfbsdk.service.DemoIntentService;
//import com.zfbpaysdk.pay.starzfbsdk.service.DemoPushService;
import com.zfbpaysdk.pay.starzfbsdk.service.StarPayApplication;
import com.zfbpaysdk.pay.starzfbsdk.zfbpay.StarPay;

import java.io.File;

public class InitActivity extends AppCompatActivity {
//    private static final String TAG = "starpay";
//    private boolean isServiceRunning = false;
//    private String appkey = "";
//    private String appsecret = "";
//    private String appid = "";
//    private static final int REQUEST_PERMISSION = 0;
//    private Class userPushService = DemoPushService.class;
//    private Activity mContext;
    public static boolean initSdk = true;//默认false,无推送默认true,有推送默认false
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_star);
//        mContext = this;
//        parseManifests();
//        Log.d(TAG, "initializing sdk...");
//
//        PackageManager pkgManager = mContext.getPackageManager();
//
//        // 读写 sd card 权限非常重要, android6.0默认禁止的, 建议初始化之前就弹窗让用户赋予该权限
//        boolean sdCardWritePermission =
//                pkgManager.checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, mContext.getPackageName()) == PackageManager.PERMISSION_GRANTED;
//
//        // read phone state用于获取 imei 设备信息
//        boolean phoneSatePermission =
//                pkgManager.checkPermission(Manifest.permission.READ_PHONE_STATE, mContext.getPackageName()) == PackageManager.PERMISSION_GRANTED;
//
//        if (Build.VERSION.SDK_INT >= 23 && !sdCardWritePermission || !phoneSatePermission) {
//            requestPermission();
//        } else {
//            PushManager.getInstance().initialize(mContext.getApplicationContext(), userPushService);
//        }
//
//        // 注册 intentService 后 PushDemoReceiver 无效, sdk 会使用 DemoIntentService 传递数据,
//        // AndroidManifest 对应保留一个即可(如果注册 DemoIntentService, 可以去掉 PushDemoReceiver, 如果注册了
//        // IntentService, 必须在 AndroidManifest 中声明)
//        PushManager.getInstance().registerPushIntentService(mContext.getApplicationContext(), DemoIntentService.class);
//        // 应用未启动, 个推 service已经被唤醒,显示该时间段内离线消息
//        if (StarPayApplication.payloadData != null) {
//            //tLogView.append(StarPayApplication.payloadData);
//        }
//        PushManager.getInstance().initialize(this.getApplicationContext(), userPushService); // 重新初始化sdk
//        isServiceRunning = true;
//        initSdk = true;
//    }
//
//    private void parseManifests() {
//        String packageName = mContext.getPackageName();
//        try {
//            ApplicationInfo appInfo = mContext.getPackageManager().getApplicationInfo(packageName, PackageManager.GET_META_DATA);
//            if (appInfo.metaData != null) {
//                appid = appInfo.metaData.getString("PUSH_APPID");
//                appsecret = appInfo.metaData.getString("PUSH_APPSECRET");
//                appkey = appInfo.metaData.getString("PUSH_APPKEY");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    private void requestPermission() {
//        ActivityCompat.requestPermissions(mContext, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE,Manifest.permission.SYSTEM_ALERT_WINDOW},
//                REQUEST_PERMISSION);
//        Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
//                Uri.parse("package:" + getPackageName()));
//        startActivityForResult(intent,10);
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == 10) {
//            if (Build.VERSION.SDK_INT >= 23) {
//                if (!Settings.canDrawOverlays(this)) {
//                    // SYSTEM_ALERT_WINDOW permission not granted...
//                    Toast.makeText(InitActivity.this,"此应用需要这个选线",Toast.LENGTH_SHORT);
//                    requestPermission();
//                }
//            }
//        }
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
//        if (requestCode == REQUEST_PERMISSION) {
//            if ((grantResults.length == 2 && grantResults[0] == PackageManager.PERMISSION_GRANTED
//                    && grantResults[1] == PackageManager.PERMISSION_GRANTED)) {
//                PushManager.getInstance().initialize(this.getApplicationContext(), userPushService);
//            } else {
//                Log.e(TAG, "We highly recommend that you need to grant the special permissions before initializing the SDK, otherwise some "
//                        + "functions will not work");
//                PushManager.getInstance().initialize(this.getApplicationContext(), userPushService);
//            }
//        } else {
//            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        }
//    }
//
//    @Override
//    public void onDestroy() {
//        StarPayApplication.payloadData.delete(0, StarPayApplication.payloadData.length());
//        super.onDestroy();
//    }
//
//    @Override
//    public void onStop() {;
//        super.onStop();
//    }
//
//
//    /**
//     * 测试addTag接口.
//     */
//    private void addTag() {
//        final View view = new EditText(this);
//        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
//        alertBuilder.setTitle("设置Tag").setNegativeButton("取消", new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.dismiss();
//            }
//        }).setPositiveButton("确定", new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int which) {
//                TextView tagText = (TextView) view;
//
//                Log.d(TAG, "setTag input tags = " + tagText.getText().toString());
//
//                String[] tags = tagText.getText().toString().split(",");
//                Tag[] tagParam = new Tag[tags.length];
//                for (int i = 0; i < tags.length; i++) {
//                    Tag t = new Tag();
//                    t.setName(tags[i]);
//                    tagParam[i] = t;
//                }
//
//                int i = PushManager.getInstance().setTag(mContext, tagParam, "" + System.currentTimeMillis());
//                String text = "设置标签失败, 未知异常";
//
//                // 这里的返回结果仅仅是接口调用是否成功, 不是真正成功, 真正结果见{
//                // com.getui.demo.DemoIntentService.setTagResult 方法}
//                switch (i) {
//                    case PushConsts.SETTAG_SUCCESS:
//                        text = "接口调用成功";
//                        break;
//
//                    case PushConsts.SETTAG_ERROR_COUNT:
//                        text = "接口调用失败, tag数量过大, 最大不能超过200个";
//                        break;
//
//                    case PushConsts.SETTAG_ERROR_FREQUENCY:
//                        text = "接口调用失败, 频率过快, 两次间隔应大于1s";
//                        break;
//
//                    case PushConsts.SETTAG_ERROR_NULL:
//                        text = "接口调用失败, tag 为空";
//                        break;
//
//                    default:
//                        break;
//                }
//
//                Toast.makeText(mContext, text, Toast.LENGTH_SHORT).show();
//                dialog.dismiss();
//            }
//        }).setView(view);
//        alertBuilder.create().show();
//    }
//
//    private void getVersion() {
//        String version = PushManager.getInstance().getVersion(this);
//        Toast.makeText(this, "当前sdk版本 = " + version, Toast.LENGTH_SHORT).show();
//    }
//
//    private void getCid() {
//        String cid = PushManager.getInstance().getClientid(this);
//        Toast.makeText(this, "当前应用的cid = " + cid, Toast.LENGTH_LONG).show();
//        Log.d(TAG, "当前应用的cid = " + cid);
//    }
//
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK) {
//            Intent intent = new Intent(Intent.ACTION_MAIN);
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            intent.addCategory(Intent.CATEGORY_HOME);
//            startActivity(intent);
//            return true;
//        }
//        return super.onKeyDown(keyCode, event);
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        StarPay.getInstance(this).showToast(false);
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        StarPay.getInstance(this).showToast(true);
//    }
}
