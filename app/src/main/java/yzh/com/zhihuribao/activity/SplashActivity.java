package yzh.com.zhihuribao.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import yzh.com.zhihuribao.R;
import yzh.com.zhihuribao.Task.SplashDownTask;
import yzh.com.zhihuribao.constant.MyConstant;
import yzh.com.zhihuribao.info.SplashData;
import yzh.com.zhihuribao.listener.ISplashListener;
import yzh.com.zhihuribao.tool.UrlToString;

public class SplashActivity extends AppCompatActivity implements ISplashListener{

    private ImageView mianImageView;
    private TextView mainTextView;
    //闪屏停留时间
    public static final long SLEEP_TIME = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        initView();
        initData();
    }

    private void initView() {
        mianImageView= (ImageView) findViewById(R.id.iv_splash);
        mainTextView= (TextView) findViewById(R.id.tv_splash);
    }

    private void initData(){
        new SplashDownTask(MyConstant.SPLASH_URL,this).execute();
    }
    //下载数据完成后回掉接口
    @Override
    public void sucess(SplashData splashData,long time) {
        //Log.e("Tag","text---"+splashData.getText());
        //Log.e("Tag","img---"+splashData.getImg());

        mainTextView.setText(splashData.getText());
        Picasso.with(SplashActivity.this)
                .load(splashData.getImg())
                .into(mianImageView);
        //开启异步任务实现跳转
        new SplashTask(time).execute();
    }

    //闪屏画面跳转的异步任务
    class SplashTask extends AsyncTask<Void,Void,Void>{
        //数据加载时间
        private long loadTime;

        public SplashTask(long loadTime) {
            this.loadTime = loadTime;
        }

        @Override
        protected Void doInBackground(Void... params) {
            if (loadTime < SLEEP_TIME) {
                try {
                    Thread.sleep(SLEEP_TIME - loadTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Intent intent = new Intent();
            //进入主画面
            intent.setClass(SplashActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
