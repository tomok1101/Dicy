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
import java.util.stream.Collectors;

@Service
@Transactional
public class IYysFishDailyServiceImpl implements IYysFishDailyService {
    @Autowired
    private YysFishDailyMapper fishDailyMapper;

    @Autowired
    private YysDearfriendMapper dearfriendMapper;

    @Override
    public void touch(String wxid) {
        YysDearfriend dearfriend = dearfriendMapper.selectOne(new QueryWrapper<YysDearfriend>().lambda().eq(YysDearfriend::getWxid, wxid));

        //没注册的不理
        if (dearfriend == null) {
            return;
        }

        YysFishDaily yysFishDaily = fishDailyMapper.selectOne(new QueryWrapper<YysFishDaily>().lambda()
                .eq(YysFishDaily::getWxid, wxid)
                .eq(YysFishDaily::getDate, new SimpleDateFormat("yyyy-MM-dd").format(new Date()))
        );

        //初始化今天摸鱼数据
        if (yysFishDaily == null) {
            //基础
            yysFishDaily = new YysFishDaily();
            yysFishDaily.setWxid(wxid);
            yysFishDaily.setNickname(dearfriend.getNickname());
            yysFishDaily.setFishLv(1);
            yysFishDaily.setBonusLv(0);
            Date date = new Date();
            yysFishDaily.setDate(date);

            //昨日数据
            Calendar c = Calendar.getInstance();
            c.add(Calendar.DAY_OF_MONTH, -1);
            Date yesterday = c.getTime();

            List<YysFishDaily> yesterdayFishs = fishDailyMapper.selectList(new QueryWrapper<YysFishDaily>().lambda()
                    .eq(YysFishDaily::getWxid, wxid)
                    .eq(YysFishDaily::getDate, yesterday)
            );
            String fishKing = yesterdayFishs.stream().max(Comparator.comparing(YysFishDaily::getFishLv)).get().getWxid();

            String fishBones = yesterdayFishs.stream().min(Comparator.comparing(YysFishDaily::getFishLv)).get().getWxid();

            List<YysFishDaily> myFish = yesterdayFishs.stream().filter(fish -> fish.getWxid().equals(wxid)).collect(Collectors.toList());

            //昨日摸鱼量
            if (ObjectUtil.isNull(myFish)){
                yysFishDaily.setExpellifish(0);
            }else {
                YysFishDaily yesterdayFish = myFish.get(0);
                if (yesterdayFish.getFishLv() > 0 && yesterdayFish.getFishLv() <= 30){
                    yysFishDaily.setExpellifish(1);
                }
                else if (yesterdayFish.getFishLv() > 30 && yesterdayFish.getFishLv() <= 60){
                    yysFishDaily.setExpellifish(2);
                }
                else if (yesterdayFish.getFishLv() > 60 && yesterdayFish.getFishLv() <= 180){
                    yysFishDaily.setExpellifish(3);
                }else {
                    yysFishDaily.setExpellifish(4);
                }
                if (yesterdayFish.getWxid().equals(fishBones) || yesterdayFish.getWxid().equals(fishKing)){
                    yysFishDaily.setAvadabanana(1);
                }
            }

            fishDailyMapper.insert(yysFishDaily);
        } else {
            int i = yysFishDaily.getFishLv() + 1;
            if (i % 50 == 0) {
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
        if (ObjectUtil.isNotEmpty(list)) {
            for (YysFishDaily fish :
                    list) {
                touchToday += fish.getFishLv() == null ? 0 : fish.getFishLv();
                lvs.add(fish.getFishLv() + fish.getBonusLv());
            }
        }
        touchKing = list.get(lvs.indexOf(Collections.max(lvs))).getWxid();

        if (!"".equals(touchKing)) {
            YysDearfriend dearfriend = dearfriendMapper.selectOne(new QueryWrapper<YysDearfriend>().lambda().eq(YysDearfriend::getWxid, touchKing));
            if (ObjectUtil.isNotEmpty(dearfriend)) {
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
    public Map<String, Object> spellcasting(String wxid, String nickname, Integer max, Integer min) {
        Map<String, Object> param = new HashMap<>();
        //目标
        YysFishDaily poorMan = fishDailyMapper.selectOne(new QueryWrapper<YysFishDaily>().lambda()
                .eq(YysFishDaily::getNickname, nickname)
                .eq(YysFishDaily::getDate, new SimpleDateFormat("yyyy-MM-dd").format(new Date())));
        if (ObjectUtil.isNull(poorMan)) {
            param.put("status", "miss");
            return param;
        }
        //犯人
        YysFishDaily badMan = fishDailyMapper.selectOne(new QueryWrapper<YysFishDaily>().lambda()
                .eq(YysFishDaily::getWxid, wxid)
                .eq(YysFishDaily::getDate, new SimpleDateFormat("yyyy-MM-dd").format(new Date()))
        );

        //未注册不能打
        if (ObjectUtil.isNull(badMan)) {
            param.put("status", "miss");
            return param;
        }

        //没子弹
        if (badMan.getExpellifish() == 0) {
            param.put("status", "null");
            return param;
        }

        //boom!
        int i = RollUtil.a2bRoll(min, max);
        if (poorMan.getWxid().equals(badMan.getWxid())) {
            badMan.setExpellifish(badMan.getExpellifish() - 1);
            badMan.setBonusLv(poorMan.getBonusLv() + i);
            fishDailyMapper.updateById(badMan);
        } else {
            badMan.setExpellifish(badMan.getExpellifish() - 1);
            poorMan.setBonusLv(poorMan.getBonusLv() + i);
            fishDailyMapper.updateById(badMan);
            fishDailyMapper.updateById(poorMan);
        }
        param.put("status", "headShot");
        param.put("damage", i);
        return param;
    }


}
