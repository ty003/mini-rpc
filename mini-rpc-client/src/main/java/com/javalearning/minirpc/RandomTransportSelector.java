package com.javalearning.minirpc;

import com.javalearning.minirpc.common.utils.ReflectionUtils;
import com.javalearning.minirpc.transport.TransportClient;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 随机Selector，相当于负载均衡机制random
 */
@Slf4j
public class RandomTransportSelector implements TransportSelector {

    /**
     * 已经建立连接的Client
     */
    private List<TransportClient> clientList;


    public RandomTransportSelector() {
        this.clientList = new CopyOnWriteArrayList<>();
    }

    @Override
    public void init(List<Peer> peers, int count, Class<? extends TransportClient> clazz) {
        count = Math.max(count, 1);
        for (Peer peer : peers) {
            for (int i = 0; i < count; i++) {
                TransportClient client = ReflectionUtils.newInstance(clazz);
                client.connect(peer);
                clientList.add(client);
            }
            log.info("Connect server: {}", peer);
        }
    }

    @Override
    public TransportClient select() {
        int idx = new Random().nextInt(clientList.size());
        return clientList.remove(idx);
    }

    @Override
    public void release(TransportClient client) {
        clientList.add(client);
    }

    @Override
    public void close() {
        for (TransportClient client : clientList) {
            client.close();
        }
        clientList.clear();
    }
}
