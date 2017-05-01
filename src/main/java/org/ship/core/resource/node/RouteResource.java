package org.ship.core.resource.node;

import org.ship.core.service.node.INodeService;
import org.ship.core.vo.node.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Created by wx on 2017/4/30.
 */
@RestController
@RequestMapping(value = "/ship/node")
public class RouteResource {
    @Autowired
    private INodeService nodeService;

    @RequestMapping(value = "/routeList", method = RequestMethod.GET)
    public Object getRoutes(@RequestParam("nodeId") int nodeId) {
        Object object = null;
        object = nodeService.getRoutes(nodeId);
        return object;
    }

    @RequestMapping(value = "/route", method = RequestMethod.GET)
    public Route getRoute(@RequestParam("id") int id) {
        return nodeService.getRoute(id);
    }

    @RequestMapping(value = "/route", method = RequestMethod.POST)
    public Route createRoute(@RequestBody Route route) {
        return nodeService.createRoute(route);
    }

    @RequestMapping(value = "/route", method = RequestMethod.PUT)
    public Route modRoute(@RequestBody Route route) {
        return nodeService.modRoute(route);
    }

    @RequestMapping(value = "/route", method = RequestMethod.DELETE)
    public void deleteRoute(@RequestParam("id") int id) {
        nodeService.deleteRoute(id);
    }
}