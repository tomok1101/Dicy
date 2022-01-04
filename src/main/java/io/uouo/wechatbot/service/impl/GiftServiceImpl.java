package io.uouo.wechatbot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.uouo.wechatbot.entity.Game;
import io.uouo.wechatbot.entity.Gift;
import io.uouo.wechatbot.mapper.GameMapper;
import io.uouo.wechatbot.mapper.GiftMapper;
import io.uouo.wechatbot.service.GiftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GiftServiceImpl implements GiftService {
    @Autowired
    private GiftMapper giftMapper;


    @Override
    public void put() {
        giftMapper.delete(null);
        Gift gift1 = new Gift();
        gift1.setWxid("wxid_ary60w783fjn21");
        gift1.setGift("tom");
        giftMapper.insert(gift1);
        Gift gift2 = new Gift();
        gift2.setWxid("watermelonhang");
        gift2.setGift("柯基");
        giftMapper.insert(gift2);
        Gift gift3 = new Gift();
        gift3.setWxid("watermelonhang");
        gift3.setGift("豆豆");
        giftMapper.insert(gift3);
        Gift gift4 = new Gift();
        gift4.setWxid("watermelonhang");
        gift4.setGift("东宝");
        giftMapper.insert(gift4);
        Gift gift5 = new Gift();
        gift5.setWxid("watermelonhang");
        gift5.setGift("董董");
        giftMapper.insert(gift5);
        Gift gift6 = new Gift();
        gift6.setWxid("watermelonhang");
        gift6.setGift("蟹蟹");
        giftMapper.insert(gift6);
        Gift gift7 = new Gift();
        gift7.setWxid("watermelonhang");
        gift7.setGift("狗蛋");
        giftMapper.insert(gift7);
        Gift gift8 = new Gift();
        gift8.setWxid("watermelonhang");
        gift8.setGift("呵呵");
        giftMapper.insert(gift8);
        Gift gift9 = new Gift();
        gift9.setWxid("watermelonhang");
        gift9.setGift("无海");
        giftMapper.insert(gift9);
        Gift gift10 = new Gift();
        gift10.setWxid("wxid_b7n18ak51za621");
        gift10.setGift("AP");
        giftMapper.insert(gift10);
        Gift gift11 = new Gift();
        gift11.setWxid("watermelonhang");
        gift11.setGift("香辛料");
        giftMapper.insert(gift11);
        Gift gift12 = new Gift();
        gift12.setWxid("watermelonhang");
        gift12.setGift("鬼鬼");
        giftMapper.insert(gift12);
        Gift gift13 = new Gift();
        gift13.setWxid("watermelonhang");
        gift13.setGift("Johannes");
        giftMapper.insert(gift13);
    }

    @Override
    public String get(String wxid) {
        List<Gift> giftList = giftMapper.selectList(null);
        int i = listRoll(giftList.size()) - 1;
        String gift = giftList.get(i).getGift();
        giftMapper.deleteById(giftList.get(i).getId());
        return gift;
    }

    @Override
    public void add(String name) {
        Gift gift13 = new Gift();
        gift13.setWxid("watermelonhang");
        gift13.setGift(name);
        giftMapper.insert(gift13);
    }

    @Override
    public void del(String name) {
        giftMapper.delete(new QueryWrapper<Gift>().eq("gift",name));
    }


    private int listRoll(int i) {
        int dice = (int) (Math.random() * i +1);
        return dice;
    }

}
