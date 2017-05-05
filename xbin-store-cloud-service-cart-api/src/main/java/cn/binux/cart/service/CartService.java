package cn.binux.cart.service;


import cn.binux.cart.service.hystrix.CartServiceHystrix;
import cn.binux.pojo.CartInfo;
import cn.binux.pojo.XbinResult;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 购物车相关操作 Service
 *
 * @author xubin.
 * @create 2017-05-04
 */

@FeignClient(value = "xbin-store-cloud-service-cart",fallback = CartServiceHystrix.class)
public interface CartService {

    @RequestMapping(value = "/addCart",method = RequestMethod.POST)
    XbinResult addCart(
            @RequestParam("pid")        Long pid,
            @RequestParam("pcount")     Integer pcount,
            @RequestParam("uuid")       String uuid
    );

    @RequestMapping(value = "/getCartInfoListByCookiesId",method = RequestMethod.POST)
    List<CartInfo> getCartInfoListByCookiesId(@RequestParam("cookieUUID") String cookieUUID);

    /**
     *
     * 根据商品id和数量对购物车增加商品或减少商品
     *
     * @param pid       商品id
     * @param pcount    增加数量
     * @param type      1 增加 2 减少
     * @param index     商品位置   ps:用于直接定位商品 不用遍历整个购物车
     * @return
     */
    @RequestMapping(value = "/decreOrIncre",method = RequestMethod.POST)
    XbinResult decreOrIncre(
            @RequestParam("pid")        Long pid,
            @RequestParam("pcount")     Integer pcount,
            @RequestParam("type")       Integer type,
            @RequestParam("index")      Integer index,
            @RequestParam("cookieUUID") String cookieUUID
    );


}
