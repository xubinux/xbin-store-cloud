package cn.binux.search.service;


import cn.binux.pojo.SearchResult;
import cn.binux.pojo.XbinResult;
import cn.binux.search.service.hystrix.SearchServiceHystrix;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Solr Service
 *
 * @author xubin.
 * @create 2017-05-03
 */

@FeignClient(value = "xbin-store-cloud-service-search",fallback = SearchServiceHystrix.class)
public interface SearchService {

    //http://localhost:8512/search/SolrService/importAllItems/TztyomXxDyi92
    /**
     * 导入全部商品索引
     *
     * @return
     */
    @RequestMapping(value = "/importAllItems",method = RequestMethod.POST)
    XbinResult importAllItems();

    //http://localhost:8512/search/SolrService/search/查询条件/1/60
    /**
     * 查询商品
     * @param queryString 查询条件
     * @param page 第几页
     * @param rows 每页几条
     * @return 返回商品Json
     * @throws Exception
     */
    @RequestMapping(value = "/search",method = RequestMethod.POST)
    SearchResult search(
            @RequestParam("q")                  String queryString,
            @RequestParam(name = "page",defaultValue = "1")   Integer page,
            @RequestParam(name = "rows",defaultValue = "0")   Integer rows
    );
}
