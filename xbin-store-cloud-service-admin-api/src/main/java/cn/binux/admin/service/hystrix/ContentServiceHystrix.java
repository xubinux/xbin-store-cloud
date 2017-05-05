package cn.binux.admin.service.hystrix;

import cn.binux.admin.service.ContentService;
import cn.binux.pojo.TbCategorySecondary;
import cn.binux.pojo.XbinResult;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 内容维护 熔断处理
 *
 * @author xubin.
 * @create 2017-04-27 上午11:40
 */

@Component
public class ContentServiceHystrix implements ContentService {


    @Override
    public Map<String, Object> getCategoryList(Integer sEcho, Integer iDisplayStart, Integer iDisplayLength) {
        return null;
    }

    @Override
    public XbinResult saveCategory(String id, String name, Integer sort_order) {
        return null;
    }

    @Override
    public Map<String, Object> getCategorySecondaryList(Integer sEcho, Integer iDisplayStart, Integer iDisplayLength) {
        return null;
    }

    @Override
    public Map<String, Object> getSearchCategorySecondaryList(String sSearch, Integer sEcho, Integer iDisplayStart, Integer iDisplayLength) {
        return null;
    }

    @Override
    public XbinResult saveCategorySecondary(TbCategorySecondary categorySecondary) {
        return null;
    }
}
