package org.ship.core.dao.node;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.ship.core.vo.node.Iface;

import java.util.Collection;

/**
 * Created by wx on 2017/4/29.
 */
public interface IfaceDao {
    @Select("SELECT * FROM ship_node_iface WHERE node_id = #{nodeId}")
    Collection<Iface> getIfacesByNodeId(@Param("nodeId") int nodeId);

    @Select("SELECT * FROM ship_node_iface WHERE id = #{ifaceId}")
    Iface getIface(@Param("ifaceId") int ifaceId);
}
