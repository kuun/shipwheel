package org.ship.core.resource.node;

import org.ship.core.service.node.INodeService;
import org.ship.core.util.Pagination;
import org.ship.core.vo.node.IpAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wx on 2017/4/30.
 */
@RestController
@RequestMapping(value = "/ship/node")
public class IpAddressResource {
    @Autowired
    private INodeService nodeService;

    /**
     * 分页查询
     * @param nodeId
     * @param obj
     * @return
     */
    @RequestMapping(value = "/ipAddrList", method = RequestMethod.POST)
    public Pagination<IpAddress> getIpAddrs(@RequestParam("nodeId") int nodeId,
                                            @RequestBody Map<String, String> obj) {
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
    public IpAddress createIpAddr(@RequestBody IpAddress ipAddr) {
        return nodeService.createIpAddr(ipAddr);
    }

    @RequestMapping(value = "/ipAddr", method = RequestMethod.PUT)
    public IpAddress modIpaddr(@RequestBody IpAddress ipaddr) {
        return nodeService.modIpAddr(ipaddr);
    }

    @RequestMapping(value = "/ipAddr", method = RequestMethod.DELETE)
    public void deleteIpAddr(@RequestParam("id") int id) {
        nodeService.deleteIpAddr(id);
    }
}
