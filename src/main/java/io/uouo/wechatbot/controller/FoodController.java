package io.uouo.wechatbot.controller;

import io.uouo.wechatbot.common.util.AjaxResult;
import io.uouo.wechatbot.entity.Food;
import io.uouo.wechatbot.service.FoodService;
import io.uouo.wechatbot.service.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author: tom
 * @Date: 2021-10-21 17:57
 * @Description: < 描述 >
 */
@RestController
@RequestMapping("food")
public class FoodController {

    @Autowired
    private FoodService foodService;


    /**
     * 描述: 列表
     *
     * @param 
     * @return io.uouo.wechatbot.common.util.AjaxResult
     * @Author tom
     * @Date 2021-10-21
     */
    @GetMapping("/list")
    public AjaxResult list() {
        return new AjaxResult(200,"操作成功",foodService.list());
    }


    /**
     * 描述: 增加
     *
     * @param food
     * @return io.uouo.wechatbot.common.util.AjaxResult
     * @Author tom
     * @Date 2021-10-21
     */
    @PostMapping("/add")
    public AjaxResult add(String food) {
        foodService.add(food);
        return AjaxResult.success();
    }

    /**
     * 描述: 修改
     *
     * @param food
     * @return io.uouo.wechatbot.common.util.AjaxResult
     * @Author tom
     * @Date 2021-10-21
     */
    @PostMapping("/update")
    public AjaxResult update(@RequestBody Food food) {
        // 发送消息
        foodService.update(food);
        return AjaxResult.success();
    }

}