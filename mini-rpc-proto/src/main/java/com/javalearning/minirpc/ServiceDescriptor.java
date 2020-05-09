package com.javalearning.minirpc;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Objects;

/**
 * 表示服务
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServiceDescriptor {
    private String clazz;
    private String method;
    private String returnType;
    private String[] parameterTypes;

    /**
     * 根据Class和Method实例化ServiceDescriptor
     *
     * @param clazz  Class对象
     * @param method Method对象
     * @return ServiceDescriptor对象
     */
    public static ServiceDescriptor from(Class clazz, Method method) {
        ServiceDescriptor serverDesc = new ServiceDescriptor();
        serverDesc.setClazz(clazz.getName());
        serverDesc.setMethod(method.getName());
        serverDesc.setReturnType(method.getReturnType().getTypeName());
        Class[] paramClasses = method.getParameterTypes();
        String[] paramTypes = new String[paramClasses.length];
        for (int i = 0; i < paramClasses.length; i++) {
            paramTypes[i] = paramClasses[i].getName();
        }
        serverDesc.setParameterTypes(paramTypes);
        return serverDesc;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ServiceDescriptor that = (ServiceDescriptor) o;
        return clazz.equals(that.clazz) &&
                method.equals(that.method) &&
                returnType.equals(that.returnType) &&
                Arrays.equals(parameterTypes, that.parameterTypes);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(clazz, method, returnType);
        result = 31 * result + Arrays.hashCode(parameterTypes);
        return result;
    }
}
