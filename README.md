# 一个最简单的RPC框架





## 一、模块划分

![RPC架构图](https://gitee.com/tongying003/MapDapot/raw/master/img/20200510011854.svg)





## 二、难点分析

### 1. Jetty嵌入

- Server
- ServletContextHandler
- ServletHolder



### 2. 动态代理

- Proxy.newProxyInstance
- RemoteInvoker implements  InvocationHandler



## 三、不足与展望

- 安全性
- 服务端处理能力
- 注册中心
- 集成能力