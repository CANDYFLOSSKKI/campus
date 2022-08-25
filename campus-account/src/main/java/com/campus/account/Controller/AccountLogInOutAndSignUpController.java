package com.campus.account.Controller;

import com.campus.account.Entity.Admin;
import com.campus.account.Entity.UserDoc;
import com.campus.account.Service.AdminService;
import com.campus.account.Service.UserService;
import com.campus.common.Entity.Resp;
import com.campus.common.Entity.RespContainData;
import com.campus.common.Service.LoginInfoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@RestController
public class AccountLogInOutAndSignUpController {
    @Resource
    private UserService userService;
    @Resource
    private AdminService adminService;
    @Resource
    private LoginInfoService loginInfoService;


    @PostMapping("/signup/user")
    public Resp signUpUser(@RequestBody UserDoc info){
        return userService.signUpUser(info);
    }

    @PostMapping("/signup/admin")
    public Resp signUpAdmin(@RequestBody UserDoc info){
        if(!loginInfoService.loginJudge().isSignal()){
            return loginInfoService.loginJudge();
        }
        return adminService.signUpAdmin(info);
    }

    @PostMapping("/login")
    public Resp loginUserAndAdmin(@RequestBody UserDoc info){
        Resp resp=adminService.loginAdmin(info);
        if(resp.isSignal()){
           return resp;
        }
        return userService.loginUser(info);
    }

    @PostMapping("/logout")
    public Resp logOut(){
        if(!loginInfoService.loginJudge().isSignal()){
            return loginInfoService.loginJudge();
        }
        loginInfoService.delLoginInfo();
        return new Resp(true,"登出成功");
    }

    @PostMapping("/switch")
    public Resp switchLogin(){
        if(!loginInfoService.loginJudge().isSignal()){
            return loginInfoService.loginJudge();
        }
        int userID=adminService.getById(loginInfoService.getLoginID()).getUserID();
        loginInfoService.setLoginType(userID);
        loginInfoService.setLoginSchool(userService.getById(userID).getBindSchool());
        loginInfoService.setLoginPlate(loginInfoService.getLoginSchool());
        return new Resp(true,"已切换至"+(loginInfoService.getLoginType().equals("user")?"普通":"管理员")+"用户,ID:"+loginInfoService.getLoginID());
    }

    @GetMapping("/me")
    public RespContainData getLoginInfo(){
        if(!loginInfoService.loginJudge().isSignal()){
            return new RespContainData(loginInfoService.loginJudge());
        }
        RespContainData resp=new RespContainData();
        resp.setSignal(true);
        if(loginInfoService.getLoginType().equals("user")){
            resp.setMessage("您当前的身份为用户,下面是基本信息");
            resp.setData(userService.getById(loginInfoService.getLoginID()));
        }else{
            resp.setMessage("您当前的身份为管理员,下面是基本信息及关联的普通用户信息");
            List<Object> list=new ArrayList<Object>();
            Admin admin=adminService.getById(loginInfoService.getLoginID());
            list.add(admin);
            list.add(userService.getById(admin.getUserID()));
            resp.setData(list);
        }
        return resp;
    }
}
