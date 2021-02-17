package client.state.infopassive;

import java.util.ArrayList;
import java.util.Collections;

import static client.state.infopassive.InfoPassiveState.*;

public class infoPassiveLogic {
    private static infoPassiveLogic instance = new infoPassiveLogic();
    private int[] chooseOne;

    private boolean TwiceDraw;
    private boolean OffCards;
    private boolean Warriors;
    private boolean FreePower;
    private boolean ManaJump;

    private int twicedraw = getConfig().getTwicedrawDefault();
    private int offcards = getConfig().getOffcardsDefault();
    private int warriors = getConfig().getWarriorsDefault();
    private int freepower = getConfig().getFreepowerDefault();
    private int heropowerCounter = getConfig().getHeropowerCounterDefault();
    private int manajump = getConfig().getManajumpDefault();
    private int toChooseP = config.getNumPassivesToShow();
    private int allP = config.getNumAllPassives();

    private infoPassiveLogic() {
        init();
    }

    private void init(){
        chooseOne = new int[toChooseP];
        createRand();
    }

    public static infoPassiveLogic getInstance() {
        return instance;
    }

    public void reset(){
        TwiceDraw = false;
        OffCards = false;
        Warriors = false;
        FreePower = false;
        ManaJump = false;

        twicedraw = getConfig().getTwicedrawDefault();
        offcards = getConfig().getOffcardsDefault();
        warriors = getConfig().getWarriorsDefault();
        freepower = getConfig().getFreepowerDefault();
        heropowerCounter = getConfig().getHeropowerCounterDefault();
        manajump = getConfig().getManajumpDefault();
    }

    String passiveName(int p) {
        switch (chooseOne[p]) {
            case 0:
                return "twice_draw";
            case 1:
                return "off_cards";
            case 2:
                return "warriors";
            case 3:
                return "free_power";
            case 4:
                return "mana_jump";
        }
        return null;
    }

    void setPassive(int p) {
        switch (chooseOne[p]) {
            case 0:
                setTwiceDraw(true);
                break;
            case 1:
                setOffCards(true);
                break;
            case 2:
                setWarriors(true);
                break;
            case 3:
                setFreePower(true);
                break;
            case 4:
                setManaJump(true);
                break;
        }
    }

    private void createRand() {
        ArrayList<Integer> tmp = new ArrayList<>();
        for (int i = 0; i < allP; i++) {
            tmp.add(i);
        }
        Collections.shuffle(tmp);
        for (int i = 0; i < toChooseP; i++) {
            chooseOne[i] = tmp.get(i);
        }
    }

    private void setTwiceDraw(boolean twiceDraw) {
        twicedraw = getConfig().getTwicedraw();
        TwiceDraw = twiceDraw;
    }

    private void setOffCards(boolean offCards) {
        offcards = getConfig().getOffcards();
        OffCards = offCards;
    }

    private void setWarriors(boolean warriors) {
        Warriors = warriors;
    }

    private void setFreePower(boolean freePower) {
        freepower = getConfig().getFreepower();
        heropowerCounter = getConfig().getHeropowerCounter();
        FreePower = freePower;
    }

    private void setManaJump(boolean manaJump) {
        manajump = getConfig().getManajump();
        ManaJump = manaJump;
    }

    int getToChooseP() {
        return toChooseP;
    }

    public int getHeropowerCounter() {
        return heropowerCounter;
    }

    public boolean isWarriors() {
        return Warriors;
    }

    public int getTwicedraw() {
        return twicedraw;
    }

    public int getOffcards() {
        return offcards;
    }

    public int getManajump() {
        return manajump;
    }

    public boolean isFreePower() {
        return FreePower;
    }
}
