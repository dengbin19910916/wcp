package com.midea.wcp.user.jpa.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "sync_detail")
@Entity
@Data
public class SyncDetail implements Serializable {

    private static final long serialVersionUID = -5836388031763020442L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

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
