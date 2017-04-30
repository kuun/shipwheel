package org.ship.core.dao.user;

import org.apache.ibatis.annotations.*;
import org.omg.PortableInterceptor.USER_EXCEPTION;
import org.ship.core.vo.user.User;

import java.util.Collection;

/**
 * Created by wx on 2017/4/29.
 */
public interface UserDao {
    @Select("SELECT * FROM ship_user")
    Collection<User> getUsers();

    @Select("SELECT * FROM ship_user WHERE name = #{name}")
    User getUserByName(@Param("name") String name);

    @Select("SELECT * FROM ship_user WHERE name = #{name} AND password = #{password}")
    User getUserByNameAndPwd(@Param("name") String name, @Param("password") String password);

    @Insert("insert into ship_user (name, password, is_admin) values(#{name}, #{password}, #{is_admin})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int createUser(User user);

    @Update("update ship_user set password = #{password} where name = #{name}")
    void modUser(@Param("name") String name, @Param("password") String password);

}
