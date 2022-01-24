package io.uouo.wechatbot.service;

import io.uouo.wechatbot.entity.DicyDict;

import java.util.List;

public interface IDicyDictService {

    DicyDict rollByDict(String type);

    List<DicyDict> holyTriangle();
}
