package org.ship.core.manager.node;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.ship.core.dao.node.*;
import org.ship.core.service.node.INodeService;
import org.ship.core.util.PageQuery;
import org.ship.core.util.Pagination;
import org.ship.core.vo.node.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.Collection;

/**
 * Created by wx on 2017/4/29.
 */
@Service
public class NodeManager implements INodeService {
    private static final Logger log = LoggerFactory.getLogger(NodeManager.class);

    @Autowired
    private NicDao nicDao;

    @Autowired
    private IpAddrDao ipAddrDao;

    @Autowired
    private RouteDao routeDao;

    @Autowired
    private DnsDao dnsDao;

    @Autowired
    private ConnRuleDao connRuleDao;

    @Override
    public Collection<Nic> getNicsByNodeId(int nodeId) {
        return nicDao.getNicsByNodeId(nodeId);
    }

    @Override
    public Nic getNic(int nicId) {
        return nicDao.getNic(nicId);
    }

    @Override
    public Pagination<IpAddress> getIpAddrList(int nodeId, int page, int limit) throws SQLException {
        Pagination<IpAddress> pg = null;
        try {
            pg = PageQuery.query(IpAddress.class, page, limit, () -> ipAddrDao.getCount(nodeId),
                    (offset) -> ipAddrDao.getIpAddrListByPage(nodeId,limit, offset));
        } catch (Exception e) {
            log.error("error: {}", ExceptionUtils.getStackTrace(e));
        }
        return pg;
    }

    @Override
    public Collection<IpAddress> getIpAddrList(int nodeId) {
        return ipAddrDao.getIpAddrListByNodeId(nodeId);
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

    @Override
    public Collection<Route> getRoutes(int nodeId) {
        return routeDao.getRoutes(nodeId);
    }

    @Override
    public Route getRoute(int id) {
        return routeDao.getRoute(id);
    }

    @Override
    public Route createRoute(Route route) {
        routeDao.createRoute(route);
        return route;
    }

    @Override
    public Route modRoute(Route route) {
        routeDao.modRoute(route.getGateway(), route.getId());
        return route;
    }

    @Override
    public void deleteRoute(int id) {
        routeDao.deleteRoute(id);
    }

    @Override
    public Dns getDns(int nodeId) {
        return dnsDao.getDns(nodeId);
    }

    @Override
    public Dns modDns(int nodeId, String dns) {
        dnsDao.modDns(nodeId, dns);
        Dns newDns = new Dns();
        newDns.setDns(dns);
        return newDns;
    }

    @Override
    public Collection<ConnRule> getConnRules() {
        return connRuleDao.getConnRules();
    }

    @Override
    public ConnRule getConnRule(int id) {
        return connRuleDao.getConnRule(id);
    }

    @Override
    public ConnRule createConnRule(ConnRule rule) {
        connRuleDao.addConnRule(rule);
        return rule;
    }

    @Override
    public ConnRule modConnRule(ConnRule rule) {
        connRuleDao.modConnRule(rule);
        return rule;
    }

    @Override
    public void deleteConnRule(int id) {
        connRuleDao.delConnRule(id);
    }

    @Override
    public void modConnStatus( boolean status, int id) {
        ConnRule connRule = connRuleDao.getConnRule(id);
        if (connRule.isStatus() == status) return;
        connRule.setStatus(status);
        connRuleDao.modConnRule(connRule);
    }
}
