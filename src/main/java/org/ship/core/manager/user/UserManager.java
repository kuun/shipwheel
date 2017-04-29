package org.ship.core.manager.user;

import org.ship.core.dao.user.UserDao;
import org.ship.core.service.user.IUserService;
import org.ship.core.vo.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * Created by wx on 2017/4/29.
 */
@Service
public class UserManager implements IUserService{

    @Autowired
    private UserDao userDao;

    @Override
    public Collection<User> getUsers() {
        return userDao.getUsers();
    }
}
