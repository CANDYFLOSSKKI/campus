package com.campus.dataset.Article.Controller;

import com.campus.common.Entity.Resp;
import com.campus.common.Service.LoginInfoService;
import com.campus.dataset.Article.Entity.ArticleDoc;
import com.campus.dataset.Article.Service.ArticleService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/text/manage")
public class ArticleManageController {
    @Resource
    private ArticleService service;
    @Resource
    private LoginInfoService loginInfoService;

    @PostMapping("/setad")
    public Resp setAdArticle(@RequestBody ArticleDoc info){
        if(!loginInfoService.loginJudge().isSignal()){
            return loginInfoService.loginJudge();
        }
        return service.setAdArticle(info);
    }

}
