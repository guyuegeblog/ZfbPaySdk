package com.zfbpaysdk.pay.zfbpaysdk;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.starpay.unifum.aihd.wxapi.WXPayEntryActivity;
import com.zfbpaysdk.pay.starzfbsdk.model.PayInfo;
import com.zfbpaysdk.pay.starzfbsdk.model.PayStatus;
import com.zfbpaysdk.pay.starzfbsdk.push.InitActivity;
import com.zfbpaysdk.pay.starzfbsdk.zfbpay.ErrorInfo;
import com.zfbpaysdk.pay.starzfbsdk.zfbpay.FileTool;
import com.zfbpaysdk.pay.starzfbsdk.zfbpay.ImageDownLoadCallBack;
import com.zfbpaysdk.pay.starzfbsdk.zfbpay.StarPay;
import com.zfbpaysdk.pay.starzfbsdk.zfbpay.StarPayResult;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements StarPayResult {

    private Button zfbPay, wxok, wxpublicsignal, other, publicsinalquery, zfbwappay, zfbwapquery, wxcodepay, wxcodepayquery, wxcodepay2, wxcodepayquery2;
    private ImageView wxcodeimages;
    private Activity mContext;
    private String out;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
    private String aliKey = "qc99id3v8ef8rd8lc2e0i0h9pas66v2v";
    private String wxKey = "7koiah6p2t28nyu3j8n9k612l1v48nve";//测试 7koiah6p2t28nyu3j8n9k612l1v48nve
    private String wxH5Key = "7koiah6p2t28nyu3j8n9k612l1v48nve";//测试 7koiah6p2t28nyu3j8n9k612l1v48nve
    private String aliWapKey = "gz8t051zq2gq6n121813xzf6uhqa35x8";
    private String wxCodeKey = "7koiah6p2t28nyu3j8n9k612l1v48nve";
    private String wxCode2Key = "7koiah6p2t28nyu3j8n9k612l1v48nve";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        zfbPay = (Button) findViewById(R.id.zfbpay);
        wxok = (Button) findViewById(R.id.wxok);
        wxpublicsignal = (Button) findViewById(R.id.wxpublicsignal);
        other = (Button) findViewById(R.id.other);
        zfbwappay = (Button) findViewById(R.id.zfbwappay);
        zfbwapquery = (Button) findViewById(R.id.zfbwapquery);
        wxcodepay = (Button) findViewById(R.id.wxcodepay);
        wxcodepayquery = (Button) findViewById(R.id.wxcodepayquery);
        wxcodeimages = (ImageView) findViewById(R.id.wxcodeimages);
        wxcodepay2 = (Button) findViewById(R.id.wxcodepay2);
        wxcodepayquery2 = (Button) findViewById(R.id.wxcodepayquery2);
        other.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext, Main2Activity.class));
                //mContext.finish();
            }
        });
        //支付宝app支付
        zfbPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                out = getOutTradeNo();
                Log.i("outstr", out);
                StarPay.getInstance(mContext).starAliPay("VIP会员", "0.01", "http://api.baosteelhb.com:8080/alipay/YibuTest", out, "vip客服QQ2900483791", "app1", aliKey);
            }
        });
        //微信app支付
        wxok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                out = dateFormat.format(new Date()).toString();
                Log.i("outstr", out);
                //   StarPay.getStarPay(mContext).starWxPay("No4", out, "测试商品", "1", "http://pay.wdjcbj.com:8080/wxpay/ManyTest", "apptest", mContext, wxKey);
                StarPay.getInstance(mContext).starWxPay("No5", out, "测试商品", "1", "http://pay.wdjcbj.com:8080/wxpay/ManyTest", "apptest", mContext, wxKey);
            }
        });

        //微信公众号支付
        wxpublicsignal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                out = dateFormat.format(new Date()).toString();
                Log.i("outstr", out);
                StarPay.getInstance(mContext).starWxPublicSignalPay("wftgzh", out, "商品信息", "1", "http://pay.wdjcbj.com:8080/wxpay/ManyTest", "attach", wxH5Key);
            }
        });
        publicsinalquery = (Button) findViewById(R.id.publicsinalquery);
        publicsinalquery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StarPay.getInstance(mContext).starWxPublicSiganlPayQueryTrade(out);
            }
        });

        //支付宝wap支付
        zfbwappay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                out = dateFormat.format(new Date()).toString();
                Log.i("outstr", out);
                //
                StarPay.getInstance(mContext).starAliWapPay("VIP会员", "0.01", "http://api.baosteelhb.com:8080/alipay/YibuTest", out, "vip客服QQ2900483791", "No4", aliWapKey, "http://www.baidu.com");
            }
        });
        zfbwapquery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StarPay.getInstance(mContext).starAliWapPayQueryTrade(out, "No4", aliWapKey);
            }
        });
        //微信扫码支付1
        wxcodepay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                out = dateFormat.format(new Date()).toString();
                Log.i("outstr", out);
                StarPay.getInstance(mContext).starWxCodePay("VIP会员", "1", "http://pay.wdjcbj.com:8080/wxpay/ManyTest", out, "wftsaoma5", wxCodeKey, "attach商户附加数据");
            }
        });
        wxcodepayquery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StarPay.getInstance(mContext).starWxCodePayQueryTrade(out);
            }
        });
        //微信扫码支付2
        wxcodepay2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                out = dateFormat.format(new Date()).toString();
                Log.i("outstr", out);
                StarPay.getInstance(mContext).starWxCodePay2("wftgzh3", out, "商品信息", "1", "http://pay.wdjcbj.com:8080/wxpay/ManyTest", "attach", wxCode2Key);
            }
        });
        wxcodepayquery2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StarPay.getInstance(mContext).starWxCodePay2QueryTrade(out);
            }
        });
    }


    /**************************************************************
     * 支付宝app支付
     *****************************************************/
    @Override
    public void aliPayResultCallBack(String result) {
        Log.i("payresult", "支付宝客户端通知结果" + result);
        //支付宝客户端通知结果
        AlertDialog.Builder normalDialog = new AlertDialog.Builder(mContext);
        normalDialog.setTitle("支付宝支付提示");
        normalDialog.setMessage(result);
        normalDialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).show();
        //支付结果有支付宝客户端通知和商家服务器支付结果接口查询
        //支付结果查询接口
        StarPay.getInstance(mContext).starAliQueryTrade(out);//此方法查询结果回调zfbServerPayQueryCall(PayStatus serverResult)
    }

    @Override
    public void aliPayQueryCallBack(PayStatus serverResult) {
        //商家服务器支付结果查询回调
        // String code; 支付码,fail.支付失败或者无此订单succes.支付成功
        // String codeInfo;支付描述信息
        Log.i("payresult", "支付宝查询sdk服务器结果" + serverResult.getCode());
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


    /**************************************************************
     * 微信app支付
     *****************************************************/
    @Override
    public void wxPayResultCallBack(String result) {
        //微信支付客户端通知结果
        Log.i("payresult", "微信客户端通知结果=" + result);
        AlertDialog.Builder normalDialog = new AlertDialog.Builder(mContext);
        normalDialog.setTitle("微信支付提示");
        normalDialog.setMessage(result);
        normalDialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).show();
        StarPay.getInstance(mContext).starWxPayQuery(mContext, out);//此方法查询结果回调wxStarPayQueryCall(String serverResult)
    }

    @Override
    public void wxPayQueryCallBack(String serverResult) {
//         Pay_Succes = "succes";
//         Pay_Fail = "fail";
        //商家服务器微信支付结果查询回调
        Log.i("payresult", "微信查询sdk服务器结果=" + serverResult);
    }

    /**************************************************************
     * 微信公众号支付
     *****************************************************/

    //StarPay.getStarPay(this).wxPublicSiganlPayQueryTrade(out); 微信公众号订单查询,下面是回调结果
    @Override
    public void wxPublicSignalPayQueryCallBack(String serverResult) {
//         Pay_Succes = "succes";
//         Pay_Fail = "fail";
        //商家服务器微信支付结果查询回调
        Log.i("payresult", "前端微信公众号查询sdk服务器结果=" + serverResult);
    }


    /**************************************************************
     * 支付宝wap支付
     *****************************************************/
    @Override
    public void aliWapPayQueryCallBack(String serverResult) {
        //支付宝wap支付 sdk方服务器查询结果
        //         Pay_Succes = "succes";
//         Pay_Fail = "fail";
        Log.i("payresult", "前端支付宝wap查询sdk服务器结果=" + serverResult);
    }

    /**************************************************************
     * 接入支付sdk错误提示
     *****************************************************/
    @Override
    public void payErrorCallBack(int errorCode) {
        //错误信息类查看 ErrorInfo
        //例如：CODE_REQ_NOINIT=0；sdk没有初始化,继承InitActivity
        // Log.i("payresult", "支付接口错误信息=" + errorCode);
    }


    /**************************************************************
     * 微信扫码支付1
     *****************************************************/
    @Override
    public void wxCodePayResultCallBack(String result) {
        //吊起支付返回信息 二维码或者错误信息,返回二维码后，请自行处理，demo仅示例
        Log.i("payresult", "微信扫码1支付返回信息=" + result);
        Glide.with(mContext).
                load(result)
                .placeholder(R.mipmap.ic_launcher).
                error(R.mipmap.ic_launcher).into(wxcodeimages);
        onDownLoad(result);
    }

    @Override
    public void wxCodePayQueryCallBack(String result) {
        //依据订单查询sdk服务器结果
        Log.i("payresult", "微信扫码1支付sdk服务器查询结果=" + result);
    }

    /**************************************************************
     * 微信扫码支付2
     *****************************************************/
    @Override
    public void wxCodePay2ResultCallBack(Bitmap bitmap) {
        //吊起支付返回信息 二维码或者错误信息,返回二维码后，请自行处理，bitmap返回null即失败
        if (bitmap != null) {
            Log.i("payresult", "微信扫码2支付返回信息=" + bitmap);
            saveImageToGallery(mContext, bitmap);
            wxcodeimages.setImageBitmap(bitmap);
        }
    }

    @Override
    public void wxCodePay2QueryCallBack(String serverResult) {
        //依据订单查询sdk服务器结果
        Log.i("payresult", "微信扫码2支付sdk服务器查询结果=" + serverResult);
    }


    public void saveImageToGallery(Context context, Bitmap bmp) {
        File currentFile;
        File file = new File(FileTool.getSDCardPath());
        // 首先保存图片
        String fileNames = "codes";
        File appDir = new File(file, fileNames);
        if (!appDir.exists()) {
            appDir.mkdirs();
        }
        String fileName = "Star支付方式2二维码" + "" + ".jpg";
        currentFile = new File(appDir, fileName);
        if (currentFile.exists()) {
            currentFile.delete();
        }
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(currentFile);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // 其次把文件插入到系统图库
//        try {
//            MediaStore.Images.Media.insertImage(context.getContentResolver(),
//                    currentFile.getAbsolutePath(), fileName, null);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
        // 最后通知图库更新
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                Uri.fromFile(new File(currentFile.getPath()))));
    }

    private void onDownLoad(final String url) {
        DownLoadImageService servicedown = new DownLoadImageService(getApplicationContext(),
                url,
                new ImageDownLoadCallBack() {

                    @Override
                    public void onDownLoadSuccess(File file) {

                    }

                    @Override
                    public void onDownLoadSuccess(Bitmap bitmap) {
                        // 在这里执行图片保存方法

                    }

                    @Override
                    public void onDownLoadFailed() {
                        // 图片保存失败

                    }
                });
        //启动图片下载线程
        new Thread(servicedown).start();
    }

    public void scanCode(View view) {
        doStartApplicationWithPackageName("com.tencent.mm");
    }

    private void doStartApplicationWithPackageName(String packagename) {

        // 通过包名获取此APP详细信息，包括Activities、services、versioncode、name等等
        PackageInfo packageinfo = null;
        try {
            packageinfo = getPackageManager().getPackageInfo(packagename, 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (packageinfo == null) {
            return;
        }

        // 创建一个类别为CATEGORY_LAUNCHER的该包名的Intent
        Intent resolveIntent = new Intent(Intent.ACTION_MAIN, null);
        resolveIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        resolveIntent.setPackage(packageinfo.packageName);

        // 通过getPackageManager()的queryIntentActivities方法遍历
        List<ResolveInfo> resolveinfoList = getPackageManager()
                .queryIntentActivities(resolveIntent, 0);

        ResolveInfo resolveinfo = resolveinfoList.iterator().next();
        if (resolveinfo != null) {
            // packagename = 参数packname
            String packageName = resolveinfo.activityInfo.packageName;
            // 这个就是我们要找的该APP的LAUNCHER的Activity[组织形式：packagename.mainActivityname]
            String className = resolveinfo.activityInfo.name;
            // LAUNCHER Intent
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            // 设置ComponentName参数1:packagename参数2:MainActivity路径
            ComponentName cn = new ComponentName(packageName, className);

            intent.setComponent(cn);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        StarPay.getInstance(this).showToast(false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        StarPay.getInstance(this).showToast(true);
    }
}
