package com.midea.wcp.user.mybatis.model;

import lombok.Data;

import java.util.Date;

@Data
public class MpUser {
    private int id;

    private String appId;

    private String openId;

    private String unionId;

    private String nickname;

    private String sex;

    private int sexId;

    private String country;

    private String province;

    private String city;

    private String language;

    private String headImgUrl;

    private String imgId;

    private String headImgCatch;

    private int subscribe;

    private Date subscribe_time;

    private String remark;

    private int groupId;

    private String source;

    private String sourceId;

    private Date createdAt;

    private Date updatedAt;

    private String mobile;

    private String qq;

    private String email;

    private int contactStatus;

    private Date cancelSubscribeTime;

    private int isBind;
    private String uid;
}
