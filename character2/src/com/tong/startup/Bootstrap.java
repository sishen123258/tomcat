package com.tong.startup;

import com.tong.connector.http.Constants;
import com.tong.connector.http.HttpConnector;

/**
 * Created by Tong on 2015/5/13.
 */
public final class Bootstrap {

    public static void main(String[] args) {
        System.out.println("Bootstrap.main:"+ Constants.WEB_ROOT);
        HttpConnector httpConnector=new HttpConnector();
        httpConnector.start();
    }

}
