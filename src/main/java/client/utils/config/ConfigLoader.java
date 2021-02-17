package client.utils.config;

import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ConfigLoader {
    private static ConfigLoader loader;
    private HashMap<String, Configs> addressess;
    private static String defaultAddress = "src/main/resources/configFiles/MainConfigFile.properties";
    private HashMap<String, Configs> statesConfigs;
    private HashMap<String, Configs> frameConfigs;
    private HashMap<String, Configs> assets;

    private ConfigLoader(String address) {
        init(address);
    }

    public static ConfigLoader getInstance(String address) {
        if (loader == null) {
            if (address.equals("default")) {
                address = defaultAddress;
            }
            loader = new ConfigLoader(address);
        }
        return loader;
    }

    private void init(String address) {
        FileReader reader;

        addressess = new HashMap<>();
        statesConfigs = new HashMap<>();
        frameConfigs = new HashMap<>();
        assets = new HashMap<>();

        try {
            Configs addresses = new Configs();
            reader = new FileReader(address);
            addresses.load(reader);
            this.addressess.put("RESOURCE_URL", addresses);
        } catch (Exception e) {
            e.printStackTrace();
        }
        loadProperties();
    }

    private void loadProperties() {
        Set<Map.Entry<Object, Object>> entries = addressess.get("RESOURCE_URL").entrySet();
        for (Map.Entry<Object, Object> entry : entries) {
            System.out.println(entry.getKey() + " " + entry.getValue());
        }

        for (Map.Entry<Object, Object> entry : entries) {
            String adrs = (String) entry.getValue();
            String key = (String) entry.getKey();
            String lowerCase = key.toLowerCase();
            if (!lowerCase.contains("url")) {
                Configs property = new Configs();
                try {
                    File test = new File(adrs);
                    FileReader reader = new FileReader(test);
                    property.load(reader);

                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (lowerCase.contains("frame")) {
                    System.out.println("frame added : " + key);
                    frameConfigs.put(key, property);
                } else if (lowerCase.contains("state")) {
                    System.out.println("state added : " + key);
                    statesConfigs.put(key, property);
                } else if (lowerCase.contains("asset")) {
                    System.out.println("asset added : " + key);
                    assets.put(key, property);
                }

            }
        }

    }

    public String getAddress(String resource_url) {
        return addressess.get("RESOURCE_URL").getProperty(resource_url);
    }

    public Configs getFrameProperties(String name) {
        return frameConfigs.get(name);
    }

    public Configs getStateProperties(String name) {
        return statesConfigs.get(name);
    }

    public Configs getAssetProperties(String name) {
        return assets.get(name);
    }


}
