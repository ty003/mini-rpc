package com.javalearning.minirpc.transport.impl;

import com.javalearning.minirpc.transport.RequestHandler;
import com.javalearning.minirpc.transport.TransportServer;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 基于Http的通信服务端实现
 */
@Slf4j
public class HttpTransportServer implements TransportServer {

    private Server server;
    private RequestHandler handler;

    @Override
    public void init(int port, RequestHandler requestHandler) {
        this.handler = requestHandler;
        this.server = new Server(port);

        // Servlet接收请求
        ServletContextHandler context = new ServletContextHandler();
        server.setHandler(context);
        ServletHolder holder = new ServletHolder(new RequestServlet());
        context.addServlet(holder, "/*");
    }

    @Override
    public void start() {
        try {
            server.start();
            server.join();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    @Override
    public void stop() {
        try {
            server.stop();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    class RequestServlet extends HttpServlet {
        @Override
        protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
             InputStream inputStream = req.getInputStream();
            OutputStream outputStream = resp.getOutputStream();
            if (handler != null) {
                handler.onRequest(inputStream, outputStream);
            }
            outputStream.flush();
        }
    }
}
