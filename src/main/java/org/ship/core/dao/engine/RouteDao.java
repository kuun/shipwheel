package org.ship.core.dao.engine;

import org.apache.ibatis.annotations.*;
import org.ship.core.vo.engine.Route;

import java.util.Collection;

/**
 * Created by wx on 2017/4/30.
 */
public interface RouteDao {
    String SQL = "SELECT ifaceRoute.id, ifaceRoute.dst_net, ifaceRoute.dst_mask, ifaceRoute.iface_id, ifaceRoute.node_id, ifaceRoute.gateway, iface.name AS ifaceName " +
            "FROM ship_iface_route AS ifaceRoute INNER JOIN ship_node_iface AS iface ON ifaceRoute.iface_id = iface.id ";

    @Select("SELECT COUNT(*) FROM ship_iface_route WHERE node_id = #{nodeId}")
    int getCount(@Param("nodeId") int nodeId);

    @Select(SQL + "WHERE ifaceRoute.node_id = #{nodeId} ORDER BY id LIMIT #{limit} OFFSET #{offset}")
    Collection<Route> getRoutes(@Param("nodeId") int nodeId,
                                @Param("limit") int limit,
                                @Param("offset") int offset);

    @Select("SELECT * FROM ship_iface_route WHERE id = #{id}")
    Route getRoute(@Param("id") int id);

    @Select("SELECT * FROM ship_iface_route WHERE node_id = #{nodeId} AND gateway = #{gateway}")
    Route findExistRoute(@Param("nodeId") int nodeId,
                         @Param("gateway") String gateway);

    @Insert("INSERT INTO ship_iface_route (dst_net, dst_mask, iface_id, node_id, gateway) " +
            "VALUES (#{dst_net}, #{dst_mask}, #{iface_id}, #{node_id}, #{gateway})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void createRoute(Route route);

    @Update("update ship_iface_route set gateway = #{gateway} where id = #{id}")
    void modRoute(@Param("gateway") String gateway, @Param("id") int id);

    @Delete("delete from ship_iface_route where id = #{id}")
    void deleteRoute(@Param("id") int id);

    @Delete("delete from ship_iface_route where iface_id = #{ifaceId}")
    void delRouteOnIface(@Param("ifaceId") int ifaceId);
}
