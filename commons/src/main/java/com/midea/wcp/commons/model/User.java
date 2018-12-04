package com.midea.wcp.commons.model;

import com.google.gson.JsonObject;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.util.List;

@Data
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String appId;

    @Transient
    @ManyToMany(mappedBy = "users")
    private List<Account> accounts;

    @Transient
    @ManyToMany
    private List<Account.Tag> tags;


    private int subscribe;
    private String openid;
    private String nickname;
    private int sex;
    private String language;
    private String city;
    private String province;
    private String country;
    private String headimgurl;
    private long subscribe_time;
    private String unionid;
    private String remark;
    private int groupid;
    private String subscribe_scene;
    private int qr_scene;
    private String qr_scene_str;

}
