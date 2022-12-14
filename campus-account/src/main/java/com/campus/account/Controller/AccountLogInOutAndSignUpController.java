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
        return new Resp(true,"η»εΊζε");
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
        return new Resp(true,"ε·²εζ’θ³"+(loginInfoService.getLoginType().equals("user")?"ζ?ι":"η?‘ηε")+"η¨ζ·,ID:"+loginInfoService.getLoginID());
    }

    @GetMapping("/me")
    public RespContainData getLoginInfo(){
        if(!loginInfoService.loginJudge().isSignal()){
            return new RespContainData(loginInfoService.loginJudge());
        }
        RespContainData resp=new RespContainData();
        resp.setSignal(true);
        if(loginInfoService.getLoginType().equals("user")){
            resp.setMessage("ζ¨ε½εηθΊ«δ»½δΈΊη¨ζ·,δΈι’ζ―εΊζ¬δΏ‘ζ―");
            resp.setData(userService.getById(loginInfoService.getLoginID()));
        }else{
            resp.setMessage("ζ¨ε½εηθΊ«δ»½δΈΊη?‘ηε,δΈι’ζ―εΊζ¬δΏ‘ζ―εε³θηζ?ιη¨ζ·δΏ‘ζ―");
            List<Object> list=new ArrayList<Object>();
            Admin admin=adminService.getById(loginInfoService.getLoginID());
            list.add(admin);
            list.add(userService.getById(admin.getUserID()));
            resp.setData(list);
        }
        return resp;
    }
}
