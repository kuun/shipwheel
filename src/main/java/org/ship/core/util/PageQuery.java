package org.ship.core.util;

import java.util.Collection;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Created by wx on 2017/5/2.
 */
public class PageQuery {

    /**
     * page query
     * @param resultType result data type of the query
     * @param page the current page of query
     * @param limit the limit of one page
     * @param totalCount a callback to get total count of the query
     * @param getPage a callback to get the current page of the query
     * @return one page of the query result
     */

    public static <T> Pagination<T> query(Class<T>resultType,
                                          int page, int limit,
                                          Supplier<Integer> totalCount,
                                          Function<Integer, Collection<T>> getPage) {
        Pagination<T> list = new Pagination<T>();
        int total = totalCount.get();
        list.setTotal(total);
        int offset = (page - 1) * limit;
        if (offset >= total) {
            offset = total - limit;
        }
        if (offset < 0) {
            offset = 0;
        }
        int pageCount = (int)Math.ceil(total * 1.0 / limit);
        if (page > pageCount) {
            page = pageCount;
        }
        Collection<T> data = getPage.apply(offset);
        list.setData(data);
        list.setPageCount(pageCount);
        list.setCurPage(page);
        list.setLimit(limit);
        return list;
    }
}
