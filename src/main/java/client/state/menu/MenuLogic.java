package client.state.menu;

import client.state.Handler;
import client.state.setting.Setting;
import client.state.StatusState;

import static client.utils.Logger.logger;
import static client.state.State.setCurrentState;

class MenuLogic {
    private Handler handler;

    MenuLogic(Handler handler) {
        this.handler = handler;
    }

    void play() {
        if (handler.getGame().getEnvironment().getCurrentPlayer().getCurrentDeck().getDeck().size() < 15) {
            setCurrentState(handler.getGame().getCollectionState());
            handler.getGame().getSearch().setVisible(true);
            logger(handler.getGame().getEnvironment().getKey(), "menu state : change state : collection state");
        } else {
            setCurrentState(handler.getGame().getInfoPassiveState());
            logger(handler.getGame().getEnvironment().getKey(), "menu state : change state : infopassive state");
        }
    }

    void shop() {
        setCurrentState(handler.getGame().getShopState());
        logger(handler.getGame().getEnvironment().getKey(), "menu state : change state : shop state");
    }

    void collection() {
        setCurrentState(handler.getGame().getCollectionState());
        handler.getGame().getSearch().setVisible(true);
        logger(handler.getGame().getEnvironment().getKey(), "menu state : change state : collection state");
    }

    void status() {
        setCurrentState(handler.getGame().getStatusState());
        if (handler.getGame().getStatusState() instanceof StatusState) {
            ((StatusState) handler.getGame().getStatusState()).updateDecks();
            ((StatusState) handler.getGame().getStatusState()).reset();
        }
        logger(handler.getGame().getEnvironment().getKey(), "menu state : change state : status state");
    }

    void setting() {
        ((Setting) handler.getGame().getSetting()).setActive(true);
        ((MenuState)handler.getGame().getMenuState()).setActive(false);
    }
}
