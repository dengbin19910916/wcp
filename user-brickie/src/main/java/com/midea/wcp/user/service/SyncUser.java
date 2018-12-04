package com.midea.wcp.user.service;

import com.midea.wcp.commons.message.OpenIdWrapper;

/**
 * 用于保存openId到数据库，以完成数据库中粉丝的更新
 */
public interface SyncUser {
    void saveOpenid(OpenIdWrapper openIDWrapper);
}
