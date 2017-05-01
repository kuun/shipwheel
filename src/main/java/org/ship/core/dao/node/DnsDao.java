package org.ship.core.dao.node;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.ship.core.vo.node.Dns;

/**
 * Created by wx on 2017/5/1.
 */
public interface DnsDao {
    @Select("select * from ship_dns where node_id = #{nodeId}")
    Dns getDns(@Param("nodeId") int nodeId);

    @Update("update ship_dns set dns = #{dns} where node_id = #{nodeId}")
    void modDns(@Param("nodeId") int nodeId, @Param("dns") String dns);
}
