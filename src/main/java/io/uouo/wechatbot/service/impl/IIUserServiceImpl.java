package io.uouo.wechatbot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import io.uouo.wechatbot.entity.User;
import io.uouo.wechatbot.mapper.UserMapper;
import io.uouo.wechatbot.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IIUserServiceImpl implements IUserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public void sayMyaName(String wxid, String nickname) {
        User user = userMapper.selectOne(new QueryWrapper<User>().eq("wxid",wxid));
        if (user == null){
            userMapper.insert(new User(null,wxid,nickname,null));
        }else {
            UpdateWrapper<User> updateWrapper = new UpdateWrapper<User>();
            updateWrapper.eq("wxid",user.getWxid()).set("nickname", nickname);
            userMapper.update(null,updateWrapper);
        }
    }
}
