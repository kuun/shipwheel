package org.ship.core.resource.engine;

import org.ship.core.service.engine.IEngineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by wx on 2017/4/29.
 */
@RestController
@RequestMapping(value = "/ship/node/iface")
public class IfaceResource {
    @Autowired
    private IEngineService nodeService;

    @RequestMapping(method = RequestMethod.GET)
    public Object getIfaces(@RequestParam(value = "nodeId", required = false) String nodeId,
                          @RequestParam(value = "ifaceId", required = false) String ifaceId) {
        Object object = null;
        if (ifaceId != null) {
            int iface_id = Integer.parseInt(ifaceId);
            object = nodeService.getIface(iface_id);
        }
        if (nodeId != null) {
            int node_id = Integer.parseInt(nodeId);
            object = nodeService.getIfacesByNodeId(node_id);
        }
        return object;
    }
}
