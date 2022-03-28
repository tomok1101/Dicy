package io.uouo.wechatbot.service;

import io.uouo.wechatbot.entity.DicyDict;

import java.util.List;

public interface IDicyDictService {

    DicyDict rollByDict(String code);

    List<DicyDict> holyTriangle();

    List<DicyDict> getListByType(String code);
}
