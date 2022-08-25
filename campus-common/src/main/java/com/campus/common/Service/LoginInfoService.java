package com.campus.common.Service;

import com.campus.common.Entity.Resp;
import com.campus.common.Entity.UserLoginSaveInfo;

public interface LoginInfoService {

    //1:LoginInfo整体操作方法
    void renewLoginInfo(UserLoginSaveInfo info);
    UserLoginSaveInfo getLoginInfo();
    void delLoginInfo();


    //2:LoginInfo单字段getter
    String getLoginName();

    String getLoginType();

    String getLoginSchool();
    String getLoginPlate();
    Integer getLoginID();
    Integer getTextID();


    //3:Login单字段setter
    void setLoginSchool(String school);
    void setLoginPlate(String plate);
    void setLoginType(int ID);
    void setLoginTextID(int ID);


    //4:登录判断
    boolean loginOrnot();
    Resp loginJudge();          //仅判断是否登录
}
