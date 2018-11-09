package com.midea.wcp.pipeline;

import lombok.Data;

import java.util.List;

@Data
public class User {

    private String id;
    private String openId;
    private String unionId;
    private String appId;
    private String nickName;
    private String sex;
    private String sexId;
    private String country;
    private String province;
    private String city;
    private String language;
    private String headImgUrl;
    private String imgId;
    private String headImgCat;
    private String subscribe;
    private Long subScribeTime;
    private String remark;
    private String groupId;
    private String source;
    private String sourceId;
    private Long createdAt;
    private Long updatedAt;
    private String mobile;
    private String qq;
    private String email;
    private String contactStatus;
    private Long cancelSubscribeTime;
    private String uid;
    private String isBind;
    private List<Integer> tagId;
    private List<Integer> groupCode;
}
