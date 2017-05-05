package cn.binux.search.service.hystrix;

import cn.binux.pojo.SearchResult;
import cn.binux.pojo.XbinResult;
import cn.binux.search.service.SearchService;
import org.springframework.stereotype.Component;

/**
 * 搜索服务 熔断处理
 *
 * @author xubin.
 * @create 2017-05-03 下午9:19
 */

@Component
public class SearchServiceHystrix implements SearchService {


    /**
     * 导入全部商品索引
     *
     * @return
     */
    @Override
    public XbinResult importAllItems() {
        return null;
    }

    /**
     * 查询商品
     *
     * @param queryString 查询条件
     * @param page        第几页
     * @param rows        每页几条
     * @return 返回商品Json
     * @throws Exception
     */
    @Override
    public SearchResult search(String queryString, Integer page, Integer rows) {
        return null;
    }
}
