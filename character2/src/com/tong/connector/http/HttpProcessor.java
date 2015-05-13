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
            request.setQueryString(null);
            uri=new String(requestLine.uri,0,requestLine.uriEnd);
        }

        // Checking for an absolute URI (with the HTTP protocol)
        if (!uri.startsWith("/")) {
            int pos = uri.indexOf("://");
            // Parsing out protocol and host name
            if (pos != -1) {
                pos = uri.indexOf('/', pos + 3);
                if (pos == -1) {
                    uri = "";
                }
                else {
                    uri = uri.substring(pos);
                }
            }
        }
        // Parse any requested session ID out of the request URI
        String match = ";jsessionid=";
        int semicolon = uri.indexOf(match);
        if (semicolon >= 0) {
            String rest = uri.substring(semicolon + match.length());
            int semicolon2 = rest.indexOf(';');
            if (semicolon2 >= 0) {
                request.setRequestedSessionId(rest.substring(0, semicolon2));
                rest = rest.substring(semicolon2);
            }
            else {
                request.setRequestedSessionId(rest);
                rest = "";
            }
            request.setRequestedSessionURL(true);
            uri = uri.substring(0, semicolon) + rest;
        }
        else {
            request.setRequestedSessionId(null);
            request.setRequestedSessionURL(false);
        }

        // Normalize URI (using String operations at the moment)
        String normalizedUri = normalize(uri);
        // Set the corresponding request properties
        ((HttpRequest) request).setMethod(method);
        request.setProtocol(protocol);
        if (normalizedUri != null) {
            ((HttpRequest) request).setRequestURI(normalizedUri);
        }
        else {
            ((HttpRequest) request).setRequestURI(uri);
        }

        if (normalizedUri == null) {
            throw new ServletException("Invalid URI: " + uri + "'");
        }
    }

    protected String normalize(String path) {
        if (path == null)
            return null;
        // Create a place for the normalized path
        String normalized = path;

        // Normalize "/%7E" and "/%7e" at the beginning to "/~"
        if (normalized.startsWith("/%7E") || normalized.startsWith("/%7e"))
            normalized = "/~" + normalized.substring(4);

        // Prevent encoding '%', '/', '.' and '\', which are special reserved
        // characters
        if ((normalized.indexOf("%25") >= 0)
                || (normalized.indexOf("%2F") >= 0)
                || (normalized.indexOf("%2E") >= 0)
                || (normalized.indexOf("%5C") >= 0)
                || (normalized.indexOf("%2f") >= 0)
                || (normalized.indexOf("%2e") >= 0)
                || (normalized.indexOf("%5c") >= 0)) {
            return null;
        }

        if (normalized.equals("/."))
            return "/";

        // Normalize the slashes and add leading slash if necessary
        if (normalized.indexOf('\\') >= 0)
            normalized = normalized.replace('\\', '/');
        if (!normalized.startsWith("/"))
            normalized = "/" + normalized;

        // Resolve occurrences of "//" in the normalized path
        while (true) {
            int index = normalized.indexOf("//");
            if (index < 0)
                break;
            normalized = normalized.substring(0, index) +
                    normalized.substring(index + 1);
        }

        // Resolve occurrences of "/./" in the normalized path
        while (true) {
            int index = normalized.indexOf("/./");
            if (index < 0)
                break;
            normalized = normalized.substring(0, index) +
                    normalized.substring(index + 2);
        }

        // Resolve occurrences of "/../" in the normalized path
        while (true) {
            int index = normalized.indexOf("/../");
            if (index < 0)
                break;
            if (index == 0)
                return (null);  // Trying to go outside our context
            int index2 = normalized.lastIndexOf('/', index - 1);
            normalized = normalized.substring(0, index2) +
                    normalized.substring(index + 3);
        }

        // Declare occurrences of "/..." (three or more dots) to be invalid
        // (on some Windows platforms this walks the directory tree!!!)
        if (normalized.indexOf("/...") >= 0)
            return (null);

        // Return the normalized path that we have completed
        return (normalized);

    }

    private void parseHeaders(SocketInputStream inputStream) {
        HttpHeader httpHeader=new HttpHeader();
    }

}
