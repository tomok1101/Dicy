package io.uouo.wechatbot.mainService;

import io.uouo.wechatbot.domain.WechatReceiveMsg;

public interface SheSayService {
    //回复
    void sheReplying(WechatReceiveMsg wechatReceiveMsg);

    //统计
    void sheCounting(WechatReceiveMsg wechatReceiveMsg);

    //读取
    void sheReading(WechatReceiveMsg wechatReceiveMsg);
}
