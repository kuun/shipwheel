package org.ship.core.service.node;

import org.ship.core.util.Pagination;
import org.ship.core.vo.node.*;

import java.sql.SQLException;
import java.util.Collection;

/**
 * Created by wx on 2017/4/29.
 */
public interface INodeService {
    Iface getIface(int ifaceId);
    Collection<Iface> getIfacesByNodeId(int nodeId);

    Pagination<IpAddress> getIpAddrList(int nodeId, int page, int limit) throws SQLException;
    Collection<IpAddress> getIpAddrList(int noedId);
    IpAddress getIpAddr(int id);
    IpAddress createIpAddr(IpAddress ipaddr);
    IpAddress modIpAddr(IpAddress ipAddr);
    void deleteIpAddr(int id);

    Pagination<Route> getRoutes(int nodeId, int page, int limit) throws SQLException;
    Route getRoute(int id);
    Route createRoute(Route route);
    Route modRoute(Route route);
    void deleteRoute(int id);

    Dns getDns(int nodeId);
    Dns modDns(int nodeId, String dns);

    Pagination<ConnRule> getConnRules(int page, int limit) throws SQLException;
    ConnRule getConnRule(int id);
    ConnRule createConnRule(ConnRule rule);
    ConnRule modConnRule(ConnRule rule);
    void deleteConnRule(int id);
    void modConnStatus(boolean status, int id);
 }
