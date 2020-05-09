package com.javalearning.minirpc;

public class Client {
    public static void main(String[] args) {
        RpcClient client = new RpcClient();
        CalculateService service = client.getProxy(CalculateService.class);
        int res1 = service.add(1, 2);
        int res2 = service.minus(10, 3);
        System.out.println(res1);
        System.out.println(res2);
    }
}
