package client.state.game;

import client.animation.*;
import client.models.*;
import client.models.cards.*;
import client.models.entities.Entity;
import client.models.entities.heroes.HeroPower;
import client.state.Handler;
import client.state.game.actions.HeroAction;
import client.state.game.actions.heroPowerActions.*;
import client.state.game.objectPool.MinionObjectPool;
import client.state.game.objectPool.WeaponObjectPool;
import client.state.game.questReward.Quest;
import client.state.infopassive.infoPassiveLogic;
import client.state.ShowErrors;
import client.utils.Asset;
import client.models.entities.Minion;
import client.models.entities.Weapon;
import client.utils.Converter;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import static client.audio.SoundEffect.effectPlay;
import static client.audio.SoundEffect.setFile;
import static client.input.MouseInput.*;
import static client.utils.Converter.*;
import static client.state.infopassive.infoPassiveLogic.*;
import static client.state.game.GameState.*;
import static client.utils.Logger.logger;
import static client.utils.RandomNumber.getRandom;
import static client.utils.Text.drawString;

public class GameLogic {
    private Handler handler;
    private Player[] players = new Player[2];
    private Player player;
    private infoPassiveLogic passiveLogic;
    private ActionLogic actionLogic;
    private GameEvent gameEvent;
    private ChooseCard[] chooseCard;
    private boolean gameEnded;
    private HashMap<String, String> rewardsInModeOne;
    private HashSet<Anim> anims;
    private MinionObjectPool minionObjectPool = new MinionObjectPool();
    private WeaponObjectPool weaponObjectPool = new WeaponObjectPool();
    private GameTimer gameTimer = new GameTimer(this);

    private static final int MAX_MINIONS = config.getMaxMinions();
    private static final int MAX_MANA = config.getMaxMana();
    private int cardsInHand = config.getCardsInHand();
    private int minionSpacing = config.getMinionSpacing();
    private int mode = config.getMode();
    private String enemy = config.getEnemy();
    private String deckReader = config.getDeckReader();
    private String player_one_hero = config.getPlayer_one_hero();
    private String player_two_hero = config.getPlayer_two_hero();


    public GameLogic(Handler handler) {
        this.handler = handler;
        gameEvent = new GameEvent(180, 370);
    }

    void init() {
        passiveLogic = getInstance();
        actionLogic = new ActionLogic(this);
        chooseCard = new ChooseCard[2];
        anims = new HashSet<>();
        rewardsInModeOne = new HashMap<>();

        setPlayer();
        setDecks();
        setPlayerFields();
    }

    private void setPlayerFields() {
        players[0].setMana(passiveLogic.getManajump());
        players[0].setMaxMana(passiveLogic.getManajump());
        players[1].setMana(passiveLogic.getManajump());
        players[1].setMaxMana(passiveLogic.getManajump());
        players[0].addEntity(players[0].getHero());
        players[1].addEntity(players[1].getHero());
        players[0].getHero().setBounding(665, 665);
        players[1].getHero().setBounding(665, 85);
        players[0].getHero().setX(665);
        players[0].getHero().setY(665);
        players[1].getHero().setX(665);
        players[1].getHero().setY(85);

        player = players[0];

        chooseCard[0] = new ChooseCard(players[0], handler);
        chooseCard[0].setActive(true);
        chooseCard[1] = new ChooseCard(players[1], handler);
        players[0].setTurn(passiveLogic.getManajump());
        players[1].setTurn(passiveLogic.getManajump());

        for (Player player : players)
            setHeroPowerAction(player);

    }

    private void setDecks() {
        ArrayList<String> names = new ArrayList<>();
        for (client.models.cards.card card : players[0].getCurrentDeck().getDeck()) {
            names.add(card.getName() + ".json");
        }
        players[0].getCurrentDeck().setDeck(cardFromJson(names));
        for (int i = 0; i < 4 / passiveLogic.getTwicedraw(); i++) {
            drawWithOutAnim(passiveLogic.getTwicedraw(), players[0]);
        }

        names.clear();
        for (client.models.cards.card card : players[1].getCurrentDeck().getDeck()) {
            names.add(card.getName() + ".json");
        }
        players[1].getCurrentDeck().setDeck(cardFromJson(names));
        for (int i = 0; i < 4 / passiveLogic.getTwicedraw(); i++) {
            drawWithOutAnim(passiveLogic.getTwicedraw(), players[1]);
        }
    }

