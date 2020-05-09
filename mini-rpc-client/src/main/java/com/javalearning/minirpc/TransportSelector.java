package com.javalearning.minirpc;

import com.javalearning.minirpc.transport.TransportClient;

import java.util.List;

/**
 * 选择一个Server去连接
 */
public interface TransportSelector {

    /**
     * 初始化Selector
     *
     * @param peers 可以连接的端点信息
     * @param count client与Server建立多少个连接
     * @param clazz client实现Class
     */
    void init(List<Peer> peers, int count, Class<? extends TransportClient> clazz);

    /**
     * @return 选择一个transport与server做交互
     */
    TransportClient select();

    /**
     * 释放用完的client
     */
    void release(TransportClient client);

    /**
     * 关闭selector
     */
    void close();
}
