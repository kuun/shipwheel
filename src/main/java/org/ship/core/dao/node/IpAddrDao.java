package org.ship.core.dao.node;

import org.apache.ibatis.annotations.*;
import org.ship.core.vo.node.IpAddress;

import java.util.Collection;

/**
 * Created by wx on 2017/4/30.
 */
public interface IpAddrDao {
    String SQL = "SELECT addr.id, addr.nic_id, addr.node_id, addr.ip, addr.mask, nic.name AS nicName " +
            "FROM ship_nic_addr AS addr INNER JOIN ship_node_nic AS nic ON addr.nic_id = nic.id ";

    @Select("SELECT COUNT(*) FROM ship_nic_addr where node_id = #{nodeId}")
    int getCount(@Param("nodeId") int nodeId);

    @Select(SQL + "WHERE addr.node_id = #{nodeId} ORDER BY id LIMIT #{limit} OFFSET #{offset}")
    Collection<IpAddress> getIpAddrListByPage(@Param("nodeId") int nodeId,
                                                @Param("limit") int limit,
                                                @Param("offset") int offset);

    @Select("SELECT * FROM ship_nic_addr WHERE node_id = #{nodeId}")
    Collection<IpAddress> getIpAddrListByNodeId(@Param("nodeId") int nodeId);

    @Select("SELECT * FROM ship_nic_addr WHERE id = #{id}")
    IpAddress getIpAddr(@Param("id") int id);

    @Insert("INSERT INTO ship_nic_addr (nic_id, node_id, ip, mask) VALUES (#{nic_id}, #{node_id}, #{ip}, #{mask})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void createIpAddr(IpAddress ipAddress);

    @Update("UPDATE ship_nic_addr SET ip = #{ip}, mask = #{mask}, nic_id = #{nic_id} WHERE id = #{id}")
    void modIpAddr(IpAddress ipAddress);

    @Delete("DELETE FROM ship_nic_addr WHERE id = #{id}")
    void deleteIpAddr(@Param("id") int id);
}
