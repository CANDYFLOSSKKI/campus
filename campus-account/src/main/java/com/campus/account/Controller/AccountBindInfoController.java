package com.campus.account.Controller;
import com.campus.account.Service.UserService;
import com.campus.common.Entity.Resp;
import com.campus.common.Service.LoginInfoService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/bind")
public class AccountBindInfoController {
    @Resource
    private UserService userService;
    @Resource
    private LoginInfoService loginInfoService;

    @GetMapping("/phone")
    public Resp bindUserPhone(@RequestParam String phone){
        if(!loginInfoService.loginJudge().isSignal()){
            return loginInfoService.loginJudge();
        }
        return userService.bindUserPhone(phone);
    }

    @GetMapping("/email")
    public Resp bindUserEmail(@RequestParam String email){
        if(!loginInfoService.loginJudge().isSignal()){
            return loginInfoService.loginJudge();
        }
        return userService.bindUserEmail(email);
    }

    @GetMapping("/school")
    public Resp bindUserSchool(@RequestParam String school){
        if(!loginInfoService.loginJudge().isSignal()){
            return loginInfoService.loginJudge();
        }
        return userService.bindUserSchool(school);
    }
}
