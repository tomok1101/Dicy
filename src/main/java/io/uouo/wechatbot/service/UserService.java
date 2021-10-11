package io.uouo.wechatbot.service;

import io.uouo.wechatbot.domain.WechatReceiveMsg;
import io.uouo.wechatbot.entity.User;

import java.util.List;

public interface UserService {
    void sayMyaName(String wxid,String nickname);
}
