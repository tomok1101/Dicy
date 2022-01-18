package io.uouo.wechatbot.service;

public interface IYysFishDailyService {

    //摸了
    void touch(String wxid);

    //摸了多少
    Integer touchLv(String wxid);
}
