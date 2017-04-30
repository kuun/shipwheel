package org.ship.core.dao.node;

import org.apache.ibatis.annotations.*;
import org.ship.core.vo.node.Route;

import java.util.Collection;

/**
 * Created by wx on 2017/4/30.
 */
public interface RouteDao {
    @Select("SELECT * FROM ship_nic_route WHERE node_id = #{nodeId}")
    Collection<Route> getRoutes(@Param("nodeId") int nodeId);

    @Select("SELECT * FROM ship_nic_route WHERE id = #{id}")
    Route getRoute(@Param("id") int id);

    @Insert("INSERT INTO ship_nic_route (subnet, mask, nic_id, node_id, gateway) " +
            "VALUES (#{subnet}, #{mask}, #{nic_id}, #{node_id}, #{gateway})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void createRoute(Route route);

    @Update("update ship_nic_route set gateway = #{gateway} where id = #{id}")
    void modRoute(@Param("gateway") String gateway, @Param("id") int id);

    @Delete("delete from ship_nic_route where id = #{id}")
    void deleteRoute(@Param("id") int id);
}
