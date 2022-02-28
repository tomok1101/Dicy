package io.uouo.wechatbot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.uouo.wechatbot.common.util.RollUtil;
import io.uouo.wechatbot.entity.SpellEvent;
import io.uouo.wechatbot.mapper.SpellEventMapper;
import io.uouo.wechatbot.service.ISpellEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ISpellEventServiceImpl implements ISpellEventService {

    @Autowired
    private SpellEventMapper SpellEventMapper;


    @Override
    public SpellEvent getExpellifishEvent() {
        List<SpellEvent> SpellEvents = SpellEventMapper.selectList(new QueryWrapper<SpellEvent>().lambda().eq(SpellEvent::getType, "expellifish"));
        int i = RollUtil.iRoll(SpellEvents.size()) - 1;
        return SpellEvents.get(i);
    }

    @Override
    public SpellEvent getAvadaBananaEvent() {
        List<SpellEvent> SpellEvents = SpellEventMapper.selectList(new QueryWrapper<SpellEvent>().lambda().eq(SpellEvent::getType, "avadabanana"));
        int i = RollUtil.iRoll(SpellEvents.size()) - 1;
        return SpellEvents.get(i);
    }
}
