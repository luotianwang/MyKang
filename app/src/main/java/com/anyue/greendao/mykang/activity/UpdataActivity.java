package com.anyue.greendao.mykang.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
 * Created by apple on 2017/11/14.
 */
public class UpdataActivity extends BaseActivity {

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
    Intent intent;
    UserDao mUserDao;
    Long id;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adduser);
        ButterKnife.bind(this);
        intent = getIntent();
        User user = (User) intent.getSerializableExtra("data");
        this.setTitle("修改");
        this.setImgHide(true);
        mUserDao = MyApplication.getInstances().getDaoSession().getUserDao();
        edtUsername.setText(user.getName());
        edtUserphone.setText(user.getPhone());
        edtUserwork.setText(user.getWork());
        if (user.getYaoyue().equals("0")) {
            rbYaoyue.setChecked(true);
        } else {
            rbWeiyaoyue.setChecked(true);
        }
        txtDatatime.setText(user.getData());
        edtRemark.setText(user.getRemark());
        id = user.getId();
    }

    @OnClick(R.id.btn_preserve)
    public void onViewClicked() {
        User user = new User();
        user.setId(id);
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
            mUserDao.update(user);
            Toast.makeText(getApplicationContext(), "修改成功", Toast.LENGTH_LONG).show();
            finish();
        }

    }

    @OnClick({R.id.edt_username, R.id.edt_userphone, R.id.edt_userwork, R.id.edt_remark, R.id.txt_datatime})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.edt_username:
                edtUsername.setText("");
                break;
            case R.id.edt_userphone:
                edtUserphone.setText("");
                break;
            case R.id.edt_userwork:
                edtUserwork.setText("");
                break;
            case R.id.edt_remark:
                edtRemark.setText("");
                break;
            case R.id.txt_datatime:
                setDataTime(txtDatatime);
                break;
        }
    }
}
