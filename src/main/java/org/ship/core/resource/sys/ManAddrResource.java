package org.ship.core.resource.sys;

import org.ship.core.service.sys.ISysService;
import org.ship.core.vo.sys.ManAddr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by wx on 2017/5/1.
 */
@RestController
@RequestMapping(value = "/ship/sys/manAddr")
public class ManAddrResource {
    @Autowired
    private ISysService sysService;

    @RequestMapping(method = RequestMethod.GET)
    public ManAddr getManAddr() {
        return sysService.getManAddr();
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ManAddr modManAddr(@RequestBody ManAddr manAddr) {
        return sysService.modManAddr(manAddr);
    }
}
