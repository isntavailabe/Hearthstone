package client.state.shop;

import client.buttons.*;
import client.buttons.Button;
import client.state.Handler;
import client.state.State;
import client.utils.Asset;
import client.utils.Constants;
import client.utils.config.ShopConfig;

import java.awt.*;
import java.util.ArrayList;

import static client.input.MouseInput.getMouseX;
import static client.input.MouseInput.getMouseY;
import static client.utils.Text.drawString;
import static client.utils.Logger.logger;

public class ShopState extends State {
    private ArrayList<Button> buttons;
    private ShopConfig config = new ShopConfig("SHOPSTATE_CONFIGFILE");
    private int currentPage;
    private ShopMapper mapper;

    private int cardWidth = config.getCardWidth();
    private int cardHeight = config.getCardHeight();
    private int cardsInPage = config.getCardInPage();
    private int Spacing = config.getSpacing();
    private int lockWidth = config.getLockWidth();
    private int lockHeight = config.getLockHeight();
    private int cardX = config.getCardX();
    private int rowOneY = config.getRowOneY();
    private int rowTwoY = config.getRowTwoY();

    public ShopState(Handler handler) {
        super(handler);
        buttons = new ArrayList<>();
        createButtons();
        currentPage = 1;
        mapper = new ShopMapper(handler);
    }

    @Override
    public void update() {
        for (Button b : buttons) {
            b.update();
        }
    }

    @Override
    public void render(Graphics2D g) {
        g.drawImage(Asset.backgrounds.get("shopback"), 0, 0, Constants.GameWidth, Constants.GameHeight, null);
        renderCards(g);
        renderButtons(g);
        renderWallet(g);
    }

    private void renderCards(Graphics2D g) {
        try {
            for (int i = 1; i <= cardsInPage / 2; i++) {
                mapper.renderCard(g, (currentPage * cardsInPage) - (cardsInPage + 1) + i, i, rowOneY, 1,
                        cardX, Spacing, cardWidth, cardHeight, lockWidth, lockHeight);
            }
            for (int i = (cardsInPage / 2 + 1); i <= cardsInPage; i++) {
                mapper.renderCard(g, (currentPage * cardsInPage) - (cardsInPage + 1) + i, i, rowTwoY, (cardsInPage / 2 + 1),
                        cardX, Spacing, cardWidth, cardHeight, lockWidth, lockHeight);
            }
        } catch (Exception e) {

        }
    }

    private void renderWallet(Graphics2D g) {
        drawString(g, "Diamonds : " + handler.getGame().getEnvironment().getCurrentPlayer().getDiamonds(),
                1000, 60, false, Color.white, Asset.font20);
    }

    private void renderButtons(Graphics2D g) {
        for (Button b : buttons) {
            b.render(g);
            if (b instanceof CardButton) {
                try {
                    if (getMouseY() < rowTwoY)
                        mapper.showInfo(g, (CardButton) b, (getMouseX() - cardX) / Spacing + (currentPage - 1) * cardsInPage);
                    else
                        mapper.showInfo(g, (CardButton) b, (getMouseX() - cardX) / Spacing + (currentPage - 1) * cardsInPage + cardsInPage / 2);
                } catch (Exception e) {

                }
            }
        }
    }

    private void createButtons() {
        //MENU
        buttons.add(new CircleButton(40, 830, 60, new Action() {
            public void doAction() {
                setCurrentState(handler.getGame().getMenuState());
                logger(handler.getGame().getEnvironment().getKey(), "shop state : change state : menu state");
            }
        }, Asset.utils.get("menu_button")));
        //EXIT
        buttons.add(new CircleButton(40, 760, 60, new Action() {
            public void doAction() {
                logger(handler.getGame().getEnvironment().getKey(), "shop state : exit");
                System.exit(0);
            }
        }, Asset.utils.get("exit_button")));
        ////CARD BUTTONS
        for (int i = 0; i < cardsInPage / 2; i++) {
            buttons.add(new CardButton(cardX + Spacing * i, rowOneY, cardWidth, cardHeight, new Action() {
                public void doAction() {
                    mapper.buySellCard(((getMouseX() - cardX) / Spacing) + (currentPage - 1) * cardsInPage,
                            ((getMouseX() - cardX) / Spacing) + (currentPage - 1) * cardsInPage);
                }
            }));
        }
        for (int i = cardsInPage / 2; i < cardsInPage; i++) {
            buttons.add(new CardButton(cardX + Spacing * (i - cardsInPage / 2), rowTwoY, cardWidth, cardHeight, new Action() {
                public void doAction() {
                    mapper.buySellCard(((getMouseX() - cardX) / Spacing) + (currentPage - 1) * cardsInPage + cardsInPage / 2,
                            ((getMouseX() - cardX) / Spacing) + (currentPage - 1) * cardsInPage + cardsInPage / 2);
                }
            }));
        }
        //NEXT PAGE
        buttons.add(new RectButton(1150, 400, 20, 60, new Action() {
            public void doAction() {
                nextPage();
                logger(handler.getGame().getEnvironment().getKey(), "shop state : next page");
            }
        }, Asset.utils.get("right_arrow_in")));
        //PREVIOUS PAGE
        buttons.add(new RectButton(350, 400, 20, 60, new Action() {
            public void doAction() {
                previousPage();
                logger(handler.getGame().getEnvironment().getKey(), "shop state : previous page");
            }
        }, Asset.utils.get("left_arrow_in")));
        //SET CARDS : NEUTRAL
        buttons.add(new RectButton(400, 60, 40, 40, new Action() {
            public void doAction() {
                mapper.setNeutral();
                logger(handler.getGame().getEnvironment().getKey(), "shop state : select neutral cards");
            }
        }, Asset.heroclasses.get("neutralclass_in")));
    }

    private void nextPage() {
        currentPage++;
        if (mapper.hasReachedLimit((currentPage - 1) * 6))
            currentPage--;
    }

    private void previousPage() {
        currentPage--;
        if (currentPage < 1)
            currentPage = 1;
    }

    public ShopMapper getMapper() {
        return mapper;
    }
}
