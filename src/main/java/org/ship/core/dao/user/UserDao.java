package org.ship.core.dao.user;

import org.apache.ibatis.annotations.*;
import org.omg.PortableInterceptor.USER_EXCEPTION;
import org.ship.core.vo.user.User;

import java.util.Collection;

/**
 * Created by wx on 2017/4/29.
 */
public interface UserDao {
    @Select("select count(*) from ship_user")
    int getCount();

    @Select("SELECT * FROM ship_user order by id limit #{limit} offset #{offset}")
    Collection<User> getUsers(@Param("offset") int offset,
                              @Param("limit") int limit);

    @Select("SELECT * FROM ship_user WHERE name = #{name}")
    User getUserByName(@Param("name") String name);

    @Select("SELECT * FROM ship_user WHERE name = #{name} AND password = #{password}")
    User getUserByNameAndPwd(@Param("name") String name, @Param("password") String password);

    @Insert("INSERT INTO ship_user (name, password, is_admin) VALUES(#{name}, #{password}, #{is_admin})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int createUser(User user);

    @Update("UPDATE ship_user SET password = #{password} WHERE name = #{name}")
    void modUser(@Param("name") String name, @Param("password") String password);

}
