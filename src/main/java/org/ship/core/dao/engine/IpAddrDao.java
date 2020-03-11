package org.ship.core.dao.engine;

import org.apache.ibatis.annotations.*;
import org.ship.core.vo.engine.IpAddress;

import java.util.Collection;

/**
 * Created by wx on 2017/4/30.
 */
public interface IpAddrDao {
    String SQL = "SELECT addr.id, addr.iface_id, addr.node_id, addr.ip, addr.mask, iface.name AS ifaceName " +
            "FROM ship_iface_addr AS addr INNER JOIN ship_node_iface AS iface ON addr.iface_id = iface.id ";

    @Select("SELECT COUNT(*) FROM ship_iface_addr where node_id = #{nodeId}")
    int getCount(@Param("nodeId") int nodeId);

    @Select(SQL + "WHERE addr.node_id = #{nodeId} ORDER BY id LIMIT #{limit} OFFSET #{offset}")
    Collection<IpAddress> getIpAddrListByPage(@Param("nodeId") int nodeId,
                                                @Param("limit") int limit,
                                                @Param("offset") int offset);

    @Select("SELECT * FROM ship_iface_addr WHERE node_id = #{nodeId}")
    Collection<IpAddress> getIpAddrListByNodeId(@Param("nodeId") int nodeId);

    @Select("SELECT * FROM ship_iface_addr WHERE id = #{id}")
    IpAddress getIpAddr(@Param("id") int id);

    @Select("SELECT * FROM ship_iface_addr WHERE node_id = #{nodeId} AND ip = #{ip}")
    IpAddress getIpAddrByNodeIdAndIp(@Param("nodeId") int nodeId, @Param("ip") String ip);

    @Select("select * from ship_iface_addr where iface_id = #{ifaceId}")
    Collection<IpAddress> getIpAddrByIfaceId(@Param("ifaceId") int ifaceId);

    @Insert("INSERT INTO ship_iface_addr (iface_id, node_id, ip, mask) VALUES (#{iface_id}, #{node_id}, #{ip}, #{mask})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void createIpAddr(IpAddress ipAddress);

    @Update("UPDATE ship_iface_addr SET ip = #{ip}, mask = #{mask}, iface_id = #{iface_id} WHERE id = #{id}")
    void modIpAddr(IpAddress ipAddress);

    @Delete("DELETE FROM ship_iface_addr WHERE id = #{id}")
    void deleteIpAddr(@Param("id") int id);
}
