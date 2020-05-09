package com.javalearning.minirpc;

import com.javalearning.minirpc.codec.Decoder;
import com.javalearning.minirpc.codec.Encoder;
import com.javalearning.minirpc.common.utils.ReflectionUtils;

import java.lang.reflect.Proxy;

/**
 * Rpc客户端，构建远程服务的代理
 */
public class RpcClient {
    private RpcClientConfig config;
    private Encoder encoder;
    private Decoder decoder;
    private TransportSelector selector;


    public RpcClient() {
        this(new RpcClientConfig());
    }

    public RpcClient(RpcClientConfig config) {
        this.config = config;
        this.encoder = ReflectionUtils.newInstance(config.getEncoderClass());
        this.decoder = ReflectionUtils.newInstance(config.getDecoderClass());
        this.selector = ReflectionUtils.newInstance(config.getSelectorClass());
        this.selector.init(config.getServers(), config.getConnectCount(), config.getTransportClient());
    }

    /**
     * 获得远程服务的代理对象
     * @param clazz         远程服务接口
     * @param <T>           范型
     * @return              调用远程服务返回对象
     */
    public <T> T getProxy(Class<T> clazz){
        return (T) Proxy.newProxyInstance(getClass().getClassLoader(), new Class[]{clazz}, new RemoteInvocationHandler(clazz, encoder, decoder, selector));
    }

}
