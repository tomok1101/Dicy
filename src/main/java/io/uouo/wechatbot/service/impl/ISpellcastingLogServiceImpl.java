package io.uouo.wechatbot.service.impl;

import io.uouo.wechatbot.mapper.SpellcastingLogMapper;
import io.uouo.wechatbot.service.ISpellcastingLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ISpellcastingLogServiceImpl implements ISpellcastingLogService {

    @Autowired
    private SpellcastingLogMapper SpellcastingLogMapper;


}
