package cn.binux.search.controller;

import cn.binux.pojo.SearchResult;
import cn.binux.search.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.UnsupportedEncodingException;

/**
 * 搜索 Controller
 *
 * @author xubin.
 * @create 2017-05-04
 */

@Controller
@RefreshScope
public class SearchController {

    @Autowired
    private SearchService searchService;

    @Value("${search_result_rows}")
    private Integer SEARCH_RESULT_ROWS;

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public String search(
            @RequestParam("q") String queryString,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "0") Integer rows,
            Model model) {

        if (rows == 0) {
            rows = SEARCH_RESULT_ROWS;
        }

        if (queryString != null) {

            String string = null;
            try {
                string = new String(queryString.getBytes("iso8859-1"), "utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            SearchResult search = searchService.search(string, page, rows);

            // 异常测试
            //int i = 1 / 0;

            model.addAttribute("query", string);
            model.addAttribute("totalPages", search.getPageCount());
            model.addAttribute("itemList", search.getItemList());
            model.addAttribute("page", search.getCurPage());

        }

        return "search";

    }
}
