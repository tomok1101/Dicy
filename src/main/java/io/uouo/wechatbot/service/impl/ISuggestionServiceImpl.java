package io.uouo.wechatbot.service.impl;

import io.uouo.wechatbot.entity.Suggestion;
import io.uouo.wechatbot.mapper.SuggestionMapper;
import io.uouo.wechatbot.service.ISuggestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ISuggestionServiceImpl implements ISuggestionService {
    @Autowired
    private SuggestionMapper suggestionMapper;

    @Override
    public void send(Suggestion suggestion) {
        suggestionMapper.insert(suggestion);
    }
}
