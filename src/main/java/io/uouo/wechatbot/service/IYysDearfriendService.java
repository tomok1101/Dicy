package io.uouo.wechatbot.service;

import io.uouo.wechatbot.entity.YysDearfriend;

public interface IYysDearfriendService {

    //查人
    String check(String wxid);

    //录入
    boolean add(YysDearfriend dearfriend);
}
