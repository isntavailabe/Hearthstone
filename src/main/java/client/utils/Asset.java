package client.utils;

import client.utils.config.ConfigLoader;
import client.utils.config.Configs;

import javax.imageio.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.io.*;

public class Asset {
    private Configs properties;
    private String name;

    public static BufferedImage weapon;
    public static BufferedImage minion;
    public static HashMap<String, BufferedImage> backgrounds;
    public static HashMap<String, BufferedImage> heroDeck;
    public static HashMap<String, BufferedImage> cards;
    public static HashMap<String, BufferedImage> heroPowers;
    public static HashMap<String, BufferedImage> heroes;
    public static HashMap<String, BufferedImage> passives;
    public static HashMap<String, BufferedImage> heroclasses;
    public static HashMap<String, BufferedImage> utils;
    public static HashMap<String, BufferedImage> button;
    public static HashMap<String, BufferedImage> gamebacks;
    public static BufferedImage[] frames;
    public static HashMap<String, String> sound;
    public static AlphaComposite ac;
    public static AlphaComposite ac2;
    public static Font white40;
    public static Font font20;


    public Asset(String name) {
        this.name = name;
        setProperties();
        initialize();
    }

    private void setProperties() {
        this.properties = ConfigLoader.getInstance("default").getAssetProperties(name);
    }

    private void initialize() {
        backgrounds = new HashMap<>();
        heroDeck = new HashMap<>();
        cards = new HashMap<>();
        heroPowers = new HashMap<>();
        heroes = new HashMap<>();
        passives = new HashMap<>();
        heroclasses = new HashMap<>();
        utils = new HashMap<>();
        button = new HashMap<>();
        gamebacks = new HashMap<>();
        sound = new HashMap<>();
        frames = new BufferedImage[12];

        loadFonts();
        loadSounds();
        loadImages();

    }

    private static BufferedImage loadImage(String path) {
        File f = new File(path);
        try {
            return ImageIO.read(f);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void loadImages() {

        backgrounds.put("login", loadImage(properties.getProperty("login")));
        backgrounds.put("menuback", loadImage(properties.getProperty("menuback")));
        backgrounds.put("gameback", loadImage(properties.getProperty("gameback")));
        backgrounds.put("collectionback", loadImage(properties.getProperty("collectionback")));
        backgrounds.put("shopback", loadImage(properties.getProperty("shopback")));
        backgrounds.put("infopassiveback", loadImage(properties.getProperty("infopassiveback")));
        backgrounds.put("statusback", loadImage(properties.getProperty("statusback")));

        gamebacks.put("gameback", loadImage(properties.getProperty("gameback")));
        gamebacks.put("gameback2", loadImage(properties.getProperty("gameback2")));
        gamebacks.put("gameback3", loadImage(properties.getProperty("gameback3")));

        minion = loadImage(properties.getProperty("minion"));
        weapon = loadImage(properties.getProperty("weapon"));


        heroDeck.put("mage", loadImage(properties.getProperty("mage_deck")));
        heroDeck.put("warlock", loadImage(properties.getProperty("warlock_deck")));
        heroDeck.put("rogue", loadImage(properties.getProperty("rogue_deck")));
        heroDeck.put("priest", loadImage(properties.getProperty("priest_deck")));
        heroDeck.put("hunter", loadImage(properties.getProperty("hunter_deck")));

        utils.put("info", loadImage(properties.getProperty("info")));
        utils.put("showerror", loadImage(properties.getProperty("showerror")));
        utils.put("lock", loadImage(properties.getProperty("lock")));
        utils.put("unlock", loadImage(properties.getProperty("unlock")));
        utils.put("mana", loadImage(properties.getProperty("mana")));
        utils.put("maxmana", loadImage(properties.getProperty("maxmana")));
        utils.put("selected_minion", loadImage(properties.getProperty("selected_minion")));
        utils.put("taunt", loadImage(properties.getProperty("taunt")));
        utils.put("divine_shield", loadImage(properties.getProperty("divine_shield")));
        utils.put("immune_hero", loadImage(properties.getProperty("immune_hero")));


        ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float) properties.readFloat("ac"));
        ac2 = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, properties.readInteger("ac2"));

