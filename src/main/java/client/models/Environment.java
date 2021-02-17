package client.models;

import client.models.cards.card;
import client.models.entities.heroes.Hero;

import java.util.ArrayList;

import static client.utils.Converter.*;
import static client.utils.Converter.directoryFileList;

public class Environment {
    private static Environment environment;
    private static ArrayList<Player> players;
    private static ArrayList<Hero> heroes;
    private static ArrayList<card> cards;
    private static ArrayList<card> neutralCards;
    private static ArrayList<card> firstcollection;
    private static ArrayList<Hero> firstheroes;
    private static ArrayList<card> heroCards;
    private static Player currentPlayer;
    private String key;

    private Environment(){}

    public static Environment getInstance(){
        if(environment == null)
            loadEnv();
        return environment;
    }

    private static void loadEnv(){
        ArrayList<Player> players = new ArrayList<Player>();
        ArrayList<card> cards = new ArrayList<card>();
        ArrayList<Hero> heroes = new ArrayList<Hero>();
        ArrayList<card> neutralCards = new ArrayList<card>();
        ArrayList<card> heroCards = new ArrayList<card>();
        ArrayList<card> firstcollection = new ArrayList<card>();
        ArrayList<Hero> firstheroes = new ArrayList<Hero>();

        players.addAll(playerFromJson(directoryFileList("json/player")));
        cards.addAll(cardFromJson(directoryFileList("json/card")));
        heroes.addAll(heroFromJson(directoryFileList("json/hero")));
        neutralCards.addAll(cardFromJson(directoryFileList("json/neutralcards")));
        heroCards.addAll(cardFromJson(directoryFileList("json/herocards")));
        firstcollection.addAll(cardFromJson(directoryFileList("json/firstcollection")));
        firstheroes.addAll(firstheroFromJson(directoryFileList("json/unlockedheroes")));
        environment = new Environment(players, heroes, cards, neutralCards, heroCards, firstcollection, firstheroes);
    }
    private Environment(ArrayList players, ArrayList heroes,ArrayList cards,ArrayList neutralCards,
                        ArrayList heroCards,ArrayList firstcollection, ArrayList firstheroes){
        Environment.players = players;
        Environment.heroes = heroes;
        Environment.cards = cards;
        Environment.neutralCards = neutralCards;
        Environment.heroCards = heroCards;
        Environment.firstcollection = firstcollection;
        Environment.firstheroes = firstheroes;
    }
    public card getCard(String name){
        for(client.models.cards.card card : cards){
            if(card.getName().equals(name))
                return card;
        }
        return null;
    }
    public Hero getHero(String name){
        for(Hero hero : heroes){
            if(hero.getName().equals(name))
                return hero;
        }
        return null;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public ArrayList<card> getCards() {
        return cards;
    }

    public ArrayList<card> getNeutralCards() {
        return neutralCards;
    }

    public ArrayList<card> getFirstcollection() {
        return firstcollection;
    }

    public ArrayList<Hero> getFirstheroes() {
        return firstheroes;
    }

    public ArrayList<card> getHeroCards() {
        return heroCards;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        Environment.currentPlayer = currentPlayer;
    }
}
