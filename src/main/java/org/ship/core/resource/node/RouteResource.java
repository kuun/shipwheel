package org.ship.core.resource.node;

import org.ship.core.service.node.INodeService;
import org.ship.core.util.Pagination;
import org.ship.core.vo.node.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.Map;

/**
 * Created by wx on 2017/4/30.
 */
@RestController
@RequestMapping(value = "/ship/node")
public class RouteResource {
    @Autowired
    private INodeService nodeService;

    @RequestMapping(value = "/routeList", method = RequestMethod.POST)
    public Pagination<Route> getRoutes(@RequestParam("nodeId") int nodeId,
                                @RequestBody Map<String, String> obj) throws SQLException {
        int page = Integer.parseInt(obj.get("page"));
        int limit = Integer.parseInt(obj.get("limit"));
        return nodeService.getRoutes(nodeId, page, limit);
    }

    @RequestMapping(value = "/route", method = RequestMethod.GET)
    public Route getRoute(@RequestParam("id") int id) {
        return nodeService.getRoute(id);
    }

    @RequestMapping(value = "/route", method = RequestMethod.POST)
    public Object createRoute(@RequestBody Route route) throws Exception {
        Object obj = null;
        obj = nodeService.createRoute(route);
        return obj;
    }

    @RequestMapping(value = "/route", method = RequestMethod.PUT)
    public Object modRoute(@RequestBody Route route) throws Exception {
        Object object = null;
        object = nodeService.modRoute(route);
        return object;
    }

    @RequestMapping(value = "/route", method = RequestMethod.DELETE)
    public Object deleteRoute(@RequestParam("id") int id) throws Exception {
        Object object = null;
        object = nodeService.deleteRoute(id);
        return object;
    }
}
