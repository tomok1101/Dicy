package io.uouo.wechatbot.service.impl;

import cn.hutool.core.util.ObjectUtil;
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
        YysDearfriend yysDearfriend = dearfriendMapper.selectById(wxid);
        return yysDearfriend == null?"那谁":yysDearfriend.getNickname();
    }

    @Override
    public boolean add(YysDearfriend dearfriend) {
        YysDearfriend oldDear = dearfriendMapper.selectById(dearfriend.getWxid());
        if (ObjectUtil.isNotEmpty(oldDear)){
            return false;
        }
        dearfriendMapper.insert(dearfriend);
        return true;
    }
}
