package io.uouo.wechatbot.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.uouo.wechatbot.entity.YysDearfriend;
import io.uouo.wechatbot.mapper.YysDearfriendMapper;
import io.uouo.wechatbot.service.IYysDearfriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IYysDearfriendServiceImpl implements IYysDearfriendService {
    @Autowired
    private YysDearfriendMapper dearfriendMapper;


    @Override
    public String check(String wxid) {
        YysDearfriend yysDearfriend = dearfriendMapper.selectOne(new QueryWrapper<YysDearfriend>().lambda().eq(YysDearfriend::getWxid,wxid));
        return yysDearfriend == null?"那个谁":yysDearfriend.getNickname();
    }

    @Override
    public boolean add(YysDearfriend dearfriend) {
        YysDearfriend oldDear = dearfriendMapper.selectOne(new QueryWrapper<YysDearfriend>().lambda().eq(YysDearfriend::getWxid,dearfriend.getWxid()));
        if (ObjectUtil.isNotEmpty(oldDear)){
            return false;
        }
        dearfriendMapper.insert(dearfriend);
        return true;
    }
}
