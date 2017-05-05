package cn.binux.portal.service.impl;

import cn.binux.mapper.TbContentMapper;
import cn.binux.pojo.TbContent;
import cn.binux.pojo.TbContentExample;
import cn.binux.portal.service.PortalContentService;
import cn.binux.utils.FastJsonConvert;
import cn.binux.utils.JedisClient;
import io.swagger.annotations.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 首页内容Service
 * @create 2017-05-04
 */
@Api(value = "API - PortalContentServiceImpl", description = "首页操作")
@RestController
@RefreshScope
public class PortalContentServiceImpl implements PortalContentService {

    private static Logger logger = Logger.getLogger(PortalContentServiceImpl.class);

    @Autowired
    private TbContentMapper contentMapper;

    @Autowired
    private JedisClient jedisClient;

    @Value("${redisKey.prefix.index_ad}")
    private String INDEX_AD;


    @Override
    @ApiOperation("获取首页广告")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "bigAdIndex", value = "", required = true, dataType = "Long"),
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
    public List<TbContent> getContentByCid(Long bigAdIndex) {

        //先查询缓存

        try {
            logger.info("=====>查询Redis");
            String list = jedisClient.hget(INDEX_AD, bigAdIndex + "");

            if (StringUtils.isNotBlank(list)) {

                logger.info("=======>查询Redis 返回结果");
                return FastJsonConvert.convertJSONToArray(list, TbContent.class);
            }

        } catch (Exception e) {
            logger.error(e);
        }


        TbContentExample example = new TbContentExample();
        TbContentExample.Criteria criteria = example.createCriteria();
        criteria.andCategoryIdEqualTo(bigAdIndex);

        logger.info("=======>查询数据库");
        List<TbContent> list = contentMapper.selectByExample(example);

        //添加缓存
        try {
            logger.info("=======>添加缓存");
            jedisClient.hset(INDEX_AD, bigAdIndex + "", FastJsonConvert.convertObjectToJSON(list));
        } catch (Exception e) {
            logger.error(e);
        }

        return list;

    }

}
