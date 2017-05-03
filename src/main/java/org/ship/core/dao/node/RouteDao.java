package org.ship.core.dao.node;

import org.apache.ibatis.annotations.*;
import org.ship.core.vo.node.Route;

import java.util.Collection;

/**
 * Created by wx on 2017/4/30.
 */
public interface RouteDao {
    String SQL = "SELECT nicRoute.id, nicRoute.subnet, nicRoute.mask, nicRoute.nic_id, nicRoute.node_id, nicRoute.gateway, nic.name AS nicName " +
            "FROM ship_nic_route AS nicRoute INNER JOIN ship_node_nic AS nic ON nicRoute.nic_id = nic.id ";

    @Select("SELECT COUNT(*) FROM ship_nic_route WHERE node_id = #{nodeId}")
    int getCount(@Param("nodeId") int nodeId);

    @Select(SQL + "WHERE nicRoute.node_id = #{nodeId} ORDER BY id LIMIT #{limit} OFFSET #{offset}")
    Collection<Route> getRoutes(@Param("nodeId") int nodeId,
                                @Param("limit") int limit,
                                @Param("offset") int offset);

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
