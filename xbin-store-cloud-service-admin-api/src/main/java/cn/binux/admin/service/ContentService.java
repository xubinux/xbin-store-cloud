package cn.binux.admin.service;


import cn.binux.admin.service.hystrix.ContentServiceHystrix;
import cn.binux.pojo.TbCategorySecondary;
import cn.binux.pojo.XbinResult;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * 内容维护
 *
 * @author xubin.
 * @create 2017-04-27
 */

@FeignClient(value = "xbin-store-cloud-service-admin",fallback = ContentServiceHystrix.class)
public interface ContentService {

    @RequestMapping(value = "/getCategoryList",method = RequestMethod.POST)
    Map<String, Object> getCategoryList(
            @RequestParam("sEcho")          Integer sEcho, 
            @RequestParam("iDisplayStart")  Integer iDisplayStart, 
            @RequestParam("iDisplayLength") Integer iDisplayLength
    );

    @RequestMapping(value = "/save/category",method = RequestMethod.POST)
    XbinResult saveCategory(
            @RequestParam("id")             String id, 
            @RequestParam("name")           String name, 
            @RequestParam("sort_order")     Integer sort_order
    );

    @RequestMapping(value = "/getCategorySecondary",method = RequestMethod.POST)
    Map<String,Object> getCategorySecondaryList(
            @RequestParam("sEcho")          Integer sEcho, 
            @RequestParam("iDisplayStart")  Integer iDisplayStart,
            @RequestParam("iDisplayLength") Integer iDisplayLength
    );

    @RequestMapping(value = "/getSearchCategorySecondaryList",method = RequestMethod.POST)
    Map<String,Object> getSearchCategorySecondaryList(
            @RequestParam("sSearch")        String sSearch, 
            @RequestParam("sEcho")          Integer sEcho, 
            @RequestParam("iDisplayStart")  Integer iDisplayStart, 
            @RequestParam("iDisplayLength") Integer iDisplayLength
    );

    @RequestMapping(value = "/saveCategorySecondary",method = RequestMethod.POST)
    XbinResult saveCategorySecondary(TbCategorySecondary categorySecondary);
}
