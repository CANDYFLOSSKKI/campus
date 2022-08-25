package com.campus.account.Service;

import com.campus.common.Entity.Resp;
import com.campus.common.Entity.UserLoginSaveInfo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.campus.account.Entity.*;
import com.campus.account.Mapper.UserMapper;
import com.campus.common.Service.LoginInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;

@Service
public class UserServiceImp extends ServiceImpl<UserMapper, User> implements UserService {
    @Resource
    private UserMapper mapper;
    @Resource
    private LoginInfoService loginInfoService;

    @Override
    public User getUserByDoc(UserDoc info){
        LambdaQueryWrapper<User> wrapper=new LambdaQueryWrapper<>();
        wrapper.eq(User::getName,info.getName()).eq(User::getPassword,info.getPassword());
        return mapper.selectOne(wrapper);
    }

    @Override
    public User getUserByName(String name){
        LambdaQueryWrapper<User> wrapper=new LambdaQueryWrapper<>();
        wrapper.eq(User::getName,name);
        return mapper.selectOne(wrapper);
    }

    @Override
    public boolean delUserByName(String name){
        LambdaQueryWrapper<User> wrapper=new LambdaQueryWrapper<>();
        wrapper.eq(User::getName,name);
        return mapper.delete(wrapper)==1;
    }

    @Override
    public Resp signUpUser(UserDoc info){
        if(getUserByDoc(info)==null){
            mapper.insert(new User(info));
            return new Resp(true,"已成功注册新用户:"+info.getName());
        }
        return new Resp(false,"已存在同名用户,注册失败");
    }

    @Override
    public Resp loginUser(UserDoc info){
        LambdaQueryWrapper<User> wrapper=new LambdaQueryWrapper<>();
        wrapper.eq(User::getName,info.getName());
        User user=getUserByDoc(info);
        if(user!=null){
            user.setLastLoginTime(LocalDateTime.now());
            mapper.update(user,wrapper);
            loginInfoService.renewLoginInfo(new UserLoginSaveInfo(user.getID(),user.getName(),"user",user.getBindSchool(),user.getBindSchool(),0));
            return new Resp(true,"登录成功,欢迎您,用户:"+info.getName());
        }
        return new Resp(false,"用户名或密码错误,登录失败");
    }

    @Override
    public Resp updateUserPassword(UserDoc info){
        LambdaUpdateWrapper<User> wrapper=new LambdaUpdateWrapper<>();
        wrapper.eq(User::getName,info.getName());
        User user=getUserByName(info.getName());
        if(user!=null){
            user.setPassword(info.getPassword());
            mapper.update(user,wrapper);
            return new Resp(true,"已成功修改密码");
        }
        return new Resp(false,"原密码不匹配,请重新输入");
    }

    @Override
    public Resp bindUserPhone(String phone){
        LambdaQueryWrapper<User> wrapperSelect=new LambdaQueryWrapper<>();
        wrapperSelect.eq(User::getBindPhone,phone);
        if(mapper.selectOne(wrapperSelect)!=null){
            return new Resp(false,"该手机号已被绑定");
        }
        LambdaUpdateWrapper<User> wrapper=new LambdaUpdateWrapper<>();
        wrapper.eq(User::getName,loginInfoService.getLoginName());
        User user=getUserByName(loginInfoService.getLoginName());
        user.setBindPhone(phone);
        mapper.update(user,wrapper);
        return new Resp(true,"已成功修改绑定手机:"+phone);
    }

    @Override
    public Resp bindUserEmail(String email){
        LambdaQueryWrapper<User> wrapperSelect=new LambdaQueryWrapper<>();
        wrapperSelect.eq(User::getBindEmail,email);
        if(mapper.selectOne(wrapperSelect)!=null){
            return new Resp(false,"该邮箱已被绑定");
        }
        LambdaUpdateWrapper<User> wrapper=new LambdaUpdateWrapper<>();
        wrapper.eq(User::getName,loginInfoService.getLoginName());
        User user=getUserByName(loginInfoService.getLoginName());
        user.setBindEmail(email);
        mapper.update(user,wrapper);
        return new Resp(true,"已成功修改绑定邮箱:"+email);
    }

    @Override
    public Resp bindUserSchool(String school) {
        LambdaUpdateWrapper<User> wrapper=new LambdaUpdateWrapper<>();
        wrapper.eq(User::getName,loginInfoService.getLoginName());
        User user=getUserByName(loginInfoService.getLoginName());
        user.setBindSchool(school);
        mapper.update(user,wrapper);
        loginInfoService.setLoginSchool(school);
        loginInfoService.setLoginPlate(school);
        return new Resp(true,"已成功修改绑定学校:"+school);
    }
}
