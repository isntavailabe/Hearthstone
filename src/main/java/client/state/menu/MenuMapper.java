package client.state.menu;

import client.state.Handler;

class MenuMapper {
    private MenuLogic logic;

    MenuMapper(Handler handler) {
        logic = new MenuLogic(handler);
    }

    void setting() {
        logic.setting();
    }

    void play() {
        logic.play();
    }

    void status() {
        logic.status();
    }

    void collection() {
        logic.collection();
    }

    void shop() {
        logic.shop();
    }


}