    void renderTimeRemained(Graphics2D g) {
        if (!gameTimer.isAlive())
            return;
        int t = (int) (60000 - (System.currentTimeMillis() - gameTimer.getTimer().getPreviousTime())) / 1000;
        if (t < 21) {
            drawString(g, String.valueOf(t), 745, 20, true, Color.red, Asset.font20);
        }
    }

    void startTimer() {
        gameTimer.start();
    }

    private void setHeroPowerAction(Player player) {
        player.getHero().getHeroPower().setBounding(new Ellipse2D.Double(player.getHero().getX() + 200,
                player.getHero().getY() + 50, 100, 100));
        switch (player.getHero().getName()) {
            case "rogue":
                player.getHero().getHeroPower().setAction(new RoguePower(this, null, player));
                break;
            case "mage":
                player.getHero().getHeroPower().setAction(new MagePower(this, null, player));
                break;
            case "warlock":
                player.getHero().getHeroPower().setAction(new WarlockPower(this, null, player));
                break;
            case "priest":
                player.getHero().getHeroPower().setAction(new PriestPower(this, null, player));
                break;
            case "hunter":
                player.getHero().getHeroPower().setAction(new HunterPower(this, null, player));
        }
    }

    private void setPlayer() {
        switch (mode) {
            case 0:
                setModeOne();
                break;
            case 1:
                setFromDeckReader();
                break;
            case 2:
                setEnemy();
        }
    }

    private void setEnemy() {
        players[0] = handler.getGame().getEnvironment().getCurrentPlayer();
        players[1] = Converter.getEnemy(enemy);
        players[0].setHero(handler.getGame().getEnvironment()
                .getHero(players[0].getCurrentDeck().getHeroClass()).clone());
        players[1].setHero(handler.getGame().getEnvironment()
                .getHero(players[1].getCurrentDeck().getHeroClass()).clone());
        players[1].setQuests(new ArrayList<>());
        ((Enemy) players[1]).setLogic(this);
        ((Enemy) players[1]).setTimer(new RandomTimer(this, (Enemy) players[1]));
        ((Enemy) players[1]).getTimer().start();

    }

    private void setModeOne() {
        players[0] = handler.getGame().getEnvironment().getCurrentPlayer();
        players[0].setHero(handler.getGame().getEnvironment()
                .getHero(players[0].getCurrentDeck().getHeroClass()).clone());
        players[1] = Converter.getPlayer(enemy);
        players[1].setHero(handler.getGame().getEnvironment()
                .getHero(players[1].getCurrentDeck().getHeroClass()).clone());
    }

    private void setFromDeckReader() {
        players[0] = handler.getGame().getEnvironment().getCurrentPlayer();
        players[1] = Converter.getPlayer(enemy);
        players[0].setHero(handler.getGame().getEnvironment().getHero(player_one_hero).clone());
        players[1].setHero(handler.getGame().getEnvironment().getHero(player_two_hero).clone());
        players[0].getCurrentDeck().setDeck(cardFromJsonWithJson(getDeckReader(deckReader).getFriend(), rewardsInModeOne));
        players[1].getCurrentDeck().setDeck(cardFromJsonWithJson(getDeckReader(deckReader).getEnemy(), rewardsInModeOne));
    }

    private int getNextCardX(Player player) {
        return (40 + cardsInHand * 70) - (player.getHand().size() + 1) * 70;
    }

    public void drawCard(int count, Player player) {
        for (int i = 0; i < count; i++) {
            if (player.getCurrentDeck().getDeck().size() > 0) {
                int c = getRandom(player.getCurrentDeck().getDeck().size());
                if (mode == 1) {
                    c = 0;
                }
                card card = player.getCurrentDeck().getDeck().get(c);
                drawThis(card, player);
            } else {
                showMessage("YOUR DECK IS EMPTY!", 2000);
            }
        }
    }

