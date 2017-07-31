package org.ship.core.resource.user;

import org.ship.core.service.user.IUserService;
import org.ship.core.util.Pagination;
import org.ship.core.vo.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Map;

/**
 * Created by wx on 2017/4/28.
 */
@RestController
@RequestMapping(value = "/ship/user")
public class UserResource {
    private final Logger logger = LoggerFactory.getLogger(UserResource.class);

    @Autowired
    private IUserService userService;

    @RequestMapping(value = "/userList", method = RequestMethod.POST)
    public Pagination<User> getUsers(@RequestBody Map<String, String> obj) {
        int page = Integer.parseInt(obj.get("page"));
        int limit = Integer.parseInt(obj.get("limit"));
        return userService.getUsers(page, limit);
    }

    @RequestMapping(method = RequestMethod.GET)
    public Object getUser(@RequestParam(value = "name", required = false) String name) throws SQLException {
        return userService.getUserByName(name);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public Object modUser(@RequestBody Map<String, String> obj) throws Exception{
        Object object = null;
        String name = obj.get("name");
        String oldPwd = obj.get("old_pwd");
        String newPwd = obj.get("new_pwd");
        object = userService.modUser(name, oldPwd, newPwd);
        return object;
    }

    @RequestMapping(method = RequestMethod.POST)
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }
}
