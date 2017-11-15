package com.anyue.greendao.mykang.activity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.anyue.greendao.mykang.MyApplication;
import com.anyue.greendao.mykang.R;
import com.anyue.greendao.mykang.adapter.MyDelAdapter;
import com.anyue.greendao.mykang.base.BaseActivity;
import com.anyue.greendao.mykang.bean.User;
import com.anyue.greendao.mykang.gen.UserDao;
import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by apple on 2017/11/14.
 */

public class DeluserActivity extends BaseActivity {
    final static Map<Integer, Boolean> checkStates = new HashMap<>();
    @BindView(R.id.list_view)
    SwipeMenuListView listView;
    @BindView(R.id.btn_selectcall)
    Button btnSelectcall;
    @BindView(R.id.btn_cancelcall)
    Button btnCancelcall;
    @BindView(R.id.btn_del)
    Button btnDel;
    List<User> list;
    UserDao mUserDao;
    MyDelAdapter adapter;
    @BindView(R.id.txt_issuju)
    TextView txtIssuju;
    @BindView(R.id.btn_reverse)
    Button btnReverse;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deluser);
        ButterKnife.bind(this);
        this.setTitle("删除");
        this.setImgHide(true);
        mUserDao = MyApplication.getInstances().getDaoSession().getUserDao();
        list = mUserDao.loadAll();
        if (list.size() > 0) {
            txtIssuju.setVisibility(View.GONE);
        } else {
            txtIssuju.setVisibility(View.VISIBLE);
        }
        //    初始化
        for (int i = 0; i < list.size(); i++) {
            checkStates.put(i, false);
        }
        adapter = new MyDelAdapter(list, this, checkStates);
        listView.setAdapter(adapter);

        // step 1. create a MenuCreator
        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
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
                mUserDao.delete(list.get(position));
                list.clear();
                list.addAll(mUserDao.loadAll());
                adapter.notifyDataSetChanged();
                return false;
            }
        });
    }

    @OnClick({R.id.btn_selectcall, R.id.btn_cancelcall, R.id.btn_del, R.id.btn_reverse})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_selectcall:
                selectAll();
                break;
            case R.id.btn_cancelcall:
                cancelAll();
                break;
            case R.id.btn_del:
                delete();
                break;
            case R.id.btn_reverse:
                reverse();
                break;
        }
    }


    //删除
    private void delete() {
        int count = list.size() - 1;
        for (int i = count; i >= 0; i--) {
            if (checkStates.get(i)) {

                mUserDao.delete(list.get(i));
                list.remove(i);
            }
        }
        list.clear();
        list.addAll(mUserDao.loadAll());
        if (list.size() > 0) {
            txtIssuju.setVisibility(View.GONE);
        } else {
            txtIssuju.setVisibility(View.VISIBLE);
        }
        adapter.notifyDataSetChanged();


    }

    //    取消所有选择
    private void cancelAll() {

        Log.v("check", listView.getChildCount() + "");
        for (int i = 0; i < checkStates.size(); i++) {
            checkStates.put(i, false);
        }
        adapter.notifyDataSetChanged();
    }

    //    反选
    private void reverse() {
        for (int i = 0; i < checkStates.size(); i++) {
            if (checkStates.get(i)) {

                checkStates.put(i, false);
            } else {
                checkStates.put(i, true);
            }
        }
        adapter.notifyDataSetChanged();
    }

    //    全选
    private void selectAll() {
        for (int i = 0; i < checkStates.size(); i++) {
            checkStates.put(i, true);
        }
        adapter.notifyDataSetChanged();
    }


    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }

}
