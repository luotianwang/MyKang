package com.anyue.greendao.mykang.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.anyue.greendao.mykang.MyApplication;
import com.anyue.greendao.mykang.R;
import com.anyue.greendao.mykang.adapter.MyAdapter;
import com.anyue.greendao.mykang.base.BaseActivity;
import com.anyue.greendao.mykang.bean.User;
import com.anyue.greendao.mykang.gen.UserDao;
import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by apple on 2017/11/14.
 */

public class YaoyueAtivity extends BaseActivity {
    @BindView(R.id.list_View)
    SwipeMenuListView listView;
    MyAdapter myAdapter;
    List<User> list;
    UserDao mUserDao;
    @BindView(R.id.btn_yaoyue)
    Button btnYaoyue;
    @BindView(R.id.btn_weiyaoyue)
    Button btnWeiyaoyue;
    @BindView(R.id.layout_xiaosi)
    LinearLayout layoutXiaosi;
    @BindView(R.id.layout_xiaosi2)
    LinearLayout layoutXiaosi2;
    @BindView(R.id.layout_shuju)
    TextView layoutShuju;
    @BindView(R.id.layout_kongzhi)
    LinearLayout layoutKongzhi;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_queryuser);
        ButterKnife.bind(this);
        layoutXiaosi.setVisibility(View.GONE);
        layoutXiaosi2.setVisibility(View.VISIBLE);
        this.setTitle("邀约客户");
        this.setImgHide(true);

        mUserDao = MyApplication.getInstances().getDaoSession().getUserDao();
        list = mUserDao.queryBuilder().where(UserDao.Properties.Yaoyue.eq("0")).list();

        if (list.size() == 0) {
            list = mUserDao.queryBuilder().where(UserDao.Properties.Yaoyue.eq("1")).list();
            btnWeiyaoyue.setTextColor(getResources().getColor(android.R.color.white));
            btnWeiyaoyue.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            btnYaoyue.setTextColor(getResources().getColor(android.R.color.black));
            btnYaoyue.setBackgroundColor(getResources().getColor(android.R.color.white));
        }


        myAdapter = new MyAdapter(list, this);
        listView.setAdapter(myAdapter);
        SwipeMenuCreator creator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                // create "open" item
                SwipeMenuItem openItem = new SwipeMenuItem(
                        getApplicationContext());
                //是否在删除左边添加其他按钮
                // set item background
                openItem.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9,
                        0xCE)));
                //set item width
                openItem.setWidth(dp2px(90));
                // set item title
                openItem.setTitle("Open");
                // set item title fontsize
                openItem.setTitleSize(18);
                // set item title font color
                openItem.setTitleColor(Color.WHITE);
                // add to menu
                menu.addMenuItem(openItem);
                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
                        0x3F, 0x25)));
                // set item width
                deleteItem.setWidth(dp2px(90));
                // set a icon
                deleteItem.setIcon(R.drawable.ic_delete);
                // add to menu
                menu.addMenuItem(deleteItem);
            }
        };
        // set creator
        listView.setMenuCreator(creator);

        listView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        Intent dialIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + list.get(position).getPhone()));//跳转到拨号界面，同时传递电话号码
                        startActivity(dialIntent);
                        break;
                    case 1:
                        mUserDao.delete(list.get(position));
                        list.clear();
                        list.addAll(mUserDao.loadAll());
                        myAdapter.notifyDataSetChanged();
                        break;
                }

                return false;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(YaoyueAtivity.this, UpdataActivity.class);
                intent.putExtra("data", list.get(i));
                startActivity(intent);
            }
        });
    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }

    @Override
    protected void onStart() {
        super.onStart();
        list.clear();
        list.addAll(mUserDao.queryBuilder().where(UserDao.Properties.Yaoyue.eq("0")).list());
        if (list.size() > 0) {
            layoutKongzhi.setVisibility(View.VISIBLE);
            layoutShuju.setVisibility(View.GONE);
        } else {
            if (list.size() == 0) {
                list.addAll(mUserDao.queryBuilder().where(UserDao.Properties.Yaoyue.eq("1")).list());
                btnWeiyaoyue.setTextColor(getResources().getColor(android.R.color.white));
                btnWeiyaoyue.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                btnYaoyue.setTextColor(getResources().getColor(android.R.color.black));
                btnYaoyue.setBackgroundColor(getResources().getColor(android.R.color.white));
            }
            if (list.size() == 0) {
                layoutShuju.setVisibility(View.VISIBLE);
                layoutKongzhi.setVisibility(View.GONE);
            }
        }
        myAdapter.notifyDataSetChanged();
    }

    @OnClick({R.id.btn_yaoyue, R.id.btn_weiyaoyue})
    public void onViewClicked(View view) {
        list.clear();
        switch (view.getId()) {
            case R.id.btn_yaoyue:
                list.addAll(mUserDao.queryBuilder().where(UserDao.Properties.Yaoyue.eq("0")).list());
                myAdapter.notifyDataSetChanged();
                btnYaoyue.setTextColor(getResources().getColor(android.R.color.white));
                btnYaoyue.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                btnWeiyaoyue.setTextColor(getResources().getColor(android.R.color.black));
                btnWeiyaoyue.setBackgroundColor(getResources().getColor(android.R.color.white));
                break;
            case R.id.btn_weiyaoyue:
                btnWeiyaoyue.setTextColor(getResources().getColor(android.R.color.white));
                btnWeiyaoyue.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                btnYaoyue.setTextColor(getResources().getColor(android.R.color.black));
                btnYaoyue.setBackgroundColor(getResources().getColor(android.R.color.white));
                list.addAll(mUserDao.queryBuilder().where(UserDao.Properties.Yaoyue.eq("1")).list());
                myAdapter.notifyDataSetChanged();
                break;
        }
    }
}
