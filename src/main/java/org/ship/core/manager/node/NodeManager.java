package org.ship.core.manager.node;

import org.ship.core.dao.node.NicDao;
import org.ship.core.service.node.INodeService;
import org.ship.core.vo.node.Nic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * Created by wx on 2017/4/29.
 */
@Service
public class NodeManager implements INodeService{

    @Autowired
    private NicDao nicDao;

    @Override
    public Collection<Nic> getNicsByNodeId(int nodeId) {
        return nicDao.getNicsByNodeId(nodeId);
    }

    @Override
    public Nic getNic(int nicId) {
        return nicDao.getNic(nicId);
    }
}
