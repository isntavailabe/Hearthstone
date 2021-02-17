package client.utils.config;

public class FrameConfig {
    private Configs properties;
    private String name;
    private int width;
    private int height;
    private int CloseOperation;
    private boolean Resizable;
    private int fps;



    public FrameConfig(String name) {
        this.name = name;
        setProperties();
        initialize();
    }

    private void setProperties() {
        this.properties = ConfigLoader.getInstance("default").getFrameProperties(name);
    }

    private void initialize() {
        width = properties.readInteger("width");
        height = properties.readInteger("height");
        CloseOperation = properties.readInteger("CloseOperation");
        Resizable = properties.readBoolean("Resizable");
        fps = properties.readInteger("fps");
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getCloseOperation() {
        return CloseOperation;
    }

    public boolean isResizable() {
        return Resizable;
    }

    public int getFps() {
        return fps;
    }
}
