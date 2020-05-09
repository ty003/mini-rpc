package com.javalearning.minirpc;

import com.javalearning.minirpc.codec.Decoder;
import com.javalearning.minirpc.codec.Encoder;
import com.javalearning.minirpc.transport.TransportClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 代理对象的handler
 */
@Slf4j
public class RemoteInvocationHandler implements InvocationHandler {
    private Class clazz;
    private Encoder encoder;
    private Decoder decoder;
    private TransportSelector selector;

    public <T> RemoteInvocationHandler(Class<T> clazz, Encoder encoder, Decoder decoder, TransportSelector selector) {
        this.clazz = clazz;
        this.encoder = encoder;
        this.decoder = decoder;
        this.selector = selector;
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Request request = new Request();
        request.setService(ServiceDescriptor.from(clazz, method));
        request.setParameters(args);

        Response response = invokeRemote(request);
        if (response == null || response.getCode() != 0) {
            throw new IllegalStateException("Fail to invoke remote: " + response);
        }
        return response.getData();
    }

    /**
     * 远程方法调用
     * @param request       请求对象
     * @return              远程服务返回信息
     */
    private Response invokeRemote(Request request) {
        Response response = null;
        TransportClient client = null;

        try {
            client = selector.select();
            byte[] outByte = encoder.encode(request);
            InputStream received = client.write(new ByteArrayInputStream(outByte));
            byte[] inBytes = IOUtils.readFully(received, received.available());
            response = decoder.decoder(inBytes, Response.class);
        } catch (IOException e) {
            log.warn(e.getMessage(), e);
            response = new Response();
            response.setCode(1);
            String errMsg = e.getClass() + " : " + e.getMessage();
            response.setMessage("Rpc client got error: " + errMsg);
            e.printStackTrace();
        } finally {
            if (client != null) {
                selector.release(client);
            }
        }
        return response;
    }
}
