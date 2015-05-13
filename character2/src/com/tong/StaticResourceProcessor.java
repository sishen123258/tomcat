package com.tong;

import com.tong.connector.http.HttpRequest;
import com.tong.connector.http.HttpResponse;

import java.io.IOException;

/**
 * Created by Tong on 2015/5/13.
 */
public class StaticResourceProcessor {

    public void process(HttpRequest request, HttpResponse response) {
        try {
            response.sendStaticResource();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
