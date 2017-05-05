package org.ship.core.manager.node;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.ship.core.dao.node.*;
import org.ship.core.service.node.INodeService;
import org.ship.core.util.PageQuery;
import org.ship.core.util.Pagination;
import org.ship.core.util.Subnet;
import org.ship.core.util.Utils;
import org.ship.core.vo.node.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by wx on 2017/4/29.
 */
@Service
public class NodeManager implements INodeService {
    private static final Logger log = LoggerFactory.getLogger(NodeManager.class);
    private ReentrantLock lock = new ReentrantLock();

    @Autowired
    private IfaceDao ifaceDao;

    @Autowired
    private IpAddrDao ipAddrDao;

    @Autowired
    private RouteDao routeDao;

    @Autowired
    private DnsDao dnsDao;

    @Autowired
    private ConnRuleDao connRuleDao;

    @Override
    public Collection<Iface> getIfacesByNodeId(int nodeId) {
        return ifaceDao.getIfacesByNodeId(nodeId);
    }

    @Override
    public Iface getIface(int ifaceId) {
        return ifaceDao.getIface(ifaceId);
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
    public Map<String, String> createIpAddr(IpAddress ipAddress) throws Exception {
        Map<String, String> map = new HashMap<>();
        try {
            Iface iface = ifaceDao.getIface(ipAddress.getIface_id());
            if (iface == null) {
                map.put("flag", "1");
                map.put("msg", "未找到该网卡");
                return map;
            }
            IpAddress existIp = ipAddrDao.getIpAddrByNodeIdAndIp(ipAddress.getNode_id(), ipAddress.getIp());
            if (existIp != null) {
                map.put("flag", "2");
                map.put("msg", "IP地址已存在");
                return map;
            }
            verifyIpAddress(ipAddress);
            ipAddrDao.createIpAddr(ipAddress);
            map.put("flag", "0");
            map.put("msg", "添加成功");
        } catch (Exception e) {
            log.error("create ipAddr error: {}", ExceptionUtils.getStackTrace(e));
            throw new Exception("添加异常");
        }

        return map;
    }

    @Override
    public Map<String, String> modIpAddr(IpAddress ipAddress) throws Exception {
        Map<String, String> map = new HashMap<>();
        try {
            IpAddress old_ipAddr = ipAddrDao.getIpAddr(ipAddress.getId());
            if (old_ipAddr == null) {
                map.put("flag", "1");
                map.put("msg", "未找到该IP");
                return map;
            }
            IpAddress existIp = ipAddrDao.getIpAddrByNodeIdAndIp(ipAddress.getNode_id(), ipAddress.getIp());
            if (existIp != null) {
                map.put("flag", "2");
                map.put("msg", "IP地址已存在");
                return map;
            }
            Collection<ConnRule> connRules = connRuleDao.getConnRuleByIpAddrId(ipAddress.getId());
            for (ConnRule cr: connRules) {
                if (cr.isStatus()) {
                    map.put("flag", "3");
                    map.put("msg", "IP地址正在被规则使用，请先停用");
                    return map;
                }
            }
            verifyIpAddress(ipAddress);
            ipAddrDao.modIpAddr(ipAddress);
            map.put("flag", "0");
            map.put("msg", "添加成功");
        } catch (Exception e) {
            log.error("mod ipAddress error: {}", ExceptionUtils.getStackTrace(e));
            throw new Exception("修改异常");
        }
        return map;
    }

    @Override
    public Map<String, String> deleteIpAddr(int id) throws Exception {
        Map<String, String> map = new HashMap<>();
        try {
            lock.lock();
            IpAddress ipAddress = ipAddrDao.getIpAddr(id);
            if (ipAddress == null) {
                map.put("flag", "1");
                map.put("msg", "未找到该IP");
                return map;
            }
            Collection<IpAddress> ips = ipAddrDao.getIpAddrByIfaceId(ipAddress.getIface_id());
            if (ips.isEmpty()) {
                routeDao.delRouteOnIface(ipAddress.getIface_id());
            }
            ipAddrDao.deleteIpAddr(id);
            map.put("flag", "0");
            map.put("msg", "删除成功");
        } catch (Exception e) {
            log.error("del ipAddr error: {}", ExceptionUtils.getStackTrace(e));
            throw new Exception("删除异常");
        } finally {
            lock.unlock();
        }
        return map;
    }

    @Override
    public Pagination<Route> getRoutes(int nodeId, int page, int limit) throws SQLException {
        Pagination<Route> pg = null;
        try {
            pg = PageQuery.query(Route.class, page, limit, () -> routeDao.getCount(nodeId),
                    (offset) ->routeDao.getRoutes(nodeId, limit, offset));
        } catch (Exception e) {
            log.error("error: {}", ExceptionUtils.getStackTrace(e));
        }
        return pg;
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
    public Pagination<ConnRule> getConnRules(int page, int limit) throws SQLException {
        Pagination<ConnRule> pg = null;
        try {
            pg = PageQuery.query(ConnRule.class, page, limit, () -> connRuleDao.getCount(),
                    (offset) -> connRuleDao.getConnRules(limit, offset));
        } catch (Exception e) {
            log.error("error: {}", ExceptionUtils.getStackTrace(e));
            throw e;
        }
        return pg;
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

    private void verifyIpAddress(IpAddress ipAddress) throws Exception {
        Utils.verifyIp(ipAddress.getIp());
        Utils.verifySubnet(ipAddress.getIp(), ipAddress.getMask());
        Subnet subnet = new Subnet(ipAddress.getIp(), ipAddress.getMask());
        if (!subnet.includeIp(ipAddress.getIp())) {
            throw new Exception("invalid ip");
        }
        Collection<IpAddress> addrs = ipAddrDao.getIpAddrListByNodeId(ipAddress.getNode_id());
        long count = addrs.stream()
                .filter(ipAddr -> ipAddr.getIface_id() != ipAddress.getIface_id())
                .filter(ipAddr -> {
                    try {
                        Subnet testSubnet = new Subnet(ipAddr.getIp(), ipAddr.getMask());
                        return subnet.intersect(testSubnet);
                    } catch (Exception e) {
                        log.info("error: {}", ExceptionUtils.getStackTrace(e));
                    }
                    return false;
                })
                .count();
        if (count > 0) {
            throw new Exception("subnet is conflicted with other subnet");
        }
    }
}
