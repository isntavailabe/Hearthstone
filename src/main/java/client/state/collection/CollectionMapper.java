package client.state.collection;

import client.buttons.CardButton;

import client.state.Handler;

import java.awt.*;

class CollectionMapper {
    private CollectionLogic logic;

    CollectionMapper(Handler handler) {
        logic = new CollectionLogic(handler);
    }

    void renderDeckManager(int x, int y, Graphics2D g) {
        logic.renderDeckManager(x, y, g);
    }

    void showInfo(Graphics2D g, CardButton b, int index, int a) {
        logic.showInfo(g, b, index, a);
    }

    void removeDeck() {
        logic.removeDeck();
    }

    void search() {
        logic.search();
    }

    void updateFilters() {
        logic.updateFilters();
    }

    void renderCard(Graphics2D g, int num, int i, int height, int l, int cardX, int spacing, int cardWidth, int cardHeight, int lockwidth, int lockHeight) {
        logic.renderCard(g, num, i, height, l, cardX, spacing, cardWidth, cardHeight, lockwidth, lockHeight);
    }

    boolean hasReachedLimit(int a) {
        return logic.hasReachedLimit(a);
    }

    void setEditState() {
        logic.setEditState();
    }

    void setNeutral() {
        logic.setNeutral();
    }

    void setHeroClass(int a) {
        logic.setHeroClass(a);
    }

    void scrollUp() {
        logic.scrollUp();
    }

    void scrollDown() {
        logic.scrollDown();
    }

    void setDeckState() {
        logic.getDeckManager().setCurrent(DeckManager.state.DECK);
    }

    void doManaFilterStuff(int b, boolean filter) {
        logic.doManaFilterStuff(b, filter);
    }

    void removeCard() {
        logic.removeCard();
    }

    void setCurrentDeck() {
        logic.setCurrentDeck();
    }

    void setHero(int s) {
        logic.setSelectedHero(s);
    }

    void handleCardsYouHave() {
        logic.handleCardsYouHave();
    }

    void handleCardsYouDontHave() {
        logic.handleCardsYouDontHave();
    }

    void handleDeckManagerState() {
        logic.handleDeckManagerState();
    }

    void handleCardSelected(int plus, int spacing, int cardsInpage, int current) {
        logic.handleCardSelected(plus, spacing, cardsInpage, current);
    }
}
