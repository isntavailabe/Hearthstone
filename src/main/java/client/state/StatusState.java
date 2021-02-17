package client.state;

import client.buttons.Action;
import client.buttons.Button;
import client.buttons.CircleButton;
import client.buttons.RectButton;
import client.models.cards.card;
import client.utils.Asset;
import client.utils.Constants;
import client.utils.config.StatusConfig;
import client.models.cards.Collection;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import static client.utils.Text.drawString;
import static client.utils.Logger.logger;

public class StatusState extends State {
    private ArrayList<Button> buttons;
    private HashMap<String, Collection> allDecks;
    private ArrayList<String> topten;
    private int selected;
    private String selectedDeck;
    private StatusConfig config = new StatusConfig("STATUSSTATE_CONFIGFILE");

    private int labelX = config.getLabelX();
    private int labelY = config.getLabelY();
    private int infoX = config.getInfoX();
    private int infoY = config.getInfoY();
    private int deckNameX = config.getDeckNameX();
    private int deckNameY = config.getDeckNameY();
    private int lengthLimit = config.getLengthLimit();
    private int Spacing = config.getSpacing();

    public StatusState(Handler handler) {
        super(handler);
        buttons = new ArrayList<>();
        topten = new ArrayList<>();
        allDecks = handler.getGame().getEnvironment().getCurrentPlayer().getDeck();
        selected = 0;
        createButtons();
        init();
    }

    private void avrMana() {
        for (String deck : topten) {
            int manaCost = 0;
            for (client.models.cards.card card : allDecks.get(deck).getDeck()) {
                manaCost += card.getManaCost();
            }
            if (allDecks.get(deck).getDeck().size() != 0)
                allDecks.get(deck).setAvrManaCost(manaCost / allDecks.get(deck).getDeck().size());
        }
    }

    public void updateDecks() {
        allDecks = handler.getGame().getEnvironment().getCurrentPlayer().getDeck();
        topten.clear();
        topten.addAll(allDecks.keySet());
        avrMana();
        sort();
        updateCardUsage();
    }

    private void updateCardUsage() {
        for (String string : topten) {
            ArrayList<card> mostUsedCards = new ArrayList<>();
            getMostUsed(allDecks.get(string), mostUsedCards);
            if (mostUsedCards.size() > 1)
                getMostRarity(mostUsedCards);
            if (mostUsedCards.size() > 1)
                getMostMana(mostUsedCards);
            if (mostUsedCards.size() > 1)
                getMinionCards(mostUsedCards);
            Collections.shuffle(mostUsedCards);
            if (mostUsedCards.size() != 0)
                allDecks.get(string).setMostUsed(mostUsedCards.get(0));
        }
    }

    private void getMinionCards(ArrayList<card> mostManaCost) {
        boolean hasMinion = false;
        for (card card : mostManaCost) {
            if (card.getType().equals("minion"))
                hasMinion = true;
        }
        if (hasMinion)
            for (int i = mostManaCost.size() - 1; i >= 0; i--)
                if (!mostManaCost.get(i).getType().equals("minion"))
                    mostManaCost.remove(i);
    }

    private void getMostMana(ArrayList<card> mostRarity) {
        int maxMana = 0;
        for (card card : mostRarity) {
            if (card.getManaCost() >= maxMana)
                maxMana = card.getManaCost();
        }
        for (int i = mostRarity.size() - 1; i >= 0; i--)
            if (mostRarity.get(i).getManaCost() != maxMana)
                mostRarity.remove(i);
    }

    private void getMostRarity(ArrayList<card> mostUsedCards) {
        int maxRarity = 0;
        for (card card : mostUsedCards) {
            if (card.getRarityValue() >= maxRarity)
                maxRarity = card.getRarityValue();
        }
        for (int i = mostUsedCards.size() - 1; i >= 0; i--)
            if (mostUsedCards.get(i).getRarityValue() != maxRarity)
                mostUsedCards.remove(i);
    }

    private void getMostUsed(Collection collection, ArrayList<card> mostUsedCards) {
        int maxUsage = 0;
        for (card card : collection.getCounter().keySet()) {
            if (collection.getCounter().get(card) > maxUsage)
                maxUsage = collection.getCounter().get(card);
        }
        for (card card : collection.getCounter().keySet())
            if (collection.getCounter().get(card) == maxUsage)
                mostUsedCards.add(card);
    }

    public void reset() {
        selected = 0;
        if (topten.size() != 0)
            selectedDeck = topten.get(0);
    }

    private void init() {
        topten.addAll(allDecks.keySet());
        sort();
        selectedDeck = null;
        if (topten.size() != 0)
            selectedDeck = topten.get(0);
    }

    @Override
    public void update() {
        for (Button b : buttons) {
            b.update();
        }
    }

    @Override
    public void render(Graphics2D g) {
        g.drawImage(Asset.backgrounds.get("statusback"), 0, 0, Constants.GameWidth, Constants.GameHeight, null);
        renderButtons(g);
        renderInfo(g);
        renderDecks(g);
    }

