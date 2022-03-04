package io.uouo.wechatbot.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.uouo.wechatbot.common.util.RollUtil;
import io.uouo.wechatbot.entity.SpellEvent;
import io.uouo.wechatbot.entity.SpellcastingLog;
import io.uouo.wechatbot.entity.YysDearfriend;
import io.uouo.wechatbot.entity.YysFishDaily;
import io.uouo.wechatbot.mapper.SpellcastingLogMapper;
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

    @Autowired
    private SpellcastingLogMapper spellcastingLogMapper;

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
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            String yesterdayString = formatter.format(yesterday);
            List<YysFishDaily> yesterdayFishs = fishDailyMapper.selectList(new QueryWrapper<YysFishDaily>().lambda()
                    .eq(YysFishDaily::getDate, yesterdayString)
            );

            List<YysFishDaily> myFish = yesterdayFishs.stream().filter(fish -> fish.getWxid().equals(wxid)).collect(Collectors.toList());

            //昨日摸鱼量
            if (myFish.size() == 0){
                yysFishDaily.setExpellifish(0);
                yysFishDaily.setAvadabanana(0);
            }else {
                //发放奖励
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

                String fishKing = yesterdayFishs.stream().max(Comparator.comparing(YysFishDaily::getFishLv)).get().getWxid();
                String fishBones = yesterdayFishs.stream().min(Comparator.comparing(YysFishDaily::getBonusLv)).get().getWxid();
                if (yesterdayFish.getWxid().equals(fishBones) || yesterdayFish.getWxid().equals(fishKing)) {
                    yysFishDaily.setAvadabanana(1);
                } else {
                    yysFishDaily.setAvadabanana(0);
                }

                //昨天子弹累加
                yysFishDaily.setExpellifish(yysFishDaily.getExpellifish() + yesterdayFish.getExpellifish());
                yysFishDaily.setAvadabanana(yysFishDaily.getAvadabanana() + yesterdayFish.getAvadabanana());
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
        //总
        List<YysFishDaily> list = fishDailyMapper.selectList(new QueryWrapper<YysFishDaily>().lambda()
                .eq(YysFishDaily::getDate, new SimpleDateFormat("yyyy-MM-dd").format(new Date()))
        );

        //总摸量
        Integer touchToday = 0;
        String touchKing = "";
        String touchICU = "";
        List<Integer> lvs = new LinkedList<>();
        if (ObjectUtil.isNotEmpty(list)) {
            for (YysFishDaily fish :
                    list) {
                lvs.add(fish.getFishLv() + fish.getBonusLv());
            }
            touchToday = list.stream().collect(Collectors.summingInt(YysFishDaily::getFishLv));
            touchKing = list.get(lvs.indexOf(Collections.max(lvs))).getNickname();
            touchICU = list.get(lvs.indexOf(Collections.min(lvs))).getNickname();
        }


        Map<String, Object> param = new HashMap<>();
        param.put("TT", touchToday);
        param.put("TK", touchKing);
        param.put("TI", touchICU);
        param.put("TM", list.size());

        return param;
    }

    @Override
    public Map<String, Object> spellcasting(String wxid, String nickname, SpellEvent event, String type) {
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
        if (type.equals("avadabanana")) {
            if (badMan.getAvadabanana() == 0 || badMan.getExpellifish() < 10) {
                param.put("status", "null");
                return param;
            }
            //扣子弹
            if (badMan.getAvadabanana() > 0) {
                badMan.setAvadabanana(badMan.getAvadabanana() - 1);
            } else if (badMan.getExpellifish() >= 10) {
                badMan.setExpellifish(badMan.getExpellifish() - 10);
            }

        }
        else if (type.equals("expellifish")){
            if (badMan.getExpellifish() == 0) {
                param.put("status", "null");
                return param;
            }
            badMan.setExpellifish(badMan.getExpellifish() - 1);
        }


        //boom!
        int i = RollUtil.a2bRoll(event.getMin(), event.getMax());
        //创自己
        if (poorMan.getWxid().equals(badMan.getWxid())) {
            badMan.setBonusLv(poorMan.getBonusLv() + i);
        } else {
            poorMan.setBonusLv(poorMan.getBonusLv() + i);
            fishDailyMapper.updateById(poorMan);
        }
        fishDailyMapper.updateById(badMan);

        //施法日志
        SpellcastingLog log = new SpellcastingLog();
        log.setSpellId(event.getId());
        log.setBadmanWxid(badMan.getWxid());
        log.setPoormanWxid(poorMan.getWxid());
        log.setCreateTime(new Date());
        spellcastingLogMapper.insert(log);

        param.put("status", "headShot");
        param.put("damage", i);
        return param;
    }


}
