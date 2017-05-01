package org.ship.core.manager.sys;

import org.ship.core.dao.sys.ManAddrDao;
import org.ship.core.service.sys.ISysService;
import org.ship.core.vo.sys.ManAddr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by wx on 2017/5/1.
 */
@Service
public class SysManager implements ISysService {
    @Autowired
    private ManAddrDao manAddrDao;

    @Override
    public ManAddr getManAddr() {
        return manAddrDao.getManAddr();
    }

    @Override
    public ManAddr modManAddr(ManAddr manAddr) {
        manAddrDao.modManAddr(manAddr);
        return manAddrDao.getManAddr();
    }
}
