package org.ship.core.dao.node;

import org.apache.ibatis.annotations.*;
import org.ship.core.vo.node.ConnRule;

import java.util.Collection;

/**
 * Created by wx on 2017/5/1.
 */
public interface ConnRuleDao {
    @Select("SELECT * FROM ship_conn_rule")
    Collection<ConnRule> getConnRules();

    @Select("SELECT * FROM ship_conn_rule WHERE id = #{id}")
    ConnRule getConnRule(@Param("id") int id);

    @Insert("INSERT INTO ship_conn_rule (conn_type, direction, listen_ip_id, listen_port, dst_ip, dst_port, conn_ip_id, status) " +
            "VALUES (#{conn_type}, #{direction}, #{listen_ip_id}, #{listen_port}, #{dst_ip}, #{dst_port}, #{conn_ip_id}, #{status})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void addConnRule(ConnRule rule);

    @Update("UPDATE ship_conn_rule SET conn_type = #{conn_type}, direction = #{direction}, listen_ip_id = #{listen_ip_id}," +
            "listen_port = #{listen_port}, dst_ip = #{dst_ip}, dst_port = #{dst_port}, conn_ip_id = #{conn_ip_id}, status = #{status} WHERE id = #{id}")
    void modConnRule(ConnRule rule);

    @Delete("DELETE FROM ship_conn_rule WHERE id = #{id}")
    void delConnRule(@Param("id") int id);
}
