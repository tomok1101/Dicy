package io.uouo.wechatbot.service.impl;

import io.uouo.wechatbot.entity.Food;
import io.uouo.wechatbot.mapper.FoodMapper;
import io.uouo.wechatbot.service.IFoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IFoodServiceImpl implements IFoodService {
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

    @Override
    public void delete(int no) {
        foodMapper.deleteById(no);
    }
}
