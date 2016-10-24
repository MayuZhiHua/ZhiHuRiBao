package yzh.com.zhihuribao.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import yzh.com.zhihuribao.R;
import yzh.com.zhihuribao.constant.MyConstant;
import yzh.com.zhihuribao.db.CollectManager;
import yzh.com.zhihuribao.info.CollectData;
import yzh.com.zhihuribao.tool.UrlToString;

public class CollectDetailsActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private WebView bodyWebView;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collect_details);
        initToolbar();
        initView();
        initData();

    }

    private void initView() {
        bodyWebView= (WebView) findViewById(R.id.webview_collect_details);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //加载menu资源
        getMenuInflater().inflate(R.menu.menu_delete, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            //点击按钮返回上一界面
            case android.R.id.home:
                finish();
                break;
            case R.id.delete://删除按钮的监听
                CollectManager collectManager=new CollectManager(this);
                CollectData collectData=new CollectData();
                collectData.setId(id);
                Intent intent =new Intent() ;
                try {
                    collectManager.delete(collectData);
                    Toast.makeText(CollectDetailsActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
                    intent.putExtra("Flag",true);
                    setResult(200,intent);
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(CollectDetailsActivity.this, "删除失败", Toast.LENGTH_SHORT).show();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    //初始化传过来的Intent数据
    private void initData() {
        Intent intent = getIntent();
        id = intent.getIntExtra("ID", 0);
        //Log.e("Tag","ID"+id);
        new DownLoadBodyTask().execute();
    }

    private void initToolbar() {
        toolbar= (Toolbar) findViewById(R.id.toolbar_collect_details);
        toolbar.setTitle("我的收藏");
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setBackgroundColor(getResources().getColor(R.color.toolbar));
        setSupportActionBar(toolbar);
        ActionBar actionBar=getSupportActionBar();
        //给左上角图标的左边加上一个返回的图标
        actionBar.setDisplayHomeAsUpEnabled(true);
        // 自定义返回按钮（默认是返回箭头）
        actionBar.setHomeAsUpIndicator(R.mipmap.back);
    }
    //下载JSON解析并给WebView
    class DownLoadBodyTask extends AsyncTask<Void,Void,String>{

        @Override
        protected String doInBackground(Void... params) {
            String s = UrlToString.urlToString(MyConstant.DETAILS_URL + id);
            return s;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONObject jsonObject= new JSONObject(s);
                String body = jsonObject.getString("body");
                //Log.e("Tag","Body"+body);
                bodyWebView.loadDataWithBaseURL(null,body,"text/html","UTF-8",null);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

}
