package com.javalearning.minirpc.transport;

import com.javalearning.minirpc.Peer;

import java.io.InputStream;

/**
 * 通信客户端
 * 1. 创建连接
 * 2. 发送数据，等待响应
 * 3. 关闭连接
 */
public interface TransportClient {

    /**
     * 获取连接
     *
     * @param peer 服务端端点（ip和端口号）
     */
    void connect(Peer peer);

    /**
     * 发起请求
     *
     * @param data 请求数据
     * @return 返回数据
     */
    InputStream write(InputStream data);

    /**
     * 断开连接
     */
    void close();
}
