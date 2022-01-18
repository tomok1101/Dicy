package io.uouo.wechatbot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.uouo.wechatbot.entity.YysFishDaily;
import io.uouo.wechatbot.mapper.YysFishDailyMapper;
import io.uouo.wechatbot.service.IYysFishDailyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class IYysFishDailyServiceImpl implements IYysFishDailyService {
    @Autowired
    private YysFishDailyMapper fishDailyMapper;

    @Override
    public void touch(String wxid) {
        YysFishDaily yysFishDaily = fishDailyMapper.selectOne(new QueryWrapper<YysFishDaily>().lambda()
                .eq(YysFishDaily::getWxid, wxid)
                .eq(YysFishDaily::getDate, new Date())
        );
        if (yysFishDaily == null){
            yysFishDaily = new YysFishDaily();
            yysFishDaily.setDate(new Date());
            yysFishDaily.setWxid(wxid);
            yysFishDaily.setFishLv(1);
            fishDailyMapper.insert(yysFishDaily);
        }else {
            yysFishDaily.setFishLv(yysFishDaily.getFishLv()+1);
            fishDailyMapper.updateById(yysFishDaily);
        }
    }

    @Override
    public Integer touchLv(String wxid) {
        YysFishDaily fish = fishDailyMapper.selectOne(new QueryWrapper<YysFishDaily>().lambda()
                .eq(YysFishDaily::getWxid, wxid)
                .eq(YysFishDaily::getDate, new Date()));
        return fish == null ? 0 : fish.getFishLv();
    }
}
