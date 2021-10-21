package io.uouo.wechatbot.service;

import io.uouo.wechatbot.entity.Food;
import io.uouo.wechatbot.entity.Game;

import java.util.List;

public interface FoodService {
    //查询总数
    int countAll();

    //查询指定项
    Food selectByid(int id);

    //增加
    void add(String food);

    //修改
    void update(Food food);

    //列表
    List<Food> list();
}
