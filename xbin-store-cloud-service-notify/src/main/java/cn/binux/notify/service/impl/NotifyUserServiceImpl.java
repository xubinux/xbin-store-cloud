package cn.binux.notify.service.impl;

import cn.binux.notify.service.NotifyUserService;
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

import java.util.HashMap;

/**
 * 用户通知服务实现
 *
 * @author xubin.
 * @create 2017-05-05
 */

@Api(value = "API - NotifyUserServiceImpl", description = "用户通知")
@RefreshScope
@RestController
public class NotifyUserServiceImpl implements NotifyUserService {

    private static final Logger logger = LoggerFactory.getLogger(NotifyUserServiceImpl.class);

    @Autowired
    private JedisClient jedisClient;

    @Value("${redisKey.prefix.mobile_login_code.key}")
    private String MOBILE_LOGIN_CODE;

    @Value("${redisKey.prefix.mobile_login_time.key}")
    private String MOBILE_LOGIN_TIME;

    @Value("${mobile_number_ceiling}")
    private Integer MOBILE_NUMBER_CEILING;

    @Value("${redisKey.prefix.mobile_login_code.expire_time}")
    private Integer MOBILE_LOGIN_CODE_EXPIRE;

    @Value("${redisKey.prefix.mobile_login_time.expire_time}")
    private Integer MOBILE_LOGIN_TIME_EXPIRE;

    /**
     * 发送短信
     *
     * @param mobile 手机号码 带国际区号
     * @return ({'rs':1})   第一次
     *         ({'remain':'该手机还可获取2次验证码，请尽快完成验证'})
     *                      第二次提示
     *         ({'remain':'该手机还可获取1次验证码，请尽快完成验证'})
     *                      第三次提示
     *         ({"rs":1})   第四次
     *         ({"rs":-1})  网络繁忙
     */
    @Override
    @ApiOperation("获取商品信息")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "mobile", value = "", required = true, dataType = "String"),
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
    public String mobileNotify(@PathVariable("mobile") String mobile) {

        HashMap<String, Object> map = new HashMap<>();

        //System.out.println(mobile);

        //截取手机号码如+008615669970088
        String phone = mobile.substring(5, mobile.length());

        int code = (int) ((Math.random() * 9 + 1) * 100000);

        //查询Redis  号码1小时获取次数
        try {
            String key = MOBILE_LOGIN_TIME + phone;
            String key1 = MOBILE_LOGIN_CODE + phone;
            String time = jedisClient.get(key);

            //查询不到
            if (StringUtils.isBlank(time)) {

                // 发送短信==================

                //保存登录次数到Redis
                //初始化次数为3次
                jedisClient.set(key, MOBILE_NUMBER_CEILING + "");
                //设置过期时间
                jedisClient.expire(key, MOBILE_LOGIN_TIME_EXPIRE);
                //保存code到Redis
                jedisClient.set(key1, code + "");
                //设置过期时间
                jedisClient.expire(key1, MOBILE_LOGIN_CODE_EXPIRE);

                map.put("rs", 1);

                return FastJsonConvert.convertObjectToJSONBracket(map);
            }

            //查询到 判断是否为0 次数减一
            int nub = Integer.parseInt(time);
            if (nub == 0) {
                jedisClient.del(key1);
                map.put("rs", -1);

                return FastJsonConvert.convertObjectToJSONBracket(map);
            }

            // 发送短信==================

            if (nub == 1) {
                jedisClient.set(key, --nub + "");
                map.put("rs", 1);

                return FastJsonConvert.convertObjectToJSONBracket(map);
            }

            jedisClient.set(key, --nub + "");

            //保存code到Redis
            jedisClient.set(key1, code + "");
            //设置过期时间
            jedisClient.expire(key1, MOBILE_LOGIN_CODE_EXPIRE);

            String result = "该手机还可获取" + nub + "次验证码，请尽快完成验证 验证码:" + code;
            map.put("remain", result);

            return "({'remain':'" + result + "'})";

        } catch (Exception e) {
            logger.error("Redis error", e);
        }

        map.put("rs", -1);

        return FastJsonConvert.convertObjectToJSONBracket(map);
    }
}
