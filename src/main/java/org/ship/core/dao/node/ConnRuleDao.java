package org.ship.core.dao.node;

import org.apache.ibatis.annotations.*;
import org.ship.core.util.Pagination;
import org.ship.core.vo.node.ConnRule;

import java.util.Collection;

/**
 * Created by wx on 2017/5/1.
 */
public interface ConnRuleDao {
    Collection<ConnRule> getConnRules(@Param("limit") int limit, @Param("offset") int offset);
    ConnRule getConnRule(@Param("id") int id);
    Collection<ConnRule> getConnRuleByIpAddrId(@Param("ipAddrId") int ipAddrId);


    @Select("SELECT COUNT(*) FROM ship_conn_rule")
    int getCount();

    @Select("select id from ship_conn_rule where listen_port = #{listen_port} and listen_addr_id = #{listen_addr_id}")
    Collection<Integer> getIdByTcp(@Param("listen_port") int listen_port,
                                   @Param("listen_addr_id") int listen_addr_id);

    @Insert("INSERT INTO ship_conn_rule (rule_type, direct, listen_addr_id, listen_port, dst_addr, dst_port, send_addr_id, status) " +
            "VALUES (#{rule_type}, #{direct}, #{listen_addr.id}, #{listen_port}, #{dst_addr}, #{dst_port}, #{send_addr.id}, #{status})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void addConnRule(ConnRule rule);

    @Update("UPDATE ship_conn_rule SET rule_type = #{rule_type}, direct = #{direct}, listen_addr_id = #{listen_addr.id}," +
            "listen_port = #{listen_port}, dst_addr = #{dst_addr}, dst_port = #{dst_port}, send_addr_id = #{send_addr.id}, status = #{status} WHERE id = #{id}")
    void modConnRule(ConnRule rule);

    @Delete("DELETE FROM ship_conn_rule WHERE id = #{id}")
    void delConnRule(@Param("id") int id);
}
