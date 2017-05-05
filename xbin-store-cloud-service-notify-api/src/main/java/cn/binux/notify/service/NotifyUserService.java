package cn.binux.notify.service;

import cn.binux.notify.service.hystrix.NotifyUserServiceHystrix;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 用户通知服务接口
 *
 * @author xubin.
 * @create 2017-05-05
 */

@FeignClient(value = "xbin-store-cloud-service-notify",fallback = NotifyUserServiceHystrix.class)
public interface NotifyUserService {
    /**
     * 发送短信
     *
     * @param mobile 手机号码 带国际区号
     * @return ({"rs":1})   第一次
     *         ({'remain':'\u8be5\u624b\u673a\u8fd8\u53ef\u83b7\u53d62\u6b21\u9a8c\u8bc1\u7801\uff0c\u8bf7\u5c3d\u5feb\u5b8c\u6210\u9a8c\u8bc1'})
     *                      第二次提示还有2次
     *         ({'remain':'\u8be5\u624b\u673a\u8fd8\u53ef\u83b7\u53d62\u6b21\u9a8c\u8bc1\u7801\uff0c\u8bf7\u5c3d\u5feb\u5b8c\u6210\u9a8c\u8bc1'})
     *                      第三次提示还有1次
     *         ({'remain':'\u8be5\u624b\u673a\u8fd8\u53ef\u83b7\u53d62\u6b21\u9a8c\u8bc1\u7801\uff0c\u8bf7\u5c3d\u5feb\u5b8c\u6210\u9a8c\u8bc1'})
     *                      第四次提示此号码发送短信已达上限 请于1小时后尝试
     *         ({"rs":-1})  网络繁忙
     */
    @RequestMapping(value = "/mobileNotify/{mobile}",method = RequestMethod.POST)
    String mobileNotify(@PathVariable("mobile") String mobile);

}
