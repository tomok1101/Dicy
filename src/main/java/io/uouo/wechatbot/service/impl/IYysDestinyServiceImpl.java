package io.uouo.wechatbot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.uouo.wechatbot.common.util.RollUtil;
import io.uouo.wechatbot.entity.DicyDict;
import io.uouo.wechatbot.entity.Event;
import io.uouo.wechatbot.entity.YysDearfriend;
import io.uouo.wechatbot.entity.YysDestiny;
import io.uouo.wechatbot.mapper.EventMapper;
import io.uouo.wechatbot.mapper.YysDearfriendMapper;
import io.uouo.wechatbot.mapper.YysDestinyMapper;
import io.uouo.wechatbot.service.IDicyDictService;
import io.uouo.wechatbot.service.IEventService;
import io.uouo.wechatbot.service.IGameService;
import io.uouo.wechatbot.service.IYysDestinyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class IYysDestinyServiceImpl implements IYysDestinyService {
    @Autowired
    private YysDestinyMapper yysDestinyMapper;
    
    @Autowired
    private IDicyDictService iDicyDictService;
    
    @Autowired
    private YysDearfriendMapper dearfriendMapper;

    @Autowired
    private IGameService iGameService;


    @Override
    public YysDestiny destiny(String wxid) {
        //没注册的不理
        YysDearfriend dearfriend = dearfriendMapper.selectOne(new QueryWrapper<YysDearfriend>().lambda().eq(YysDearfriend::getWxid, wxid));
        if (dearfriend == null) {
            return null;
        }

        //初始化今天运势
        YysDestiny yysDestiny = yysDestinyMapper.selectOne(new QueryWrapper<YysDestiny>().lambda()
                .eq(YysDestiny::getWxid, wxid)
                .eq(YysDestiny::getDate, new SimpleDateFormat("yyyy-MM-dd").format(new Date()))
        );
        if (yysDestiny == null) {
            yysDestiny = new YysDestiny();
            yysDestiny.setWxid(wxid);
            yysDestiny.setNickname(dearfriend.getNickname());
            Date date = new Date();
            yysDestiny.setDate(date);
            
            String destiny;
            int destinyPoint = RollUtil.hundredRoll();
            //大成功or大失败 直接过
            if (destinyPoint <= 5 || destinyPoint >= 95) {
                if (destinyPoint <= 5) {
                    destiny = "\uD83C\uDF8A欧皇[庆祝]";
                } else {
                    destiny = "\uD83E\uDD2F非酋\uD83C\uDF1A";
                }
            } else {
                //前缀
                if (destinyPoint <= 15) {
                    destiny = "大";
                } else if (destinyPoint <= 35) {
                    destiny = "中";
                } else if (destinyPoint <= 50) {
                    destiny = "小";
                } else {
                    destiny = "末";
                }

                if (RollUtil.hundredRoll() >= 50) {
                    destiny += "吉";
                } else {
                    destiny += "凶";
                }
            }
            yysDestiny.setDestiny(destiny);

            List<DicyDict> destinies = iDicyDictService.getListByType("destiny");
            Map<String, List<DicyDict>> typeMap = destinies.stream().collect(Collectors.groupingBy(DicyDict::getType));
            List<DicyDict> rise = typeMap.get("rise");
            List<DicyDict> ordinary = typeMap.get("ordinary");
            List<DicyDict> fall = typeMap.get("fall");
            int r1, r2, f1, f2;
            r1 = RollUtil.iRoll(ordinary.size())-1;

            do {
                r2 = RollUtil.iRoll(ordinary.size())-1;
            }while (r2 == r1);

            do {
                f1 = RollUtil.iRoll(ordinary.size())-1;
            }while (f1 == r1 || f1 == r2);

            do {
                f2 = RollUtil.iRoll(ordinary.size())-1;
            }while (f2 == r1 || f2 == r2 || f2 == f1);


            //宜
            DicyDict riseEvent = rise.get(RollUtil.iRoll(rise.size()) - 1);
            yysDestiny.setRise1(riseEvent.getValue());
            yysDestiny.setRise2(ordinary.get(r1).getValue());
            yysDestiny.setRise3(ordinary.get(r2).getValue());

            //忌
            DicyDict fallEvent = fall.get(RollUtil.iRoll(rise.size()) - 1);
            yysDestiny.setFall1(fallEvent.getValue());
            yysDestiny.setFall2(ordinary.get(f1).getValue());
            yysDestiny.setFall3(ordinary.get(f2).getValue());

            //游戏
            yysDestiny.setGame(iGameService.selectByid(RollUtil.iRoll(iGameService.countAll())).getGame());

            yysDestinyMapper.insert(yysDestiny);
        }

        return yysDestiny;
    }
}
