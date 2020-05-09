package com.javalearning.minirpc;

import com.javalearning.minirpc.server.RpcServer;
import impl.CalculateServiceImpl;

public class Server {
    public static void main(String[] args) {
        RpcServer server = new RpcServer();
        server.register(CalculateService.class, new CalculateServiceImpl());
        server.start();
    }
}
