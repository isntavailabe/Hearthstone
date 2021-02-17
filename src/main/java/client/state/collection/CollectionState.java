package client.state.collection;

import client.buttons.*;
import client.buttons.Button;
import client.state.Handler;
import client.state.State;
import client.utils.Asset;
import client.utils.Constants;
import client.utils.config.CollectionConfig;

import java.awt.*;
import java.util.ArrayList;

import static client.input.MouseInput.*;
import static client.utils.Text.drawString;
import static client.utils.Logger.logger;

public class CollectionState extends State {
    private ArrayList<Button> buttons;
    private CollectionMapper mapper;
    private int currentPage;
    private static CollectionConfig config = new CollectionConfig("COLLECTIONSTATE_CONFIGFILE");

    private int cardWidth = config.getCardWidth();
    private int cardHeight = config.getCardHeight();
    private int lockwidth = config.getLockWidth();
    private int lockHeight = config.getLockHeight();
    private int spacing = config.getCardSpacing();
    private int cardsInpage = config.getCardsInPage();
    private int deckManagerX = config.getDeckManagerX();
    private int deckManagerY = config.getDeckManagerY();
    private int manaY = config.getManaY();
    private int manaX = config.getManaX();
    private int manaSpacing = config.getManaSpacing();
    private int cardX = config.getCardX();
    private int rowOneY = config.getRowOneY();
    private int rowTwoY = config.getRowTwoY();


    public CollectionState(Handler handler) {
        super(handler);
        mapper = new CollectionMapper(handler);
        buttons = new ArrayList<>();
        createButtons();
        currentPage = 1;
    }

    static CollectionConfig getConfig() {
        return config;
    }

    @Override
    public void update() {
        for (Button b : buttons) {
            b.update();
        }
    }

    @Override
    public void render(Graphics2D g) {
        g.drawImage(Asset.backgrounds.get("collectionback"), 0, 0, Constants.GameWidth, Constants.GameHeight, null);
        renderCards(g);
        drawStrings(g);
        renderButtons(g);
        mapper.renderDeckManager(deckManagerX, deckManagerY, g);
        renderMana(g);
    }

    private void drawStrings(Graphics2D g) {
        for (int i = 0; i < handler.getGame().getEnvironment().getCurrentPlayer().getUnlockedHeroes().size(); i++) {
            String str = handler.getGame().getEnvironment().getCurrentPlayer().getUnlockedHeroes().get(i).getName();
            drawString(g, str, 1400, 175 + 50 * i, false, Color.white, Asset.font20);
        }
    }

    private void renderMana(Graphics2D g) {
        for (int i = 1; i < 10; i++) {
            drawString(g, String.valueOf(i), manaX + manaSpacing * i - 5, manaY, true, Color.white, Asset.font20);
        }
        drawString(g, "+10", manaX + manaSpacing * 10 - 5, manaY, true, Color.white, Asset.font20);
        drawString(g, "all", manaX + manaSpacing * 11 - 5, manaY, true, Color.white, Asset.font20);
    }

    private void renderButtons(Graphics2D g) {
        for (Button b : buttons) {
            b.render(g);
            if (b instanceof CardButton) {
                try {
                    if (getMouseY() < rowTwoY)
                        mapper.showInfo(g, (CardButton) b, (getMouseX() - cardX) / spacing + (currentPage - 1) * cardsInpage, 0);
                    else
                        mapper.showInfo(g, (CardButton) b, (getMouseX() - cardX) / spacing + (currentPage - 1) * cardsInpage, cardsInpage / 2);
                } catch (Exception e) {

                }
            }
        }
    }

