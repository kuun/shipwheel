package org.ship.core.resource.user;

import org.ship.core.service.user.IUserService;
import org.ship.core.vo.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.Collection;

/**
 * Created by wx on 2017/4/28.
 */
@RestController
@RequestMapping(value = "/ship/user/admin")
public class UserResource {
    private final Logger logger = LoggerFactory.getLogger(UserResource.class);

    @Autowired
    private IUserService userService;

    @RequestMapping(method = RequestMethod.GET)
    public Collection<User> getUser() throws SQLException {
        return userService.getUsers();
    }

    @RequestMapping(method = RequestMethod.PUT)
    public void modUser(HttpServletRequest request) {

    }
}
