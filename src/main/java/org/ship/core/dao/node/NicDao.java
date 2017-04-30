package org.ship.core.dao.node;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.ship.core.vo.node.Nic;

import java.util.Collection;

/**
 * Created by wx on 2017/4/29.
 */
public interface NicDao {
    @Select("select * from ship_node_nic where node_id = #{nodeId}")
    Collection<Nic> getNicsByNodeId(@Param("nodeId") int nodeId);

    @Select("select * from ship_node_nic where id = #{nicId}")
    Nic getNic(@Param("nicId") int nicId);
}
