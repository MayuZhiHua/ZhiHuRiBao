package yzh.com.zhihuribao.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import yzh.com.zhihuribao.R;
import yzh.com.zhihuribao.constant.MyConstant;
import yzh.com.zhihuribao.info.Data;
import yzh.com.zhihuribao.listener.IDownloadListener;
import yzh.com.zhihuribao.listener.IOnItemClickListener;

/**
 * Recycler 的适配器
 * Created by HP on 2016/10/8.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHold> {

    private Context context;
    private List<Data> datas;
    private IOnItemClickListener iOnItemClickListener;

    public RecyclerAdapter(Context context, List<Data> datas,IOnItemClickListener iOnItemClickListener) {
        this.context = context;
        this.datas = datas;
        this.iOnItemClickListener=iOnItemClickListener;
    }


    @Override
    public MyViewHold onCreateViewHolder(ViewGroup parent, int viewType) {
        //由布局加载View
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_recyclerview_main, parent, false);
        return new MyViewHold(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHold holder, final int position) {
        final Data data = datas.get(position);
        //Log.e("Tag",data.getTitle()+position);
        //绑定到指定的view上
        //如果item上没有图片
        if(!(data.getImageUrl().equals(MyConstant.IMAGES_DEFAULT))){
            Picasso.with(context)
                    .load(data.getImageUrl())
                    .into(holder.iv);
        }else{
            //如果没有图片，让ImageView消失
            holder.iv.setVisibility(View.GONE);
        }
        holder.tv.setText(data.getTitle());
        //item点击监听
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iOnItemClickListener.onItemClick(data);
            }
        });

    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    //自定义的ViewHolder
    class MyViewHold extends RecyclerView.ViewHolder{
        ImageView iv;
        TextView tv;
        public MyViewHold(View itemView) {
            super(itemView);
            iv = (ImageView) itemView.findViewById(R.id.iv_news_picture);
            tv = (TextView) itemView.findViewById(R.id.tv_news_title);
        }
    }
}
