package com.campus.account.Service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.campus.account.Entity.Admin;
import com.campus.account.Entity.UserDoc;
import com.campus.common.Entity.Resp;

public interface AdminService extends IService<Admin> {

    Admin getAdminByDoc(UserDoc info);
    Admin getAdminByName(String name);
    Resp signUpAdmin(UserDoc info);
    Resp loginAdmin(UserDoc info);

    Resp updateAdminPassword(UserDoc info);
}
