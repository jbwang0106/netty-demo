package com.netty.demo.wechat.demo.session;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Session {

    private String userId;

    private String userName;

    @Override
    public String toString() {
        return userId + " : " + userName;
    }
}
