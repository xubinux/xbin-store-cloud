package cn.binux.search.service.impl;

import cn.binux.pojo.SearchResult;
import cn.binux.pojo.SolrItem;
import cn.binux.pojo.XbinResult;
import cn.binux.search.mapper.SearchMapper;
import cn.binux.search.service.SearchService;
import io.swagger.annotations.*;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Solr Service 实现类
 *
 * @author xubin.
 * @create 2017-05-03
 */

@RestController
public class SearchServiceImpl implements SearchService {

    @Autowired
    private SearchMapper searchMapper;

    @Autowired
    private SolrClient solrClient;

    private static Logger logger = LoggerFactory.getLogger(SearchServiceImpl.class);

    @Override
    @ApiOperation("初始化solr数据 导入全部商品数据")
    @ApiResponses(
            {
                    @ApiResponse(code = 200, message = "Successful — 请求已完成"),
                    @ApiResponse(code = 400, message = "请求中有语法问题，或不能满足请求"),
                    @ApiResponse(code = 401, message = "未授权客户机访问数据"),
                    @ApiResponse(code = 404, message = "服务器找不到给定的资源；文档不存在"),
                    @ApiResponse(code = 500, message = "服务器不能完成请求")
            }
    )
    public XbinResult importAllItems() {

        List<SolrItem> solrItemList = searchMapper.getSolrItemList();

        try {
            for (SolrItem solrItem : solrItemList) {

                SolrInputDocument document = new SolrInputDocument();

                document.addField("id", solrItem.getId());
                document.addField("item_category_name", solrItem.getCategory_name());
                document.addField("item_title", solrItem.getTitle());

                String image = solrItem.getImage();
                String[] split = image.split(",");

                document.addField("item_image", split[0]);
                document.addField("item_price", solrItem.getPrice());
                document.addField("item_sell_point", solrItem.getSell_point());
                document.addField("item_desc", solrItem.getItem_desc());

                solrClient.add(document);

            }

            solrClient.commit();

            logger.info("import success num {}",solrItemList.size());
        } catch (Exception e) {
            logger.error("import error", e);
        }

        return XbinResult.ok();
    }

    @Override
    @ApiOperation("搜索商品")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "queryString", value = "", required = true, dataType = "String"),
                    @ApiImplicitParam(name = "page", value = "", required = true, dataType = "Integer"),
                    @ApiImplicitParam(name = "rows", value = "", required = true, dataType = "Integer")
            }
    )
    @ApiResponses(
            {
                    @ApiResponse(code = 200, message = "Successful — 请求已完成"),
                    @ApiResponse(code = 400, message = "请求中有语法问题，或不能满足请求"),
                    @ApiResponse(code = 401, message = "未授权客户机访问数据"),
                    @ApiResponse(code = 404, message = "服务器找不到给定的资源；文档不存在"),
                    @ApiResponse(code = 500, message = "服务器不能完成请求")
            }
    )
    public SearchResult search(
            @RequestParam("q")                  String queryString,
            @RequestParam(defaultValue = "1")   Integer page,
            @RequestParam(defaultValue = "0")   Integer rows
    ) {

        SearchResult searchResult = new SearchResult();

        SolrQuery query = new SolrQuery();

        //设置查询条件
        query.setQuery(queryString);

        //设置分页
        query.setStart((page - 1) * rows);

        query.setRows(rows);

        //设置默认搜素域
        query.set("df", "item_keywords");

        query.setHighlight(true);

        query.addHighlightField("item_title");

        query.setHighlightSimplePre("<em style=\"color:red\">");

        query.setHighlightSimplePost("</em>");

        QueryResponse response = null;
        try {
            response = solrClient.query(query);
        } catch (SolrServerException e) {
            logger.error("query solr error", e);
        } catch (IOException e) {
            logger.error("query error", e);
        }

        SolrDocumentList results = response.getResults();

        searchResult.setRecordCount(results.getNumFound());

        List<SolrItem> solrItems = new ArrayList<SolrItem>();

        Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();

        for (SolrDocument result : results) {

            SolrItem solrItem = new SolrItem();

            solrItem.setId((String) result.get("id"));

            List<String> strings = highlighting.get(result.get("id")).get("item_title");
            if (strings != null && strings.size() > 0) {
                solrItem.setTitle(strings.get(0));
            } else {
                solrItem.setTitle((String) result.get("item_title"));
            }
            solrItem.setCategory_name((String) result.get("item_category_name"));
            solrItem.setImage((String) result.get("item_image"));
            solrItem.setSell_point((String) result.get("item_sell_point"));
            solrItem.setItem_desc((String) result.get("item_desc"));
            solrItem.setPrice((Long) result.get("item_price"));

            solrItems.add(solrItem);

        }

        searchResult.setItemList(solrItems);
        searchResult.setCurPage(page);

        long recordCount = searchResult.getRecordCount();
        long pageCount = recordCount / rows;

        if (recordCount % rows > 0) {
            pageCount++;
        }

        searchResult.setPageCount(pageCount);

        return searchResult;

    }
}
