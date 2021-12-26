package io.uouo.wechatbot.service.impl;

import io.uouo.wechatbot.entity.Game;
import io.uouo.wechatbot.mapper.GameMapper;
import io.uouo.wechatbot.mapper.GiftMapper;
import io.uouo.wechatbot.service.GiftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GiftServiceImpl implements GiftService {
    @Autowired
    private GiftMapper giftMapper;


    @Override
    public int countAll() {
        return giftMapper.selectCount(null);
    }


}
