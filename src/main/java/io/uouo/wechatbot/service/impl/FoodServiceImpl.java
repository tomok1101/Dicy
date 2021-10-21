package io.uouo.wechatbot.service.impl;

import io.uouo.wechatbot.entity.Food;
import io.uouo.wechatbot.entity.Game;
import io.uouo.wechatbot.entity.Shake;
import io.uouo.wechatbot.mapper.FoodMapper;
import io.uouo.wechatbot.mapper.ShakeMapper;
import io.uouo.wechatbot.service.FoodService;
import io.uouo.wechatbot.service.ShakeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class FoodServiceImpl implements FoodService {
    @Autowired
    private FoodMapper foodMapper;

    @Override
    public int countAll() {
        return foodMapper.selectCount(null);
    }

    @Override
    public Food selectByid(int id) {
        return foodMapper.selectById(id);
    }

    @Override
    public void add(String food) {
        foodMapper.insert(new Food(null,food));
    }

    @Override
    public void update(Food food) {
        foodMapper.updateById(food);
    }

    @Override
    public List<Food> list() {
        return foodMapper.selectList(null);
    }
}
