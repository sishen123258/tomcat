package startup;

import core.SimpleContainer;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.connector.http.HttpConnector;

import java.io.IOException;

/**
 * Created by Tong on 2015/5/17.
 */
public final class Bootstrap {

    public static void main(String[] args) {
        HttpConnector connector=new HttpConnector();
        SimpleContainer container=new SimpleContainer();
        connector.setContainer(container);
        try {
            connector.initialize();
            connector.start();

            System.in.read();
        } catch (LifecycleException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
