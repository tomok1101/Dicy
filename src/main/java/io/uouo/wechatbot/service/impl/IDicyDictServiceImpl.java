package io.uouo.wechatbot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.uouo.wechatbot.common.util.RollUtil;
import io.uouo.wechatbot.entity.DicyDict;
import io.uouo.wechatbot.mapper.DicyDictMapper;
import io.uouo.wechatbot.service.IDicyDictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IDicyDictServiceImpl implements IDicyDictService {
    @Autowired
    private DicyDictMapper dicyDictMapper;


    @Override
    public String draw() {
        List<DicyDict> tarots = dicyDictMapper.selectList(new QueryWrapper<DicyDict>().lambda()
                .eq(DicyDict::getCode, "tarot")
                .orderByAsc(DicyDict::getSort));
        int i = RollUtil.iRoll(tarots.size()) - 1;
        return tarots.get(i).getValue();
    }
}