        cards.put("aluneth", loadImage(properties.getProperty("aluneth")));
        cards.put("arcane explosion", loadImage(properties.getProperty("arcane_explosion")));
        cards.put("diving gryphon", loadImage(properties.getProperty("diving_gryphon")));
        cards.put("boulderfist ogre", loadImage(properties.getProperty("boulderfist_ogre")));
        cards.put("cold blood", loadImage(properties.getProperty("cold_blood")));
        cards.put("core hound", loadImage(properties.getProperty("core_hound")));
        cards.put("deranged doctor", loadImage(properties.getProperty("deranged_doctor")));
        cards.put("dreadscale", loadImage(properties.getProperty("dreadscale")));
        cards.put("friendly smith", loadImage(properties.getProperty("friendly_smith")));
        cards.put("polymorph", loadImage(properties.getProperty("polymorph")));
        cards.put("rain of fire", loadImage(properties.getProperty("rain_of_fire")));
        cards.put("savage roar", loadImage(properties.getProperty("savage_roar")));
        cards.put("serrated tooth", loadImage(properties.getProperty("serrated_tooth")));
        cards.put("shadowblade", loadImage(properties.getProperty("shadowblade")));
        cards.put("skull of the man'ari", loadImage(properties.getProperty("skull_of_the_man'ari")));
        cards.put("spitting camel", loadImage(properties.getProperty("spitting_camel")));
        cards.put("stonetusk boar", loadImage(properties.getProperty("stonetusk_boar")));
        cards.put("stormwind knight", loadImage(properties.getProperty("stormwind_knight")));
        cards.put("swipe", loadImage(properties.getProperty("swipe")));
        cards.put("gearblade", loadImage(properties.getProperty("gearblade")));
        cards.put("heavy axe", loadImage(properties.getProperty("heavy_axe")));
        cards.put("mirage blade", loadImage(properties.getProperty("mirage_blade")));
        cards.put("high priest amet", loadImage(properties.getProperty("high_priest_amet")));
        cards.put("thoughtsteal", loadImage(properties.getProperty("thoughtsteal")));
        cards.put("swamp king dred", loadImage(properties.getProperty("swamp_king_dred")));
        cards.put("enchanted raven", loadImage(properties.getProperty("enchanted_raven")));
        cards.put("book of specters", loadImage(properties.getProperty("book_of_specters")));
        cards.put("curio collector", loadImage(properties.getProperty("curio_collector")));
        cards.put("learn draconic", loadImage(properties.getProperty("learn_draconic")));
        cards.put("pharaoh's blessing", loadImage(properties.getProperty("pharaoh's_blessing")));
        cards.put("sathrovarr", loadImage(properties.getProperty("sathrovarr")));
        cards.put("security rover", loadImage(properties.getProperty("security_rover")));
        cards.put("shadowflame", loadImage(properties.getProperty("shadowflame")));
        cards.put("sprint", loadImage(properties.getProperty("sprint")));
        cards.put("swarm of locusts", loadImage(properties.getProperty("swarm_of_locusts")));
        cards.put("tomb warden", loadImage(properties.getProperty("tomb_warden")));
        cards.put("trogg beastrager", loadImage(properties.getProperty("trogg_beastrager")));
        cards.put("strength in numbers", loadImage(properties.getProperty("strength_in_numbers")));
        cards.put("card_back", loadImage(properties.getProperty("card_back")));

        heroPowers.put("mage_power", loadImage(properties.getProperty("mage_power")));
        heroPowers.put("hunter_power", loadImage(properties.getProperty("hunter_power")));
        heroPowers.put("warlock_power", loadImage(properties.getProperty("warlock_power")));
        heroPowers.put("priest_power", loadImage(properties.getProperty("priest_power")));
        heroPowers.put("rogue_power", loadImage(properties.getProperty("rogue_power")));

        heroes.put("mage", loadImage(properties.getProperty("mage")));
        heroes.put("hunter", loadImage(properties.getProperty("hunter")));
        heroes.put("warlock", loadImage(properties.getProperty("warlock")));
        heroes.put("priest", loadImage(properties.getProperty("priest")));
        heroes.put("rogue", loadImage(properties.getProperty("rogue")));

