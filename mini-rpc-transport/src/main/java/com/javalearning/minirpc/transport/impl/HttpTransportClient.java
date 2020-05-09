package com.javalearning.minirpc.transport.impl;

import com.javalearning.minirpc.Peer;
import com.javalearning.minirpc.transport.TransportClient;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 基于Http的客户端通信实现
 */
public class HttpTransportClient implements TransportClient {
    private String url;
    @Override
    public void connect(Peer peer) {
        this.url = "http://" + peer.getHost() + ":" + peer.getPort();
    }

    @Override
    public InputStream write(InputStream data) {
        try {
            HttpURLConnection httpConn = (HttpURLConnection) new URL(url).openConnection();
            httpConn.setDoInput(true);
            httpConn.setDoOutput(true);
            httpConn.setUseCaches(false);
            httpConn.setRequestMethod("POST");

            httpConn.connect();
            IOUtils.copy(data, httpConn.getOutputStream());

            int resultCode = httpConn.getResponseCode();
            if(resultCode == HttpURLConnection.HTTP_OK){
                return httpConn.getInputStream();
            }else{
                return httpConn.getErrorStream();
            }
        } catch (IOException e) {
           throw new IllegalStateException(e);
        }
    }

    @Override
    public void close() {

    }
}
