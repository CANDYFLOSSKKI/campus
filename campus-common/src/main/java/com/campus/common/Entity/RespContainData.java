package com.campus.common.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RespContainData {
    private boolean signal;
    private String message;
    private Object data;

    public RespContainData(Resp resp){
        this.signal=resp.isSignal();
        this.message=resp.getMessage();
        this.data=null;
    }
}
