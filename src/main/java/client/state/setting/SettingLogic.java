package client.state.setting;

import client.Login;
import client.state.Handler;
import client.state.ShowErrors;
import client.state.menu.MenuState;

import java.io.File;

import static client.models.Environment.getInstance;

class SettingLogic {
    private Handler handler;

    SettingLogic(Handler handler) {
        this.handler = handler;
    }

    private void deletePlayer(String pass) {
        if (handler.getGame().getEnvironment().getCurrentPlayer().getPass().equals(pass)) {
            deleteFile(handler.getGame().getEnvironment().getCurrentPlayer().getName() + ".json");
            handler.getGame().getEnvironment().setCurrentPlayer(null);
            System.exit(0);
            Login login = new Login(getInstance());
        } else {
            showMessage("Wrong password!", 2000);
        }
    }

    public static void deleteFile(String filename) {
        String str = "json/player/" + filename;
        File file = new File(str);
        file.delete();
    }

    private void showMessage(String message, int time) {
        ((ShowErrors) handler.getGame().getShowErrors()).setActive(true);
        ((ShowErrors) handler.getGame().getShowErrors()).setMessage(message, time);
    }

    void deletePlayerButton() {
        if (!handler.getGame().getDeletePlayer().isVisible())
            handler.getGame().getDeletePlayer().setVisible(true);
        else {
            if (handler.getGame().getDeletePlayer().getText().isEmpty())
                return;
            deletePlayer(handler.getGame().getDeletePlayer().getText());
        }
    }

    void backToMenu() {
        ((MenuState) handler.getGame().getMenuState()).setActive(true);
        ((MenuState)handler.getGame().getMenuState()).setActive(false);
        handler.getGame().getDeletePlayer().setVisible(false);
    }

}
