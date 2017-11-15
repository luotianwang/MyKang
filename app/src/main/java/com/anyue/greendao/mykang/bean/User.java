package com.anyue.greendao.mykang.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

import java.io.Serializable;

/**
 * Created by apple on 2017/11/13.
 */

@Entity
public class User implements Serializable {
    @Id
    private Long id;
    private String name;
    private String phone;
    private String work;
    private String yaoyue;
    private String data;
    private String remark;
    public String getRemark() {
        return this.remark;
    }
    public void setRemark(String remark) {
        this.remark = remark;
    }
    public String getData() {
        return this.data;
    }
    public void setData(String data) {
        this.data = data;
    }
    public String getWork() {
        return this.work;
    }
    public void setWork(String work) {
        this.work = work;
    }
    public String getPhone() {
        return this.phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public void setYaoyue(String yaoyue) {
        this.yaoyue = yaoyue;
    }
    public String getYaoyue() {
        return this.yaoyue;
    }
    @Generated(hash = 2097560558)
    public User(Long id, String name, String phone, String work, String yaoyue,
            String data, String remark) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.work = work;
        this.yaoyue = yaoyue;
        this.data = data;
        this.remark = remark;
    }
    @Generated(hash = 586692638)
    public User() {
    }
}
