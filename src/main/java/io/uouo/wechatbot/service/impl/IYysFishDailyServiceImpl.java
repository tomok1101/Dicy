package io.uouo.wechatbot.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.uouo.wechatbot.entity.YysDearfriend;
import io.uouo.wechatbot.entity.YysFishDaily;
import io.uouo.wechatbot.mapper.YysDearfriendMapper;
import io.uouo.wechatbot.mapper.YysFishDailyMapper;
import io.uouo.wechatbot.service.IYysFishDailyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class IYysFishDailyServiceImpl implements IYysFishDailyService {
    @Autowired
    private YysFishDailyMapper fishDailyMapper;

    @Autowired
    private YysDearfriendMapper dearfriendMapper;

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

    @Override
    public Map<String, Object> touchToday() {
        List<YysFishDaily> list = fishDailyMapper.selectList(new QueryWrapper<YysFishDaily>().lambda()
                .eq(YysFishDaily::getDate, new Date()));
        Integer touchToday = 0;
        String touchKing = "";
        Integer kingLv = null;
        if (ObjectUtil.isNotEmpty(list)){
            for (YysFishDaily fish :
                    list) {
                touchToday += fish.getFishLv() == null ? 0 : fish.getFishLv();
                if (kingLv == null) {
                    kingLv = fish.getFishLv();
                    touchKing = fish.getWxid();
                } else {
                    if (kingLv.compareTo(fish.getFishLv()) > 0) {
                        kingLv = fish.getFishLv();
                        touchKing = fish.getWxid();
                    }
                }
            }
        }
        if (!"".equals(touchKing)){
            YysDearfriend dearfriend = dearfriendMapper.selectById(touchKing);
            if (ObjectUtil.isNotEmpty(dearfriend)){
                touchKing = dearfriend.getNickname();
            }
        }
        Map<String, Object> param = new HashMap<>();
        param.put("TT", touchToday);
        param.put("TK", touchKing);
        param.put("TM", list.size());

        return param;
    }

}