    private void createButtons() {
        buttons.add(new CircleButton(1400, 20, 60, new Action() {
            public void doAction() {
                logger(handler.getGame().getEnvironment().getKey(), "status state : exit");
                System.exit(0);
            }
        }, Asset.utils.get("exit_button")));
        buttons.add(new CircleButton(1320, 20, 60, new Action() {
            public void doAction() {
                setCurrentState(handler.getGame().getMenuState());
                logger(handler.getGame().getEnvironment().getKey(), "status state : change state : menu state");
            }
        }, Asset.utils.get("menu_button")));
        buttons.add(new RectButton(945, 140, 40, 40, new Action() {
            public void doAction() {
                if (allDecks.size() == 0)
                    return;
                selected--;
                if (selected < 0)
                    selected = 0;
                selectedDeck = topten.get(selected);
                logger(handler.getGame().getEnvironment().getKey(), "status state : scroll decks");
            }
        }, Asset.utils.get("arrow_up")));
        buttons.add(new RectButton(945, 820, 40, 40, new Action() {
            public void doAction() {
                if (allDecks.size() == 0)
                    return;
                selected++;
                if (selected >= topten.size())
                    selected = topten.size() - 1;
                selectedDeck = topten.get(selected);
                logger(handler.getGame().getEnvironment().getKey(), "status state : scroll decks");
            }
        }, Asset.utils.get("arrow_down")));
    }

    private void renderButtons(Graphics2D g) {
        for (Button b : buttons) {
            b.render(g);
        }
    }

    private void renderDecks(Graphics2D g) {
        if (allDecks.size() == 0)
            return;
        try {
            drawString(g, "HERO CLASS : ", labelX, labelY, false, Color.white, Asset.white40);
            drawString(g, "TOTAL WINS : ", labelX, labelY + Spacing, false, Color.white, Asset.white40);
            drawString(g, "TOTAL PLAYED : ", labelX, labelY + 2 * Spacing, false, Color.white, Asset.white40);
            drawString(g, "WINS/PLAYED : ", labelX, labelY + 3 * Spacing, false, Color.white, Asset.white40);
            drawString(g, "AVERAGE MANACOST : ", labelX, labelY + 4 * Spacing, false, Color.white, Asset.white40);
            drawString(g, "MOST USED CARD : ", labelX, labelY + 5 * Spacing, false, Color.white, Asset.white40);

            drawString(g, allDecks.get(selectedDeck).getHeroClass().toUpperCase(), infoX, infoY, true, Color.white, Asset.white40);
            drawString(g, String.valueOf(allDecks.get(selectedDeck).getWins()), infoX, infoY + Spacing, true, Color.white, Asset.white40);
            drawString(g, String.valueOf(allDecks.get(selectedDeck).getTotalPlayed()), infoX, infoY + 2 * Spacing, true, Color.white, Asset.white40);
            if (allDecks.get(selectedDeck).getTotalPlayed() != 0)
                drawString(g, String.valueOf((double) allDecks.get(selectedDeck).getWins() / (double) allDecks.get(selectedDeck).getTotalPlayed()), infoX,
                        infoY + 3 * Spacing, true, Color.white, Asset.white40);
            drawString(g, String.valueOf(allDecks.get(selectedDeck).getAvrManaCost()), infoX, infoY + 4 * Spacing, true, Color.white, Asset.white40);
            if (allDecks.get(selectedDeck).getMostUsed() != null)
                drawString(g, String.valueOf(allDecks.get(selectedDeck).getMostUsed().getName()), infoX, infoY + 5 * Spacing, true, Color.white, Asset.white40);

        } catch (Exception ignore) {

        }
    }

    private void renderInfo(Graphics2D g) {
        if (allDecks.size() == 0 || selectedDeck == null)
            return;
        g.drawImage(Asset.heroes.get(allDecks.get(selectedDeck).getHeroClass() + "Status"), 130, 305, 230, 270, null);
        drawString(g, deckName(), deckNameX, deckNameY, true, Color.white, Asset.font20);
    }

    private String deckName() {
        if (allDecks.get(selectedDeck).getName().length() > lengthLimit)
            return allDecks.get(selectedDeck).getName().substring(0, lengthLimit) + "...";
        return allDecks.get(selectedDeck).getName();
    }

    private void sort() {
        String tmp;
        for (int i = 0; i < topten.size(); i++) {
            for (int j = i + 1; j < topten.size(); j++) {
                if (winsFracPlayed(i, j)) {
                    tmp = topten.get(i);
                    topten.set(i, topten.get(j));
                    topten.set(j, tmp);
                }
            }
        }
    }

    private boolean winsFracPlayed(int i, int j) {
        double jj = (double) allDecks.get(topten.get(j)).getWins() / allDecks.get(topten.get(j)).getTotalPlayed();
        double ii = (double) allDecks.get(topten.get(i)).getWins() / allDecks.get(topten.get(i)).getTotalPlayed();
        if (jj > ii)
            return true;
        else if (jj == ii) {
            return totalWins(i, j);
        }
        return false;
    }

    private boolean totalWins(int i, int j) {
        int jj = allDecks.get(topten.get(j)).getWins();
        int ii = allDecks.get(topten.get(i)).getWins();
        if (jj > ii)
            return true;
        else if (jj == ii) {
            return totalPlayed(i, j);
        }
        return false;
    }

    private boolean totalPlayed(int i, int j) {
        int jj = allDecks.get(topten.get(j)).getTotalPlayed();
        int ii = allDecks.get(topten.get(i)).getTotalPlayed();
        if (jj > ii)
            return true;
        else if (jj == ii) {
            return avrMana(i, j);
        }
        return false;
    }

    private boolean avrMana(int i, int j) {
        int jj = allDecks.get(topten.get(j)).getAvrManaCost();
        int ii = allDecks.get(topten.get(i)).getAvrManaCost();
        return jj > ii;
    }
}
