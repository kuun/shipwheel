package org.ship.core.service.sys;

import org.ship.core.vo.sys.ManAddr;

/**
 * Created by wx on 2017/5/1.
 */
public interface ISysService {
    ManAddr getManAddr();
    ManAddr modManAddr(ManAddr manAddr);
}
