package io.uouo.wechatbot.controller;

import io.uouo.wechatbot.common.util.AjaxResult;
import io.uouo.wechatbot.entity.Game;
import io.uouo.wechatbot.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author: tom
 * @Date: 2021-10-21 17:57
 * @Description: < 描述 >
 */
@RestController
@RequestMapping("game")
public class GameController {

    @Autowired
    private GameService gameService;


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
        return new AjaxResult(200,"操作成功",gameService.list());
    }


    /**
     * 描述: 增加
     *
     * @param game
     * @return io.uouo.wechatbot.common.util.AjaxResult
     * @Author tom
     * @Date 2021-10-21
     */
    @PostMapping("/add")
    public AjaxResult add(String game) {
        gameService.add(game);
        return AjaxResult.success();
    }

    /**
     * 描述: 修改
     *
     * @param game
     * @return io.uouo.wechatbot.common.util.AjaxResult
     * @Author tom
     * @Date 2021-10-21
     */
    @PostMapping("/update")
    public AjaxResult update(@RequestBody Game game) {
        // 发送消息
        gameService.update(game);
        return AjaxResult.success();
    }

}
