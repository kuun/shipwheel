package org.ship.core.resource.user;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.ship.core.manager.user.UserManager;
import org.ship.core.service.user.IUserService;
import org.ship.core.vo.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.net.HttpCookie;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wx on 2017/4/30.
 */
@RestController
@RequestMapping(value = "/ship")
public class LoginResource {
    private static final Logger log = LoggerFactory.getLogger(LoginResource.class);
    @Autowired
    private IUserService userService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Map<String, String> login(@RequestBody Map<String, String> obj, HttpSession session) throws Exception {
        Map<String, String> map = new HashMap<>();
        try {
            User user = userService.login(obj.get("name"), obj.get("password"));
            if (user == null) {
                map.put("flag", "1");
                map.put("msg", "用户名或密码不正确");
                return map;
            }
            map.put("flag", "0");
        } catch (Exception e) {
            log.error("login error: {}", ExceptionUtils.getStackTrace(e));
        }
        return map;
    }
}
