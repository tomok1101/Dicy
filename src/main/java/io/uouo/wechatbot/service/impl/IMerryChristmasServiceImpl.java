package io.uouo.wechatbot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.uouo.wechatbot.common.util.RollUtil;
import io.uouo.wechatbot.entity.MerryChristmas;
import io.uouo.wechatbot.mapper.MerryChristmasMapper;
import io.uouo.wechatbot.service.IMerryChristmasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IMerryChristmasServiceImpl implements IMerryChristmasService {
    @Autowired
    private MerryChristmasMapper giftMapper;


    @Override
    public void reset() {
        giftMapper.delete(null);
        MerryChristmas gift1 = new MerryChristmas();
        gift1.setWxid("wxid_ary60w783fjn21");
        gift1.setGift("tom");
        giftMapper.insert(gift1);
        MerryChristmas gift2 = new MerryChristmas();
        gift2.setWxid("watermelonhang");
        gift2.setGift("柯基");
        giftMapper.insert(gift2);
        MerryChristmas gift3 = new MerryChristmas();
        gift3.setWxid("watermelonhang");
        gift3.setGift("豆豆");
        giftMapper.insert(gift3);
        MerryChristmas gift4 = new MerryChristmas();
        gift4.setWxid("watermelonhang");
        gift4.setGift("东宝");
        giftMapper.insert(gift4);
        MerryChristmas gift5 = new MerryChristmas();
        gift5.setWxid("watermelonhang");
        gift5.setGift("董董");
        giftMapper.insert(gift5);
        MerryChristmas gift6 = new MerryChristmas();
        gift6.setWxid("watermelonhang");
        gift6.setGift("蟹蟹");
        giftMapper.insert(gift6);
        MerryChristmas gift7 = new MerryChristmas();
        gift7.setWxid("watermelonhang");
        gift7.setGift("狗蛋");
        giftMapper.insert(gift7);
        MerryChristmas gift8 = new MerryChristmas();
        gift8.setWxid("watermelonhang");
        gift8.setGift("呵呵");
        giftMapper.insert(gift8);
        MerryChristmas gift9 = new MerryChristmas();
        gift9.setWxid("watermelonhang");
        gift9.setGift("无海");
        giftMapper.insert(gift9);
        MerryChristmas gift10 = new MerryChristmas();
        gift10.setWxid("wxid_b7n18ak51za621");
        gift10.setGift("AP");
        giftMapper.insert(gift10);
        MerryChristmas gift11 = new MerryChristmas();
        gift11.setWxid("watermelonhang");
        gift11.setGift("香辛料");
        giftMapper.insert(gift11);
        MerryChristmas gift12 = new MerryChristmas();
        gift12.setWxid("watermelonhang");
        gift12.setGift("鬼鬼");
        giftMapper.insert(gift12);
        MerryChristmas gift13 = new MerryChristmas();
        gift13.setWxid("watermelonhang");
        gift13.setGift("Johannes");
        giftMapper.insert(gift13);
    }

    @Override
    public String get(String wxid) {
        List<MerryChristmas> giftList = giftMapper.selectList(null);
        int i = RollUtil.iRoll(giftList.size()) - 1;
        String gift = giftList.get(i).getGift();
        giftMapper.deleteById(giftList.get(i).getId());
        return gift;
    }

    @Override
    public void add(String name) {
        MerryChristmas gift13 = new MerryChristmas();
        gift13.setWxid("watermelonhang");
        gift13.setGift(name);
        giftMapper.insert(gift13);
    }

    @Override
    public void del(String name) {
        giftMapper.delete(new QueryWrapper<MerryChristmas>().eq("gift",name));
    }

}
