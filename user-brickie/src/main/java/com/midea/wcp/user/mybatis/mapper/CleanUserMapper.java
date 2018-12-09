package com.midea.wcp.user.mybatis.mapper;

import com.midea.wcp.user.mybatis.model.MpUser;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * A表:本地记录
 * B表:最新数据
 */
@Mapper
public interface CleanUserMapper {
    /**
     * batch save mp user
     */
    @Insert("<script>" +
            "insert into ${table} " +
            "(app_id, open_id, union_id, nick_name, sex, " +
            "sex_id, country, province, city, language, head_img_url, " +
            "img_id, head_img_catch, subscribe, sub_scribe_time, remark, " +
            "group_id, source, source_id, created_at, updated_at, " +
            "mobile, qq, email, contact_status, cancel_subscribe_time, is_bind, uid) " +
            "values" +
            "<foreach collection ='list' item='user' separator =','> " +
            "(#{user.appId},#{user.openId},#{user.unionId},#{user.nickname},#{user.sex}," +
            "#{user.sexId},#{user.country},#{user.province},#{user.city},#{user.language},#{user.headImgUrl}," +
            "#{user.imgId},#{user.headImgCatch},#{user.subscribe},#{user.subscribe_time},#{user.remark}," +
            "#{user.groupId},#{user.source},#{user.sourceId},#{user.createdAt},#{user.updatedAt}," +
            "#{user.mobile},#{user.qq},#{user.email},#{user.contactStatus},#{user.cancelSubscribeTime},#{user.isBind},#{user.uid})" +
            "</foreach >" +
            "</script>")
    Integer batchSaveMpUser(@Param("list") List<MpUser> list, @Param("table") String table);

    /**
     * batch save open id
     */
    @Insert("<script>" +
            "insert into ${table}" +
            "(openid)" +
            "values" +
            "<foreach collection ='list' item='openid' separator =','> " +
            "(#{openid})" +
            "</foreach >" +
            "</script>")
    Integer batchSaveOpenId(@Param("list") List<String> list, @Param("table") String table);

}