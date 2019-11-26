package com.example.jxxy.cmk.http;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.Toast;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import rx.Subscriber;

// ProgressDialogSubscriber<T> 抽象类
public abstract class ProgressDialogSubscriber<T>  extends Subscriber<T> {

    private final Context mContext;
    private ProgressDialog mDialog;

    public ProgressDialogSubscriber(Context context){this.mContext=context;}

    @Override
    public void onCompleted() {
        dismissProgressDialog();
    }

    @Override
    public void onError(Throwable e) {
        if(e instanceof SocketTimeoutException){
            Toast.makeText(mContext,"网络中断，请检查你的网络状态", Toast.LENGTH_SHORT).show();
        }else if (e instanceof ConnectException){
            Toast.makeText(mContext,"网络中断，请检查您的网络状态", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(mContext,"error:"+e.getMessage(), Toast.LENGTH_SHORT).show();
            System.out.println("error:"+e.getMessage());
        }dismissProgressDialog();
    }//利用Subscriber进行,其中Observer 有三个方法：
    // onNext（T t）:一个参数方法，所有的事件都在此方法中执行，Rxjava中需要对每个事件单独执行，并且以队列的形式依次执行。
    // onCompleted（）：无参方法，表示事件正常结束，当没有onNext()方法发出的时候，需要触发onCompleted()方法标志事件正常完成。
    // onError（Throwable e）：一个参数方法，事件执行遇到异常，同时剩余的onNext不再执行。
    // 注意：onCompleted 和 onError 两个方法只能有一个执行，并且只能在事件序列的最后一个，要么是事件顺利完成触发onCompleted要么是出现异常触发onError 。


    private void dismissProgressDialog(){
        if (mDialog!=null&&mDialog.isShowing()){
            mDialog.dismiss();
            mDialog=null;
        }
    }//加载好后隐藏进度条

    @Override
    public void onStart() {
        super.onStart();
        showProgressDialog();
    }

    private void showProgressDialog(){
        if (mDialog==null){
            mDialog = new ProgressDialog(mContext);
            mDialog.setCancelable(true);
            mDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialogInterface) {
                    ProgressDialogSubscriber.this.unsubscribe();
                }
            });
        }if (mDialog!=null&&mDialog.isShowing()){
            mDialog.show();
        }
    }//显示进度条
}
