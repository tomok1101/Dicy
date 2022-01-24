package io.uouo.wechatbot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.uouo.wechatbot.common.util.RollUtil;
import io.uouo.wechatbot.entity.DicyDict;
import io.uouo.wechatbot.mapper.DicyDictMapper;
import io.uouo.wechatbot.service.IDicyDictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class IDicyDictServiceImpl implements IDicyDictService {
    @Autowired
    private DicyDictMapper dicyDictMapper;


    @Override
    public DicyDict rollByDict(String type) {
        List<DicyDict> dict = dicyDictMapper.selectList(new QueryWrapper<DicyDict>().lambda()
                .eq(DicyDict::getCode, type)
                .orderByAsc(DicyDict::getSort));
        int i = RollUtil.iRoll(dict.size()) - 1;
        return dict.get(i);
    }

    @Override
    public List<DicyDict> holyTriangle() {
        List<DicyDict> dictList = dicyDictMapper.selectList(new QueryWrapper<DicyDict>().lambda()
                .eq(DicyDict::getCode, "tarot")
                .orderByAsc(DicyDict::getSort));
        int i1 = RollUtil.iRoll(22) - 1;
        int i2;
        int i3;
        do {
            i2 = RollUtil.iRoll(22) - 1;
        }while (i2 == i1);
        do {
            i3 = RollUtil.iRoll(22) - 1;
        }while (i3 == i1 && i3 == i2);
        List<DicyDict> dicts = new LinkedList<>();
        dicts.add(dictList.get(i1));
        dicts.add(dictList.get(i2));
        dicts.add(dictList.get(i3));
        return dicts;
    }
}
