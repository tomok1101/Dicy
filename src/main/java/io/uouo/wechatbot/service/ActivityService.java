package io.uouo.wechatbot.service;

import io.uouo.wechatbot.entity.Activity;
import io.uouo.wechatbot.entity.Game;

import java.util.List;

public interface ActivityService {
    //查询总数
    int countAll();

    //查询指定项
    Activity selectByid(int id);

    //增加
    void add(String activity);

    //修改
    void update(Activity activity);

    //列表
    List<Activity> list();
}
