package org.ship.core.user.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by wx on 2017/4/28.
 */
@RestController
public class UserResource {

    @RequestMapping("user")
    public String getUser() {
        return "hello world";
    }
}
