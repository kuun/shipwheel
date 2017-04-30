package org.ship.core.resource.node;

import org.ship.core.service.node.INodeService;
import org.ship.core.vo.node.Nic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

/**
 * Created by wx on 2017/4/29.
 */
@RestController
@RequestMapping(value = "/ship/node/nic")
public class NicResource {
    @Autowired
    private INodeService nodeService;

    @RequestMapping(method = RequestMethod.GET)
    public Object getNics(@RequestParam(value = "nodeId", required = false) String nodeId,
                          @RequestParam(value = "nicId", required = false) String nicId) {
        Object object = null;
        if (nicId != null) {
            int nic_id = Integer.parseInt(nicId);
            object = nodeService.getNic(nic_id);
        }
        if (nodeId != null) {
            int node_id = Integer.parseInt(nodeId);
            object = nodeService.getNicsByNodeId(node_id);
        }
        return object;
    }
}
