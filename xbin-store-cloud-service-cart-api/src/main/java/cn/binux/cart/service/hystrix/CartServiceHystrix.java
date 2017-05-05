package cn.binux.cart.service.hystrix;

import cn.binux.cart.service.CartService;
import cn.binux.pojo.CartInfo;
import cn.binux.pojo.XbinResult;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 购物车服务 熔断处理
 *
 * @author xubin.
 * @create 2017-05-04 下午11:59
 */

@Component
public class CartServiceHystrix implements CartService {


    @Override
    public XbinResult addCart(Long pid, Integer pcount, String uuid) {
        return null;
    }

    @Override
    public List<CartInfo> getCartInfoListByCookiesId(String cookieUUID) {
        return null;
    }

    @Override
    public XbinResult decreOrIncre(Long pid, Integer pcount, Integer type, Integer index, String cookieUUID) {
        return null;
    }
}