    public void drawThis(card card, Player player) {
        if (player.getHand().size() < cardsInHand) {
            drawCardAnimation(card, player);
        }
        player.getCurrentDeck().getDeck().remove(card);
        actionLogic.executeDrawCardActions();
    }

    public void drawCardAnimation(card card, Player player) {
        DrawCardAnimationBuilder builder = new DrawCardAnimationBuilder();
        builder.setOriginX(1300)
                .setOriginY(600)
                .setDestinationX(getNextCardX(player))
                .setDestinationY(840)
                .setPainter(new Rotate(new Flip(new Painter(Asset.cards.get(card.getName()), 150, 230),
                        new Painter(Asset.cards.get("card_back"), 150, 230))))
                .setSpeed(x -> Math.pow(x, 1 / 1.4))
                .setC(card)
                .setListener(c1 -> addToHand(player, card));
        anims.add(new DrawCardAnimation(builder));
    }

    private void drawWithOutAnim(int count, Player player) {
        for (int i = 0; i < count; i++) {
            if (player.getCurrentDeck().getDeck().size() > 0) {
                int c = getRandom(player.getCurrentDeck().getDeck().size());
                if (mode == 1) {
                    c = 0;
                }
                card card = player.getCurrentDeck().getDeck().get(c);
                if (player.getHand().size() < cardsInHand) {
                    addToHand(player, card);
                }
                player.getCurrentDeck().getDeck().remove(player.getCurrentDeck().getDeck().get(c));
            } else {
                showMessage("YOUR DECK IS EMPTY!", 2000);
            }
        }
        actionLogic.executeDrawCardActions();
    }

    private void addToHand(Player player, card c) {
        player.getHand().add(c);
    }

    public boolean canSummonMoreMinions(Player player) {
        return player.getMinions().size() < MAX_MINIONS;
    }

    public void endTurn() {
        actionLogic.executeEndTurnActions();
    }

    public void startTurn() {
        actionLogic.clearActionToWait();
        actionLogic.handleOneTurnAction();
        player.setTurn(player.getTurn() + 1);
        if (player.getTurn() <= MAX_MANA) {
            player.setMana(player.getTurn());
            player.setMaxMana(player.getTurn());
        } else {
            player.setMana(MAX_MANA);
        }
        player = (player.equals(players[0])) ? players[1] : players[0];
        actionLogic.executeStartTurnActions();
        drawCard(passiveLogic.getTwicedraw(), player);

        player.getHero().getHeroPower().setCount(0);
        resetAttributes();
        gameTimer.getTimer().resetTimer();
        if (mode == 2 && player.equals(players[1])) {
            ((Enemy) players[1]).setUrTurn(true);
            ((Enemy) players[1]).resetRandomTimer();
        }
    }

    void restartTimer() {
        if (gameTimer == null)
            return;
        gameTimer.getTimer().resetTimer();
        gameTimer.setPause(false);
    }

    void pauseTimer() {
        if (gameTimer == null)
            return;
        gameTimer.setPause(true);
    }

    private void resetAttributes() {
        for (Minion minion : player.getMinions()) {
            minion.getAttributes().remove(Attribute.HAS_ATTACKED);
        }
        if (player.getWeapon() != null)
            player.getWeapon().getAttributes().remove(Attribute.HAS_ATTACKED);
        actionLogic.setActor(null);
        actionLogic.setTarget(null);
    }

    public void playCard(card card) {
        actionLogic.clearActionToWait();
        if (card instanceof MinionCard) {
            SummonMinion((MinionCard) card);
        } else if (card instanceof SpellCard) {
            playSpellCard((SpellCard) card);
        } else if (card instanceof WeaponCard) {
            playWeaponCard((WeaponCard) card);
        }
    }

