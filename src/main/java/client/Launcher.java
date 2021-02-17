package client;

import client.utils.Asset;
import client.utils.config.ConfigLoader;

import static client.models.Environment.*;

public class Launcher {
    public static void main(String[] args){
        Asset asset = new Asset("ASSET_CONFIGFILE");
        ConfigLoader urls = ConfigLoader.getInstance("default");
        Login login = new Login(getInstance());
    }
}
