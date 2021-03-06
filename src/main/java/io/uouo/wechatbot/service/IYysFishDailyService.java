package io.uouo.wechatbot.service;

import io.uouo.wechatbot.entity.SpellEvent;
import io.uouo.wechatbot.entity.YysFishDaily;

import java.util.Map;

public interface IYysFishDailyService {

    //摸了
    void touch(String wxid);

    //摸了多少
    YysFishDaily touchLv(String wxid);

    //日摸量
    Map<String, Object> touchToday();

    //施法
    Map<String, Object> spellcasting(String wxid, String nickname, SpellEvent event, String type);


}
