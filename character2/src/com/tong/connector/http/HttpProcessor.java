package com.tong.connector.http;

import com.tong.ServletProcessor;
import com.tong.StaticResourceProcessor;
import com.tong.connector.HttpConnector;

import javax.servlet.ServletException;
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
        } catch (ServletException e) {
            e.printStackTrace();
        }


    }



    private void parserRequest(SocketInputStream inputStream, OutputStream outputStream) throws IOException, ServletException {
        inputStream.readRequestLine(requestLine);
        String method=new String(requestLine.method,0,requestLine.methodEnd);

        String uri=null;
        String protocol=new String(requestLine.protocol,0,requestLine.protocolEnd);

        if(method.length()<1){
            throw new ServletException("Missing request method");
        }else if (requestLine.uriEnd < 1) {
            throw new ServletException("Missing HTTP request URI");
        }

        int question=requestLine.indexOf("?");
        if(question>=0){
            request.setQueryString(new String(requestLine.uri,question+1,requestLine.uriEnd-question-1));
            uri=new String(requestLine.uri,0,question);
        }else{
            uri=new String(requestLine.uri,0,requestLine.uriEnd);
        }




    }

    private void parseHeaders(SocketInputStream inputStream) {

    }

    public static void main(String[] args) {
        int i="abcd".indexOf("e");
        System.out.println("HttpProcessor.main"+i);
    }
}
