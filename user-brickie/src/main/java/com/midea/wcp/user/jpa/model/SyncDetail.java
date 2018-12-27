package com.midea.wcp.user.jpa.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

//@Table(name = "mp_user_wxd19c5a897bbaaaae")
//@Entity
@Data
public class SyncDetail implements Serializable {

    private static final long serialVersionUID = -5836388031763020442L;

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

//    @Column(name = "app_id")
    private String appId;

//    @Column(name = "open_id")
    private String openId;

//    @Column(name = "union_id")
    private String unionId;

//    @Column(name = "nick_name")
    private String nickname;

    private int sex;

//    @Column(name = "sex_id")
    private String sexId;

    private String country;

    private String province;

    private String city;

    private String language;

//    @Column(name = "head_img_url")
    private String headImgUrl;

//    @Column(name = "img_id")
    private String imgId;

//    @Column(name = "head_img_catch")
    private String headImgCatch;

    private int subscribe;

//    @Column(name = "sub_scribe_time")
    private Date subscribe_time;

    private String remark;

//    @Column(name = "group_id")
    private int groupId;

    private String source;

//    @Column(name = "source_id")
    private String sourceId;

//    @Column(name = "created_at")
    private Date createdAt;

//    @Column(name = "updated_at")
    private Date updatedAt;

    private String mobile;

    private String qq;

    private String email;

//    @Column(name = "contact_status")
    private int contactStatus;

//    @Column(name = "cancel_subscribe_time")
    private Date cancelSubscribeTime;

//    @Column(name = "is_bind")
    private Boolean isBind;
    private String uid;
}
