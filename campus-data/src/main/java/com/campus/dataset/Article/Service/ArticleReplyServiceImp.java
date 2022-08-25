package com.campus.dataset.Article.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.campus.common.Service.LoginInfoService;
import com.campus.dataset.Article.Entity.ArticleReply;
import com.campus.dataset.Article.Mapper.ArticleReplyMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ArticleReplyServiceImp extends ServiceImpl<ArticleReplyMapper, ArticleReply> implements ArticleReplyService{
    @Resource
    private ArticleReplyMapper mapper;
    @Resource
    private LoginInfoService loginInfoService;
}
