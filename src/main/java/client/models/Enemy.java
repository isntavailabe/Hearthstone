package client.models;

import client.animation.AttackAnimation;
import client.animation.AttackAnimationBuilder;
import client.animation.Painter;
import client.models.cards.card;
import client.models.entities.Entity;
import client.models.entities.Minion;
import client.state.game.GameLogic;
import client.utils.Asset;

import java.util.ArrayList;
import java.util.Collections;

import static client.utils.RandomNumber.getRandom;

public class Enemy extends Player {
    transient private GameLogic logic;
    transient private RandomTimer timer;
    private boolean urTurn;

    public Enemy(GameLogic logic) {
        this.logic = logic;
    }

    void doRandomAction() {
        if (hasWinningStrategy()) {
            killHero();
            return;
        }
        if (isInDanger()) {
            attackMinionNWeapon();
            return;
        }
        attackWithWeapon();
        attackWithMinion();
        playCard();
    }

    private void attackMinionNWeapon() {
        ArrayList<Entity> targets = new ArrayList<>();
        targets.addAll(logic.getTheOtherPlayer().getMinions());
        if (logic.getTheOtherPlayer().getWeapon() != null)
            targets.add(logic.getTheOtherPlayer().getWeapon());
        ArrayList<Entity> actors = new ArrayList<>();
        actors.addAll(minions);
        if (weapon != null)
            actors.add(weapon);
        int min = Math.min(actors.size(), targets.size());
        for (int i = 0; i < min; i++) {
            logic.getActionLogic().attack(actors.get(i), targets.get(i), this, logic.getTheOtherPlayer());
        }
    }

    private boolean isInDanger() {
        int maxDamage = 0;
        for (Minion minion : logic.getTheOtherPlayer().getMinions())
            maxDamage += minion.getAttack();
        if (weapon != null)
            maxDamage += weapon.getAttack();
        return hero.getHp() <= maxDamage;
    }

    private boolean hasWinningStrategy() {
        int maxAttack = 0;
        for (Minion minion : minions)
            maxAttack += minion.getAttack();
        if (weapon != null)
            maxAttack += weapon.getAttack();
        return logic.getTheOtherPlayer().getHero().getHp() <= maxAttack;
    }

    private void killHero() {
        for (Minion minion : minions)
            logic.getActionLogic().attack(minion, logic.getTheOtherPlayer().getHero(), this, logic.getTheOtherPlayer());
        if (weapon != null)
            logic.getActionLogic().attack(weapon, logic.getTheOtherPlayer().getHero(), this, logic.getTheOtherPlayer());
    }

    private void attackWithWeapon() {
        if (!canAttackWithWeapon())
            return;
        int r = getRandom(logic.getTheOtherPlayer().getMinions().size() + 1);
        Entity target;
        if (r != logic.getTheOtherPlayer().getMinions().size())
            target = logic.getTheOtherPlayer().getMinions().get(r);
        else
            target = logic.getTheOtherPlayer().getHero();

        attack(this.weapon, target);
    }

    private void attack(Entity actor, Entity target) {
        Player player1 = logic.getPlayer();
        Player player2 = logic.getTheOtherPlayer();

        AttackAnimationBuilder builder = new AttackAnimationBuilder();
        builder.setOriginX(target.getX() + 30)
                .setOriginY(target.getY() + 30)
                .setDestinationX(target.getX() - 20)
                .setDestinationY(target.getY() - 20)
                .setPainter(new Painter(Asset.utils.get("splash"), 0, 0))
                .setSpeed(w -> Math.pow(w, 1 / 1.2))
                .setActor(this.weapon)
                .setTarget(target)
                .setActorPlayer(player1)
                .setTargetPlayer(player2)
                .setListener((actorr, targett, actorPlayer, targetPlayer) -> logic.getActionLogic().attack(actor, target, player1, player2));
        logic.getAnims().add(new AttackAnimation(builder));
    }

    private boolean canAttackWithWeapon() {
        return weapon != null;
    }

    private void attackWithMinion() {
        if (!canAttackWithMinion())
            return;
        for (Minion minion : minions) {
            int randTarget = getRandom(logic.getTheOtherPlayer().getMinions().size() + 1);
            Entity target;
            if (randTarget != logic.getTheOtherPlayer().getMinions().size())
                target = logic.getTheOtherPlayer().getMinions().get(randTarget);
            else
                target = logic.getTheOtherPlayer().getHero();

            attack(minion, target);
        }
    }

    private boolean canAttackWithMinion() {
        return minions.size() > 0;
    }

    private void playCard() {
        ArrayList<card> possibleMoves = canPlayCard();
        if (possibleMoves.size() == 0)
            return;
        int r = getRandom(possibleMoves.size());
        logic.playCard(possibleMoves.get(r));
    }

    private ArrayList<card> canPlayCard() {
        ArrayList<card> possibleMoves = new ArrayList<>();
        for (card card : hand) {
            if (card.getManaCost() <= mana)
                possibleMoves.add(card);
        }
        return possibleMoves;
    }

    public void setUrTurn(boolean urTurn) {
        this.urTurn = urTurn;
    }

    public boolean isUrTurn() {
        return urTurn;
    }

    public RandomTimer getTimer() {
        return timer;
    }

    public void resetRandomTimer() {
        timer.getTimer().resetTimer();
    }

    public void setTimer(RandomTimer timer) {
        this.timer = timer;
    }

    public void setLogic(GameLogic logic) {
        this.logic = logic;
    }


}
