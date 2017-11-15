package com.anyue.greendao.mykang.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.anyue.greendao.mykang.R;
import com.anyue.greendao.mykang.bean.User;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by apple on 2017/11/14.
 */

public class MyAdapter extends BaseAdapter {
    List<User> list;
    Context context;

    public MyAdapter(List<User> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size() > 0 ? list.size() : 0;
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public int getViewTypeCount() {
        // menu type count
        return 3;
    }

    @Override
    public int getItemViewType(int position) {
        // current menu type
        return position % 3;
    }

    @Override
    public View getView(int postion, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_listquery, null);
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

        if (list.get(postion).getYaoyue().equals("0")) {
            viewHolder.txtYaoyue.setText("已邀约");
        } else {
            viewHolder.txtYaoyue.setText("未邀约");
        }
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

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
