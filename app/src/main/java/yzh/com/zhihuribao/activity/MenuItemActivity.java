package yzh.com.zhihuribao.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import yzh.com.zhihuribao.R;
import yzh.com.zhihuribao.Task.MenuItemRecyclerTask;
import yzh.com.zhihuribao.adapter.RecyclerAdapter;
import yzh.com.zhihuribao.constant.MyConstant;
import yzh.com.zhihuribao.info.CollectData;
import yzh.com.zhihuribao.info.Data;
import yzh.com.zhihuribao.info.MeunItemData;
import yzh.com.zhihuribao.listener.IMenuItemDataListener;
import yzh.com.zhihuribao.listener.IOnItemClickListener;

public class MenuItemActivity extends AppCompatActivity implements IMenuItemDataListener,IOnItemClickListener {

    private Toolbar toolbar;
    private ImageView menuitemImageView;
    private TextView menuitemTextView;
    private XRecyclerView xRecyclerView;
    //xRecyclerView的数据源
    private List<Data> recyclerDatas;
    private RecyclerAdapter adapter;
    //从上一个页面传过来的数据结构
    private MeunItemData meunItemData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_item);
        //获得数据源
        Bundle bundle=new Bundle();
        Intent intent=getIntent();
        bundle=intent.getExtras();
        meunItemData= (MeunItemData) bundle.getSerializable(MyConstant.KEY_INTENT);
        //Log.e("Tag","获得的数据源---"+meunItemData.getTitle());
        initToolbar();
        initView();
        initRecyclerViewData();
    }

    private void initRecyclerViewData() {
        String id=String.valueOf(meunItemData.getId());
        String url=MyConstant.THEME_URL+id;
        //开启异步任务下载数据源
        new MenuItemRecyclerTask(url,this).execute();

    }

    private void initView() {
        menuitemImageView= (ImageView) findViewById(R.id.iv_menuitem);
        menuitemTextView = (TextView) findViewById(R.id.tv_menuitem);
        Picasso.with(this)
                .load(meunItemData.getImageUrl())
                .into(menuitemImageView);
        menuitemTextView.setText(meunItemData.getTitle());
    }

    private void initToolbar() {
        toolbar= (Toolbar) findViewById(R.id.toolbar_menuitem);
        toolbar.setTitle(meunItemData.getName());
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        ActionBar actionBar=getSupportActionBar();
        //给左上角图标的左边加上一个返回的图标
        actionBar.setDisplayHomeAsUpEnabled(true);
        // 自定义返回按钮（默认是返回箭头）
        actionBar.setHomeAsUpIndicator(R.mipmap.back);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            //点击ToolBar按钮返回上一界面
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    //下载数据源的回掉方法
    @Override
    public void success(List<Data> datas) {
        //Log.e("Tag","datas ----"+datas.get(5).getTitle());
        recyclerDatas=new ArrayList<>();
        recyclerDatas.addAll(datas);
        //Log.e("Tag","datas ----"+recyclerDatas.get(5).getTitle());
        xRecyclerView= (XRecyclerView) findViewById(R.id.recyclerview_menuitem);
        //适配器
        adapter=new RecyclerAdapter(this,recyclerDatas,this);
        //Log.e("Tag","datas ----"+recyclerDatas.get(5).getTitle());
        xRecyclerView.setAdapter(adapter);
        //设置布局样式
        initLayoutManager();
        //设置不支持上下拉刷新
        xRecyclerView.setPullRefreshEnabled(false);
        xRecyclerView.setLoadingMoreEnabled(false);
    }

    private void initLayoutManager() {
        //线性布局管理
        LinearLayoutManager layoutManager = new LinearLayoutManager(this){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        layoutManager.setOrientation(OrientationHelper.VERTICAL); //设置布局的方向
        xRecyclerView.setLayoutManager(layoutManager); //RecyclerView设置布局管理
    }

    //Recycler Item的监听
    @Override
    public void onItemClick(Data data) {
        int id=data.getId();
        String imgUrl = data.getImageUrl();
        String title = data.getTitle();
        //Log.e("Tag",String.valueOf(id));
        Intent intent =new Intent(MenuItemActivity.this,DetailsActivity.class);
        CollectData collectData=new CollectData();
        collectData.setId(id);
        collectData.setTitle(title);
        collectData.setImgUrl(imgUrl);
        Bundle bundle=new Bundle();
        bundle.putSerializable("CollectData",collectData);
        intent.putExtra(MyConstant.KEY_INTENT,bundle);
        intent.putExtra(MyConstant.MAIN_TO_DETAILS,1);
        startActivity(intent);
    }
}
