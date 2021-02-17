package client.utils;

import client.models.Enemy;
import client.models.Player;
import client.models.cards.*;
import client.models.entities.heroes.Hero;
import com.google.gson.Gson;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Converter {

    public static ArrayList<Player> playerFromJson(ArrayList<String> fileName) {
        ArrayList<Player> plyrs = new ArrayList<>();
        for (String s : fileName) {
            Player p = null;
            try {
                String jsonString = "";
                String line;
                BufferedReader reader = new BufferedReader(new FileReader("json/player/" + s));
                while ((line = reader.readLine()) != null) {
                    jsonString += line + '\n';
                }
                p = new Gson().fromJson(jsonString, Player.class);
            } catch (FileNotFoundException ex) {
                System.out.println("File not found : " + s);
            } catch (IOException ex) {
                System.out.println("Error while reading file : " + s);
            }
            plyrs.add(p);
        }
        return plyrs;
    }

    public static Player getPlayer(String fileName) {
        Player p = null;
        try {
            String jsonString = "";
            String line;
            BufferedReader reader = new BufferedReader(new FileReader("json/mode/" + fileName));
            while (null != (line = reader.readLine())) {
                jsonString += line + '\n';
            }
            p = new Gson().fromJson(jsonString, Player.class);
        } catch (FileNotFoundException ex) {
            System.out.println("File not found : " + fileName);
        } catch (IOException ex) {
            System.out.println("Error while reading file : " + fileName);
        }
        return p;
    }

    public static Player getEnemy(String fileName) {
        Enemy p = null;
        try {
            String jsonString = "";
            String line;
            BufferedReader reader = new BufferedReader(new FileReader("json/mode/" + fileName));
            while (null != (line = reader.readLine())) {
                jsonString += line + '\n';
            }
            p = new Gson().fromJson(jsonString, Enemy.class);
        } catch (FileNotFoundException ex) {
            System.out.println("File not found : " + fileName);
        } catch (IOException ex) {
            System.out.println("Error while reading file : " + fileName);
        }
        return p;
    }

    public static ArrayList<card> cardFromJson(ArrayList<String> fileName) {
        ArrayList<card> cards = new ArrayList<>();
        int swch = 0;
        for (String s : fileName) {
            card c = null;
            cards.add(readFile(swch, s, "", c));
        }
        return cards;
    }

    public static ArrayList<card> cardFromJsonWithJson(ArrayList<String> fileName, HashMap<String, String> rewards) {
        ArrayList<card> cards = new ArrayList<>();
        int swch = 0;
        for (String s : fileName) {
            card c = null;
            if (s.contains("->")) {
                String card1 = s.substring(0, s.indexOf("-"));
                String card2 = s.substring(s.indexOf(">") + 1);
                card c1 = readFile(swch, card1, ".json", c);
                card c2 = readFile(swch, card2, ".json", c);
                cards.add(c1);
                rewards.put(c1.getName(), c2.getName());
                continue;
            }
            cards.add(readFile(swch, s, ".json", c));
        }
        return cards;
    }

    private static card readFile(int swch, String filename, String format, card c) {
        try {
            String jsonString = "";
            String line;
            BufferedReader reader = new BufferedReader(new FileReader("json/card/" + filename + format));
            while ((line = reader.readLine()) != null) {
                jsonString += line + '\n';
                if (line.contains("type") && line.contains("spell"))
                    swch = 0;
                else if (line.contains("type") && line.contains("minion"))
                    swch = 1;
                else if (line.contains("type") && line.contains("weapon"))
                    swch = 2;
            }
            if (swch == 0)
                c = new Gson().fromJson(jsonString, SpellCard.class);
            else if (swch == 1)
                c = new Gson().fromJson(jsonString, MinionCard.class);
            else if (swch == 2)
                c = new Gson().fromJson(jsonString, WeaponCard.class);
        } catch (FileNotFoundException ex) {
            System.out.println("File not found : " + filename);
        } catch (IOException ex) {
            System.out.println("Error while reading file : " + filename);
        }
        return c;
    }

    public static ArrayList heroFromJson(ArrayList<String> fileName) {
        ArrayList<Hero> heroes = new ArrayList<>();
        for (String s : fileName) {
            Hero h = null;
            try {
                String jsonString = "";
                String line;
                BufferedReader reader = new BufferedReader(new FileReader("json/hero/" + s));
                while ((line = reader.readLine()) != null) {
                    jsonString += line + '\n';
                }
                h = new Gson().fromJson(jsonString, Hero.class);
            } catch (FileNotFoundException ex) {
                System.out.println("File not found : " + s);
            } catch (IOException ex) {
                System.out.println("Error while reading file : " + s);
            }
            heroes.add(h);
        }
        return heroes;
    }

    public static ArrayList firstheroFromJson(ArrayList<String> fileName) {
        ArrayList<Hero> heroes = new ArrayList<>();
        for (String s : fileName) {
            Hero h = null;
            try {
                String jsonString = "";
                String line;
                BufferedReader reader = new BufferedReader(new FileReader("json/unlockedheroes/" + s));
                while ((line = reader.readLine()) != null) {
                    jsonString += line + '\n';
                }
                h = new Gson().fromJson(jsonString, Hero.class);

            } catch (FileNotFoundException ex) {
                System.out.println("File not found : " + s);
            } catch (IOException ex) {
                System.out.println("Error while reading file : " + s);
            }
            heroes.add(h);
        }
        return heroes;
    }

    public static void playerToJson(Player player) {
        String jsonString = new Gson().toJson(player);
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("json/player/" + player.getName() + ".json"));
            writer.write(jsonString);
            writer.close();
        } catch (IOException ex) {
            System.out.println("Unable to open file : " + player.getName());
        }
    }

    public static void cardToJson(card card) {
        String jsonString = new Gson().toJson(card);
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("json/card/" + card.getName() + ".json"));
            writer.write(jsonString);
            writer.close();
        } catch (IOException ex) {
            System.out.println("Unable to open file : " + card.getName());
        }
    }

    public static void heroToJson(Hero hero) {
        String jsonString = new Gson().toJson(hero);
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("json/hero/" + hero.getName() + ".json"));
            writer.write(jsonString);
            writer.close();
        } catch (IOException ex) {
            System.out.println("Unable to open file : " + hero.getName());
        }
    }

    public static DeckReader getDeckReader(String s) {
        DeckReader deckReader = null;
        try {
            String jsonString = "";
            String line;
            BufferedReader reader = new BufferedReader(new FileReader("json/mode/" + s));
            while ((line = reader.readLine()) != null) {
                jsonString += line + '\n';
            }
            deckReader = new Gson().fromJson(jsonString, DeckReader.class);
        } catch (FileNotFoundException ex) {
            System.out.println("File not found : " + s);
        } catch (IOException ex) {
            System.out.println("Error while reading file : " + s);
        }
        return deckReader;
    }

    public static ArrayList<String> directoryFileList(String dir) {
        File[] dirList = new File(dir).listFiles();
        ArrayList<String> fileNameList = new ArrayList<>();
        for (File file : dirList) {
            if (file.isFile()) {
                fileNameList.add(file.getName());
            }
        }
        return fileNameList;
    }
}
