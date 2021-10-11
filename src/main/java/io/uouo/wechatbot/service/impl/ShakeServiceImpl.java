package io.uouo.wechatbot.service.impl;

import io.uouo.wechatbot.entity.Shake;
import io.uouo.wechatbot.entity.Shakeman;
import io.uouo.wechatbot.mapper.ShakeMapper;
import io.uouo.wechatbot.service.ShakeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

@Service
public class ShakeServiceImpl implements ShakeService {
    @Autowired
    private ShakeMapper shakeMapper;

    @Override
    public void shake(String game, Integer num, int time) {
        Shake shake = new Shake();
        shake.setGame(game);
        shake.setNum(num);
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.DATE, time);
        shake.setEndTime(c.getTime());
        shake.setJoinNum(0);
        shake.setCreatTime(new Date());
        shakeMapper.insert(shake);
    }
}
