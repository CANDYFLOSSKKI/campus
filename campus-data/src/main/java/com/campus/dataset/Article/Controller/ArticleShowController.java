package com.campus.dataset.Article.Controller;

import com.campus.common.Entity.Resp;
import com.campus.common.Entity.RespContainData;
import com.campus.common.Service.LoginInfoService;
import com.campus.dataset.Article.Entity.ArticleDoc;
import com.campus.dataset.Article.Service.ArticleService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/text/show")
public class ArticleShowController {
    @Resource
    private ArticleService service;
    @Resource
    private LoginInfoService loginInfoService;

    @GetMapping("/all")
    public RespContainData show(){
        if(!loginInfoService.getLoginPlate().equals("校园广场")&&loginInfoService.loginJudge().isSignal()){
            return service.showPlateArticle();
        }
        return service.showManageArticle();
    }

    @GetMapping("/plate")
    public Resp showPlateNow(){
        if(!loginInfoService.loginJudge().isSignal()){
            return loginInfoService.loginJudge();
        }
        return new Resp(true,"您当前所在的板块是:"+loginInfoService.getLoginPlate());
    }

    @GetMapping("/switch/plate")
    public Resp switchPlateNow(@RequestParam String plate){
        if(!loginInfoService.loginJudge().isSignal()){
            return loginInfoService.loginJudge();
        }
        if(plate.equals("公告栏")){
            return new Resp(false,"无法切换至公告栏板块");
        }
        loginInfoService.setLoginPlate(plate);
        return new Resp(true,"已成功切换板块至:"+plate);
    }

    @GetMapping("/into/{title}")
    public Resp shiftIntoText(@PathVariable String title){
        if(!loginInfoService.loginJudge().isSignal()){
            return loginInfoService.loginJudge();
        }
        return service.shiftIntoText(title);
    }

    @GetMapping("/out")
    public Resp shiftOutText(){
        if(!loginInfoService.loginJudge().isSignal()){
            return loginInfoService.loginJudge();
        }
        loginInfoService.setLoginTextID(0);
        return new Resp(true,"已回到当前板块主页面");
    }
}