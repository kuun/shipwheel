package org.ship.core.manager.node;

import org.ship.core.dao.node.IpAddrDao;
import org.ship.core.dao.node.NicDao;
import org.ship.core.service.node.INodeService;
import org.ship.core.vo.node.IpAddress;
import org.ship.core.vo.node.Nic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * Created by wx on 2017/4/29.
 */
@Service
public class NodeManager implements INodeService {

    @Autowired
    private NicDao nicDao;

    @Autowired
    private IpAddrDao ipAddrDao;

    @Override
    public Collection<Nic> getNicsByNodeId(int nodeId) {
        return nicDao.getNicsByNodeId(nodeId);
    }

    @Override
    public Nic getNic(int nicId) {
        return nicDao.getNic(nicId);
    }

    @Override
    public Collection<IpAddress> getIpAddrList(int nodeId) {
        return ipAddrDao.getIpAddrListByNodeId(nodeId);
    }

    @Override
    public Collection<IpAddress> getIpAddrList(int nodeId, int nicId) {
        return ipAddrDao.getIpAddrListByNodeIdAndNicId(nodeId, nicId);
    }

    @Override
    public IpAddress getIpAddr(int id) {
        return ipAddrDao.getIpAddr(id);
    }

    @Override
    public IpAddress createIpAddr(IpAddress ipAddress) {
        ipAddrDao.createIpAddr(ipAddress);
        return ipAddress;
    }

    @Override
    public IpAddress modIpAddr(IpAddress ipAddress) {
        ipAddrDao.modIpAddr(ipAddress);
        return  ipAddress;
    }

    @Override
    public void deleteIpAddr(int id) {
        ipAddrDao.deleteIpAddr(id);
    }
}
