package core;

import org.apache.catalina.*;

import javax.naming.directory.DirContext;
import javax.servlet.ServletException;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;

/**
 * Created by Tong on 2015/5/17.
 */
public  class SimpleContainer implements Container{

    public static final String WEB_ROOT =
            System.getProperty("user.dir") + File.separator  + "webroot";

    public SimpleContainer() {
    }

    @Override
    public void addChild(Container container) {

    }

    @Override
    public void addContainerListener(ContainerListener containerListener) {

    }

    @Override
    public void addMapper(Mapper mapper) {

    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener propertyChangeListener) {

    }

    @Override
    public Container findChild(String s) {
        return null;
    }

    @Override
    public Container[] findChildren() {
        return new Container[0];
    }

    @Override
    public Mapper findMapper(String s) {
        return null;
    }

    @Override
    public Mapper[] findMappers() {
        return new Mapper[0];
    }

    @Override
    public Cluster getCluster() {
        return null;
    }

    @Override
    public String getInfo() {
        return null;
    }

    @Override
    public Loader getLoader() {
        return null;
    }

    @Override
    public Logger getLogger() {
        return null;
    }

    @Override
    public Manager getManager() {
        return null;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public Container getParent() {
        return null;
    }

    @Override
    public ClassLoader getParentClassLoader() {
        return null;
    }

    @Override
    public Realm getRealm() {
        return null;
    }

    @Override
    public DirContext getResources() {
        return null;
    }

    @Override
    public void invoke(Request request, Response response) throws IOException, ServletException {

    }

    @Override
    public Container map(Request request, boolean b) {
        return null;
    }

    @Override
    public void removeChild(Container container) {

    }

    @Override
    public void removeContainerListener(ContainerListener containerListener) {

    }

    @Override
    public void removeMapper(Mapper mapper) {

    }

    @Override
    public void removePropertyChangeListener(PropertyChangeListener propertyChangeListener) {

    }

    @Override
    public void setCluster(Cluster cluster) {

    }

    @Override
    public void setLoader(Loader loader) {

    }

    @Override
    public void setLogger(Logger logger) {

    }

    @Override
    public void setManager(Manager manager) {

    }

    @Override
    public void setName(String s) {

    }

    @Override
    public void setParent(Container container) {

    }

    @Override
    public void setParentClassLoader(ClassLoader classLoader) {

    }

    @Override
    public void setRealm(Realm realm) {

    }

    @Override
    public void setResources(DirContext dirContext) {

    }
}
