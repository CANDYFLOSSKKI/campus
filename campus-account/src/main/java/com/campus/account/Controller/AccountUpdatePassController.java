package com.campus.account.Controller;
import com.campus.account.Entity.UserDoc;
import com.campus.account.Service.AdminService;
import com.campus.account.Service.UserService;
import com.campus.common.Entity.Resp;
import com.campus.common.Service.LoginInfoService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/update")
public class AccountUpdatePassController {
    @Resource
    private UserService userService;
    @Resource
    private AdminService adminService;
    @Resource
    private LoginInfoService loginInfoService;

    @PostMapping("/now")
    public Resp updateNowLoginPassword(@RequestParam String password){
        if(!loginInfoService.loginJudge().isSignal()){
            return loginInfoService.loginJudge();
        }
        if(loginInfoService.getLoginType().equals("user")){
            return updateUserPassword(new UserDoc(loginInfoService.getLoginName(),password));
        }
        return updateAdminPassword(new UserDoc(loginInfoService.getLoginName(),password));
    }

    @PostMapping("/user")
    public Resp updateUserPassword(@RequestBody UserDoc info){
        if(!loginInfoService.loginJudge().isSignal()){
            return loginInfoService.loginJudge();
        }
        return userService.updateUserPassword(info);
    }

    @PostMapping("/admin")
    public Resp updateAdminPassword(@RequestBody UserDoc info){
        if(!loginInfoService.loginJudge().isSignal()){
            return loginInfoService.loginJudge();
        }
        return adminService.updateAdminPassword(info);
    }
}
