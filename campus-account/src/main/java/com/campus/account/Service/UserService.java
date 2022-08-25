package com.campus.account.Service;

import com.campus.common.Entity.Resp;
import com.baomidou.mybatisplus.extension.service.IService;
import com.campus.account.Entity.*;

public interface UserService extends IService<User> {

    User getUserByDoc(UserDoc info);
    User getUserByName(String name);

    boolean delUserByName(String name);

    Resp signUpUser(UserDoc info);
    Resp loginUser(UserDoc info);
    Resp updateUserPassword(UserDoc info);

    Resp bindUserPhone(String phone);
    Resp bindUserEmail(String email);
    Resp bindUserSchool(String school);
}
