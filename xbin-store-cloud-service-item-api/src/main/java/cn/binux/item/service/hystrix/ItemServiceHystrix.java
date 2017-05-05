package cn.binux.item.service.hystrix;

import cn.binux.item.service.ItemService;
import cn.binux.pojo.TbItem;
import cn.binux.pojo.TbItemDesc;
import org.springframework.stereotype.Component;

/**
 * 商品服务 熔断处理
 *
 * @author xubin.
 * @create 2017-05-04
 */

@Component
public class ItemServiceHystrix implements ItemService {

    @Override
    public TbItem getItemById(Long itemId) {
        return null;
    }

    @Override
    public TbItemDesc getItemDescById(Long itemId) {
        return null;
    }
}
