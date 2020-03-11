package org.ship.core.dao.engine;

import org.apache.ibatis.annotations.Select;
import org.ship.core.vo.engine.Engine;

import java.util.Collection;

/**
 * Created by wx on 2017/5/8.
 */
public interface EngineDao {
    @Select("select * from ship_engine")
    Collection<Engine> getNodes();
}
