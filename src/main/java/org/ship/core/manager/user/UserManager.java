package org.ship.core.manager.user;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.ship.core.dao.user.UserDao;
import org.ship.core.service.user.IUserService;
import org.ship.core.util.PageQuery;
import org.ship.core.util.Pagination;
import org.ship.core.vo.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wx on 2017/4/29.
 */
@Service
public class UserManager implements IUserService {
    private static final Logger log = LoggerFactory.getLogger(UserManager.class);

    @Autowired
    private UserDao userDao;

    @Override
    public Pagination<User> getUsers(int page, int limit) {
        Pagination<User> pg = null;
        try {
            pg = PageQuery.query(User.class, page, limit, () -> userDao.getCount(),
                    (offset) -> userDao.getUsers(offset, limit));
        } catch (Exception e) {
            log.error("error: {}", ExceptionUtils.getStackTrace(e));
        }
        return pg;
    }

    @Override
    public User getUserByName(String name) {
        return userDao.getUserByName(name);
    }

    @Override
    public Map<String, String> modUser(String name, String oldPwd, String newPwd) throws Exception {
        Map<String, String> map = new HashMap<>();
        try {
            User user = userDao.getUserByNameAndPwd(name, oldPwd);
            log.debug("user: {}", user);
            if (user == null) {
                map.put("flag", "1");
                map.put("msg", "用户名或密码不正确");
                return map;
            }
            userDao.modUser(name, newPwd);
            map.put("flag", "0");
            map.put("msg", "修改成功");
        } catch (Exception e) {
            log.error("mod password error: {}", ExceptionUtils.getStackTrace(e));
            throw new Exception("修改异常");
        }
        return map;
    }

    @Override
    public User createUser(User user) {
        userDao.createUser(user);
        return user;
    }

    @Override
    public User login(String name, String password) {
        return userDao.getUserByNameAndPwd(name, password);
    }
}
