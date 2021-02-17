package client.models.entities.heroes;

import client.state.game.actions.Action;

import java.awt.*;

public class HeroPower implements Cloneable{
    private String name;
    private int count;
    private int manaCost;
    private boolean isPassive;
    private boolean needTarget;
    transient private Action action;
    transient private Shape bounding;

    private int spellManaCost;
    private int restoreFactor;
    private int rogueOffCard;

    private HeroPower(int mana, int x, int y) {
        manaCost = mana;
    }

    @Override
    public HeroPower clone() {
        HeroPower clone = null;
        try {
            clone = (HeroPower) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        clone = new HeroPower(0, 0, 0);
        clone.setManaCost(this.getManaCost());
        clone.setName(this.getName());
        clone.setNeedTarget(this.getNeedTarget());
        clone.setPassive(this.isPassive);
        clone.setRestoreFactor(this.getRestoreFactor());
        clone.setSpellManaCost(this.getSpellManaCost());
        clone.setRogueOffCard(this.getRogueOffCard());
        return clone;
    }

    public void fireAction() {
        action.execute();
    }

    public void addCount() {
        count++;
    }

    public boolean isHovered(int x, int y) {
        return bounding.contains(x, y);
    }

    public String getName() {
        return name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getManaCost() {
        return manaCost;
    }

    public boolean isPassive() {
        return isPassive;
    }

    public Boolean getNeedTarget() {
        return needTarget;
    }

    public int getSpellManaCost() {
        return spellManaCost;
    }

    public int getRestoreFactor() {
        return restoreFactor;
    }

    public Shape getBounding() {
        return bounding;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRogueOffCard() {
        return rogueOffCard;
    }

    private void setManaCost(int manaCost) {
        this.manaCost = manaCost;
    }

    private void setPassive(boolean passive) {
        isPassive = passive;
    }

    private void setNeedTarget(boolean needTarget) {
        this.needTarget = needTarget;
    }

    public void setBounding(Shape bounding) {
        this.bounding = bounding;
    }

    private void setSpellManaCost(int spellManaCost) {
        this.spellManaCost = spellManaCost;
    }

    private void setRestoreFactor(int restoreFactor) {
        this.restoreFactor = restoreFactor;
    }

    private void setRogueOffCard(int rogueOffCard) {
        this.rogueOffCard = rogueOffCard;
    }
}
