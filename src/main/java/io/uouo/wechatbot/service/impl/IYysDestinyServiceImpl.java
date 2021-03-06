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
            rise.addAll(ordinary);
            fall.addAll(ordinary);

            String r1, r2, r3, f1, f2, f3;

            r1 = rise.get(RollUtil.iRoll(rise.size())-1).getValue();
            do {
                r2 = rise.get(RollUtil.iRoll(rise.size())-1).getValue();
            }while (r2.equals(r1));

            do {
                r3 = rise.get(RollUtil.iRoll(rise.size())-1).getValue();
            }while (r3.equals(r1) || r3.equals(r2));

            do {
                f1 = rise.get(RollUtil.iRoll(fall.size())-1).getValue();
            }while (f1.equals(r1) || f1.equals(r2) || f1.equals(r3));

            do {
                f2 = rise.get(RollUtil.iRoll(fall.size())-1).getValue();
            }while (f2.equals(r1) || f2.equals(r2) || f2.equals(r3) || f2.equals(f1));

            do {
                f3 = rise.get(RollUtil.iRoll(fall.size())-1).getValue();
            }while (f3.equals(r1) || f3.equals(r2) || f3.equals(r3) || f3.equals(f1) || f3.equals(f2));


            //宜
            yysDestiny.setRise1(r1);
            yysDestiny.setRise2(r2);
            yysDestiny.setRise3(r3);

            //忌
            yysDestiny.setFall1(f1);
            yysDestiny.setFall2(f2);
            yysDestiny.setFall3(f3);

            //游戏
            yysDestiny.setGame(iGameService.selectByid(RollUtil.iRoll(iGameService.countAll())).getGame());

            yysDestinyMapper.insert(yysDestiny);
        }

        return yysDestiny;
    }
}
