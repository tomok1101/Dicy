package io.uouo.wechatbot.service.impl;

import io.uouo.wechatbot.entity.Activity;
import io.uouo.wechatbot.mapper.ActivityMapper;
import io.uouo.wechatbot.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActivityServiceImpl implements ActivityService {
    @Autowired
    private ActivityMapper activityMapper;

    @Override
    public int countAll() {
        return activityMapper.selectCount(null);
    }

    @Override
    public Activity selectByid(int id) {
        return activityMapper.selectById(id);
    }
    @Override
    public void add(String activity) {
        activityMapper.insert(new Activity(null,activity));
    }

    @Override
    public void update(Activity activity) {
        activityMapper.updateById(activity);
    }

    @Override
    public List<Activity> list() {
        return activityMapper.selectList(null);
    }

    @Override
    public void delete(int no) {
        activityMapper.deleteById(no);
    }
}
