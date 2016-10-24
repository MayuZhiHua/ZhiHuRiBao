
package yzh.com.zhihuribao.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.net.URL;

import yzh.com.zhihuribao.R;
import yzh.com.zhihuribao.Task.DetailsTask;
import yzh.com.zhihuribao.constant.MyConstant;
import yzh.com.zhihuribao.db.CollectManager;
import yzh.com.zhihuribao.info.CollectData;
import yzh.com.zhihuribao.info.Data;
import yzh.com.zhihuribao.info.DetailsData;
import yzh.com.zhihuribao.listener.IDetailsListener;
import yzh.com.zhihuribao.tool.UrlToString;

public class DetailsActivity extends AppCompatActivity implements IDetailsListener{

    private TextView detailsTextView;
    private ImageView detailsImageView;
    private Toolbar toolbar;
    private WebView bodyWebView;

    //出发地的类型，分为MainActivity = 0,和MenuItemActivity = 1
    private int type;
    private CollectData collectData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        initToolbar();
        initData();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        bodyWebView.stopLoading(); //停止加载
        ((ViewGroup)bodyWebView.getParent()).removeView(bodyWebView); //把webview从视图中移除
        bodyWebView.removeAllViews(); //移除webview上子view
        bodyWebView.clearCache(true); //清除缓存
        bodyWebView.clearHistory(); //清除历史
        bodyWebView.destroy(); //销毁webview自身

    }
    private void initView(DetailsData data) {
        detailsTextView= (TextView) findViewById(R.id.tv_details);
        detailsImageView= (ImageView) findViewById(R.id.iv_details);
        bodyWebView = (WebView) findViewById(R.id.webview_body);
        WebSettings webSettings =bodyWebView.getSettings();
        //设置WebView属性，能够执行Javascript脚本
        webSettings.setJavaScriptEnabled(true);
        //设置可以访问文件
        webSettings.setAllowFileAccess(true);
        String title=data.getTitle();
        String imageUrl=data.getImageUrl();
        //Log.e("Tag",imageUrl);
        String body=data.getBody();
        //String css=data.getCss();
        if(! (imageUrl.equals(MyConstant.IMAGES_DEFAULT))) {
            Picasso.with(this)
                    .load(imageUrl)
                    .into(detailsImageView);

        }
        detailsTextView.setText(title);
        //WebView 加载Html字符串
        bodyWebView.loadDataWithBaseURL(null,body,"text/html","UTF-8",null);


        //WebView 加载Css字符串
        //new CssTask(css).execute();
    }

    private void initData() {
        Intent intent = getIntent();
        Bundle bundle=intent.getBundleExtra(MyConstant.KEY_INTENT);
        collectData = (CollectData) bundle.getSerializable("CollectData");

        int id = collectData.getId();
        type = intent.getIntExtra(MyConstant.MAIN_TO_DETAILS,100);
        String url=MyConstant.DETAILS_URL+String.valueOf(id);
        new DetailsTask(url,new DetailsData(),this,type).execute();
    }

    private void initToolbar() {
        toolbar= (Toolbar) findViewById(R.id.toolbar_details);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        ActionBar actionBar=getSupportActionBar();
        //给左上角图标的左边加上一个返回的图标
        actionBar.setDisplayHomeAsUpEnabled(true);
        // 自定义返回按钮（默认是返回箭头）
        actionBar.setHomeAsUpIndicator(R.mipmap.back);
    }
    //Toolbar上的返回按钮销毁当前画面
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            //点击按钮结束当前画面
            case android.R.id.home:
                finish();
                break;

            case R.id.collect://收藏按钮的监听
                CollectManager collectManager=new CollectManager(this);
                try {
                    //Log.e("Tag","id  ---" + collectData.getId());
                    //Log.e("Tag","Title---" + collectData.getTitle());
                    //Log.e("Tag","ImgUrl---" + collectData.getImgUrl());
                    collectManager.insert(collectData);
                    Toast.makeText(DetailsActivity.this, "收藏成功", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(DetailsActivity.this, "收藏失败", Toast.LENGTH_SHORT).show();
                }
                break;

        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //加载menu资源
        getMenuInflater().inflate(R.menu.menu_details, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //下载完数据源初始化View
    @Override
    public void success(DetailsData data) {
        //Log.e("Tag","title"+data.getTitle());
        initView(data);
    }




}
