package com.anyue.greendao.mykang;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.anyue.greendao.mykang.activity.AddUsers;
import com.anyue.greendao.mykang.activity.DeluserActivity;
import com.anyue.greendao.mykang.activity.QueryUsers;
import com.anyue.greendao.mykang.activity.YaoyueAtivity;
import com.anyue.greendao.mykang.base.BaseActivity;
import com.bumptech.glide.Glide;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MainActivity extends BaseActivity {
    Intent intent;
    @BindView(R.id.layout_addusers)
    LinearLayout layoutAddusers;
    @BindView(R.id.layout_delusers)
    LinearLayout layoutDelusers;
    @BindView(R.id.layout_queryusers)
    LinearLayout layoutQueryusers;
    @BindView(R.id.banner)
    Banner banner;
    List<Integer> images;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        this.setTitle("首页");
        images = new ArrayList<>();
        images.add(R.drawable.kbl);
        images.add(R.drawable.c1);
        images.add(R.drawable.c2);
        images.add(R.drawable.c3);
        //设置banner样式
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        banner.setImages(images);
        //设置banner动画效果
        banner.setBannerAnimation(Transformer.DepthPage);
        //设置自动轮播，默认为true
        banner.isAutoPlay(true);
        //设置轮播时间
        banner.setDelayTime(1500);
        //设置指示器位置（当banner模式中有指示器时）
        banner.setIndicatorGravity(BannerConfig.CENTER);
        //banner设置方法全部调用完毕时最后调用
        banner.start();

    }

    public class GlideImageLoader extends ImageLoader {

        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            Glide.with(context).load(path).into(imageView);
        }
    }

    @OnClick({R.id.layout_addusers, R.id.layout_delusers, R.id.layout_queryusers, R.id.layout_yaoyueausers})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.layout_addusers:
                intent = new Intent(MainActivity.this, AddUsers.class);
                break;
            case R.id.layout_delusers:
                intent = new Intent(MainActivity.this, DeluserActivity.class);
                break;
            case R.id.layout_queryusers:
                intent = new Intent(MainActivity.this, QueryUsers.class);
                break;
            case R.id.layout_yaoyueausers:
                intent = new Intent(MainActivity.this, YaoyueAtivity.class);
                break;
        }
        startActivity(intent);
    }
}
