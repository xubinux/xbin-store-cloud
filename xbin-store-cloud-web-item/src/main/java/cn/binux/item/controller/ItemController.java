package cn.binux.item.controller;

import cn.binux.item.service.ItemService;
import cn.binux.item.vo.TbItemVO;
import cn.binux.pojo.TbItemDesc;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


/**
 * 商品查询 Controller
 *
 * @author xubin.
 * @create 2017-05-04
 */

@Api(value = "API - ItemController", description = "商品 Controller")
@Controller
public class ItemController {

    private static final Logger logger = LoggerFactory.getLogger(ItemController.class);

    @Autowired
    private ItemService itemService;

    @RequestMapping(value = "/item/{id}",method = RequestMethod.GET)
    public String  getItemByItemId(@PathVariable("id") Long itemId, Model model) {

        TbItemVO item = new TbItemVO(itemService.getItemById(itemId));

        TbItemDesc itemDesc = itemService.getItemDescById(itemId);

        model.addAttribute("item", item);
        model.addAttribute("itemDesc", itemDesc);

        return "item";
    }

}
