package com.campus.dataset.Article.Controller;


import com.campus.common.Entity.Resp;
import com.campus.common.Service.LoginInfoService;
import com.campus.dataset.Article.Service.ArticleReplyService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/reply")
public class ArticleReplyWriteController {
    @Resource
    private ArticleReplyService service;
    @Resource
    private LoginInfoService loginInfoService;

    @PostMapping()
    public Resp addArticleReply(){
        return null;
    }

}
