package com.midea.wcp.pipeline;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

@Slf4j
@Component
public class Brickie {

    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;
    @Value("${spring.datasource.url}")
    private String url;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;

    private final RestHighLevelClient client;

    public Brickie(RestHighLevelClient client) {
        this.client = client;
    }

    public static String SQL = "select " +
            "                    users.id                                           as id,\n" +
            "                    users.open_id                                      as openId,\n" +
            "                    users.union_id                                     as unionId,\n" +
            "                    users.app_id                                       as appId,\n" +
            "                    users.nick_name                                    as nickName,\n" +
            "                    users.sex                                          as sex,\n" +
            "                    users.sex_id                                       as sexId,\n" +
            "                    users.country                                      as country,\n" +
            "                    users.province                                     as province,\n" +
            "                    users.city                                         as city,\n" +
            "                    users.language                                     as language,\n" +
            "                    users.head_img_url                                 as headImgUrl,\n" +
            "                    users.img_id                                       as imgId,\n" +
            "                    users.head_img_catch                               as headImgCat,\n" +
            "                    users.subscribe                                    as subscribe,\n" +
            "                    unix_timestamp(users.sub_scribe_time) * 1000       as subScribeTime,\n" +
            "                    users.remark                                       as remark,\n" +
            "                    users.group_id                                     as groupId,\n" +
            "                    users.source                                       as source,\n" +
            "                    users.source_id                                    as sourceId,\n" +
            "                    unix_timestamp(users.created_at) * 1000            as createdAt,\n" +
            "                    unix_timestamp(users.updated_at) * 1000            as updatedAt,\n" +
            "                    users.mobile                                       as mobile,\n" +
            "                    users.qq                                           as qq,\n" +
            "                    users.email                                        as email,\n" +
            "                    users.contact_status                               as contactStatus,\n" +
            "                    unix_timestamp(users.cancel_subscribe_time) * 1000 as cancelSubscribeTime,\n" +
            "                    users.uid                                          as uid,\n" +
            "                    users.is_bind                                      as isBind\n" +
            "from tableName users\n" +
            "group by users.id";

    public void toEs(String tableName) throws ClassNotFoundException, SQLException, IOException {
        long start = System.currentTimeMillis();

        try {
            DeleteIndexRequest deleteRequest = new DeleteIndexRequest("mpuser_" + tableName);
            client.indices().delete(deleteRequest, RequestOptions.DEFAULT);
        } catch (Exception ignored) {
        }

        Class.forName(driverClassName);
        Connection connection = getConnection();
        Connection conn = getConnection();

        PreparedStatement statement = connection.prepareStatement(Brickie.SQL.replaceAll("tableName", "mp_user_" + tableName),
                ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
        statement.setFetchSize(Integer.MIN_VALUE);

        ResultSet rs = statement.executeQuery();
        while (rs.next()) {
            User user = new User();
            user.setId(rs.getString("id"));
            user.setOpenId(rs.getString("openId"));
            user.setUnionId(rs.getString("unionId"));
            user.setAppId(rs.getString("appId"));
            user.setNickName(rs.getString("nickName"));
            user.setSex(rs.getString("sex"));
            user.setSexId(rs.getString("sexId"));
            user.setCountry(rs.getString("country"));
            user.setProvince(rs.getString("province"));
            user.setCity(rs.getString("city"));
            user.setLanguage(rs.getString("language"));
            user.setHeadImgUrl(rs.getString("headImgUrl"));
            user.setImgId(rs.getString("imgId"));
            user.setHeadImgCat(rs.getString("headImgCat"));
            user.setSubscribe(rs.getString("subscribe"));
            user.setSubScribeTime(rs.getLong("subScribeTime"));
            user.setRemark(rs.getString("remark"));
            user.setGroupId(rs.getString("groupId"));
            user.setSource(rs.getString("source"));
            user.setSourceId(rs.getString("sourceId"));
            user.setCreatedAt(rs.getLong("createdAt"));
            user.setUpdatedAt(rs.getLong("updatedAt"));
            user.setMobile(rs.getString("mobile"));
            user.setQq(rs.getString("qq"));
            user.setEmail(rs.getString("email"));
            user.setContactStatus(rs.getString("contactStatus"));
            user.setCancelSubscribeTime(rs.getLong("cancelSubscribeTime"));
            user.setUid(rs.getString("uid"));
            user.setIsBind(rs.getString("isBind"));

            PreparedStatement tagPs = conn.prepareStatement("select tag_id as tagId\n" +
                    "from mp_user_tag_relation_" + tableName + "\n" +
                    "where open_id = '" + user.getOpenId() + "'");
            List<Integer> tagIds = new ArrayList<>();
            ResultSet tags = tagPs.executeQuery();
            while (tags.next()) {
                tagIds.add(tags.getInt("tagId"));
            }
            user.setTagId(tagIds);

            PreparedStatement groupPs = conn.prepareStatement("select code as code\n" +
                    "from mp_user_group_relation_" + tableName + "\n" +
                    "where open_id = '" + user.getOpenId() + "'");
            List<Integer> groupCodes = new ArrayList<>();
            ResultSet groups = groupPs.executeQuery();
            while (groups.next()) {
                groupCodes.add(groups.getInt("code"));
            }
            user.setGroupCode(groupCodes);

            IndexRequest indexRequest = new IndexRequest("mpuser_" + user.getAppId(), user.getAppId(), user.getOpenId())
                    .source(buildDocument(user));
            client.index(indexRequest, RequestOptions.DEFAULT);
            groupPs.close();
            tagPs.close();
        }

        rs.close();
        statement.close();
        connection.close();
        System.out.println("同步" + tableName + "耗时: " + (System.currentTimeMillis() - start) / 1000);
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }

    @SneakyThrows
    private static XContentBuilder buildDocument(Object object) {
        XContentBuilder builder = jsonBuilder().startObject();

        Class clazz = object.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            if (needful(field)) {
                builder.field(field.getName(), field.get(object));
            }
        }

        builder.endObject();
        return builder;
    }


    /**
     * 返回字段是否需要持久化。
     *
     * @param field 数据对象的字段
     * @return 需要持久化字段的类型
     */
    private static boolean needful(Field field) {
        Class<?> type = field.getType();
        return type == String.class
                || type == Integer.class
                || type == Long.class
                || type == List.class;
    }
}
