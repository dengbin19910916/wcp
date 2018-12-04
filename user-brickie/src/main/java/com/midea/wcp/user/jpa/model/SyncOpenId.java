package com.midea.wcp.user.jpa.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@Table(name = "sync_open_id_xxx")
public class SyncOpenId implements Serializable {

    private static final long serialVersionUID = 1595086712605563703L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "openid")
    private String openId;
}
