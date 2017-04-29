package org.ship.core.dao.user;

import org.apache.ibatis.annotations.Select;
import org.ship.core.vo.user.User;

import java.util.Collection;

/**
 * Created by wx on 2017/4/29.
 */
public interface UserDao {
    @Select("SELECT id, name, is_admin FROM ship_user")
    Collection<User> getUsers();
}
