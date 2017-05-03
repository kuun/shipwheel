package org.ship.core.service.node;

import org.ship.core.util.Pagination;
import org.ship.core.vo.node.*;

import java.sql.SQLException;
import java.util.Collection;

/**
 * Created by wx on 2017/4/29.
 */
public interface INodeService {
    Nic getNic(int nicId);
    Collection<Nic> getNicsByNodeId(int nodeId);

    Pagination<IpAddress> getIpAddrList(int nodeId, int page, int limit) throws SQLException;
    Collection<IpAddress> getIpAddrList(int noedId);
    IpAddress getIpAddr(int id);
    IpAddress createIpAddr(IpAddress ipaddr);
    IpAddress modIpAddr(IpAddress ipAddr);
    void deleteIpAddr(int id);

    Collection<Route> getRoutes(int nodeId);
    Route getRoute(int id);
    Route createRoute(Route route);
    Route modRoute(Route route);
    void deleteRoute(int id);

    Dns getDns(int nodeId);
    Dns modDns(int nodeId, String dns);

    Collection<ConnRule> getConnRules();
    ConnRule getConnRule(int id);
    ConnRule createConnRule(ConnRule rule);
    ConnRule modConnRule(ConnRule rule);
    void deleteConnRule(int id);
    void modConnStatus(boolean status, int id);
 }
