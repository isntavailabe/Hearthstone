package client.state.collection;

import client.buttons.CardButton;
import client.state.Handler;
import client.state.ShowErrors;
import client.models.cards.Collection;
import client.models.cards.card;
import client.state.State;
import client.state.shop.ShopState;
import client.utils.Asset;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

import static client.input.MouseInput.getMouseX;
import static client.input.MouseInput.getMouseY;
import static client.state.collection.CollectionState.getConfig;
import static client.utils.Logger.logger;

class CollectionLogic {
    private Handler handler;
    private DeckManager deckManager;
    private ArrayList<card> selectedArray;
    private int manaFilter;
    private boolean ManaFilter;
    private boolean f1;
    private boolean f2;

    CollectionLogic(Handler handler) {
        this.handler = handler;
        deckManager = new DeckManager(handler);
        selectedArray = new ArrayList<>();
        setSelectedArray(handler.getGame().getEnvironment().getNeutralCards());
    }

    private void addManaFilter() {
        ArrayList<card> tmp = new ArrayList<>();
        Iterator<card> iterator = selectedArray.iterator();
        while (iterator.hasNext()) {
            card c = iterator.next();
            if (c.getManaCost() == manaFilter)
                tmp.add(c);
        }
        setSelectedArray(tmp);
    }

    private void addF1Filter() {
        ArrayList<card> tmp = new ArrayList<>();
        Iterator<card> iterator = selectedArray.iterator();
        while (iterator.hasNext()) {
            card c = iterator.next();
            if (handler.getGame().getEnvironment().getCurrentPlayer().getCollection().contains(c) ||
                    handler.getGame().getEnvironment().getHeroCards().contains(c))
                tmp.add(c);
        }
        setSelectedArray(tmp);
    }

    private void addF2Filter() {
        ArrayList<card> tmp = new ArrayList<>();
        Iterator<card> iterator = selectedArray.iterator();
        while (iterator.hasNext()) {
            card c = iterator.next();
            if (!handler.getGame().getEnvironment().getCurrentPlayer().getCollection().contains(c) && c.getHeroClass().equals("neutral"))
                tmp.add(c);
        }
        setSelectedArray(tmp);
    }

    void updateFilters() {
        if (ManaFilter)
            addManaFilter();
        if (f1)
            addF1Filter();
        else if (f2)
            addF2Filter();
    }

    private void addToDeck(card card) {
        if ((card.getHeroClass().equals("neutral") && haveCard(card)) || card.getHeroClass().equals(deckManager.getDecks()
                .get(deckManager.getSelectedItem()).getHeroClass())) {
            if (canAdd(card) && (deckManager.getDecks().get(deckManager.getSelectedItem()).getDeck().size() < getConfig().getCardsInDeck())) {
                deckManager.getDecks().get(deckManager.getSelectedItem()).getDeck().add(card);
                logger(handler.getGame().getEnvironment().getKey(), "collection state : add card to deck : " + card.getName() + " added");
            } else if (!(deckManager.getDecks().get(deckManager.getSelectedItem()).getDeck().size() < getConfig().getCardsInDeck())) {
                showMessage("YOUR DECK IS FULL!", 2000);
                logger(handler.getGame().getEnvironment().getKey(), "collection state : add card to deck : " + card.getName() + " --fulldeck");
            }
        } else {
            showMessage("YOU CAN'T ADD THIS CARD!", 2000);
            logger(handler.getGame().getEnvironment().getKey(), "collection state : add card to deck : " + card.getName() + " --cant add");
        }
    }

    private boolean haveCard(card card) {
        for (client.models.cards.card c : handler.getGame().getEnvironment().getCurrentPlayer().getCollection()) {
            if (card.equals(c))
                return true;
        }
        return false;
    }

    private boolean canAdd(card card) {
        int count = 0;
        for (client.models.cards.card c : deckManager.getDecks().get(deckManager.getSelectedItem()).getDeck()) {
            if (c.equals(card))
                count++;
            if (count >= 2) {
                showMessage("YOU HAVE 2 COPIES ALREADY!", 2000);
                return false;
            }
        }
        return true;
    }

    void setSelectedHero(int i) {
        if (deckManager.getCurrent() == DeckManager.state.NEW_DECK || deckManager.getCurrent() == DeckManager.state.CHANGE_DECK) {
            deckManager.setSelectedHero(i);
            logger(handler.getGame().getEnvironment().getKey(), "collection state : change hero");
        }
    }

    private void removeCard(card card) {
        deckManager.getDecks().get(deckManager.getSelectedItem()).getDeck().remove(card);
        deckManager.getDecks().get(deckManager.getSelectedItem()).getCounter().remove(card);
        if (deckManager.getDecks().get(deckManager.getSelectedItem()).getMostUsed().equals(card))
            deckManager.getDecks().get(deckManager.getSelectedItem()).setMostUsed(null);
        logger(handler.getGame().getEnvironment().getKey(), "collection state : remove card : " + card.getName());
    }

