package io.uouo.wechatbot;

import io.uouo.wechatbot.mapper.GameMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class Test {
    @Autowired
    GameMapper gameMapper;

    @org.junit.jupiter.api.Test
    void count(){


    }

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            int dice = (int) (Math.random() * (-114 + 114 + 1) - 114);
            System.out.println(dice);
        }
    }

}
