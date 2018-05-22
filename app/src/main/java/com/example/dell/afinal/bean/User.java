package com.example.dell.afinal.bean;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.datatype.BmobRelation;

// 用户类：继承BmobUser使用Bmob SDK的API实现用户的注册和登录
public class User extends BmobUser {

    /* BmobUser中已经实现了username、password 字段, 不需要重复声明 */

    private String identity;    // 身份：学生或教师

    private BmobFile headFile;  // 头像

    private String sign;        // 个性签名

    private String nickName;    // 昵称

    private Integer sex;        // 性别

    private BmobRelation courses; // 存储该用户添加的所有课程

    private BmobRelation likes;   // 存储用户收藏的所有帖子

    private BmobRelation notifications;  // 存储用户已读的所有系统通知

    public BmobRelation getCourses() {
        return courses;
    }

    public void setCourses(BmobRelation courses) {
        this.courses = courses;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public BmobFile getHeadFile() {
        return headFile;
    }

    public void setHeadFile(BmobFile headFile) {
        this.headFile = headFile;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public BmobRelation getLikes() {
        return likes;
    }

    public void setLikes(BmobRelation likes) {
        this.likes = likes;
    }

    public BmobRelation getNotifications() {
        return notifications;
    }

    public void setNotifications(BmobRelation notifications) {
        this.notifications = notifications;
    }
}
