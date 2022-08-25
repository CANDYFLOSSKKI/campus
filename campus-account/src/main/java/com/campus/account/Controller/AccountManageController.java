package com.campus.account.Controller;
import com.campus.account.Entity.User;
import com.campus.account.Service.AdminService;
import com.campus.account.Service.UserService;
import com.campus.common.Entity.Resp;
import com.campus.common.Entity.RespContainData;
import com.campus.common.Service.LoginInfoService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/manage")
public class AccountManageController {
    @Resource
    private UserService userService;
    @Resource
    private AdminService adminService;
    @Resource
    private LoginInfoService loginInfoService;

    @GetMapping("/get/all")
    public RespContainData getAllUsers(){
        if(!loginInfoService.loginJudge().isSignal()){
            return new RespContainData(loginInfoService.loginJudge());
        }
        List<User> list=userService.list();
        if(list.size()!=0){
            return new RespContainData(true,"共查找到"+list.size()+"条用户数据",list);
        }
        return new RespContainData(false,"用户数据为空",null);
    }

    @GetMapping("/get/id")
    public RespContainData getUserById(@RequestParam int id){
        if(!loginInfoService.loginJudge().isSignal()){
            return new RespContainData(loginInfoService.loginJudge());
        }
        User user=userService.getById(id);
        if(user!=null){
            return new RespContainData(true,"成功",user);
        }
        return new RespContainData(false,"对应ID的用户不存在",null);
    }

    @GetMapping("/get/name")
    public RespContainData getUserByName(@RequestParam String name){
        if(!loginInfoService.loginJudge().isSignal()){
            return new RespContainData(loginInfoService.loginJudge());
        }
        User user=userService.getUserByName(name);
        if(user!=null){
            return new RespContainData(true,"成功",user);
        }
        return new RespContainData(false,"对应用户名的用户不存在",null);
    }

    @GetMapping("/del/id")
    public Resp delUserById(@RequestParam int id){
        if(!loginInfoService.loginJudge().isSignal()){
            return loginInfoService.loginJudge();
        }
        if(adminService.getById(id)!=null){
            return new Resp(false,"该用户和管理员用户绑定,删除失败");
        }
        boolean signal=userService.removeById(id);
        if(signal){
            return new Resp(true,"已成功删除"+id+"号用户");
        }
        return new Resp(false,"对应ID的用户不存在");
    }

    @GetMapping("/del/name")
    public Resp delUserByName(@RequestParam String name){
        if(!loginInfoService.loginJudge().isSignal()){
            return loginInfoService.loginJudge();
        }
        if(adminService.getAdminByName(name)!=null){
            return new Resp(false,"该用户和管理员用户绑定,删除失败");
        }
        if(userService.delUserByName(name)){
            return new Resp(true,"已成功删除名为"+name+"的用户");
        }
        return new Resp(false,"对应用户名的用户不存在");
    }
}
