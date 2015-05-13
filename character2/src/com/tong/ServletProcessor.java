package com.tong;

import com.tong.connector.http.HttpRequest;
import com.tong.connector.http.HttpResponse;

/**
 * Created by Tong on 2015/5/13.
 */
public class ServletProcessor {


    public void process(HttpRequest request, HttpResponse response) {
        System.out.println("ServletProcessor.process");
    }
}
