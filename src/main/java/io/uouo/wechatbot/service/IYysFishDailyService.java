package io.uouo.wechatbot.service;

import java.util.Map;

public interface IYysFishDailyService {

    //摸了
    void touch(String wxid);

    //摸了多少
    Integer touchLv(String wxid);

    //日摸量
    Map<String, Object> touchToday();

}
