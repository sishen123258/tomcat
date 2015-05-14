package com.tong;

import com.tong.connector.http.*;

import javax.servlet.Servlet;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLStreamHandler;

/**
 * Created by Tong on 2015/5/13.
 */
public class ServletProcessor {


    public void process(HttpRequest request, HttpResponse response) {

        String uri=request.getRequestURI();
        String servletName=uri.substring(uri.lastIndexOf("/")+1);
        URLClassLoader classLoader=null;

        try {
            URL []urls=new URL[1];
            URLStreamHandler streamHandler=null;
            File file=new File(Constants.WEB_ROOT);
            String repository=new URL("file",null,file.getCanonicalPath()+File.separator).toString();
            urls[0] = new URL(null, repository, streamHandler);
            classLoader=new URLClassLoader(urls);
        } catch (IOException e) {
            System.out.printf(e.toString());
        }

        Class myClass=null;
        try {
            myClass=classLoader.loadClass(servletName);
        } catch (ClassNotFoundException e) {
            System.out.printf(e.toString());
        }

        try {
            Servlet servlet= (Servlet) myClass.newInstance();
            HttpRequestFacade requestFacade=new HttpRequestFacade(request);
            HttpResponseFacade responseFacade=new HttpResponseFacade(response);
            servlet.service(requestFacade,responseFacade);
            ((HttpResponse) response).finishResponse();
        } catch (Exception e) {
            System.out.printf(e.toString());
        }


    }
}