    private void nameFilter(String str) {
        ArrayList<card> tmp = new ArrayList<>();
        for (client.models.cards.card card : handler.getGame().getEnvironment().getCards())
            if (card.getName().contains(str)) {
                tmp.add(card);
            }
        if (tmp.size() == 0)
            showMessage("NO CARD FOUND!", 2000);
        else
            setSelectedArray(tmp);
    }

    private void setSelectedArray(ArrayList<card> selectedArray) {
        this.selectedArray.clear();
        this.selectedArray.addAll(selectedArray);
    }

    private void changeDeck() {
        String str = handler.getGame().getDecksName().getText();
        if (!str.equals("")) {
            for (String deckname : handler.getGame().getEnvironment().getCurrentPlayer().getDeck().keySet()) {
                if (deckname.equals(str)) {
                    handler.getGame().getDecksName().setVisible(false);
                    deckManager.setCurrent(DeckManager.state.DECK);
                    return;
                }
            }
            for (card card : deckManager.getDecks().get(deckManager.getSelectedItem()).getDeck()) {
                if (card.getHeroClass().equals(deckManager.getDecks().get(deckManager.getSelectedItem()).getHeroClass())) {
                    handler.getGame().getDecksName().setVisible(false);
                    deckManager.setCurrent(DeckManager.state.DECK);
                    return;
                }
            }
            String hero = handler.getGame().getEnvironment().getCurrentPlayer().getUnlockedHeroes().get(deckManager.getSelectedHero()).getName();
            handler.getGame().getEnvironment().getCurrentPlayer().getDeck().remove(deckManager.getDecks().get(deckManager.getSelectedItem()).getName());
            deckManager.getDecks().get(deckManager.getSelectedItem()).setName(str);
            deckManager.getDecks().get(deckManager.getSelectedItem()).setHeroClass(hero);
            handler.getGame().getEnvironment().getCurrentPlayer().getDeck().put(str, deckManager.getDecks().get(deckManager.getSelectedItem()));
        } else {
            showMessage("SELECT DECK'S NAME AND HERO CLASS", 2000);
        }
    }

    private void newDeck() {
        if (handler.getGame().getEnvironment().getCurrentPlayer().getDeck().keySet().size() >= 20) {
            showMessage("YOU CAN ONLY HAVE 20 DECKS", 2000);
            return;
        }
        if (!handler.getGame().getDecksName().getText().trim().isEmpty()) {
            String str = handler.getGame().getDecksName().getText();
            for (Collection collection : deckManager.getDecks()) {
                if (collection.getName().equals(str)) {
                    showMessage("CHOOSE ANOTHER NAME", 2000);
                    return;
                }
            }
            Collection newdeck = new Collection();
            ArrayList<card> deck = new ArrayList<>();
            newdeck.setName(str);
            newdeck.setDeck(deck);
            newdeck.setHeroClass(handler.getGame().getEnvironment().getCurrentPlayer()
                    .getUnlockedHeroes().get(deckManager.getSelectedHero()).getName());
            deckManager.getDecks().add(newdeck);
            handler.getGame().getEnvironment().getCurrentPlayer().getDeck().put(str, newdeck);
        } else {
            showMessage("SELECT DECK'S NAME AND HERO CLASS", 2000);
        }
    }

    void scrollUp() {
        deckManager.scrollUp();
    }

    void scrollDown() {
        deckManager.scrollDown();
    }

    void setCurrentDeck() {
        if (deckManager.getDecks().size() != 0)
            handler.getGame().getEnvironment().getCurrentPlayer().setCurrentDeck(deckManager.getDecks().get(deckManager.getSelectedItem()).clone());
    }

    DeckManager getDeckManager() {
        return deckManager;
    }

    private boolean isF1() {
        return f1;
    }

    private boolean isF2() {
        return f2;
    }

    private void setManaFilter(int manaFilter) {
        this.manaFilter = manaFilter;
    }

    private void setManaFilter(boolean manaFilter) {
        ManaFilter = manaFilter;
    }

    private void setF1(boolean f1) {
        this.f1 = f1;
    }

    private void setF2(boolean f2) {
        this.f2 = f2;
    }

    boolean hasReachedLimit(int a) {
        return a > selectedArray.size();
    }

    private void showMessage(String message, int time) {
        ((ShowErrors) handler.getGame().getShowErrors()).setActive(true);
        ((ShowErrors) handler.getGame().getShowErrors()).setMessage(message, time);
    }

    void doManaFilterStuff(int b, boolean filter) {
        setSelectedArray(handler.getGame().getEnvironment().getNeutralCards());
        setManaFilter(b);
        setManaFilter(true);
    }

    void handleCardsYouHave() {
        if (isF1()) {
            setF1(false);
            setSelectedArray(handler.getGame().getEnvironment().getNeutralCards());
            logger(handler.getGame().getEnvironment().getKey(), "collection state : cards you have filter enabled");
        } else {
            setF2(false);
            setSelectedArray(handler.getGame().getEnvironment().getNeutralCards());
            setF1(true);
            logger(handler.getGame().getEnvironment().getKey(), "collection state : cards you have filter disabled");
        }
    }

