package com.midea.wcp.user.mybatis.mapper;


import com.midea.wcp.user.mybatis.model.MybatisTest;
import org.apache.ibatis.annotations.*;

@Mapper
public interface TestMapper {
    @Select("select * from cp_account where id = #{id}")
    @Results({
            @Result(property = "id",column = "id"),
            @Result(property = "name",column = "name"),
            @Result(property = "corp_id",column = "corp_id")
    })
    MybatisTest selectTest(@Param("id") Integer id);
}
