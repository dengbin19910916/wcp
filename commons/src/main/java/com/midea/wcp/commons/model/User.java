package com.midea.wcp.commons.model;

import com.google.common.collect.Lists;
import com.google.gson.JsonObject;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import javax.persistence.ManyToMany;
import javax.persistence.Transient;
import java.util.List;

@Data
@NoArgsConstructor
public class User {

    @javax.persistence.Id
    @Id
    private String openId;
    private String appId;
    private String nickname;
    private Byte sex;
    private String language;
    private String city;
    private String province;
    private String country;
    private String headImgUrl;
    private String subscribeTime;
    private String unionId;
    private String remark;
    private Integer groupId;
    private Integer[] tagIds;
    private String subscribeScene;
    private Integer qrScene;
    private String qrSceneStr;

    @Transient
    @ManyToMany(mappedBy = "users")
    private List<Account> accounts;

    @Transient
    @ManyToMany
    private List<Account.Tag> tags;

    public User(String appId, JsonObject user) {
        this.appId = appId;
        this.openId = user.get("openid").getAsString();
        this.nickname = user.get("nickname").getAsString();
        this.sex = user.get("sex").getAsByte();
        this.language = user.get("language").getAsString();
        this.city = user.get("city").getAsString();
        this.province = user.get("province").getAsString();
        this.country = user.get("country").getAsString();
        this.headImgUrl = user.get("headimgurl").getAsString();
        this.subscribeTime = user.get("subscribe_time").getAsString();
        this.unionId = user.get("unionid") == null ? null : user.get("unionid").getAsString();
        this.remark = user.get("remark").getAsString();
        this.groupId = user.get("groupid").getAsInt();
        this.tagIds = Lists.newArrayList(user.get("tagid_list").getAsJsonArray().iterator()).toArray(Integer[]::new);
        this.subscribeScene = user.get("subscribe_scene").getAsString();
        this.qrScene = user.get("qr_scene").getAsInt();
        this.qrSceneStr = user.get("qr_scene_str").getAsString();
    }
}
