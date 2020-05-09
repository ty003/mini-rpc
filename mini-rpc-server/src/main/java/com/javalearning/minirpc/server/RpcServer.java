package com.javalearning.minirpc.server;

import com.javalearning.minirpc.Request;
import com.javalearning.minirpc.Response;
import com.javalearning.minirpc.codec.Decoder;
import com.javalearning.minirpc.codec.Encoder;
import com.javalearning.minirpc.common.utils.ReflectionUtils;
import com.javalearning.minirpc.transport.RequestHandler;
import com.javalearning.minirpc.transport.TransportServer;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * RPC服务端
 */
@Slf4j
public class RpcServer {
    /**
     * 服务端配置信息
     */
    private RpcServerConfig config;

    /**
     * 通信模块
     */
    private TransportServer net;

    /**
     * 序列化模块
     */
    private Encoder encoder;

    /**
     * 反序列化模块
     */
    private Decoder decoder;

    /**
     * 服务管理器
     */
    private ServiceManager serviceManager;

    /**
     * 服务注册表
     */
    private ServiceInvoker serviceInvoker;


    /**
     * Constructors,使用默认配置构造Rpc服务端
     */
    public RpcServer() {
        this(new RpcServerConfig());
    }

    /**
     * Constructor
     *
     * @param config Rpc服务端配置信息
     */
    public RpcServer(RpcServerConfig config) {
        this.config = config;
        this.net = ReflectionUtils.newInstance(config.getTransportServer());
        this.net.init(config.getPort(), this.handler);
        this.encoder = ReflectionUtils.newInstance(config.getEncoder());
        this.decoder = ReflectionUtils.newInstance(config.getDecoder());
        this.serviceManager = new ServiceManager();
        this.serviceInvoker = new ServiceInvoker();
    }

    /**
     * 接收请求后的handler
     */
    private RequestHandler handler = new RequestHandler() {
        Response response = new Response();

        @Override
        public void onRequest(InputStream in, OutputStream out) {
            try {
                byte[] inBytes = IOUtils.readFully(in, in.available());
                Request request = decoder.decoder(inBytes, Request.class);
                ServiceInstance service = serviceManager.lookup(request);
                log.info("Received request: {}", service);
                response.setCode(0);
                response.setData(serviceInvoker.invoke(service, request));
                response.setMessage("成功");
            } catch (Exception e) {
                response.setCode(1);
                String errMsg = e.getClass().getName() + " : " + e.getMessage();
                response.setMessage("RpcServer got error: " + errMsg);
            } finally {
                byte[] outBytes = encoder.encode(response);
                try {
                    out.write(outBytes);
                    log.info("Response client, call completed!");
                } catch (IOException e) {
                    log.warn(e.getMessage(), e);
                }
            }
        }
    };


    /**
     * 启动服务端
     */
    public void start() {
        net.start();
    }

    /**
     * 关闭服务端
     */
    public void stop() {
        net.stop();
    }

    /**
     * 注册服务
     *
     * @param interfaceClass 服务接口
     * @param impl           服务实现
     * @param <T>            范型
     */
    public <T> void register(Class<T> interfaceClass, T impl) {
        serviceManager.register(interfaceClass, impl);
    }
}

