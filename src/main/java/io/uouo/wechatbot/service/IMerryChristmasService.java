package io.uouo.wechatbot.service;

public interface IMerryChristmasService {
    //查询总数
    void reset();

    String get(String wxid);

    void add(String name);

    void del(String name);

}