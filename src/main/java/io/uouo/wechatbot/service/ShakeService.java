package io.uouo.wechatbot.service;

import io.uouo.wechatbot.entity.Shake;
import io.uouo.wechatbot.entity.Shakeman;

public interface ShakeService {
    //发车
    void shake(String game,Integer num,int time);
}
