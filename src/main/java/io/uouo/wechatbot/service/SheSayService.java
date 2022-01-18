package io.uouo.wechatbot.service;

import io.uouo.wechatbot.domain.WechatReceiveMsg;

public interface SheSayService {
    //回复
    void sheReplying(WechatReceiveMsg wechatReceiveMsg);

    //统计
    void sheCounting(WechatReceiveMsg wechatReceiveMsg);

}
