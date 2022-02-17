package io.uouo.wechatbot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.uouo.wechatbot.common.util.RollUtil;
import io.uouo.wechatbot.entity.MagicEvent;
import io.uouo.wechatbot.mapper.MagicEventMapper;
import io.uouo.wechatbot.service.IMagicEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IMagicEventServiceImpl implements IMagicEventService {

    @Autowired
    private MagicEventMapper MagicEventMapper;


    @Override
    public MagicEvent getExpellifishEvent() {
        List<MagicEvent> MagicEvents = MagicEventMapper.selectList(new QueryWrapper<MagicEvent>().lambda().eq(MagicEvent::getType,"expellifish"));
        int i = RollUtil.iRoll(MagicEvents.size())-1;
        return MagicEvents.get(i);
    }

    @Override
    public MagicEvent getAvadaBananaEvent() {
        List<MagicEvent> MagicEvents = MagicEventMapper.selectList(new QueryWrapper<MagicEvent>().lambda().eq(MagicEvent::getType,"avadabanana"));
        int i = RollUtil.iRoll(MagicEvents.size())-1;
        return MagicEvents.get(i);
    }
}
