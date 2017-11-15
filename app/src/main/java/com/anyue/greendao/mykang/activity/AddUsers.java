package com.anyue.greendao.mykang.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.anyue.greendao.mykang.MyApplication;
import com.anyue.greendao.mykang.R;
import com.anyue.greendao.mykang.base.BaseActivity;
import com.anyue.greendao.mykang.bean.User;
import com.anyue.greendao.mykang.gen.UserDao;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by apple on 2017/11/13.
 */

public class AddUsers extends BaseActivity {

    @BindView(R.id.edt_username)
    EditText edtUsername;
    @BindView(R.id.edt_userphone)
    EditText edtUserphone;
    @BindView(R.id.edt_userwork)
    EditText edtUserwork;
    @BindView(R.id.rb_yaoyue)
    RadioButton rbYaoyue;
    @BindView(R.id.rb_weiyaoyue)
    RadioButton rbWeiyaoyue;
    @BindView(R.id.txt_datatime)
    TextView txtDatatime;
    @BindView(R.id.edt_remark)
    EditText edtRemark;
    @BindView(R.id.btn_preserve)
    Button btnPreserve;
    UserDao mUserDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adduser);
        ButterKnife.bind(this);
        this.setTitle("添加用户");
        this.setImgHide(true);
        mUserDao = MyApplication.getInstances().getDaoSession().getUserDao();
        txtDatatime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDataTime(txtDatatime);
            }
        });


    }


    @OnClick(R.id.btn_preserve)
    public void onViewClicked() {
        User user = new User();
        user.setId(null);
        String userName = edtUsername.getText().toString();
        String userPhone = edtUserphone.getText().toString();
        String userWork = edtUserwork.getText().toString();
        String userRemark = edtRemark.getText().toString();
        String userDatatime = txtDatatime.getText().toString();
        if (TextUtils.isEmpty(userName)) {
            Toast.makeText(getApplicationContext(), "请输入姓名", Toast.LENGTH_LONG).show();
            return;
        } else {
            user.setName(userName);
        }

        if (TextUtils.isEmpty(userPhone)) {
            Toast.makeText(getApplicationContext(), "请输入手机号", Toast.LENGTH_LONG).show();
            return;
        } else {
            user.setPhone(userPhone);
        }
        if (rbYaoyue.isChecked()) {
            user.setYaoyue("0");
        } else if (rbWeiyaoyue.isChecked()) {
            user.setYaoyue("1");
        } else {
            Toast.makeText(getApplicationContext(), "请选择是否邀约", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(userWork)) {
            user.setWork("未填写");
        } else {
            user.setWork(userWork);
        }
        if (TextUtils.isEmpty(userRemark)) {
            user.setRemark("未填写");
        } else {
            user.setRemark(userRemark);
        }
        if (TextUtils.isEmpty(userDatatime)) {
            user.setData("未选择回访时间");
        } else {
            user.setData(userDatatime);
        }
        if (TextUtils.isEmpty(user.getName()) && TextUtils.isEmpty(user.getPhone()) && TextUtils.isEmpty(user.getYaoyue())) {
            Toast.makeText(getApplicationContext(), "请填写完资料", Toast.LENGTH_LONG).show();
            return;
        } else {
            mUserDao.insert(user);
            Toast.makeText(getApplicationContext(), "保存成功", Toast.LENGTH_LONG).show();
            finish();
        }

    }
}