    private void renderCards(Graphics2D g) {
        mapper.updateFilters();
        for (int i = 1; i <= cardsInpage / 2; i++) {
            mapper.renderCard(g, (currentPage * cardsInpage) - (cardsInpage + 1) + i, i, rowOneY, 1, cardX, spacing,
                    cardWidth, cardHeight, lockwidth, lockHeight);
        }
        for (int i = cardsInpage / 2 + 1; i <= cardsInpage; i++) {
            mapper.renderCard(g, (currentPage * cardsInpage) - (cardsInpage + 1) + i, i, rowTwoY, cardsInpage / 2 + 1,
                    cardX, spacing, cardWidth, cardHeight, lockwidth, lockHeight);
        }
    }

    private void createButtons() {
        //SELECTED DECK BUTTON : TO EDIT
        buttons.add(new RectButton(1076, 120 + 86 * 4, 200, 80, () -> {
            mapper.setEditState();
            logger(handler.getGame().getEnvironment().getKey(), "collection state : edit deck");
        }));

        //SWITCH HERO CLASS
        buttons.add(new RectButton(170, 15, 45, 45, () -> {
            currentPage = 1;
            mapper.setNeutral();
            logger(handler.getGame().getEnvironment().getKey(), "collection state : select class(cards)");
        }, Asset.heroclasses.get("neutralclass_in")));
        for (int i = 1; i < handler.getGame().getEnvironment().getCurrentPlayer().getUnlockedHeroes().size() + 1; i++) {
            buttons.add(new RectButton(170 + i * 45, 15, 45, 45, () -> {
                currentPage = 1;
                mapper.setHeroClass(((getMouseX() - 170) / 45) - 1);
                logger(handler.getGame().getEnvironment().getKey(), "collection state : select class(cards)");
            }, Asset.heroclasses.get(handler.getGame().getEnvironment().getCurrentPlayer().getUnlockedHeroes().get(i - 1).getName() + "class_in")));
        }

        //DECKMANAGER : SCROLL
        buttons.add(new RectButton(1310, 60, 40, 40, () -> {
            mapper.scrollUp();
            logger(handler.getGame().getEnvironment().getKey(), "collection state : scroll deck");
        }, Asset.utils.get("arrow_up")));
        buttons.add(new RectButton(1310, 870, 40, 40, () -> {
            mapper.scrollDown();
            logger(handler.getGame().getEnvironment().getKey(), "collection state : scroll deck");
        }, Asset.utils.get("arrow_down")));

        //deckmanager deck button
        buttons.add(new RectButton(1070, 0, 240, 65, () -> {
            mapper.setDeckState();
            handler.getGame().getDecksName().setVisible(false);
            logger(handler.getGame().getEnvironment().getKey(), "collection state : set state to deck_state");
        }, Asset.utils.get("deck_button")));

        //NEXT PAGE
        buttons.add(new RectButton(980, 460, 20, 60, () -> {
            nextPage();
            logger(handler.getGame().getEnvironment().getKey(), "collection state : next page");
        }, Asset.utils.get("right_arrow_in")));

        //PREVIOUS PAGE
        buttons.add(new RectButton(170, 460, 20, 60, () -> {
            previousPage();
            logger(handler.getGame().getEnvironment().getKey(), "collection state : previous page");
        }, Asset.utils.get("left_arrow_in")));

        //FILTER : MANA
        for (int i = 1; i < 11; i++) {
            buttons.add(new CircleButton(manaX - manaSpacing / 2 + manaSpacing * i, manaY - manaSpacing / 2, manaSpacing, () -> {
                mapper.doManaFilterStuff(((getMouseX() - manaX - manaSpacing / 2) / manaSpacing) + 1, true);
                currentPage = 1;
                logger(handler.getGame().getEnvironment().getKey(), "collection state : mana filter : "
                        + (getMouseX() - manaX - manaSpacing / 2) / manaSpacing + 1);
            }, Asset.utils.get("mana")));
        }

        //FILTER : MANA : all cards
        buttons.add(new CircleButton(manaX - manaSpacing / 2 + manaSpacing * 11, manaY - manaSpacing / 2, manaSpacing, () -> {
            mapper.doManaFilterStuff(0, true);
            currentPage = 1;
            logger(handler.getGame().getEnvironment().getKey(), "collection state : all cards filter");
        }, Asset.utils.get("mana")));

        //FILTER : CARDS YOU HAVE
        buttons.add(new RectButton(170 + 40 * 13, 890, 30, 40, () -> mapper.handleCardsYouHave(), Asset.utils.get("unlock")));

        //FILTER : CARDS YOU DONT HAVE
        buttons.add(new RectButton(170 + 40 * 14 + 5, 890, 30, 40, () -> mapper.handleCardsYouDontHave(), Asset.utils.get("lock")));

        //CARD BUTTONS
        for (int i = 0; i < cardsInpage / 2; i++) {
            buttons.add(new CardButton(220 + spacing * i, 150, cardWidth, cardHeight, () ->
                    mapper.handleCardSelected(0, spacing, cardsInpage, currentPage - 1)));
        }
        for (int i = cardsInpage / 2; i < cardsInpage; i++) {
            buttons.add(new CardButton(220 + spacing * (i - cardsInpage / 2), 520, cardWidth, cardHeight, () ->
                    mapper.handleCardSelected(cardsInpage / 2, spacing, cardsInpage, currentPage - 1)));
        }

        //REMOVE CARDS
        for (int i = 0; i < 30; i++) {
            buttons.add(new RectButton(1290, 120 + 25 * i, 20, 20, () ->
                    mapper.removeCard(), Asset.utils.get("close_button")));
        }

        //SEARCH
        buttons.add(new CircleButton(800, 890, 40, () -> mapper.search(), Asset.utils.get("search")));

        //DECKMANAGER STATE
        buttons.add(new RectButton(1065, 910, 150, 60, () -> mapper.handleDeckManagerState(), Asset.button.get("newdeck_button")));
        //SELECT HERO
        for (int i = 0; i < handler.getGame().getEnvironment().getCurrentPlayer().getUnlockedHeroes().size(); i++) {
            buttons.add(new CircleButton(1320, 150 + 50 * i, 45, () ->
                    mapper.setHero((getMouseY() - 150) / 50), Asset.heroclasses.get(handler.getGame().getEnvironment().getCurrentPlayer().getUnlockedHeroes().get(i)
                    .getName() + "class_in")));
        }
        //MENU
        buttons.add(new CircleButton(40, 880, 60, () -> {
            mapper.setCurrentDeck();
            setMenuState();
        }, Asset.utils.get("menu_button")));

        //EXIT
        buttons.add(new CircleButton(40, 810, 60, () -> {
            logger(handler.getGame().getEnvironment().getKey(), "collection state : exit");
            System.exit(0);
        }, Asset.utils.get("exit_button")));

        //REMOVE DECK
        buttons.add(new RectButton(1330, 830, 140, 40, () -> mapper.removeDeck(), Asset.button.get("removedeck_button")));
        buttons.add(new RectButton(1235, 915, 90, 50, () -> {
            mapper.setCurrentDeck();
            setCurrentState(handler.getGame().getMenuState());
            handler.getGame().getSearch().setVisible(false);
            handler.getGame().getDecksName().setVisible(false);
            logger(handler.getGame().getEnvironment().getKey(), "collection state : change state : menu state");
        }, Asset.button.get("back_button")));
    }

    private void setMenuState() {
        setCurrentState(handler.getGame().getMenuState());
        handler.getGame().getSearch().setVisible(false);
        handler.getGame().getDecksName().setVisible(false);
        logger(handler.getGame().getEnvironment().getKey(), "collection state : change state : menu state");
    }

    private void nextPage() {
        currentPage += 1;
        if (mapper.hasReachedLimit((currentPage - 1) * 6))
            currentPage -= 1;
    }

    private void previousPage() {
        currentPage -= 1;
        if (currentPage < 1)
            currentPage = 1;
    }
}
