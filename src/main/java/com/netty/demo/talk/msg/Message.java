package com.netty.demo.talk.msg;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 消息bean
 * @author sdy
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Message implements Serializable {

    private static final long serialVersionUID = -1649020259570010100L;

    /**
     * 消息
     */
    private String msg;

}
