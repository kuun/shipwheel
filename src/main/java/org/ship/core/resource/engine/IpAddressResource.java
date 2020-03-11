package org.ship.core.resource.engine;

import org.ship.core.service.engine.IEngineService;
import org.ship.core.util.Pagination;
import org.ship.core.vo.engine.IpAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Map;

/**
 * Created by wx on 2017/4/30.
 */
@RestController
@RequestMapping(value = "/ship/node")
public class IpAddressResource {
    @Autowired
    private IEngineService nodeService;

    /**
     * 分页查询
     * @param nodeId
     * @param obj
     * @return
     */
    @RequestMapping(value = "/ipAddrList", method = RequestMethod.POST)
    public Pagination<IpAddress> getIpAddrs(@RequestParam("nodeId") int nodeId,
                                            @RequestBody Map<String, String> obj) throws SQLException {
        int page = Integer.parseInt(obj.get("page"));
        int limit = Integer.parseInt(obj.get("limit"));
        return nodeService.getIpAddrList(nodeId, page, limit);
    }

    @RequestMapping(value = "/ipAddrList", method = RequestMethod.GET)
    public Collection<IpAddress> getIpAddrList(@RequestParam("nodeId") int nodeId) {
        return nodeService.getIpAddrList(nodeId);
    }

    @RequestMapping(value = "/ipAddr", method = RequestMethod.GET)
    public IpAddress getIpAddr(@RequestParam("id") int id) {
        return nodeService.getIpAddr(id);
    }

    @RequestMapping(value = "/ipAddr", method = RequestMethod.POST)
    public Object createIpAddr(@RequestBody IpAddress ipAddr) throws Exception {
        Object object = null;
        object = nodeService.createIpAddr(ipAddr);
        return object;
    }

    @RequestMapping(value = "/ipAddr", method = RequestMethod.PUT)
    public Object modIpaddr(@RequestBody IpAddress ipaddr) throws Exception {
        Object object = null;
        object =  nodeService.modIpAddr(ipaddr);
        return object;
    }

    @RequestMapping(value = "/ipAddr", method = RequestMethod.DELETE)
    public Object deleteIpAddr(@RequestParam("id") int id) throws Exception{
        Object object = null;
        object = nodeService.deleteIpAddr(id);
        return object;
    }
}
