package io.uouo.wechatbot;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.uouo.wechatbot.entity.Shakeman;
import io.uouo.wechatbot.entity.User;
import io.uouo.wechatbot.mapper.ShakeMapper;
import io.uouo.wechatbot.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;

@SpringBootTest
class WechatBotApplicationTests {

    @Autowired
    private ShakeMapper shakemanMapper;

    @Test
    public void test() {
//        List<User> userList = userMapper.selectList(null);
//        for (User user : userList) {
//            System.out.println(user);
//        }
//
//        User user = userMapper.selectOne(new QueryWrapper<User>().eq("wxid","test"));
//        System.out.println(user);
//        Shakeman shakeman = new Shakeman("test",10,1,new Date(),new Date());
//        shakemanMapper.insert(shakeman);
    }

}
