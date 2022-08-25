package com.campus.dataset.Article.Controller;

import com.campus.common.Service.LoginInfoService;
import com.campus.dataset.Article.Service.ArticleReplyService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/reply/manage")
public class ArticleReplyManageController {
    @Resource
    private ArticleReplyService service;
    @Resource
    private LoginInfoService loginInfoService;


}
