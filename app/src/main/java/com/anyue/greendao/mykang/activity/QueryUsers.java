package com.anyue.greendao.mykang.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
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

/**
 * Created by apple on 2017/11/14.
 */

public class QueryUsers extends BaseActivity {
    MyAdapter myAdapter;
    List<User> list;
    UserDao mUserDao;
    @BindView(R.id.txt_where)
    EditText txtWhere;
    @BindView(R.id.list_View)
    SwipeMenuListView listView;
    @BindView(R.id.layout_kongzhi)
    LinearLayout layoutKongzhi;
    @BindView(R.id.layout_shuju)
    TextView layoutShuju;
    @BindView(R.id.txt_suju)
    TextView txtSuju;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_queryuser);
        ButterKnife.bind(this);
        this.setTitle("用户列表");
        this.setImgHide(true);
        mUserDao = MyApplication.getInstances().getDaoSession().getUserDao();
        list = mUserDao.loadAll();
        myAdapter = new MyAdapter(list, this);
        listView.setAdapter(myAdapter);

        txtWhere.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0) {
                    list.clear();
                    list.addAll(mUserDao.loadAll());
                    myAdapter.notifyDataSetChanged();

                } else {
                    list.clear();
                    list.addAll(mUserDao.queryBuilder().where(UserDao.Properties.Name.like("%" + txtWhere.getText().toString() + "%")).build().list());
                    myAdapter.notifyDataSetChanged();

                }
                if (list.size() == 0) {
                    txtSuju.setVisibility(View.VISIBLE);
                } else {
                    txtSuju.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
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
//                setPop();
                Intent intent = new Intent(QueryUsers.this, UpdataActivity.class);
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
        list.addAll(mUserDao.loadAll());

        if (list.size() > 0) {
            layoutKongzhi.setVisibility(View.VISIBLE);
            layoutShuju.setVisibility(View.GONE);
        } else {
            layoutShuju.setVisibility(View.VISIBLE);
            layoutKongzhi.setVisibility(View.GONE);
        }
        myAdapter.notifyDataSetChanged();
    }

}