        heroes.put("rogueStatus", loadImage(properties.getProperty("rogueStatus")));
        heroes.put("mageStatus", loadImage(properties.getProperty("mageStatus")));
        heroes.put("warlockStatus", loadImage(properties.getProperty("warlockStatus")));
        heroes.put("priestStatus", loadImage(properties.getProperty("priestStatus")));
        heroes.put("hunterStatus", loadImage(properties.getProperty("hunterStatus")));

        heroclasses.put("mageclass_in", loadImage(properties.getProperty("mageclass_in")));
        heroclasses.put("warlockclass_in", loadImage(properties.getProperty("warlockclass_in")));
        heroclasses.put("rogueclass_in", loadImage(properties.getProperty("rogueclass_in")));
        heroclasses.put("priestclass_in", loadImage(properties.getProperty("priestclass_in")));
        heroclasses.put("hunterclass_in", loadImage(properties.getProperty("hunterclass_in")));
        heroclasses.put("neutralclass_in", loadImage(properties.getProperty("neutralclass_in")));

        passives.put("twice_draw", loadImage(properties.getProperty("twice_draw")));
        passives.put("off_cards", loadImage(properties.getProperty("off_cards")));
        passives.put("warriors", loadImage(properties.getProperty("warriors")));
        passives.put("mana_jump", loadImage(properties.getProperty("mana_jump")));
        passives.put("free_power", loadImage(properties.getProperty("free_power")));

        utils.put("right_arrow_in", loadImage(properties.getProperty("right_arrow_in")));
        utils.put("left_arrow_in", loadImage(properties.getProperty("left_arrow_in")));
        utils.put("arrow_up", loadImage(properties.getProperty("arrow_up")));
        utils.put("arrow_down", loadImage(properties.getProperty("arrow_down")));
        utils.put("search", loadImage(properties.getProperty("search")));
        utils.put("exit_button", loadImage(properties.getProperty("exit_button")));
        utils.put("menu_button", loadImage(properties.getProperty("menu_button")));
        utils.put("endgame_button", loadImage(properties.getProperty("endgame_button")));
        utils.put("endturn_button", loadImage(properties.getProperty("endturn_button")));
        utils.put("close_button", loadImage(properties.getProperty("close_button")));
        utils.put("deck_button", loadImage(properties.getProperty("deck_button")));
        utils.put("change_state", loadImage(properties.getProperty("change_state")));
        utils.put("defense", loadImage(properties.getProperty("defense")));
        utils.put("splash", loadImage(properties.getProperty("splash")));
        utils.put("deathrattle", loadImage(properties.getProperty("deathrattle")));

        button.put("play_button", loadImage(properties.getProperty("play_button")));
        button.put("status_button", loadImage(properties.getProperty("status_button")));
        button.put("collection_button", loadImage(properties.getProperty("collection_button")));
        button.put("shop_button", loadImage(properties.getProperty("shop_button")));
        button.put("setting_button", loadImage(properties.getProperty("setting_button")));
        button.put("removedeck_button", loadImage(properties.getProperty("removedeck_button")));
        button.put("newdeck_button", loadImage(properties.getProperty("newdeck_button")));
        button.put("back_button", loadImage(properties.getProperty("back_button")));

        for (int i = 0; i < 12; i++) {
            frames[i] = loadImage(properties.getProperty("explosion_anim" + i));
        }
    }

    private void loadSounds() {
        sound.put("card_play", properties.getProperty("card_play"));
        sound.put("button_pressed", properties.getProperty("button_pressed"));
        sound.put("minion_summoned", properties.getProperty("minion_summoned"));
        sound.put("back1", properties.getProperty("back1"));
        sound.put("back2", properties.getProperty("back2"));
        sound.put("damaged", properties.getProperty("damaged"));
        sound.put("explosion", properties.getProperty("explosion"));
    }

    private void loadFonts() {
        white40 = new Font("Serif", Font.BOLD, 40);
        font20 = new Font("Serif", Font.BOLD, 20);
    }

    public static void setGameBack(BufferedImage bi) {
        backgrounds.put("gameback", bi);
    }

}