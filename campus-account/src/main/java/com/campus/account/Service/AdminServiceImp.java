package com.campus.account.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.campus.account.Entity.*;
import com.campus.account.Mapper.AdminMapper;
import com.campus.common.Entity.Resp;
import com.campus.common.Entity.UserLoginSaveInfo;
import com.campus.common.Service.LoginInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class AdminServiceImp extends ServiceImpl<AdminMapper, Admin> implements AdminService{
    @Resource
    private AdminMapper mapper;
    @Resource
    private LoginInfoService loginInfoService;
    @Resource
    private UserService userService;

    @Override
    public Admin getAdminByDoc(UserDoc info){
        LambdaQueryWrapper<Admin> wrapper=new LambdaQueryWrapper<>();
        wrapper.eq(Admin::getName,info.getName()).eq(Admin::getPassword,info.getPassword());
        return mapper.selectOne(wrapper);
    }

    @Override
    public Admin getAdminByName(String name){
        LambdaQueryWrapper<Admin> wrapper=new LambdaQueryWrapper<>();
        wrapper.eq(Admin::getName,name);
        return mapper.selectOne(wrapper);
    }
    @Override
    public Resp signUpAdmin(UserDoc info){
        if(!userService.signUpUser(info).isSignal()){
            return new Resp(false,"关联的普通用户存在重名,注册失败");
        }
        int userID=userService.getUserByName(info.getName()).getID();
        mapper.insert(new Admin(info,userID));
        return new Resp(true,"已成功注册新管理员:"+info.getName()+" 及其关联的普通用户(ID:"+userID+")");
    }

    @Override
    public Resp loginAdmin(UserDoc info){
        Admin admin=getAdminByDoc(info);
        if(admin!=null){
            loginInfoService.renewLoginInfo(new UserLoginSaveInfo(admin.getID(),admin.getName(),"admin",null,"校园广场",0));
            return new Resp(true,"登录成功,欢迎您,管理员"+admin.getName());
        }
        return new Resp(false,null);
    }

    @Override
    public Resp updateAdminPassword(UserDoc info){
        LambdaUpdateWrapper<Admin> wrapper=new LambdaUpdateWrapper<>();
        wrapper.eq(Admin::getName,info.getName());
        Admin admin=getAdminByName(info.getName());
        if(admin!=null){
            admin.setPassword(info.getPassword());
            mapper.update(admin,wrapper);
            return new Resp(true,"已成功修改密码");
        }
        return new Resp(false,"原密码不匹配,请重新输入");
    }
}
