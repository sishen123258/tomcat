package com.tong.connector.http;

import com.tong.ServletProcessor;
import com.tong.StaticResourceProcessor;
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
            outputStream=socket.getOutputStream();

            request=new HttpRequest(inputStream);
            response=new HttpResponse(outputStream);

            response.setRequest(request);
            response.setHeader("Server", "Tong Servlet Container");

            parserRequest(inputStream, outputStream);
            parseHeaders(inputStream);

            if(request.getRequestURI().startsWith("/servlet/")){
                ServletProcessor servletProcessor=new ServletProcessor();
                servletProcessor.process(request,response);
            }else{
                StaticResourceProcessor resourceProcessor=new StaticResourceProcessor();
                resourceProcessor.process(request,response);
            }
            socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private void parseHeaders(SocketInputStream inputStream) throws IOException {
        inputStream.readRequestLine(requestLine);
    }

    private void parserRequest(SocketInputStream inputStream, OutputStream outputStream) {

    }
}
