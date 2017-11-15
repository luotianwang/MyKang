package com.anyue.greendao.mykang.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.anyue.greendao.mykang.R;
import com.bigkoo.pickerview.TimePickerView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by apple on 2017/11/13.
 */

public abstract class BaseActivity extends Activity {
    TextView top_title;
    ImageView img_back;
    View top_view;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * 修改标题名
     *
     * @param title
     */
    public void setTitle(String title) {
        top_title = findview(R.id.txt_top_title);
        if (!TextUtils.isEmpty(title)) {
            top_title.setText(title);
            setImg_back();
        }
    }

    public void setImg_back() {
        img_back = findview(R.id.img_back);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void setImgHide(boolean sf) {
        top_view = findview(R.id.top_view);
        if (sf) {
            img_back.setVisibility(View.VISIBLE);
            top_view.setVisibility(View.VISIBLE);
        } else {
            img_back.setVisibility(View.GONE);
            top_view.setVisibility(View.GONE);
        }

    }

    public <T extends View> T findview(int id) {

        return (T) findViewById(id);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        top_title = null;
        img_back = null;
        top_view = null;
    }

    public void setDataTime(final TextView view) {
        InputMethodManager imm = (InputMethodManager) view.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

        //时间选择器
        TimePickerView pvTime = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                //选中事件回调
                view.setText(getTime(date));
            }
        }).build();
        pvTime.setDate(Calendar.getInstance());//注：根据需求来决定是否使用该方法（一般是精确到秒的情况），此项可以在弹出选择器的时候重新设置当前时间，避免在初始化之后由于时间已经设定，导致选中时间与当前时间不匹配的问题。
        pvTime.show();
    }

    private String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(date);
    }

}
