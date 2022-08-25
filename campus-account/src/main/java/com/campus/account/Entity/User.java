package com.campus.account.Entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("tb_user")
public class User {
    @TableId(value="ID")
    private Integer ID;
    private String name;
    private String password;
    @TableField("bindPhone")
    private String bindPhone;
    @TableField("bindEmail")
    private String bindEmail;
    @TableField("bindSchool")
    private String bindSchool;
    @TableField("lastLoginTime")
    private LocalDateTime lastLoginTime;
    public User(UserDoc doc){
        this.ID=0;
        this.name=doc.getName();
        this.password=doc.getPassword();
        this.bindEmail="";
        this.bindPhone="";
        this.bindSchool="";
        this.lastLoginTime=LocalDateTime.now();
    }
}
