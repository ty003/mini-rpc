package com.javalearning.minirpc;

import com.javalearning.minirpc.codec.Decoder;
import com.javalearning.minirpc.codec.Encoder;
import com.javalearning.minirpc.codec.impl.JSONDecoder;
import com.javalearning.minirpc.codec.impl.JSONEncoder;
import com.javalearning.minirpc.transport.TransportClient;
import com.javalearning.minirpc.transport.impl.HttpTransportClient;
import lombok.Data;

import java.util.Arrays;
import java.util.List;

/**
 * Rpc客户端配置类
 */
@Data
public class RpcClientConfig {
    private Class<? extends TransportClient> transportClient = HttpTransportClient.class;
    private Class<? extends Encoder> encoderClass = JSONEncoder.class;
    private Class<? extends Decoder> decoderClass = JSONDecoder.class;
    private Class<? extends TransportSelector> selectorClass = RandomTransportSelector.class;
    private int connectCount = 1;
    private List<Peer> servers = Arrays.asList(new Peer("127.0.0.1", 8800));
}
