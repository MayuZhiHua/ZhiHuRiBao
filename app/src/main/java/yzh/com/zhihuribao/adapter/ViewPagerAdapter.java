package yzh.com.zhihuribao.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import yzh.com.zhihuribao.listener.IOnVpClickListener;


/**
 * ViewPager 自动滚播
 * Created by HP on 2016/10/8.
 */
public class ViewPagerAdapter extends PagerAdapter{

    private List<View> viewList;
    private IOnVpClickListener iOnVpClickListener;
    public ViewPagerAdapter(List<View> viewList,IOnVpClickListener iOnVpClickListener) {
        this.viewList = viewList;
        this.iOnVpClickListener=iOnVpClickListener;
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View view = viewList.get( position % viewList.size()); //把虚拟位置转换成真实数据的下标
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iOnVpClickListener.success(position % viewList.size());
            }
        });
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View view = viewList.get(position % viewList.size());
        container.removeView(view);
    }
}
