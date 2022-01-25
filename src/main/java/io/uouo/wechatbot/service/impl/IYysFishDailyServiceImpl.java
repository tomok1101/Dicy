package io.uouo.wechatbot.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.uouo.wechatbot.common.util.RollUtil;
import io.uouo.wechatbot.entity.YysDearfriend;
import io.uouo.wechatbot.entity.YysFishDaily;
import io.uouo.wechatbot.mapper.YysDearfriendMapper;
import io.uouo.wechatbot.mapper.YysFishDailyMapper;
import io.uouo.wechatbot.service.IYysFishDailyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Transactional
public class IYysFishDailyServiceImpl implements IYysFishDailyService {
    @Autowired
    private YysFishDailyMapper fishDailyMapper;

    @Autowired
    private YysDearfriendMapper dearfriendMapper;

    @Override
    public void touch(String wxid) {
        YysFishDaily yysFishDaily = fishDailyMapper.selectOne(new QueryWrapper<YysFishDaily>().lambda()
                .eq(YysFishDaily::getWxid, wxid)
                .eq(YysFishDaily::getDate, new SimpleDateFormat("yyyy-MM-dd").format(new Date()))
        );
        YysDearfriend dearfriend = dearfriendMapper.selectOne(new QueryWrapper<YysDearfriend>().lambda().eq(YysDearfriend::getWxid, wxid));
        if (yysFishDaily == null){
            yysFishDaily = new YysFishDaily();
            yysFishDaily.setWxid(wxid);
            yysFishDaily.setNickname(dearfriend == null ? "那个谁" : dearfriend.getNickname());
            yysFishDaily.setFishLv(1);
            yysFishDaily.setBonusLv(0);

            Calendar cal = Calendar.getInstance();
            Date date = new Date();
            cal.setTime(date);
            int w = cal.get(Calendar.DAY_OF_WEEK);
            if (w == 1 || w == 7){
                yysFishDaily.setExpellifish(5);
            }else {
                yysFishDaily.setExpellifish(1);
            }
            yysFishDaily.setDate(date);
            fishDailyMapper.insert(yysFishDaily);
        }else {
            int i = yysFishDaily.getFishLv() + 1;
            if (i%100 == 0){
                yysFishDaily.setExpellifish(yysFishDaily.getExpellifish() + 1);
            }
            yysFishDaily.setFishLv(i);
            fishDailyMapper.updateById(yysFishDaily);
        }
    }

    @Override
    public YysFishDaily touchLv(String wxid) {
        YysFishDaily fish = fishDailyMapper.selectOne(new QueryWrapper<YysFishDaily>().lambda()
                .eq(YysFishDaily::getWxid, wxid)
                .eq(YysFishDaily::getDate, new SimpleDateFormat("yyyy-MM-dd").format(new Date())));
        return fish;
    }

    @Override
    public Map<String, Object> touchToday() {

        List<YysFishDaily> list = fishDailyMapper.selectList(new QueryWrapper<YysFishDaily>().lambda()
                .eq(YysFishDaily::getDate, new SimpleDateFormat("yyyy-MM-dd").format(new Date()))
                .orderByDesc(YysFishDaily::getFishLv)
        );
        Integer touchToday = 0;
        String touchKing = "";
        List<Integer> lvs = new LinkedList<>();
        if (ObjectUtil.isNotEmpty(list)){
            for (YysFishDaily fish :
                    list) {
                touchToday += fish.getFishLv() == null ? 0 : fish.getFishLv();
                lvs.add(fish.getFishLv() + fish.getBonusLv());
            }
        }
        touchKing = list.get(lvs.indexOf(Collections.max(lvs))).getWxid();

        if (!"".equals(touchKing)){
            YysDearfriend dearfriend = dearfriendMapper.selectOne(new QueryWrapper<YysDearfriend>().lambda().eq(YysDearfriend::getWxid,touchKing));
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

    @Override
    public Map<String, Object> expellifish(String wxid, String nickname, Integer damage) {
        Map<String, Object> param = new HashMap<>();
        YysFishDaily poorMan = fishDailyMapper.selectOne(new QueryWrapper<YysFishDaily>().lambda()
                .eq(YysFishDaily::getNickname, nickname)
                .eq(YysFishDaily::getDate, new SimpleDateFormat("yyyy-MM-dd").format(new Date())));
        if (ObjectUtil.isNull(poorMan)){
            param.put("status","miss");
            return param;
        }
        YysFishDaily badMan = fishDailyMapper.selectOne(new QueryWrapper<YysFishDaily>().lambda()
                .eq(YysFishDaily::getWxid, wxid)
                .eq(YysFishDaily::getDate, new SimpleDateFormat("yyyy-MM-dd").format(new Date()))
        );
        if (badMan.getExpellifish() == 0){
            param.put("status","null");
            return param;
        }
        badMan.setExpellifish(badMan.getExpellifish()-1);
        int i = RollUtil.iRoll(damage);
        poorMan.setBonusLv(poorMan.getBonusLv() + i);
        fishDailyMapper.updateById(poorMan);
        fishDailyMapper.updateById(badMan);
        param.put("status","headShot");
        param.put("damage",i);
        return param;
    }

}
