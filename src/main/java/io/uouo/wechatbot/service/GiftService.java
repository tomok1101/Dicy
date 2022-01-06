package io.uouo.wechatbot.service;

import io.uouo.wechatbot.entity.Food;

import java.util.List;

public interface GiftService {
    //查询总数
    void put();

    String get(String wxid);

    void add(String name);

    void del(String name);

}