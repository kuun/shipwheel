package org.ship.core.resource.user;

import org.ship.core.service.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wx on 2017/4/30.
 */
@RestController
@RequestMapping(value = "/ship/login")
public class LoginResource {
    @Autowired
    private IUserService userService;

    @RequestMapping(method = RequestMethod.POST)
    public Map<String, Boolean>  login(@RequestBody Map<String, String> obj) {
        Map<String, Boolean> map = new HashMap<>();
        boolean bool = userService.login(obj.get("name"), obj.get("password"));
        if (!bool) {
            map.put("success", false);
        }
        map.put("success", true);
        return map;
    }
}
