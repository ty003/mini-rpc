package com.javalearning.minirpc.server;

import com.javalearning.minirpc.codec.Decoder;
import com.javalearning.minirpc.codec.Encoder;
import com.javalearning.minirpc.codec.impl.JSONDecoder;
import com.javalearning.minirpc.codec.impl.JSONEncoder;
import com.javalearning.minirpc.transport.TransportServer;
import com.javalearning.minirpc.transport.impl.HttpTransportServer;
import lombok.Data;

/**
 * Server配置类
 */
@Data
public class RpcServerConfig {
    private Class<? extends TransportServer> transportServer = HttpTransportServer.class;
    private Class<? extends Encoder> encoder = JSONEncoder.class;
    private Class<? extends Decoder> decoder = JSONDecoder.class;
    private int port = 8800;
}