    private void setCardUsage(card card, Player player) {
        if (player.getCurrentDeck().getCounter().keySet().contains(card))
            player.getCurrentDeck().getCounter().put(card, player.getCurrentDeck().getCounter().get(card) + 1);
        else
            player.getCurrentDeck().getCounter().put(card, 1);
    }

    private boolean canPlay(card card) {
        return !(player.getMana() >= (card.getManaCost() + passiveLogic.getOffcards() - player.getHero().getHeroPower().getRogueOffCard()));
    }

    boolean canPlayHeroPower(HeroPower heroPower, Player player) {
        int cost = heroPower.getManaCost() + passiveLogic.getOffcards();
        if (passiveLogic.isFreePower())
            cost--;
        return player.getMana() >= cost;
    }

    int getHeroPowerCost(HeroPower heroPower) {
        System.out.println(heroPower.getManaCost());
        System.out.println(passiveLogic.getOffcards());
        System.out.println(passiveLogic.isFreePower());
        int cost = heroPower.getManaCost() + passiveLogic.getOffcards();
        if (passiveLogic.isFreePower())
            cost--;
        return cost;
    }

    private void SummonMinion(MinionCard minionCard) {
        if (canPlay(minionCard)) {
            showMessage("YOU DON'T HAVE ENOUGH MANA!", 2000);
            return;
        }
        if (canSummonMoreMinions(player)) {
            int index = (getMouseX() - 320) / minionSpacing;
            if (index > player.getMinions().size())
                index = player.getMinions().size();
            else if (index < 0)
                index = 0;
            summon(minionCard, index);
            player.getHand().remove(minionCard);
            setCardUsage(minionCard, player);
            ((HeroAction) player.getHero().getHeroPower().getAction()).fireSpecialAction("rogue", minionCard, null);
            logger(handler.getGame().getEnvironment().getKey(), "game state : summon minion card : card played");
        } else {
            showMessage("YOU ALREADY HAVE 7 MINIONS!", 2000);
            logger(handler.getGame().getEnvironment().getKey(), "game state : summon minion card : cant be played");
        }
        gameEvent.updateEvents("card played : " + minionCard.getName());
    }

    private void checkHunterPower(Minion minion) {
        if (getTheOtherPlayer().getHero().getName().equals("hunter")) {
            actionLogic.setHunterTarget(minion);
            player.getHero().getHeroPower().getAction().execute();
        }
    }

    private void checkSwamp(Minion target) {
        for (Minion minion : getTheOtherPlayer().getMinions()) {
            if (minion.getMinionCard().getName().equals("swamp king dred")) {
                minion.getAttributes().remove(Attribute.HAS_ATTACKED);
                Player player1 = getTheOtherPlayer();
                Player player2 = player;
                attackAnimation(minion, target, player1, player2);
            }
        }
    }

    private void summon(MinionCard minionCard, int index) {
        doSummonAction(minionCard, index, player);
    }

    public void summon(MinionCard minionCard) {
        doSummonAction(minionCard, player.getMinions().size(), player);
    }

    private void doSummonAction(MinionCard minionCard, int index, Player player) {
        int x = ((GameState) handler.getGame().getGameState()).getNextMinionX(index);
        int y = ((GameState) handler.getGame().getGameState()).getNextMinionY();
        if (index != player.getMinions().size())
            resetMinionsBounding(index, player);
        Minion m = minionObjectPool.getMinion(minionCard, x, y);
        m.getAttributes().add(Attribute.HAS_ATTACKED);
        player.getMinions().add(index, m);
        player.addEntity(m);
        setFile(Asset.sound.get("minion_summoned"));
        effectPlay();
        actionLogic.handleBattleCryActions(m);
        actionLogic.handleMinionActions(m);
        actionLogic.executeSummonAction(m);
        checkHunterPower(m);
        ((HeroAction) player.getHero().getHeroPower().getAction()).fireSpecialAction("hunter", null, m);
        checkSwamp(m);
    }

    private void resetMinionsBounding(int index, Player player) {
        for (int i = index; i < player.getMinions().size(); i++) {
            player.getMinions().get(i).resetBounding(minionSpacing);
        }
    }

