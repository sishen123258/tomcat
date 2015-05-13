package com.tong.connector.http;

import com.tong.connector.HttpConnector;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Created by Tong on 2015/5/13.
 */
public class HttpProcessor {

    private HttpConnector connector;
    private HttpRequest request;
    private HttpResponse response;
    private HttpRequestLine requestLine;

    protected String method = null;
    protected String queryString = null;

    public HttpProcessor(HttpConnector connector) {
        this.connector=connector;
    }

    public void process(Socket socket) {
        //ΪʲôSocketInputStream
        SocketInputStream inputStream=null;
        OutputStream outputStream=null;

        try {
            inputStream=new SocketInputStream(socket.getInputStream(),2048);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
