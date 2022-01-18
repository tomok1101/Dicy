package io.uouo.wechatbot.service.impl;

import io.uouo.wechatbot.entity.Event;
import io.uouo.wechatbot.mapper.EventMapper;
import io.uouo.wechatbot.service.IEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IEventServiceImpl implements IEventService {
    @Autowired
    private EventMapper eventMapper;

    @Override
    public int countAll() {
        return eventMapper.selectCount(null);
    }

    @Override
    public Event selectByid(int id) {
        return eventMapper.selectById(id);
    }
    @Override
    public void add(String event) {
        eventMapper.insert(new Event(null,event));
    }

    @Override
    public void update(Event event) {
        eventMapper.updateById(event);
    }

    @Override
    public List<Event> list() {
        return eventMapper.selectList(null);
    }

    @Override
    public void delete(int no) {
        eventMapper.deleteById(no);
    }
}
