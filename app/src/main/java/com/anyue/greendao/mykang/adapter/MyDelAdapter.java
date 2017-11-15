package com.anyue.greendao.mykang.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.anyue.greendao.mykang.R;
import com.anyue.greendao.mykang.bean.User;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by apple on 2017/11/14.
 */

public class MyDelAdapter extends BaseAdapter {

    List<User> list;
    Context context;
    Map<Integer, Boolean> checkStates;

    public MyDelAdapter(List<User> list, Context context, Map<Integer, Boolean> checkStates) {
        this.list = list;
        this.context = context;
        this.checkStates = checkStates;
    }

    @Override
    public int getCount() {
        return list.size() > 0 ? list.size() : 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int postion, View view, ViewGroup viewGroup) {


        ViewHolder viewHolder = null;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_del, null);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.txtId.setText(list.get(postion).getId().toString());
        viewHolder.txtName.setText(list.get(postion).getName());
        viewHolder.txtPhone.setText(list.get(postion).getPhone());
        viewHolder.txtWork.setText(list.get(postion).getWork());
        viewHolder.txtRemark.setText(list.get(postion).getRemark());
        viewHolder.txtDatatime.setText(list.get(postion).getData());

        viewHolder.cheCkbox.setChecked(checkStates.get(postion));


        if (list.get(postion).getYaoyue().equals("0")) {
            viewHolder.txtYaoyue.setText("已邀约");
        } else {
            viewHolder.txtYaoyue.setText("未邀约");
        }

        viewHolder.cheCkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.v("check", postion + ":" + isChecked);
                checkStates.put(postion, isChecked);
            }
        });

        return view;
    }


    static class ViewHolder {
        @BindView(R.id.txt_id)
        TextView txtId;
        @BindView(R.id.txt_name)
        TextView txtName;
        @BindView(R.id.txt_work)
        TextView txtWork;
        @BindView(R.id.txt_yaoyue)
        TextView txtYaoyue;
        @BindView(R.id.txt_datatime)
        TextView txtDatatime;
        @BindView(R.id.txt_remark)
        TextView txtRemark;
        @BindView(R.id.txt_phone)
        TextView txtPhone;
        @BindView(R.id.checkbox)
        CheckBox cheCkbox;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
