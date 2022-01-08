package io.uouo.wechatbot.service;

import io.uouo.wechatbot.entity.Game;

import java.util.List;

public interface IGameService {
    //查询总数
    int countAll();

    //查询指定项
    Game selectByid(int id);

    //增加
    void add(String game);

    //修改
    void update(Game game);

    //列表
    List<Game> list();

    //删除
    void delete(int no);
}
