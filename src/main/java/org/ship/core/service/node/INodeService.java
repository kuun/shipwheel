package org.ship.core.service.node;

import org.ship.core.vo.node.IpAddress;
import org.ship.core.vo.node.Nic;

import java.util.Collection;

/**
 * Created by wx on 2017/4/29.
 */
public interface INodeService {
    Nic getNic(int nicId);
    Collection<Nic> getNicsByNodeId(int nodeId);
    Collection<IpAddress> getIpAddrList(int nodeId);
    Collection<IpAddress> getIpAddrList(int noedId, int nicId);
    IpAddress getIpAddr(int id);
    IpAddress createIpAddr(IpAddress ipaddr);
    IpAddress modIpAddr(IpAddress ipAddr);
    void deleteIpAddr(int id);
 }
