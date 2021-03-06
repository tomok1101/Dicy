package io.uouo.wechatbot.service.impl;

import io.uouo.wechatbot.entity.Game;
import io.uouo.wechatbot.mapper.GameMapper;
import io.uouo.wechatbot.service.IGameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IIGameServiceImpl implements IGameService {
    @Autowired
    private GameMapper gameMapper;


    @Override
    public int countAll() {
        return gameMapper.selectCount(null);
    }

    @Override
    public Game selectByid(int id) {
        return gameMapper.selectById(id);
    }

    @Override
    public void add(String game) {
        gameMapper.insert(new Game(null,game));
    }

    @Override
    public void update(Game game) {
        gameMapper.updateById(game);
    }

    @Override
    public List<Game> list() {
        return gameMapper.selectList(null);
    }

    @Override
    public void delete(int no) {
        gameMapper.deleteById(no);
    }
}
