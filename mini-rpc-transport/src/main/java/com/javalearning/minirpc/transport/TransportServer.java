package com.javalearning.minirpc.transport;

/**
 * 通信服务端
 * 1. 启动，监听端口
 * 2. 接收请求
 * 3. 关闭
 */
public interface TransportServer {
    /**
     * 服务端初始化
     *
     * @param port           监听端口
     * @param requestHandler 处理数据的handler
     */
    void init(int port, RequestHandler requestHandler);

    /**
     * 服务端启动监听
     */
    void start();

    /**
     * 服务端关闭
     */
    void stop();
}
