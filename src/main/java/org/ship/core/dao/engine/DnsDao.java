package org.ship.core.dao.engine;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.ship.core.vo.engine.Dns;

/**
 * Created by wx on 2017/5/1.
 */
public interface DnsDao {
    @Select("SELECT * FROM ship_dns WHERE node_id = #{nodeId}")
    Dns getDns(@Param("nodeId") int nodeId);

    @Update("UPDATE ship_dns SET dns = #{dns} WHERE node_id = #{nodeId}")
    void modDns(@Param("nodeId") int nodeId, @Param("dns") String dns);
}
