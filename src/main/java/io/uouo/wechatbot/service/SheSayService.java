package io.uouo.wechatbot.service;

import io.uouo.wechatbot.domain.WechatReceiveMsg;

public interface SheSayService {
    //回复
    void sheJudged(WechatReceiveMsg wechatReceiveMsg);

}
