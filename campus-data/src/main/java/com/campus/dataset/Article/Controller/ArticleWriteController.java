package com.campus.dataset.Article.Controller;

import com.campus.common.Entity.Resp;
import com.campus.common.Service.LoginInfoService;
import com.campus.dataset.Article.Entity.ArticleDoc;
import com.campus.dataset.Article.Service.ArticleService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/text")
public class ArticleWriteController {
    @Resource
    private ArticleService service;
    @Resource
    private LoginInfoService loginInfoService;

    @PostMapping("/add")
    public Resp addArticle(@RequestBody ArticleDoc info){
        if(!loginInfoService.loginJudge().isSignal()){
            return loginInfoService.loginJudge();
        }
        return service.addArticle(service.fillPlate(info));
    }

    @PostMapping("/update")
    public Resp updateArticle(@RequestBody ArticleDoc info){
        if(!loginInfoService.loginJudge().isSignal()){
            return loginInfoService.loginJudge();
        }
        return service.updateArticle(service.fillPlate(info));
    }

    @PostMapping("/del")
    public Resp delArticle(@RequestBody ArticleDoc info){
        if(!loginInfoService.loginJudge().isSignal()){
            return loginInfoService.loginJudge();
        }
        return service.delArticle(service.fillPlate(info));
    }

}
