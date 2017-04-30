package org.ship.core.dao.node;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.ship.core.vo.node.Nic;

import java.util.Collection;

/**
 * Created by wx on 2017/4/29.
 */
public interface NicDao {
    @Select("SELECT * FROM ship_node_nic WHERE node_id = #{nodeId}")
    Collection<Nic> getNicsByNodeId(@Param("nodeId") int nodeId);

    @Select("SELECT * FROM ship_node_nic WHERE id = #{nicId}")
    Nic getNic(@Param("nicId") int nicId);
}
