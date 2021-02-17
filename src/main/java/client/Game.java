package client;

import client.animation.Anim;
import client.audio.Audio;
import client.input.MouseInput;
import client.state.*;
import client.state.collection.CollectionState;
import client.state.game.GameState;
import client.state.infopassive.InfoPassiveState;
import client.state.menu.MenuState;
import client.state.setting.Setting;
import client.state.shop.ShopState;
import client.utils.Asset;
import client.utils.Constants;
import client.utils.config.FrameConfig;
import client.utils.Converter;
import client.models.Environment;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;

import static client.audio.SoundEffect.effectStop;

public class Game implements Runnable {

    private JFrame frame;
    private Canvas canvas;
    private String Title;
    private FrameConfig config = new FrameConfig("MAINFRAME_CONFIGFILE");
    private int width = config.getWidth();
    private int height = config.getHeight();
    private boolean running = false;
    private Thread thread;
    private BufferStrategy bs;
    private Graphics2D g;
    private MouseInput mouseInput;
    private Handler handler;
    private Environment environment;
    private State gameState;
    private State menuState;
    private State collectionState;
    private State showErrors;
    private State shopState;
    private State statusState;
    private State infoPassiveState;
    private State setting;
    private TextField search;
    private TextField decksName;
    private TextField deletePlayer;
    private Audio audio = new Audio(Asset.sound.get("back1"));


    public Game(String title, Environment environment) {
        this.Title = title;
        this.environment = environment;
        mouseInput = new MouseInput();


        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            ((GameState) gameState).getMapper().resetPlayer();
            if (environment.getCurrentPlayer() != null)
                Converter.playerToJson(environment.getCurrentPlayer());
            audio.stop();
            effectStop();
        }));
    }

    private void init() {
        initFrame();

        handler = new Handler(this);
        Constants.setGameWidth(config.getWidth());
        Constants.setGameHeight(config.getHeight());

        initStates();
    }

    private void initFrame() {
        frame = new JFrame(Title);
        canvas = new Canvas();
        frame.setSize(width, height);
        frame.setDefaultCloseOperation(config.getCloseOperation());
        frame.setResizable(config.isResizable());
        frame.setLayout(null);
        frame.setLocationRelativeTo(null);
        frame.setUndecorated(true);
        frame.setVisible(true);

        canvas.setBounds(0, 0, width, height);

        search = new TextField("SEARCH");
        search.setBounds(850, 895, 190, 30);
        search.setVisible(false);
        frame.add(search);

        decksName = new TextField("", 10);
        decksName.setBounds(1080, 100, 190, 30);
        decksName.setVisible(false);
        frame.add(decksName);

        deletePlayer = new TextField("PASSWORD", 10);
        deletePlayer.setBounds(585, 600, 150, 30);
        deletePlayer.setVisible(false);
        frame.add(deletePlayer);

        frame.addMouseListener(mouseInput);
        frame.addMouseMotionListener(mouseInput);
        canvas.addMouseListener(mouseInput);
        canvas.addMouseMotionListener(mouseInput);

        frame.add(canvas);
    }

    private void initStates() {
        gameState = new GameState(handler);
        menuState = new MenuState(handler);
        collectionState = new CollectionState(handler);
        shopState = new ShopState(handler);
        statusState = new StatusState(handler);
        infoPassiveState = new InfoPassiveState(handler);
        showErrors = new ShowErrors(handler);
        setting = new Setting(handler);

        State.setCurrentState(menuState);
    }


    @Override
    public void run() {
        init();

        int fps = config.getFps();
        double delay = 1000000000 / fps;
        double currentTime;
        double lastTime = System.nanoTime();
        double delta = 0;

        while (running) {
            currentTime = System.nanoTime();
            delta += currentTime - lastTime;
            lastTime = currentTime;
            if (delta >= delay) {
                update();
                render();
                delta -= delay;
                addFrame();
            }
            try {
                Thread.sleep((long) (1000 / 60.0));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        stop();
    }

    private void addFrame() {
        if(((GameState) gameState).getMapper().getGameLogic().getAnims() == null)
            return;
        for (Anim anim : ((GameState) gameState).getMapper().getGameLogic().getAnims()) {
            anim.addFrame();
        }
    }

    private void update() {
        if (State.getCurrentState() != null) {
            State.getCurrentState().update();
        }
        if (setting != null && ((Setting) setting).isActive()) {
            setting.update();
        }
    }

    private void render() {
        bs = canvas.getBufferStrategy();
        if (bs == null) {
            canvas.createBufferStrategy(3);
            return;
        }
        g = (Graphics2D) bs.getDrawGraphics();

        g.clearRect(0, 0, width, height);

        renderStates();

        bs.show();
        g.dispose();
    }

    private void renderStates() {
        if (State.getCurrentState() != null)
            State.getCurrentState().render(g);

        if (((Setting) setting).isActive())
            setting.render(g);

        if (((ShowErrors) showErrors).isActive())
            showErrors.render(g);
    }

    public synchronized void start() {
        if (running)
            return;
        running = true;
        thread = new Thread(this);
        thread.start();
        audio.play();
    }

    public synchronized void stop() {
        if (!running)
            return;
        running = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //SETTER GETTER


    public TextField getDecksName() {
        return decksName;
    }

    public JFrame getFrame() {
        return frame;
    }

    public TextField getSearch() {
        return search;
    }

    public MouseInput getMouseInput() {
        return mouseInput;
    }

    public State getGameState() {
        return gameState;
    }

    public State getMenuState() {
        return menuState;
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public Graphics getG() {
        return g;
    }

    public State getCollectionState() {
        return collectionState;
    }

    public State getShopState() {
        return shopState;
    }

    public State getStatusState() {
        return statusState;
    }

    public State getInfoPassiveState() {
        return infoPassiveState;
    }

    public Environment getEnvironment() {
        return environment;
    }

    public State getShowErrors() {
        return showErrors;
    }

    public State getSetting() {
        return setting;
    }

    public Audio getAudio() {
        return audio;
    }

    public TextField getDeletePlayer() {
        return deletePlayer;
    }
}

