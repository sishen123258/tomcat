package com.tong.startup;

import com.tong.connector.HttpConnector;

/**
 * Created by Tong on 2015/5/13.
 */
public class Bootstrap {

    public static void main(String[] args) {
        HttpConnector httpConnector=new HttpConnector();
        httpConnector.start();
    }

}
