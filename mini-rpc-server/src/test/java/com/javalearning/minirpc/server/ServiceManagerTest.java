package com.javalearning.minirpc.server;

import com.javalearning.minirpc.Request;
import com.javalearning.minirpc.ServiceDescriptor;
import com.javalearning.minirpc.common.utils.ReflectionUtils;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Method;

import static org.junit.Assert.assertNotNull;

public class ServiceManagerTest {

    ServiceManager serviceManager;

    @Before
    public void init() {
        serviceManager = new ServiceManager();
    }

    @Test
    public void register() {
        TestClass testClass = new TestClass();
        serviceManager.register(TestInterface.class, testClass);
    }

    @Test
    public void lookup() {
        this.register();
        Method method = ReflectionUtils.getPublicMethods(TestInterface.class)[0];
        ServiceDescriptor serviceDesc = ServiceDescriptor.from(TestInterface.class, method);
        Request request = new Request();
        request.setService(serviceDesc);
        ServiceInstance serviceInstance = serviceManager.lookup(request);
        assertNotNull(serviceInstance);
    }
}