package com.javalearning.minirpc.server;

import com.javalearning.minirpc.Request;
import com.javalearning.minirpc.ServiceDescriptor;
import com.javalearning.minirpc.common.utils.ReflectionUtils;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 管理RPC暴露的服务
 */
@Slf4j
public class ServiceManager {

    /**
     * 服务注册表
     */
    private Map<ServiceDescriptor, ServiceInstance> services;

    public ServiceManager() {
        this.services = new ConcurrentHashMap<>();
    }

    /**
     * 注册服务
     *
     * @param interfaceClass 服务接口
     * @param impl           服务实现类
     * @param <T>            范型
     */
    public <T> void register(Class<T> interfaceClass, T impl) {
        Method[] methods = ReflectionUtils.getPublicMethods(interfaceClass);
        for (Method m : methods) {
            ServiceInstance serviceInstance = new ServiceInstance(impl, m);
            ServiceDescriptor serviceDesc = ServiceDescriptor.from(interfaceClass, m);
            services.put(serviceDesc, serviceInstance);
            log.info("Register service: {}: {}", serviceDesc.getClazz(), serviceDesc.getMethod());
        }
    }

    /**
     * 查询服务
     *
     * @param req 请求
     * @return 服务
     */
    public ServiceInstance lookup(Request req) {
        ServiceDescriptor serviceDesc = req.getService();
        return services.get(serviceDesc);
    }
}
