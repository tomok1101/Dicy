package io.uouo.wechatbot.controller;

import io.uouo.wechatbot.common.util.AjaxResult;
import io.uouo.wechatbot.entity.Activity;
import io.uouo.wechatbot.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author: tom
 * @Date: 2021-10-21 17:57
 * @Description: < 描述 >
 */
@RestController
@RequestMapping("activity")
public class ActivityController {

    @Autowired
    private ActivityService activityService;


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
        return new AjaxResult(200,"操作成功",activityService.list());
    }


    /**
     * 描述: 增加
     *
     * @param activity
     * @return io.uouo.wechatbot.common.util.AjaxResult
     * @Author tom
     * @Date 2021-10-21
     */
    @PostMapping("/add")
    public AjaxResult add(String activity) {
        activityService.add(activity);
        return AjaxResult.success();
    }

    /**
     * 描述: 修改
     *
     * @param activity
     * @return io.uouo.wechatbot.common.util.AjaxResult
     * @Author tom
     * @Date 2021-10-21
     */
    @PostMapping("/update")
    public AjaxResult update(@RequestBody Activity activity) {
        // 发送消息
        activityService.update(activity);
        return AjaxResult.success();
    }

}
