package io.uouo.wechatbot;

import io.uouo.wechatbot.entity.User;
import io.uouo.wechatbot.mapper.GameMapper;
import io.uouo.wechatbot.mapper.UserMapper;
import io.uouo.wechatbot.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class Test {
    @Autowired
    UserMapper userMapper;
    @Autowired
    GameMapper gameMapper;

    @org.junit.jupiter.api.Test
    void count(){


    }

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            int dice = (int) (Math.random() * 3 + 1);
            System.out.println(dice);
        }
    }

}
