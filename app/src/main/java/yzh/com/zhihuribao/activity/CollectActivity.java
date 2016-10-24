package yzh.com.zhihuribao.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.BoolRes;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import yzh.com.zhihuribao.R;
import yzh.com.zhihuribao.adapter.RecyclerAdapter;
import yzh.com.zhihuribao.db.CollectManager;
import yzh.com.zhihuribao.info.CollectData;
import yzh.com.zhihuribao.info.Data;
import yzh.com.zhihuribao.listener.IOnItemClickListener;

public class CollectActivity extends AppCompatActivity implements IOnItemClickListener {
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private List<Data> datas;
    private RecyclerAdapter recyclerAdapter;
    private int intentID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collect);
        initToolbar();
        initData();
        initView();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            //点击按钮返回上一界面
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initToolbar() {
        toolbar= (Toolbar) findViewById(R.id.toolbar_collect);
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

    private void initView() {
        recyclerView= (RecyclerView) findViewById(R.id.recyclerView_collect);
        recyclerAdapter = new RecyclerAdapter(this,datas,this);
        recyclerView.setAdapter(recyclerAdapter);
        initLayoutManager();
    }

    private void initLayoutManager() {
        //线性布局管理
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(OrientationHelper.VERTICAL); //设置布局的方向
        recyclerView.setLayoutManager(layoutManager); //RecyclerView设置布局管理
    }

    private void initData() {
        CollectManager collectManager=new CollectManager(this);
        try {
            List<CollectData> collectDatas = collectManager.getAll();
            datas=new ArrayList<>();
            for(int i=0;i<collectDatas.size();i++){
                Data data=new Data();
                data.setId(collectDatas.get(i).getId());
                data.setTitle(collectDatas.get(i).getTitle());
                data.setImageUrl(collectDatas.get(i).getImgUrl());
                datas.add(data);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onItemClick(Data data) {
        Intent intent = new Intent(CollectActivity.this,CollectDetailsActivity.class);
        intent.putExtra("ID",data.getId());
        //带有返回值的跳转
        startActivityForResult(intent,100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==100&&resultCode==200){
            boolean Flag=data.getBooleanExtra("Flag",false);
            //Log.e("Tag",String.valueOf(Flag));
            if (Flag){
                //更新适配器，无效
                initData();
                initView();
                //recyclerAdapter.notifyDataSetChanged();
                //recyclerView.notifyAll();
            }
        }

    }
}
