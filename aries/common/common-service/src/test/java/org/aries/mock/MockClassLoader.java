package org.aries.mock;


public class MockClassLoader extends ClassLoader {

    private Class<?> classToReturn;
    

    public void setClassToReturn(Class<?> classToReturn) {
        this.classToReturn = classToReturn;
    }

    public Class<?> loadClass(String name) throws ClassNotFoundException {
        if (classToReturn != null)
            return classToReturn;
        return super.loadClass(name);
    }

    protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        if (classToReturn != null)
            return classToReturn;
        return super.loadClass(name, resolve);
    }

}
