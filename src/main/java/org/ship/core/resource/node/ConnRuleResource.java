package org.ship.core.resource.node;

import org.ship.core.service.node.INodeService;
import org.ship.core.util.Pagination;
import org.ship.core.vo.node.ConnRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Map;

/**
 * Created by wx on 2017/5/1.
 */
@RestController
@RequestMapping(value = "/ship/node")
public class ConnRuleResource {
    @Autowired
    private INodeService nodeService;

    @RequestMapping(value = "/connRuleList", method = RequestMethod.POST)
    public Pagination<ConnRule> getConnRules(@RequestBody Map<String, String> obj) throws SQLException {
        int page = Integer.parseInt(obj.get("page"));
        int limit = Integer.parseInt(obj.get("limit"));
        return nodeService.getConnRules(page, limit);
    }

    @RequestMapping(value = "/connRule", method = RequestMethod.GET)
    public ConnRule getConnRule(@RequestParam("id") int id) {
        return nodeService.getConnRule(id);
    }

    @RequestMapping(value = "/connRule", method = RequestMethod.POST)
    public ConnRule createConnRule(@RequestBody ConnRule rule) {
        return nodeService.createConnRule(rule);
    }

    @RequestMapping(value = "/connRule", method = RequestMethod.PUT)
    public ConnRule modConnRule(@RequestBody ConnRule rule) {
        return nodeService.modConnRule(rule);
    }

    @RequestMapping(value = "/connRule", method = RequestMethod.DELETE)
    public void deleteConnRule(@RequestParam("id") int id) {
        nodeService.deleteConnRule(id);
    }

    @RequestMapping(value = "/connRule/{id}/status", method = RequestMethod.PUT)
    public void modConnStatus(@PathVariable("id") int id, @RequestParam("status") boolean status) {
        nodeService.modConnStatus(status, id);
    }
}
