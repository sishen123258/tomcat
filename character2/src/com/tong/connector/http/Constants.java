package com.tong.connector.http;

import java.io.File;

/**
 * Created by Tong on 2015/5/13.
 */
public class Constants {
    public static final String WEB_ROOT =
            System.getProperty("user.dir") + File.separator  + "webroot";
    public static final String Package = "com.tong.connector.http";
    public static final int DEFAULT_CONNECTION_TIMEOUT = 60000;
    public static final int PROCESSOR_IDLE = 0;
    public static final int PROCESSOR_ACTIVE = 1;
}