    private boolean canPlaySpell(card spellCard) {
        if ((spellCard.getHeroClass().equals("neutral") || spellCard.getHeroClass().equals("rogue"))
                && player.getHero().getName().equals("rogue"))
            return player.getMana() >= spellCard.getManaCost() + passiveLogic.getOffcards() -
                    player.getHero().getHeroPower().getSpellManaCost();
        else
            return player.getMana() >= spellCard.getManaCost() + passiveLogic.getOffcards() -
                    player.getHero().getHeroPower().getSpellManaCost() - player.getHero().getHeroPower().getRogueOffCard();
    }

    private void explosionAnimation(Entity entity) {
        setFile(Asset.sound.get("explosion"));
        effectPlay();
        ExplosionAnimationBuilder builder = new ExplosionAnimationBuilder();
        builder.setOriginX(entity.getX() - 50)
                .setOriginY(entity.getY() - 50)
                .setDestinationX(entity.getX())
                .setDestinationY(entity.getY())
                .setPainter(new Painter(Asset.frames[0], 240, 240))
                .setSpeed(w -> Math.pow(w, 1 / 1.2))
                .setFrames(Asset.frames)
                .setListener(() -> ((GameState) handler.getGame().getGameState()).endGame());
        anims.add(new ExplosionAnimation(builder));
    }

    private void attackAnimation(Entity actor, Entity target, Player player1, Player player2) {
        AttackAnimationBuilder builder = new AttackAnimationBuilder();
        builder.setOriginX(target.getX() + 30)
                .setOriginY(target.getY() + 30)
                .setDestinationX(target.getX() - 20)
                .setDestinationY(target.getY() - 20)
                .setPainter(new Painter(Asset.utils.get("splash"), 0, 0))
                .setSpeed(w -> Math.pow(w, 1 / 1.2))
                .setActor(actor)
                .setTarget(target)
                .setActorPlayer(player)
                .setTargetPlayer(getTheOtherPlayer())
                .setListener((actorr, targett, actorPlayer, targetPlayer) -> actionLogic.attack(actor, target, player1, player2));
        anims.add(new AttackAnimation(builder));
    }

    private void playSpellCard(SpellCard spellCard) {
        if (!canPlaySpell(spellCard)) {
            showMessage("YOU DON'T HAVE ENOUGH MANA!", 2000);
            return;
        }
        ((GameState) handler.getGame().getGameState()).getRenderer().setCard(spellCard);
        ((GameState) handler.getGame().getGameState()).getRenderer().setPreviousTime(System.currentTimeMillis());
        setCardUsage(spellCard, player);
        player.getHand().remove(spellCard);
        ((HeroAction) player.getHero().getHeroPower().getAction()).fireSpecialAction("mage,rogue", spellCard, null);
        defineQuest(spellCard);
        gameEvent.updateEvents("card played : " + spellCard.getName());
        effectPlay();
        logger(handler.getGame().getEnvironment().getKey(), "game state : play spell card : card played");
    }

    private void defineQuest(SpellCard spellCard) {
        if (spellCard.getQuest() == null)
            actionLogic.handleSpellActions(spellCard);
        else {
            Quest quest = new Quest(spellCard, player);
            player.getQuests().add(quest);
        }
    }

    public void setPlayerMana(card c) {
        int total = c.getManaCost() + passiveLogic.getOffcards();
        if (total < 0)
            total = 0;
        setFinalMana(player.getMana() - total, c);
    }

    public void setFinalMana(int a, card c) {
        int cost = player.getMana() - a;
        updateQuests(cost, c);
        player.setMana(a);
    }

    private void updateQuests(int a, card c) {
        if (player.getQuests().size() == 0)
            return;
        for (int i = player.getQuests().size() - 1; i >= 0; i--) {
            player.getQuests().get(i).update(a, this.getActionLogic(), c, player);
        }
    }

