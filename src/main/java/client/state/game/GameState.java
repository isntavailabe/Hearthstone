package client.state.game;

import client.audio.Audio;
import client.buttons.*;
import client.buttons.Button;
import client.models.cards.card;
import client.state.Handler;
import client.state.State;
import client.utils.Asset;
import client.utils.Constants;
import client.utils.config.GameConfig;

import java.awt.*;
import java.util.ArrayList;

import static client.input.MouseInput.*;
import static client.utils.Text.*;
import static client.utils.Logger.logger;

public class GameState extends State {
    private ArrayList<Button> buttons;
    private boolean showEvent;
    private card card;
    private PlayCardRenderer renderer;
    private GameMapper mapper = new GameMapper(handler);

    static GameConfig config = new GameConfig("GAMESTATE_CONFIGFILE");

    private int cardsInHand = config.getCardsInHand();
    private int cardWidth = config.getCardWidth();
    private int cardHeight = config.getCardHeight();
    private int cardSpacing = config.getCardSpacing();
    private int minionSpacing = config.getMinionSpacing();
    private int gameEventRecent = config.getGameEventrecent();


    public GameState(Handler handler) {
        super(handler);
        buttons = new ArrayList<>();
        createButtons();
        renderer = new PlayCardRenderer();
    }

    public void start() {
        mapper.initGameLogic();
    }

    @Override
    public void update() {
        mapper.updateChooseCard();
        if (mapper.chooseCardIsActive())
            return;
        for (Button button : buttons) {
            button.update();
        }
    }

    @Override
    public void render(Graphics2D g) {
        g.drawImage(Asset.backgrounds.get("gameback"), 0, 0, Constants.GameWidth, Constants.GameHeight, null);
        drawString(g, String.valueOf(mapper.getSizeOfDeck()), 1400, 680, true, Color.white, Asset.font20);
        renderMinions(g);
        renderMana(g);
        renderWeapon(g);
        renderHero(g);
        renderCards(g);
        renderGameEvent(g);
        renderButtons(g);
        renderChooseCard(g);
        renderDragged(g);
        renderActor(g);
        renderEntityInfo(g);
        renderTime(g);
        renderQuests(g);
        renderAnims(g);
        renderer.renderCard(g);
    }

    private void renderAnims(Graphics2D g) {
        mapper.renderAnims(g);
    }

    private void renderQuests(Graphics2D g) {
        mapper.renderQuests(g);
    }

    private void renderEntityInfo(Graphics2D g) {
        mapper.renderEntityInfo(g);
    }

    private void renderTime(Graphics2D g) {
        mapper.renderTimeRemained(g);
    }

    private void renderGameEvent(Graphics2D g) {
        if (showEvent)
            mapper.renderGameEvent(g);
    }

    private void renderChooseCard(Graphics2D g) {
        mapper.renderChooseCard(g);
    }

    private void renderDragged(Graphics2D g) {
        if (card != null) {
            if (isDragged()) {
                g.drawImage(Asset.cards.get(card.getName()), getMouseX(), getMouseY(), 110, 150, null);
            } else {
                mapper.play(card);
                card = null;
            }
        }
    }

    private void renderButtons(Graphics2D g) {
        for (Button button : buttons) {
            button.render(g);
            mapper.renderCardButton(g, button, cardsInHand, cardSpacing);
        }
    }

    private void renderCards(Graphics2D g) {
        mapper.renderCards(g, cardWidth, cardHeight, cardSpacing, cardsInHand);
    }

    private void createButtons() {
        //CARD BUTTON
        for (int i = 0; i < 11; i++) {
            buttons.add(new CardButton(50 + 70 * i, 850, cardSpacing, 150, new Action() {
                public void doAction() {
                    if (mapper.isEnemy())
                        return;
                    card = mapper.getCard((cardsInHand - 1) - (getMouseX() - 50) / cardSpacing);
                }
            }, Asset.sound.get("card_play")));
        }
        buttons.add(new CardButton(820, 850, 135, 150, new Action() {
            public void doAction() {
                if (mapper.isEnemy())
                    return;
                card = mapper.getCard(0);
            }
        }, Asset.sound.get("card_play")));
        buttons.add(new CircleButton(20, 20, 60, new Action() {
            public void doAction() {
                logger(handler.getGame().getEnvironment().getKey(), "game state : exit");
                System.exit(0);
            }
        }, Asset.utils.get("exit_button")));
        buttons.add(new RectButton(1250, 400, 170, 95, new Action() {
            public void doAction() {
                if (mapper.isEnemy())
                    return;
                mapper.changeTurn();
                logger(handler.getGame().getEnvironment().getKey(), "game state : end turn");
            }
        }, Asset.utils.get("endturn_button")));
        buttons.add(new CircleButton(20, 100, 60, new Action() {
            public void doAction() {
                exitGame();
                logger(handler.getGame().getEnvironment().getKey(), "game state : change state : menu state");
            }
        }, Asset.utils.get("menu_button")));
        buttons.add(new CircleButton(20, 180, 60, new Action() {
            public void doAction() {
                endGame();
            }
        }, Asset.utils.get("endgame_button")));
        buttons.add(new RectButton(100, 280, 50, 50, new Action() {
            public void doAction() {
                showEvent = !showEvent;
                logger(handler.getGame().getEnvironment().getKey(), "game state : change game event attribute");
            }
        }, Asset.utils.get("mana")));
        buttons.add(new RectButton(110, 530, 40, 40, new Action() {
            public void doAction() {
                mapper.arrowUp(gameEventRecent);
                logger(handler.getGame().getEnvironment().getKey(), "game state : scroll game event");
            }
        }, Asset.utils.get("arrow_down")));
        buttons.add(new RectButton(110, 350, 40, 40, new Action() {
            public void doAction() {
                mapper.arrowDown();
                logger(handler.getGame().getEnvironment().getKey(), "game state : scroll game event");
            }
        }, Asset.utils.get("arrow_up")));

        buttons.add(new RectButton(170, 70, 1050, 750, new Action() {
            @Override
            public void doAction() {
                mapper.handleAction();
            }
        }));
    }

    void endGame() {
        exitGame();
        mapper.pauseTimer();
        logger(handler.getGame().getEnvironment().getKey(), "game state : change state : menu state");
    }

    int getNextMinionX(int index) {
        return 320 + (index * minionSpacing);
    }

    int getNextMinionY() {
        return (mapper.isEqualTo()) ? 470 : 290;
    }

    int getWeaponY() {
        return (mapper.isEqualTo()) ? 660 : 80;
    }

    private void renderMinions(Graphics2D g) {
        mapper.renderMinions(g, card, minionSpacing);
    }

    private void renderMana(Graphics2D g) {
        mapper.renderMana(g);
    }

    private void renderWeapon(Graphics2D g) {
        mapper.renderWeapon(g);
    }

    private void renderHero(Graphics2D g) {
        mapper.renderHero(g);
    }

    private void renderActor(Graphics2D g) {
        mapper.renderActor(g);
    }

    private void exitGame() {
        mapper.exit();
        setCurrentState(handler.getGame().getMenuState());
        handler.getGame().getAudio().setFile(Asset.sound.get("back1"));
        Audio.play();
    }

    public GameMapper getMapper() {
        return mapper;
    }

    public PlayCardRenderer getRenderer() {
        return renderer;
    }
}
