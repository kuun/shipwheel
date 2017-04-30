package org.ship.core.resource.node;

import org.ship.core.service.node.INodeService;
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

    @RequestMapping(value = "/ipAddrList", method = RequestMethod.GET)
    public Object getIpAddrs(@RequestParam("nodeId") int nodeId,
                             @RequestParam(value = "nicId", required = false) String nicId) {
        Object object = null;
        if (nicId == null) {
            object = nodeService.getIpAddrList(nodeId);
        } else {
            int nic_id = Integer.parseInt(nicId);
            object = nodeService.getIpAddrList(nodeId, nic_id);
        }
        return object;
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
