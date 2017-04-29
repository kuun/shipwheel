package org.ship.core.manager.user;

import org.apache.commons.lang3.StringUtils;
import org.ship.core.dao.user.UserDao;
import org.ship.core.service.user.IUserService;
import org.ship.core.vo.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * Created by wx on 2017/4/29.
 */
@Service
public class UserManager implements IUserService{
    private static final Logger log = LoggerFactory.getLogger(UserManager.class);

    @Autowired
    private UserDao userDao;

    @Override
    public Collection<User> getUsers() {
        return userDao.getUsers();
    }

    @Override
    public User getUserByName(String name) {
        return userDao.getUserByName(name);
    }

    @Override
    public void modUser(String name, String oldPwd, String newPwd) throws Exception {
        User user = userDao.getUser(name, oldPwd);
        log.debug("user: {}", user);
        if (user == null) {
            throw new Exception("user name or password is invalid");
        }
        userDao.modUser(name, newPwd);
    }

    @Override
    public User createUser(User user)  {
        userDao.createUser(user);
        return user;
    }

}
