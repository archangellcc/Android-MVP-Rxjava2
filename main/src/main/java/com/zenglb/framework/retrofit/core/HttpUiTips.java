package com.zenglb.framework.retrofit.core;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.zenglb.framework.R;
import com.zenglb.framework.retrofit.utils.HttpDialogUtils;
import com.zenglb.framework.rxhttp.BaseObserver;

import java.lang.ref.WeakReference;

/**
 *
 * Created by zenglb on 2017/3/24.
 */
public class HttpUiTips {
    private static AlertDialog fatalEorTips;
    private static WeakReference<Context> lastContext;           // 其实根本没有必要

    /**
     * http 请求遇阻提示，比如没有网络不提示，再重试也无用
     *
     */
    public static void alertTip(Context mContext, String message, int code) {
        Log.e("222222222",mContext.toString()+" ABC");
        //只有主线程才回
        if(!Thread.currentThread().getName().toString().equals("main")){
            return;
        }
        //Activity作为窗口的载体不能无效的
        if (mContext == null || !(mContext instanceof Activity) || ((Activity) mContext).isFinishing())
            return;

        if(fatalEorTips==null){  //首次创建
            fatalEorTips = new AlertDialog.Builder(mContext).create();
        }else{
            if(mContext!=lastContext.get()){ // 换了一个新的Activity
                fatalEorTips=null;
                fatalEorTips = new AlertDialog.Builder(mContext).create();
            }
        }

        lastContext=new WeakReference(mContext);;

        fatalEorTips.setTitle("获取网络数据失败");
        fatalEorTips.setMessage(message);
        fatalEorTips.setButton(DialogInterface.BUTTON_POSITIVE, "知道了", (dialog, which) -> {
//            fatalEorTips=null;
        });

        fatalEorTips.show();
    }



    /**
     * showDialog & dismissDialog 在http 请求开始的时候显示，结束的时候消失
     * 当然不是必须需要显示的 !
     */
    public static void showDialog(final Context mContext, final boolean canceledOnTouchOutside, final String messageText) {
        if (mContext == null || !(mContext instanceof Activity) || ((Activity) mContext).isFinishing())
            return;
        ((Activity) mContext).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                HttpDialogUtils.showDialog(mContext, canceledOnTouchOutside, messageText);
            }
        });
    }

    public static void dismissDialog(final Context mContext) {
        if (mContext == null || !(mContext instanceof Activity) || ((Activity) mContext).isFinishing())
            return;             //maybe not good !
        if (mContext != null) {
            ((Activity) mContext).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    HttpDialogUtils.dismissDialog();
                }
            });
        }
    }

}
