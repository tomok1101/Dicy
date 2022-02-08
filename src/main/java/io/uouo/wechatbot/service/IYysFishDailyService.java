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
    Map<String, Object> expellifish(String wxid, String nickname, Integer damage);

    //阿瓦达阿巴巴
    Map<String, Object> AvadaABaBa(String id1, String nickname, Integer max);

}
