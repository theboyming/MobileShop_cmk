package com.example.jxxy.cmk.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jxxy.cmk.R;
import com.example.jxxy.cmk.common.BaseActivity;
import com.example.jxxy.cmk.common.MobileShopApp;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;



public class AdActivity extends BaseActivity {
    private TextView tv_skip;

    @Override
    public int getContentViewId() {
        return R.layout.activity_ad;
    }

    @Override
    protected void initView() {
        super.initView();

        tv_skip=(TextView)findViewById(R.id.tv_skip);
        tv_skip.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                skip();
            }
        });
        ImageView imageView = (ImageView) findViewById(R.id.iv_image);
        String url ="http://a3.qpic.cn/psb?/V13EM8qg049N9u/o3kKBu5nCJG10Vy3MKyX2*obnS3JKhERFpAnZSvoEks!/b/dFIBAAAAAAAA&ek=1&kp=1&pt=0&bo=4AEgA.ABIAMRECc!&tl=3&vuin=2157262554&tm=1573916400&sce=60-2-2&rf=viewer_311";
        ImageLoader.getInstance().displayImage(url, imageView, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {

            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                MobileShopApp.handler.post(jumpTread);
            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                MobileShopApp.handler.post(jumpTread);
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {
                MobileShopApp.handler.post(jumpTread);
            }
        });
    }
    private void skip(){
        Intent intent = new Intent(AdActivity.this,MainActivity.class);
        startActivity(intent);
        finish();
        c_count = COUNT;
        MobileShopApp.handler.removeCallbacks(jumpTread);
    }


    private final static int COUNT=5;
    private final static int DELATED=1000;
    private int c_count =COUNT;
    private static final String SKIP_TEXT="点击跳过%d";
    private Runnable jumpTread = new Runnable() {
        @Override
        public void run() {
            if (c_count<=0){
                skip();
            }else {
                tv_skip.setText(String.format(SKIP_TEXT,c_count));

                c_count--;
                MobileShopApp.handler.postDelayed(jumpTread,DELATED);
            }
        }
    };

}
