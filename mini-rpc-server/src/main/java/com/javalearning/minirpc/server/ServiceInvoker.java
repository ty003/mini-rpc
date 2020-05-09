package com.javalearning.minirpc.server;

import com.javalearning.minirpc.Request;
import com.javalearning.minirpc.common.utils.ReflectionUtils;

/**
 * 调用服务
 */
public class ServiceInvoker {
    public Object invoke(ServiceInstance service, Request req) {
        return ReflectionUtils.invoke(service.getTarget(), service.getMethod(), req.getParameters());
    }
}
