package client.utils.config;

import java.util.Properties;

public class Configs extends Properties {
    public int readInteger(String name){
        return Integer.parseInt(this.getProperty(name));
    }
    public float readFloat(String name){
        return Float.parseFloat(this.getProperty(name));
    }
    public boolean readBoolean(String name){
        return Boolean.parseBoolean(this.getProperty(name));
    }
}
