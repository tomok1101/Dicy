package io.uouo.wechatbot.service;

import io.uouo.wechatbot.entity.Event;

import java.util.List;

public interface IEventService {
    //查询总数
    int countAll();

    //查询指定项
    Event selectByid(int id);

    //增加
    void add(String event);

    //修改
    void update(Event event);

    //列表
    List<Event> list();

    //删除
    void delete(int no);
}
