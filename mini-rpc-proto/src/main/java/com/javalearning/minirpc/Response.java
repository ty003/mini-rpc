package com.javalearning.minirpc;

import lombok.Data;

/**
 * 表示RPC调用的返回
 */
@Data
public class Response {
    /**
     * 服务返回编码，0表示成功，非0表示失败
     */
    private int code = 0;
    /**
     * 具体的错误信息
     */
    private String message = "ok";
    /**
     * 返回的数据
     */
    private Object data;
}
