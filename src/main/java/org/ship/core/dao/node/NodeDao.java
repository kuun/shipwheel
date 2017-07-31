package org.ship.core.dao.node;

import org.apache.ibatis.annotations.Select;
import org.ship.core.vo.node.Node;

import java.util.Collection;

/**
 * Created by wx on 2017/5/8.
 */
public interface NodeDao {
    @Select("select * from ship_node")
    Collection<Node> getNodes();
}
