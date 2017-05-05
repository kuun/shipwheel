package org.ship.core.service.user;

import org.ship.core.util.Pagination;
import org.ship.core.vo.user.User;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Map;

/**
 * Created by wx on 2017/4/29.
 */
public interface IUserService {
    Pagination<User> getUsers(int page, int limit);
    User getUserByName(String name);
    Map<String, String> modUser(String name, String oldPwd, String newPwd) throws Exception;
    User createUser(User user);
    User login(String name, String password);
}