    private void playWeaponCard(WeaponCard weaponCard) {
        if (canPlay(weaponCard)) {
            showMessage("YOU DON'T HAVE ENOUGH MANA!", 2000);
            return;
        }
        setWeapon(weaponCard);
        setCardUsage(weaponCard, player);
        player.getHand().remove(weaponCard);
        ((HeroAction) player.getHero().getHeroPower().getAction()).fireSpecialAction("rogue", weaponCard, null);
        gameEvent.updateEvents("card played : " + weaponCard.getName());
        logger(handler.getGame().getEnvironment().getKey(), "game state : play weapon card : card played");
    }

    private void setWeapon(WeaponCard weaponCard) {
        if (player.getWeapon() != null)
            weaponObjectPool.checkIn(player.getWeapon());
        int x = 500;
        int y = ((GameState) handler.getGame().getGameState()).getWeaponY();
        Weapon weapon = weaponObjectPool.getWeapon(weaponCard, x, y);
        player.setWeapon(weapon);
        player.addEntity(weapon);
        actionLogic.handleBattleCryActions(weapon);
        actionLogic.handleWeaponActions(weapon);
        setFile(Asset.sound.get("minion_summoned"));
        effectPlay();
    }

    void handleAction() {
        if (!actionLogic.isDefined())
            actionLogic.defineActor(player, getMouseX(), getMouseY());
        else {
            Player p = getTheOtherPlayer();
            actionLogic.defineTarget(p, getMouseX(), getMouseY());
        }
        actionLogic.clearActionToWait();
    }

    GameEvent getGameEvent() {
        return gameEvent;
    }

    void reset() {
        gameEvent.reset();
        passiveLogic.reset();
        player.getQuests().clear();
    }

    void WinLose(Player winner, Player loser) {
        explosionAnimation(loser.getHero());
        if (winner.equals(players[0])) {
            showMessage("YOU WIN!", 2000);
        } else {
            showMessage("YOU LOST!", 2000);
        }
        updateStatus(players[0], winner);
    }

    private void updateStatus(Player player, Player winner) {
        Collection collection = null;
        for (String string : player.getDeck().keySet()) {
            if (string.equals(player.getCurrentDeck().getName()))
                collection = player.getDeck().get(string);
        }
        if (collection == null)
            return;
        collection.setTotalPlayed(player.getCurrentDeck().getTotalPlayed() + 1);
        if (winner.equals(player))
            collection.setWins(player.getCurrentDeck().getWins() + 1);
        collection.setCounter(player.getCurrentDeck().getCounter());
    }

    void resetPlayer() {
        for (int i = 0; i < 2; i++) {
            try {
                players[i].getHand().clear();
                players[i].getMinions().clear();
                players[i].getEntities().clear();
                players[i].setHero(null);
                players[i].setWeapon(null);
                players[i].setMana(0);
                players[i].setMaxMana(0);
                players[i].setTurn(1);
                players[i].setCurrentDeck(new Collection());
            } catch (Exception ignore) {

            }
        }
    }

    void setGameEnded(boolean gameEnded) {
        this.gameEnded = gameEnded;
    }

    public Handler getHandler() {
        return handler;
    }

    public Player getTheOtherPlayer() {
        return player.equals(players[0]) ? players[1] : players[0];
    }

    private void showMessage(String message, int time) {
        ((ShowErrors) handler.getGame().getShowErrors()).setActive(true);
        ((ShowErrors) handler.getGame().getShowErrors()).setMessage(message, time);
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public infoPassiveLogic getPassiveLogic() {
        return passiveLogic;
    }

    Player[] getPlayers() {
        return players;
    }

    ChooseCard[] getChooseCard() {
        return chooseCard;
    }

    public ActionLogic getActionLogic() {
        return actionLogic;
    }

    int getMinionSpacing() {
        return minionSpacing;
    }

    public boolean isGameEnded() {
        return gameEnded;
    }

    MinionObjectPool getMinionObjectPool() {
        return minionObjectPool;
    }

    WeaponObjectPool getWeaponObjectPool() {
        return weaponObjectPool;
    }

    public HashSet<Anim> getAnims() {
        return anims;
    }

    public HashMap<String, String> getRewardsInModeOne() {
        return rewardsInModeOne;
    }
}
