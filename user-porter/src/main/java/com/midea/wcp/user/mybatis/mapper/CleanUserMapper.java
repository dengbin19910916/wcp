package com.midea.wcp.user.mybatis.mapper;


import com.midea.wcp.user.mybatis.model.Data2Handle;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * A表:本地记录
 * B表:最新数据
 */
@Mapper
public interface CleanUserMapper {

    /**
     * duplicate openid
     */
    @Select("SELECT a.id id,a.open_id openid FROM ${table} a INNER JOIN ${table} b WHERE a.open_id = b.open_id AND a.id != b.id GROUP BY a.id")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "openid", column = "openid")
    })
    List<Data2Handle> selectDuplicateOpenid(@Param("table") String table);

    /**
     * sub 0->1
     */
    @Select("select a.id id from ${tableB} b left join ${tableA} a on b.openid = a.open_id where a.subscribe = 0")
    List<Integer> selectSubZero2One(@Param("tableA") String tableA, @Param("tableB") String tableB);

    /**
     * sub 1->0
     */
    @Select("select a.id from ${tableA} a left join ${tableB} b on a.open_id = b.openid where b.openid is null and a.subscribe = 1")
    List<Integer> selectSubOne2Zero(@Param("tableA") String tableA, @Param("tableB") String tableB);

    /**
     * 需要添加的openid
     */
    @Select("select b.openid openid from ${tableB} b left join ${tableA} a on b.openid = a.open_id where a.open_id is null")
    List<String> selectOpenid2Add(@Param("tableA") String tableA, @Param("tableB") String tableB);

    /**
     * batch update integer
     */
    @Update("<script>" +
            "update ${table} set ${column} = #{newValue} where id in " +
            "<foreach collection='ids' item='id' open='(' close=')' separator=','>" +
            "#{id}" +
            "</foreach>" +
            "</script>")
    Integer batchUpdateIntegerById(@Param("table") String table, @Param("ids") List<Integer> ids,
                                   @Param("column") String column, @Param("newValue") Integer newValue);

    /**
     * batch update string
     */
    @Update("<script>" +
            "update ${table} set ${column} = #{newValue} where id in " +
            "<foreach collection='ids' item='id' open='(' close=')' separator=','>" +
            "#{id}" +
            "</foreach>" +
            "</script>")
    Integer batchUpdateStringById(@Param("table") String table, @Param("ids") List<Integer> ids,
                                  @Param("column") String column, @Param("newValue") String newValue);


    /**
     * batch delete by id
     */
    @Delete("<script>" +
            "delete from ${table} where id in " +
            "<foreach collection='ids' item='id' open='(' close=')' separator=','>" +
            "#{id}" +
            "</foreach>" +
            "</script>")
    Integer batchDeleteById(@Param("table") String table, @Param("ids") List<Integer> ids);


    /**
     * delete all
     */
    @Select("truncate ${table}")
    void deleteAll(@Param("table") String table);


    /**
     * count open id
     */
    @Select("select COUNT(id) from ${table}")
    Integer queryOpenIdNum(@Param("table") String table);

    /**
     * fix subscribe null
     */
    @Select("update ${table} set subscribe = 0 where subscribe is null")
    Integer setSubNull2Zero(@Param("table") String table);
}