package cn.binux.item.service.impl;

import cn.binux.item.service.ItemService;
import cn.binux.mapper.TbItemDescMapper;
import cn.binux.mapper.TbItemMapper;
import cn.binux.pojo.TbItem;
import cn.binux.pojo.TbItemDesc;
import cn.binux.utils.FastJsonConvert;
import cn.binux.utils.JedisClient;
import io.swagger.annotations.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


/**
 * 商品 Service 实现
 *
 * @author xubin.
 * @create 2017-05-04
 */
@Api(value = "API - PortalContentServiceImpl", description = "首页操作")
@RestController
@RefreshScope
public class ItemServiceImpl implements ItemService {

    private static final Logger logger = LoggerFactory.getLogger(ItemServiceImpl.class);

    @Autowired
    private TbItemMapper itemMapper;

    @Autowired
    private TbItemDescMapper itemDescMapper;

    @Autowired
    private JedisClient jedisClient;

    @Value("${redisKey.prefix.item_info_profix}")
    private String ITEM_INFO_PROFIX;

    @Value("${redisKey.suffix.item_info_base_suffix}")
    private String  ITEM_INFO_BASE_SUFFIX;

    @Value("${redisKey.suffix.item_info_desc_suffix}")
    private String ITEM_INFO_DESC_SUFFIX;

    @Value("${redisKey.expire_time}")
    private Integer REDIS_EXPIRE_TIME;

    @Override
    @ApiOperation("获取商品信息")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "itemId", value = "", required = true, dataType = "Long"),
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
    public TbItem getItemById(@PathVariable("id") Long itemId) {

        String key = ITEM_INFO_PROFIX + itemId + ITEM_INFO_BASE_SUFFIX;

        try {
            String jsonItem = jedisClient.get(key);

            if (StringUtils.isNotBlank(jsonItem)) {

                logger.info("Redis 查询 商品信息 商品ID:" + itemId);

                return FastJsonConvert.convertJSONToObject(jsonItem, TbItem.class);

            } else {
                logger.error("Redis 查询不到 key:" + key);
            }
        } catch (Exception e) {
            logger.error("商品信息 获取缓存报错",e);
        }

        logger.info("根据商品ID"+itemId+"查询商品！");
        TbItem item = itemMapper.selectByPrimaryKey(itemId);

        try {
            jedisClient.set(key, FastJsonConvert.convertObjectToJSON(item));

            jedisClient.expire(key, REDIS_EXPIRE_TIME);

            logger.info("Redis 缓存商品信息 key:" + key);

        } catch (Exception e) {
            logger.error("缓存错误商品ID:" + itemId, e);
        }

        return item;

    }

    @Override
    @ApiOperation("获取商品描述")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "itemId", value = "", required = true, dataType = "Long"),
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
    public TbItemDesc getItemDescById(@PathVariable("id") Long itemId) {

        String key = ITEM_INFO_PROFIX + itemId + ITEM_INFO_DESC_SUFFIX;

        try {
            String jsonItem = jedisClient.get(key);

            if (StringUtils.isNotBlank(jsonItem)) {

                logger.info("Redis query item ID: {}" , itemId);

                return FastJsonConvert.convertJSONToObject(jsonItem, TbItemDesc.class);

            } else {
                logger.error("Redis query fail key: {}" ,key);
            }
        } catch (Exception e) {
            logger.error("Redis error", e);
        }
        TbItemDesc itemDesc = itemDescMapper.selectByPrimaryKey(itemId);
        try {
            jedisClient.set(key, FastJsonConvert.convertObjectToJSON(itemDesc));

            jedisClient.expire(key, REDIS_EXPIRE_TIME);

            logger.info("Redis query fail key: {}" ,key);

        } catch (Exception e) {
            logger.error("Redis error", e);
        }
        return itemDesc;
    }
}
