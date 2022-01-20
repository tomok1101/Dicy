package io.uouo.wechatbot.service.impl;

import io.uouo.wechatbot.common.util.RollUtil;
import io.uouo.wechatbot.entity.ExpellifishEvent;
import io.uouo.wechatbot.mapper.ExpellifishEventMapper;
import io.uouo.wechatbot.service.IExpellifishEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IExpellifishEventServiceImpl implements IExpellifishEventService {

    @Autowired
    private ExpellifishEventMapper expellifishEventMapper;


    @Override
    public ExpellifishEvent getEvent() {
        List<ExpellifishEvent> expellifishEvents = expellifishEventMapper.selectList(null);
        int i = RollUtil.iRoll(expellifishEvents.size())-1;
        return expellifishEvents.get(i);
    }
}
