package org.ship.core.service.node;

import org.ship.core.vo.node.Nic;

import java.util.Collection;

/**
 * Created by wx on 2017/4/29.
 */
public interface INodeService {
    Nic getNic(int nicId);
    Collection<Nic> getNicsByNodeId(int nodeId);
}
