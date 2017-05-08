package org.ship.core.manager.node;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.dataship.rpc.Rpc;
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
    private static Map<Integer, Node> nodes = new HashMap<>();
    private NodeClient client = null;
    private static final int RPC_PORT = 6001;

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

    @Autowired
    private NodeDao nodeDao;

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
            Node node = getNode(ipAddress.getNode_id());
            initClient(node);
            Rpc.OpResult result = client.addAddr(ipAddress);
            if (result.getCodeValue() != 0) {
                log.error("create ip addr error: {}", ipAddress);
                throw new Exception("send create ip addr to sever error");
            }
            ipAddrDao.createIpAddr(ipAddress);
            map.put("flag", "0");
            map.put("msg", "添加成功");
        } catch (Exception e) {
            log.error("create ipAddr error: {}", ExceptionUtils.getStackTrace(e));
            throw new Exception("添加异常");
        } finally {
            if (client != null) client.shutdown();
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
            Node node = getNode(ipAddress.getNode_id());
            initClient(node);
            Rpc.OpResult result = client.modAddr(old_ipAddr, ipAddress);
            if (result.getCodeValue() != 0) {
                log.error("old_addr: {}, new_addr: {}", old_ipAddr, ipAddress);
                throw new Exception("send mod msg to server error");
            }
            ipAddrDao.modIpAddr(ipAddress);
            map.put("flag", "0");
            map.put("msg", "添加成功");
        } catch (Exception e) {
            log.error("mod ipAddress error: {}", ExceptionUtils.getStackTrace(e));
            throw new Exception("修改异常");
        } finally {
            if (client != null) client.shutdown();
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
            /*Collection<IpAddress> ips = ipAddrDao.getIpAddrByIfaceId(ipAddress.getIface_id());
            if (ips.isEmpty()) {
                routeDao.delRouteOnIface(ipAddress.getIface_id());
            }*/
            Node node = getNode(ipAddress.getNode_id());
            initClient(node);
            Rpc.OpResult result = client.delAddr(ipAddress);
            if (result.getCodeValue() != 0) {
                log.error("del addr error: {}", ipAddress);
                throw new Exception("send del addr msg to server error");
            }
            ipAddrDao.deleteIpAddr(id);
            map.put("flag", "0");
            map.put("msg", "删除成功");
        } catch (Exception e) {
            log.error("del ipAddr error: {}", ExceptionUtils.getStackTrace(e));
            throw new Exception("删除异常");
        } finally {
            if (client != null) client.shutdown();
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
    public Map<String, String> createRoute(Route route) throws Exception {
        Map<String, String> map = new HashMap<>();
        try {
            Iface iface = ifaceDao.getIface(route.getIface_id());
            if (iface == null) {
                map.put("flag", "1");
                map.put("msg", "未找到该网卡");
                return map;
            }
            Route existRoute = routeDao.findExistRoute(route.getNode_id(), route.getGateway());
            if (existRoute != null) {
                map.put("flag", "2");
                map.put("msg", "路由已存在");
                return map;
            }
            routeDao.createRoute(route);
            map.put("flag", "0");
            map.put("msg", "添加成功");
        } catch (Exception e) {
            log.error("create route error: {}", ExceptionUtils.getStackTrace(e));
            throw new Exception("添加异常");
        }
        return map;
    }

    @Override
    public Map<String, String> modRoute(Route route) throws Exception {
        Map<String, String> map = new HashMap<>();
        try {
            Route old_route = routeDao.getRoute(route.getId());
            if (old_route == null) {
                map.put("flag", "1");
                map.put("msg", "未找到该路由");
                return map;
            }
            routeDao.modRoute(route.getGateway(), route.getId());
            map.put("flag", "0");
            map.put("msg", "添加成功");
        } catch (Exception e) {
            log.error("modify route error: {}", ExceptionUtils.getStackTrace(e));
            throw new Exception("修改异常");
        }
        return map;
    }

    @Override
    public Map<String, String> deleteRoute(int id) throws Exception {
        Map<String, String> map = new HashMap<>();
        try {
            Route old_route = routeDao.getRoute(id);
            if (old_route == null) {
                map.put("flag", "1");
                map.put("msg", "未找到该路由");
                return map;
            }
            routeDao.deleteRoute(id);
            map.put("flag", "0");
            map.put("msg", "删除成功");
        } catch (Exception e) {
            log.error("del route error: {}", ExceptionUtils.getStackTrace(e));
            throw new Exception("删除异常");
        }
        return map;
    }

    @Override
    public Dns getDns(int nodeId) {
        return dnsDao.getDns(nodeId);
    }

    @Override
    public Map<String, String> modDns(int nodeId, String dns) throws Exception {
        Map<String, String> map = new HashMap<>();
        try {
            Utils.verifyIp(dns);
            dnsDao.modDns(nodeId, dns);
            map.put("flag", "0");
            map.put("msg", "修改成功");
        } catch (Exception e) {
            log.error("modidy dns error: {}", ExceptionUtils.getStackTrace(e));
            throw new Exception("修改异常");
        }
        return map;
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
    public Map<String, String> createConnRule(ConnRule rule) throws Exception {
        Map<String, String> map = new HashMap<>();
        try {
            Collection<Integer> connRuleIds = connRuleDao.getIdByTcp(rule.getListen_port(), rule.getListen_addr().getId());
            if (connRuleIds.size() > 0) {
                map.put("flag", "1");
                map.put("msg", "端口被占用");
                return map;
            }
            connRuleDao.addConnRule(rule);
            map.put("flag", "0");
            map.put("msg", "添加成功");
        } catch (Exception e) {
            log.error("create connRule error: {}", ExceptionUtils.getStackTrace(e));
            throw new Exception("添加异常");
        }
        return map;
    }

    @Override
    public Map<String, String> modConnRule(ConnRule rule) throws Exception {
        Map<String, String> map = new HashMap<>();
        try {
            ConnRule old_connRule = connRuleDao.getConnRule(rule.getId());
            if (old_connRule == null) {
                map.put("flag", "1");
                map.put("msg", "规则不存在");
                return map;
            }
            if (old_connRule.isStatus()) {
                map.put("flag", "2");
                map.put("msg", "规则正在启用不能修改");
                return map;
            }
            Collection<Integer> connRuleIds = connRuleDao.getIdByTcp(rule.getListen_port(), rule.getListen_addr().getId());
            if (connRuleIds.size() > 0) {
                map.put("flag", "3");
                map.put("msg", "端口被占用");
                return map;
            }
            connRuleDao.modConnRule(rule);
            map.put("flag", "0");
            map.put("msg", "修改成功");
        } catch (Exception e) {
            log.error("modify connRule error: {}", ExceptionUtils.getStackTrace(e));
            throw new Exception("修改异常");
        }
        return map;
    }

    @Override
    public Map<String, String> deleteConnRule(int id) throws Exception {
        Map<String, String> map = new HashMap<>();
        NodeClient client = null;
        try {
            ConnRule connRule = connRuleDao.getConnRule(id);
            if (connRule == null) {
                map.put("flag", "1");
                map.put("msg", "规则不存在");
                return map;
            }
            if (connRule.isStatus()) {
                map.put("flag", "2");
                map.put("msg", "规则正在启用不能删除");
                return map;
            }
            Node node = getNode(connRule.getDirect());
            client = new NodeClient(node.getIp(), RPC_PORT);
            Rpc.OpResult result = client.delRule(connRule);
            if (result.getCodeValue() != 0) {
                log.error("connRule: {}", connRule);
                throw new Exception("send del connRule to server error");
            }
            connRuleDao.delConnRule(id);
            map.put("flag", "0");
            map.put("msg", "删除成功");
        } catch (Exception e) {
            log.error("del connRule error: {}", ExceptionUtils.getStackTrace(e));
            throw new Exception("删除异常");
        } finally {
            if (client != null) client.shutdown();
        }
        return map;
    }

    @Override
    public Map<String, String> modConnStatus( boolean status, int id) throws Exception {
        Map<String, String> map = new HashMap<>();
        NodeClient client = null;
        try {
            ConnRule connRule = connRuleDao.getConnRule(id);
            if (connRule.isStatus() == status) {
                map.put("flag", "1");
                map.put("msg", "规则正在启用中");
                return map;
            }
            connRule.setStatus(status);
            Node node = getNode(connRule.getDirect());
            client = new NodeClient(node.getIp(), RPC_PORT);
            Rpc.OpResult result = client.addRule(connRule);
            if (result.getCodeValue() != 0) {
                log.error("connRule: {}", connRule);
                throw new Exception("send connRule status to server error");
            }
            connRuleDao.modConnRule(connRule);
            map.put("flag", "0");
            map.put("msg", "提交成功");
        } catch (Exception e) {
            log.error("mod conn status error: {}", ExceptionUtils.getStackTrace(e));
        } finally {
            if (client != null) client.shutdown();
        }
        return map;
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

    private void loadNodes() {
        Collection<Node> nodeList = nodeDao.getNodes();
        for (Node node: nodeList) {
            log.debug("load node: {}", node);
            nodes.put(node.getId(), node);
        }
    }

    private Node getNode(int id) {
        loadNodes();
        return nodes.get(id);
    }

    private void initClient(Node node) {
        client = new NodeClient(node.getIp(), RPC_PORT);
    }
}
