package com.campus.common.Service;

import com.campus.common.Entity.Resp;
import com.campus.common.Entity.UserLoginSaveInfo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class LoginInfoServiceImp implements LoginInfoService{
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    private static final ObjectMapper mapper=new ObjectMapper();

    @Override
    public void setLoginType(int ID) {
        UserLoginSaveInfo info=getLoginInfo();
        info.setType("user");
        info.setID(ID);
        renewLoginInfo(info);
    }

    @Override
    public UserLoginSaveInfo getLoginInfo(){
        try{
            return mapper.readValue(stringRedisTemplate.opsForValue().get("loginInfo"),UserLoginSaveInfo.class);
        }catch(JsonProcessingException ignored){}
        return null;
    }
    @Override
    public String getLoginSchool() {
        return getLoginInfo().getSchool();
    }
    @Override
    public boolean loginOrnot() {
        return getLoginID()!=0;
    }

    @Override
    public String getLoginName() {
        return getLoginInfo().getName();
    }
    @Override
    public String getLoginType() {
        return getLoginInfo().getType();
    }
    @Override
    public String getLoginPlate(){
        return getLoginInfo().getPlate();
    }
    @Override
    public Integer getLoginID(){
        return getLoginInfo().getID();
    }
    @Override
    public Integer getTextID(){
        return getLoginInfo().getTextID();
    }


    @Override
    public void setLoginSchool(String school) {
        UserLoginSaveInfo info=getLoginInfo();
        info.setSchool(school);
        renewLoginInfo(info);
    }
    @Override
    public void setLoginPlate(String plate){
        UserLoginSaveInfo info=getLoginInfo();
        info.setPlate(plate);
        renewLoginInfo(info);
    }
    @Override
    public void setLoginTextID(int ID){
        UserLoginSaveInfo info=getLoginInfo();
        info.setTextID(ID);
        renewLoginInfo(info);
    }

    @Override
    public void renewLoginInfo(UserLoginSaveInfo info) {
        try{
            stringRedisTemplate.opsForValue().set("loginInfo",mapper.writeValueAsString(info));
        }catch(JsonProcessingException ignored){}
    }

    @Override
    public void delLoginInfo() {
        try{
            stringRedisTemplate.opsForValue().set("loginInfo",mapper.writeValueAsString(new UserLoginSaveInfo(0,null,null,null,null,0)));
        }catch(JsonProcessingException ignored){}
    }

    @Override
    public Resp loginJudge(){
        if(loginOrnot()){
            return new Resp(true,null);
        }
        return new Resp(false,"请先登录");
    }
}
