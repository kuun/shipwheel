package org.ship.core.resource.node;

import org.ship.core.service.node.INodeService;
import org.ship.core.vo.node.Dns;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Created by wx on 2017/5/1.
 */
@RestController
@RequestMapping(value = "/ship/node/dns")
public class DnsResource {
    @Autowired
    private INodeService nodeService;

    @RequestMapping(method = RequestMethod.GET)
    public Dns getDns(@RequestParam("nodeId") int nodeId) {
        return nodeService.getDns(nodeId);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public Object modDns(@RequestBody Map<String, String> obj) throws Exception {
        Object object = null;
        int nodeId = Integer.parseInt(obj.get("node_id"));
        object = nodeService.modDns(nodeId, obj.get("dns"));
        return object;
    }
}
