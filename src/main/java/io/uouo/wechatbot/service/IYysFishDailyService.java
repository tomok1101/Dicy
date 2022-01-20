package io.uouo.wechatbot.service;

import io.uouo.wechatbot.entity.YysFishDaily;

import java.util.Map;

public interface IYysFishDailyService {

    //摸了
    void touch(String wxid);

    //摸了多少
    YysFishDaily touchLv(String wxid);

    //日摸量
    Map<String, Object> touchToday();

    //除你fish
    Integer expellifish(String wxid, String nickname, Integer damage);

}
