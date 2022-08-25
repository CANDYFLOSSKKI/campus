package com.campus.common.Entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginSaveInfo {
    private Integer ID;
    private String name;
    private String type;
    private String school;
    private String plate;
    private Integer textID;
}
