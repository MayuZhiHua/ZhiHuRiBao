package yzh.com.zhihuribao.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import yzh.com.zhihuribao.R;
import yzh.com.zhihuribao.Task.DownloadStringTask;
import yzh.com.zhihuribao.Task.MenuItemTask;
import yzh.com.zhihuribao.Task.RefreshTask;
import yzh.com.zhihuribao.adapter.RecyclerAdapter;
import yzh.com.zhihuribao.adapter.ViewPagerAdapter;
import yzh.com.zhihuribao.constant.MyConstant;
import yzh.com.zhihuribao.info.CollectData;
import yzh.com.zhihuribao.info.Data;
import yzh.com.zhihuribao.info.MeunItemData;
import yzh.com.zhihuribao.listener.IDownloadListener;
import yzh.com.zhihuribao.listener.IMeunItemListener;
import yzh.com.zhihuribao.listener.IOnItemClickListener;
import yzh.com.zhihuribao.listener.IOnVpClickListener;
import yzh.com.zhihuribao.listener.IRerfershListener;

public class MainActivity extends AppCompatActivity implements
        IDownloadListener ,
        IOnItemClickListener,
        IOnVpClickListener,
        IMeunItemListener ,
        IRerfershListener{

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private ViewPager mainViewPager;
    private List<Data> viewPagerDatas;
    private List<View> viewList;
    private ViewPagerAdapter viewPagerAdapter;
    private XRecyclerView recyclerView;
    private RecyclerAdapter recyclerAdapter;
    private List<Data> recyclerDatas;
    //侧滑菜单的数据源
    private List<MeunItemData> meunDatas;

    //侧滑菜单的布局
    private NavigationView navigationView;

    //Handler 控制自动轮播
    private Handler handler=new Handler();
    private Runnable ADrunnable;
    //我的收藏TextView
    private TextView collectTextView;

    private int i=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initDrawerLayout();
        initToolBar();
        initNavigationView();
        initData();
        initRunnable();

    }

    //初始侧滑菜单并对item设置监听
    private void initNavigationView() {
        navigationView= (NavigationView) findViewById(R.id.layout_navigationview);
        View headerView = navigationView.getHeaderView(0);
        collectTextView= (TextView) headerView.findViewById(R.id.tv_collect);
        collectTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //侧滑菜单上的我的收藏的点击监听
                Intent intent =new Intent(MainActivity.this,CollectActivity.class);
                startActivity(intent);
            }
        });
        new MenuItemTask(MyConstant.MENU_ITEM_URL,MainActivity.this).execute();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                MeunItemData meunItemData=new MeunItemData();
                Intent intent=new Intent(MainActivity.this,MenuItemActivity.class);
                Bundle bundle=new Bundle();
                switch (item.getItemId()){
                    case R.id.nav_home:
                        item.setChecked(true);
                        break;
                    case R.id.caijingribao:
                        meunItemData = meunDatas.get(6);
                        Log.e("Tag","meunItemData"+meunItemData.getTitle());
                        item.setChecked(false);
                        item.setCheckable(false);
                        break;
                    case R.id.richangxinlixue:
                        meunItemData = meunDatas.get(0);
                        item.setChecked(false);
                        item.setCheckable(false);
                        break;
                    case R.id.tuijianribao:
                        meunItemData = meunDatas.get(1);
                        item.setChecked(false);
                        item.setCheckable(false);
                        break;
                    case R.id.dianyingribao:
                        meunItemData = meunDatas.get(2);
                        item.setChecked(false);
                        item.setCheckable(false);
                        break;
                    case R.id.buxuwuliao:
                        meunItemData = meunDatas.get(3);
                        item.setChecked(false);
                        item.setCheckable(false);
                        break;
                    case R.id.shejiribao:
                        meunItemData = meunDatas.get(4);
                        item.setChecked(false);
                        item.setCheckable(false);
                        break;
                    case R.id.dagongsiribao:
                        meunItemData = meunDatas.get(5);
                        item.setChecked(false);
                        item.setCheckable(false);
                        break;
                    case R.id.hulianwanganquan:
                        meunItemData = meunDatas.get(7);
                        item.setChecked(false);
                        item.setCheckable(false);
                        break;
                    case R.id.kaishiyouxi:
                        meunItemData = meunDatas.get(8);
                        item.setChecked(false);
                        item.setCheckable(false);
                        break;
                    case R.id.yinyueribao:
                        meunItemData = meunDatas.get(9);
                        item.setChecked(false);
                        item.setCheckable(false);
                        break;
                    case R.id.dongmanribao:
                        meunItemData = meunDatas.get(10);
                        item.setChecked(false);
                        item.setCheckable(false);
                        break;
                    case R.id.tiyuribao:
                        meunItemData = meunDatas.get(11);
                        item.setChecked(false);
                        item.setCheckable(false);
                        break;
                }
                drawerLayout.closeDrawers();
                //Log.e("Tag",meunItemData.getTitle());
                if(!(meunItemData==null)){
                    bundle.putSerializable(MyConstant.KEY_INTENT,meunItemData);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
                return true;
            }
        });
    }


    //初始化ViewPager,RecyclerView的数据源
    private void initData() {
        //ViewPager数据源
        viewPagerDatas= new ArrayList<>();
        new DownloadStringTask(MyConstant.MAIN_URL, viewPagerDatas, MyConstant.PAGER_VIEW_TYPE,this).execute();
        //RecyclerAdapter数据源
        recyclerDatas=new ArrayList<>();
        new DownloadStringTask(MyConstant.MAIN_URL,recyclerDatas,MyConstant.RECYCLER_VIEW_TYPE,this).execute();

    }

    private void initDrawerLayout() {
        drawerLayout= (DrawerLayout) findViewById(R.id.layout_drawer);
    }

    //初始化toolbar
    private void initToolBar() {
        toolbar= (Toolbar) findViewById(R.id.toolbar_main);
        toolbar.setTitle("首页");
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        ActionBar actionBar=getSupportActionBar();
        //给左上角图标的左边加上一个返回的图标
        actionBar.setDisplayHomeAsUpEnabled(true);
        // 自定义返回按钮（默认是返回箭头）
        actionBar.setHomeAsUpIndicator(R.drawable.drawer_menu);
    }
    //创建顶部菜单
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //加载menu资源
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            //点击按钮侧滑菜单出现
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    //下载完数据源的接口
    @Override
    public void success(List<Data> datas,int type) {
        switch (type){
            case MyConstant.PAGER_VIEW_TYPE:
                viewPagerDatas.addAll(datas);
                viewList=new ArrayList<>();
                //获得布局加载器
                LayoutInflater inflater = getLayoutInflater();
                for(int i=0;i<datas.size();i++){
                    View view=inflater.inflate(R.layout.view_header,null,false);
                    ImageView iv= (ImageView) view.findViewById(R.id.iv_vp);
                    TextView tv =(TextView)view.findViewById(R.id.tv_vp);
                    Data data=datas.get(i);
                    //Log.e("Tag","title"+data.getTitle());
                    tv.setText(data.getTitle());
                    Picasso.with(this)
                            .load(data.getImageUrl())
                            .into(iv);
                    viewList.add(view);
                }
                //初始化ViewPager 并关联适配器
                mainViewPager= (ViewPager) findViewById(R.id.vp_main_head);;
                viewPagerAdapter=new ViewPagerAdapter(viewList,this);
                mainViewPager.setAdapter(viewPagerAdapter);
                //自动轮播方法
                handler.postDelayed(ADrunnable,2000);
                break;
            case MyConstant.RECYCLER_VIEW_TYPE:
                /*for( int i=0;i<datas.size();i++){
                    Log.e("Tag",datas.get(i).getTitle()+i);
                }*/
                recyclerView = (XRecyclerView) findViewById(R.id.recyclerView_main);
                //recyclerView.setHasFixedSize(true);
                recyclerAdapter = new RecyclerAdapter(this,datas,this);
                recyclerView.setAdapter(recyclerAdapter);
                //设置布局样式
                initLayoutManager();

                //Log.e("Tag","-----"+String.valueOf(isVisBottom(recyclerView)));
               // int i=0;
                //if(isVisBottom(recyclerView)){
                   // i++;
                    //Log.e("Tag","------"+i);
                    ////设置刷新
                initXRecyclerView(datas);
               // }
                break;

        }
    }
    //设置刷新
    private void initXRecyclerView(final List<Data> datas) {
        //设置支持上拉刷新
        recyclerView.setPullRefreshEnabled(false);
        recyclerView.setLoadingMoreEnabled(true);
        //设置刷新样式
        recyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallPulse);
        //设置刷新监听
        recyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {

            @Override
            //下拉刷新触发
            public void onRefresh() {}
            @Override
            //上拉刷新触发
            public void onLoadMore() {
                //Log.e("Tag","-----"+String.valueOf(isVisBottom(recyclerView)));
                if(isVisBottom(recyclerView)){
                    //Log.e("Tag","");
                    //获得当前年月日
                    Calendar c = Calendar.getInstance();
                    int year = c.get(Calendar.YEAR);
                    int month=c.get(Calendar.MONTH);
                    int day=c.get(Calendar.DAY_OF_MONTH);
                    i++;
                    //day对应的字符串
                    String dayStr;
                    if (day-i<10){
                        if (day-i<1){
                            int j=0;
                            dayStr=String.valueOf(30-j);
                            month=month-1;
                            j++;
                        }else{
                            dayStr=0+String.valueOf(day-i);
                        }
                    }else {
                        dayStr=String.valueOf(day-i);
                    }
                    //Log.e("Tag","day ---"+dayStr);
                    String monthStr;
                    if(month<10){
                        monthStr=0+String.valueOf(month);
                    }else {
                        monthStr=String.valueOf(month);
                    }
                    //Log.e("Tag","monthStr ---"+monthStr);

                    String dateUrl=String.valueOf(year)+monthStr+dayStr;
                    String urlStr=MyConstant.REFRESH_URL+dateUrl;
                    //Log.e("Tag","urlStr --- "+urlStr);
                    //Log.e("Tag","recyclerDatas"+recyclerDatas.size());
                    new RefreshTask(urlStr,datas,MainActivity.this).execute();
                }
                }



        });
    }

    private void initLayoutManager() {
        //线性布局管理
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(OrientationHelper.VERTICAL); //设置布局的方向
        recyclerView.setLayoutManager(layoutManager); //RecyclerView设置布局管理
    }

    //自动轮播
    private void initRunnable() {
        ADrunnable=new Runnable() {
            @Override
            public void run() {
                //获得ViewPager当前位置
                int currentItem = mainViewPager.getCurrentItem();
                currentItem++;
                mainViewPager.setCurrentItem(currentItem);
                //自己调用自己,递归
                handler.postDelayed(ADrunnable,2000);
            }
        };
    }

    //Recycler的点击监听
    @Override
    public void onItemClick(Data data) {
        //Log.e("Tag","data的 id"+data.getId());
        int id=data.getId();
        String title = data.getTitle();
        String imgUrl = data.getImageUrl();
        CollectData collectData=new CollectData();
        collectData.setId(id);
        collectData.setTitle(title);
        collectData.setImgUrl(imgUrl);
        Bundle bundle=new Bundle();
        bundle.putSerializable("CollectData",collectData);
        Intent intent =new Intent(MainActivity.this,DetailsActivity.class);
        intent.putExtra(MyConstant.KEY_INTENT,bundle);
        intent.putExtra(MyConstant.MAIN_TO_DETAILS,0);
        startActivity(intent);
    }
    //ViewPager的监听
    @Override
    public void success(int position) {
        Data data = viewPagerDatas.get(position);
        int id =data.getId();
        String title = data.getTitle();
        String imgUrl = data.getImageUrl();
        CollectData collectData=new CollectData();
        collectData.setId(id);
        collectData.setTitle(title);
        collectData.setImgUrl(imgUrl);
        Bundle bundle=new Bundle();
        bundle.putSerializable("CollectData",collectData);
        Intent intent =new Intent(MainActivity.this,DetailsActivity.class);
        intent.putExtra(MyConstant.KEY_INTENT,bundle);
        intent.putExtra(MyConstant.MAIN_TO_DETAILS,0);
        startActivity(intent);
    }

    //MenuItem下载数据成功接口
    @Override
    public void success(List<MeunItemData> meunItemDatas) {
        meunDatas=meunItemDatas;
        //Log.e("Tag",meunDatas.size()+"");
    }

    //刷新数据的接口
    @Override
    public void refersh() {
        recyclerAdapter.notifyDataSetChanged();
        //recyclerView.refreshComplete();
        recyclerView.loadMoreComplete();
    }

    //判断xRecycler是否滚动到底部
    public static boolean isVisBottom(XRecyclerView recyclerView){
        LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        //屏幕中最后一个可见子项的position
        int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();
        //当前屏幕所看到的子项个数
        int visibleItemCount = layoutManager.getChildCount();
        //当前RecyclerView的所有子项个数
        int totalItemCount = layoutManager.getItemCount();
        //RecyclerView的滑动状态
        int state = recyclerView.getScrollState();
        if(visibleItemCount > 0 && lastVisibleItemPosition == totalItemCount - 1 && state == recyclerView.SCROLL_STATE_IDLE){
            return true;
        }else {
            return false;
        }
    }
}