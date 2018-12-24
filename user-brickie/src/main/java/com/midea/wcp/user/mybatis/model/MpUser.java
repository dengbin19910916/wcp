package com.midea.wcp.user.mybatis.model;

import lombok.Data;

import java.util.Date;

@Data
public class MpUser {
    private Integer id;

    private String appId;

    private String openid;

    private String unionid;

    private String nickname;

    private String sex;

    private Integer sexId;

    private String country;

    private String province;

    private String city;

    private String language;

    private String headimgurl;

    private String imgId;

    private String headImgCatch;

    private Integer subscribe;

    private Date subscribe_time;

    private String remark;

    private Integer groupId;

    private String source;

    private String sourceId;

    private Date createdAt;

    private Date updatedAt;

    private String mobile;

    private String qq;

    private String email;

    private Integer contactStatus;

    private Date cancelSubscribeTime;

    private Integer isBind;
    private String uid;
}
