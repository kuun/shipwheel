package org.ship.core.dao.sys;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.ship.core.vo.sys.ManAddr;

/**
 * Created by wx on 2017/5/1.
 */
public interface ManAddrDao {
    @Select("SELECT * FROM ship_man_addr")
    ManAddr getManAddr();

    @Update("UPDATE ship_man_addr SET ip = #{ip}, mask = #{mask}, gateway = #{gateway} WHERE id = #{id}")
    void modManAddr(ManAddr manAddr);
}
