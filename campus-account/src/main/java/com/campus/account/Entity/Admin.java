package com.campus.account.Entity;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("tb_admin")
public class Admin {
    @TableId(value="ID")
    private Integer ID;
    private String name;
    private String password;
    @TableField("userID")
    private Integer userID;

    public Admin(UserDoc doc,int userID){
        this.ID=0;
        this.name=doc.getName();
        this.password=doc.getPassword();
        this.userID=userID;
    }
}