    void handleCardsYouDontHave() {
        if (isF2()) {
            setF2(false);
            setSelectedArray(handler.getGame().getEnvironment().getNeutralCards());
            logger(handler.getGame().getEnvironment().getKey(), "collection state : cards you dont have filter enabled");
        } else {
            setF1(false);
            setSelectedArray(handler.getGame().getEnvironment().getNeutralCards());
            setF2(true);
            logger(handler.getGame().getEnvironment().getKey(), "collection state : cards you dont have filter disabled");
        }
    }

    void showInfo(Graphics2D g, CardButton b, int index, int a) {
        b.showInfo(g, selectedArray.get(index + a));
    }

    private boolean isLocked(int num) {
        return !haveCard(selectedArray.get(num)) && selectedArray.get(num).getHeroClass().equals("neutral");
    }

    void setEditState() {
        if (deckManager.getCurrent() == DeckManager.state.DECK)
            deckManager.setCurrent(DeckManager.state.EDIT_DECK);
    }

    void setHeroClass(int a) {
        setSelectedArray(handler.getGame().getEnvironment().getCurrentPlayer().getUnlockedHeroes()
                .get(a).getCards());
    }

    void setNeutral() {
        setSelectedArray(handler.getGame().getEnvironment().getNeutralCards());
    }

    void renderCard(Graphics2D g, int num, int i, int height, int l, int cardX, int spacing, int cardWidth, int cardHeight, int lockwidth, int lockHeight) {
        int size = selectedArray.size();
        if (num < size) {
            g.drawImage(Asset.cards.get(selectedArray.get(num).getName()),
                    cardX + (i - l) * spacing, height, cardWidth, cardHeight, null);
            if (isLocked(num))
                g.drawImage(Asset.utils.get("lock"), cardX + cardWidth + (i - l) * spacing - 40, height + 20, lockwidth, lockHeight, null);
        }
    }

    void removeCard() {
        if ((getMouseY() - 120) / 25 < deckManager.getDecks().get(deckManager.getSelectedItem()).getDeck().size()) {
            card c = deckManager.getDecks().get(deckManager.getSelectedItem()).getDeck().get((getMouseY() - 120) / 25);
            removeCard(c);
        }
    }

    void search() {
        if (handler.getGame().getSearch().getText().equals(""))
            return;
        String str = handler.getGame().getSearch().getText();
        nameFilter(str);
        logger(handler.getGame().getEnvironment().getKey(), "collection state : search : " + str);
    }

    void removeDeck() {
        if (deckManager.getDecks().size() == 0)
            return;
        String remove = deckManager.getDecks().get(deckManager.getSelectedItem()).getName();
        handler.getGame().getEnvironment().getCurrentPlayer().getDeck().remove(remove);
        deckManager.getDecks().remove(deckManager.getSelectedItem());
        deckManager.setSelectedItem(0);
        deckManager.setCurrent(DeckManager.state.DECK);
        logger(handler.getGame().getEnvironment().getKey(), "collection state : remove deck : " + remove);
    }

    void handleDeckManagerState() {
        if (deckManager.getCurrent() == DeckManager.state.DECK) {
            deckManager.setCurrent(DeckManager.state.NEW_DECK);
            logger(handler.getGame().getEnvironment().getKey(), "collection state : change deck state : new_deck");
        } else if (deckManager.getCurrent() == DeckManager.state.EDIT_DECK) {
            deckManager.setCurrent(DeckManager.state.CHANGE_DECK);
            logger(handler.getGame().getEnvironment().getKey(), "collection state : change deck state : change_deck_state");
        } else if (deckManager.getCurrent() == DeckManager.state.CHANGE_DECK) {
            changeDeck();
            handler.getGame().getDecksName().setVisible(false);
            deckManager.setCurrent(DeckManager.state.DECK);
            logger(handler.getGame().getEnvironment().getKey(), "collection state : change deck state : deck_state");
        } else if (deckManager.getCurrent() == DeckManager.state.NEW_DECK) {
            newDeck();
            handler.getGame().getDecksName().setVisible(false);
            getDeckManager().setCurrent(DeckManager.state.DECK);
            logger(handler.getGame().getEnvironment().getKey(), "collection state : change deck state : deck_state");
        }
    }

    void handleCardSelected(int plus, int spacing, int cardsInpage, int current) {
        if (deckManager.getCurrent() == DeckManager.state.EDIT_DECK) {
            if (((getMouseX() - 220) / spacing + current * cardsInpage + plus) < selectedArray.size()) {
                card c = selectedArray.get((getMouseX() - 220) / spacing + current * cardsInpage + plus);
                addToDeck(c);
            }
        } else {
            ((ShopState) handler.getGame().getShopState()).getMapper().getLogic().setCards(selectedArray
                    .get((getMouseX() - 220) / spacing + current * cardsInpage + plus));
            State.setCurrentState(handler.getGame().getShopState());
            handler.getGame().getSearch().setVisible(false);
            handler.getGame().getDecksName().setVisible(false);
            logger(handler.getGame().getEnvironment().getKey(), "collection state : change state : shop state");
        }
    }

    void renderDeckManager(int x, int y, Graphics2D g) {
        deckManager.render(x, y, g);
    }
}
