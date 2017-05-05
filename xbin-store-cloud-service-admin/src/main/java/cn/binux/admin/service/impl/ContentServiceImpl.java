package cn.binux.admin.service.impl;

import cn.binux.admin.service.ContentService;
import cn.binux.mapper.TbCategoryMapper;
import cn.binux.mapper.TbCategorySecondaryMapper;
import cn.binux.pojo.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 内容维护 Service
 *
 * @author xubin.
 * @create 2017-04-28
 */

@Api(value = "API - ContentServiceImpl", description = "内容操作")
@RestController
public class ContentServiceImpl implements ContentService {

    private static final Logger logger = LoggerFactory.getLogger(ContentServiceImpl.class);

    @Autowired
    private TbCategoryMapper categoryMapper;
    @Autowired
    private TbCategorySecondaryMapper categorySecondaryMapper;


    @Override
    @ApiOperation("获取一级分类列表")
    @ApiImplicitParams(
        {
            @ApiImplicitParam(name = "sEcho", value = "", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "iDisplayStart", value = "", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "iDisplayLength", value = "", required = true, dataType = "Integer")
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
    public Map<String, Object> getCategoryList(Integer sEcho, Integer iDisplayStart, Integer iDisplayLength) {
        HashMap<String, Object> map = new HashMap<>();
        int pageNum = iDisplayStart / iDisplayLength + 1;
        //System.out.println(pageNum);
        PageHelper.startPage(pageNum, iDisplayLength);

        TbCategoryExample example = new TbCategoryExample();
        TbCategoryExample.Criteria criteria = example.createCriteria();
        criteria.andSortOrderEqualTo(1);

        List<TbCategory> list = categoryMapper.selectByExample(example);
        //System.out.println(list.size());
        PageInfo<TbCategory> pageInfo = new PageInfo<>(list);

        map.put("sEcho", sEcho + 1);
        map.put("iTotalRecords", pageInfo.getTotal());//数据总条数
        map.put("iTotalDisplayRecords", pageInfo.getTotal());//显示的条数
        map.put("aData", list);//数据集合

        return map;
    }

    @Override
    @ApiOperation("保存一级分类列表")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "id", value = "", required = true, dataType = "String"),
                    @ApiImplicitParam(name = "name", value = "", required = true, dataType = "String"),
                    @ApiImplicitParam(name = "sort_order", value = "", required = true, dataType = "Integer")
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
    //@Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    public XbinResult saveCategory(String id, String name, Integer sort_order) {

        TbCategory category = new TbCategory();
        category.setId(id);
        category.setName(name);
        category.setSortOrder(sort_order);
        category.setUpdated(new Date());

        int i = categoryMapper.updateByPrimaryKey(category);

        return i > 0 ? XbinResult.ok() : XbinResult.build(400, "更新失败！");
    }

    @Override
    @ApiOperation(value = "获取二级分类列表",notes = "获取不是父分类")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "sEcho", value = "", required = true, dataType = "Integer"),
                    @ApiImplicitParam(name = "iDisplayStart", value = "", required = true, dataType = "Integer"),
                    @ApiImplicitParam(name = "iDisplayLength", value = "", required = true, dataType = "Integer")
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
    public Map<String, Object> getCategorySecondaryList(Integer sEcho, Integer iDisplayStart, Integer iDisplayLength) {

        HashMap<String, Object> map = new HashMap<>();
        int pageNum = iDisplayStart / iDisplayLength + 1;
        //System.out.println(pageNum);
        PageHelper.startPage(pageNum, iDisplayLength);

        TbCategorySecondaryExample example = new TbCategorySecondaryExample();
        TbCategorySecondaryExample.Criteria criteria = example.createCriteria();
        criteria.andParentIdEqualTo(0L);

        List<TbCategorySecondary> list = categorySecondaryMapper.selectByExample(example);
        //System.out.println(list.size());
        PageInfo<TbCategorySecondary> pageInfo = new PageInfo<>(list);

        map.put("sEcho", sEcho + 1);
        map.put("iTotalRecords", pageInfo.getTotal());//数据总条数
        map.put("iTotalDisplayRecords", pageInfo.getTotal());//显示的条数
        map.put("aData", list);//数据集合

        return map;
    }

    @Override
    @ApiOperation("根据条件,获取二级分类列表")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "sSearch", value = "", required = true, dataType = "String"),
                    @ApiImplicitParam(name = "sEcho", value = "", required = true, dataType = "Integer"),
                    @ApiImplicitParam(name = "iDisplayStart", value = "", required = true, dataType = "Integer"),
                    @ApiImplicitParam(name = "iDisplayLength", value = "", required = true, dataType = "Integer")
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
    public Map<String, Object> getSearchCategorySecondaryList(String sSearch, Integer sEcho, Integer iDisplayStart, Integer iDisplayLength) {

        HashMap<String, Object> map = new HashMap<>();
        int pageNum = iDisplayStart / iDisplayLength + 1;
        //System.out.println(pageNum);
        PageHelper.startPage(pageNum, iDisplayLength);

        TbCategorySecondaryExample example = new TbCategorySecondaryExample();
        TbCategorySecondaryExample.Criteria criteria = example.createCriteria();
        criteria.andNameLike("%" + sSearch + "%");

        List<TbCategorySecondary> list = categorySecondaryMapper.selectByExample(example);
        //System.out.println(list.size());
        PageInfo<TbCategorySecondary> pageInfo = new PageInfo<>(list);

        map.put("sEcho", sEcho + 1);
        map.put("iTotalRecords", pageInfo.getTotal());//数据总条数
        map.put("iTotalDisplayRecords", pageInfo.getTotal());//显示的条数
        map.put("aData", list);//数据集合

        return map;
    }

    @Override
    @ApiOperation("保存二级分类")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "TbCategorySecondary", value = "", required = true, dataType = "TbCategorySecondary"),
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
    //@Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    public XbinResult saveCategorySecondary(@RequestBody TbCategorySecondary categorySecondary) {

        categorySecondary.setUpdated(new Date());

        int i = categorySecondaryMapper.updateByPrimaryKeySelective(categorySecondary);

        return i > 0 ? XbinResult.ok() : XbinResult.build(400, "服务器出错!");
    }
}
