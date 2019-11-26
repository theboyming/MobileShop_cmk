package com.example.jxxy.cmk.activity;

import android.content.Intent;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;


import com.example.jxxy.cmk.R;
import com.example.jxxy.cmk.adapter.GoodsListAdapter;
import com.example.jxxy.cmk.common.BaseActivity;
import com.example.jxxy.cmk.http.entity.GoodsEntity;
import com.example.jxxy.cmk.http.presenter.GoodsPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Subscriber;

//商品列表活动页
public class GoodsListActivity extends BaseActivity {
    private int cat_id;

    @BindView(R.id.goodslist_swipe_refresh)
    SwipeRefreshLayout goodslistSwipeRefresh;

    @BindView(R.id.goodslist_recyclerview)
    RecyclerView goodslistRecyclerview;

    @BindView(R.id.goodslist_nodata)
    TextView goodslistNodata;

    private List<GoodsEntity> listData;
    private GoodsListAdapter adapter;

    @Override
    public int getContentViewId() {
        return R.layout.activity_goodslist;
    }

    @OnClick(R.id.iv_back)
    void close(){finish();}

    @Override
    protected void initView() {
        super.initView();
        goodslistNodata.setVisibility(View.GONE);

        cat_id = getIntent().getIntExtra("cat_id",0);

        goodslistSwipeRefresh.setColorSchemeResources(
                android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light
        );//通过颜色资源文件设置进度动画的颜色资源

        goodslistSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData();
            }
        });             //setOnRefreshListener添加刷新监听操作，如果用户进行下拉刷新则执行onload()方法

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(OrientationHelper.VERTICAL);   //利用布局管理器将布局设置为垂直布局

        goodslistRecyclerview.setLayoutManager(layoutManager);
        listData = new ArrayList<GoodsEntity>();        //存储商品数组
        adapter = new GoodsListAdapter(this,listData);

        adapter.setOnItemClickListener(new GoodsListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, GoodsEntity entity) {
                Intent intent = new Intent(GoodsListActivity.this,GoodsDetailActivity.class);
                intent.putExtra("goods_id",entity.getGoods_id());
                intent.putExtra("goods_name",entity.getName());
                startActivity(intent);
            }
        }); //将goodsid和goodsname通过intent传到调到活动
        goodslistRecyclerview.setAdapter(adapter);//给Recyclerview设置适配器
    }

    @Override
    protected void initData() {
        super.initData();
        loadData();
    }
    //数据加载
    private void loadData(){
        GoodsPresenter.list(new Subscriber<List<GoodsEntity>>() {
            @Override
            public void onCompleted() {
                goodslistSwipeRefresh.setRefreshing(false);
            }

            @Override
            public void onError(Throwable e) {
                goodslistSwipeRefresh.setRefreshing(false);
            }

            @Override
            public void onNext(List<GoodsEntity> goodsEntities) {   //其中onNext()方法中给TextView设置文本时，传入的是int类型的数据，如果系统找不到对应的String资源而就抛出异常，从而回调到onError()方法。
                listData.clear();
                listData.addAll(goodsEntities);
                adapter.notifyDataSetChanged();

                if (listData==null||listData.size()==0){
                    toastShort("没有该列表的商品数据！");
                    goodslistNodata.setVisibility(View.VISIBLE);
                    goodslistRecyclerview.setVisibility(View.GONE);
                }else{
                    goodslistNodata.setVisibility(View.GONE);
                    goodslistRecyclerview.setVisibility(View.VISIBLE);
                }//如果有数据者显示列表，无这显示nodata页
            }
        },cat_id);
    }
}