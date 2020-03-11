package org.ship.core.service.engine;

import org.ship.core.util.Pagination;
import org.ship.core.vo.engine.*;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Map;

/**
 * Created by wx on 2017/4/29.
 */
public interface IEngineService {
    Iface getIface(int ifaceId);
    Collection<Iface> getIfacesByNodeId(int nodeId);

    Pagination<IpAddress> getIpAddrList(int nodeId, int page, int limit) throws SQLException;
    Collection<IpAddress> getIpAddrList(int noedId);
    IpAddress getIpAddr(int id);
    Map<String, String> createIpAddr(IpAddress ipaddr) throws Exception;
    Map<String, String> modIpAddr(IpAddress ipAddr) throws Exception;
    Map<String, String> deleteIpAddr(int id) throws Exception;

    Pagination<Route> getRoutes(int nodeId, int page, int limit) throws SQLException;
    Route getRoute(int id);
    Map<String, String> createRoute(Route route) throws Exception;
    Map<String, String> modRoute(Route route) throws Exception;
    Map<String, String> deleteRoute(int id) throws Exception;

    Dns getDns(int nodeId);
    Map<String, String> modDns(int nodeId, String dns) throws Exception;

    Pagination<ConnRule> getConnRules(int page, int limit) throws SQLException;
    ConnRule getConnRule(int id);
    Map<String, String> createConnRule(ConnRule rule) throws Exception;
    Map<String, String> modConnRule(ConnRule rule) throws Exception;
    Map<String, String> deleteConnRule(int id) throws Exception;
    Map<String, String> modConnStatus(boolean status, int id) throws Exception;
 }
