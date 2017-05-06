package org.ship.core.manager.sys;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.ship.core.dao.sys.ManAddrDao;
import org.ship.core.service.sys.ISysService;
import org.ship.core.util.Subnet;
import org.ship.core.util.Utils;
import org.ship.core.vo.sys.ManAddr;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by wx on 2017/5/1.
 */
@Service
public class SysManager implements ISysService {
    private static final Logger log = LoggerFactory.getLogger(SysManager.class);
    @Autowired
    private ManAddrDao manAddrDao;

    @Override
    public ManAddr getManAddr() {
        return manAddrDao.getManAddr();
    }

    @Override
    public ManAddr modManAddr(ManAddr manAddr) throws Exception {
        Utils.verifyIp(manAddr.getIp());
        Utils.verifySubnet(manAddr.getIp(), manAddr.getMask());
        Subnet subnet = new Subnet(manAddr.getIp(), manAddr.getMask());
        if (!subnet.includeIp(manAddr.getIp())) {
            throw new Exception("ip is not valid");
        }
        String gateway = manAddr.getGateway();
        if (gateway != null && !gateway.isEmpty()) {
            if (!subnet.includeIp(gateway)) {
                throw new Exception("gateway is not valid");
            }
        }
        try {
            manAddrDao.modManAddr(manAddr);
        } catch (Exception e) {
            log.error("modify man addr error: {}", ExceptionUtils.getStackTrace(e));
        }
        return manAddrDao.getManAddr();
    }
}
