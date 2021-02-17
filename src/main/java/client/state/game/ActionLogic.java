package client.state.game;

import client.animation.AttackAnimation;
import client.animation.AttackAnimationBuilder;
import client.animation.Painter;
import client.models.Attribute;
import client.models.Player;
import client.models.cards.MinionCard;
import client.models.cards.SpellCard;
import client.models.entities.Entity;
import client.models.entities.Minion;
import client.models.entities.Weapon;
import client.models.entities.heroes.Hero;
import client.models.entities.heroes.HeroPower;
import client.state.game.actions.*;
import client.state.game.actions.cardActions.*;
import client.utils.Asset;

import java.util.*;

public class ActionLogic {
    private Entity actor;
    private Entity target;
    private boolean isDefined;
    private GameLogic gameLogic;
    private Entity hunterTarget;
    private Entity absurd;


    private HashSet<Action> endTurnActions;
    private HashSet<Action> startTurnActions;
    private HashSet<Action> drawCardActions;
    private HashSet<Action> oneTurnActions;
    private HashSet<Action> summonActions;

    private HashSet<Action> actionToWait;
    private Entity targetSelected;

    private Entity summonedMinion;

    ActionLogic(GameLogic gameLogic) {
        this.gameLogic = gameLogic;
        endTurnActions = new HashSet<>();
        startTurnActions = new HashSet<>();
        drawCardActions = new HashSet<>();
        summonActions = new HashSet<>();
        oneTurnActions = new HashSet<>();
        actionToWait = new HashSet<>();
    }

    void defineActor(Player player, int x, int y) {
        if (player.getHero().getHeroPower().isHovered(x, y)) {
            if (gameLogic.canPlayHeroPower(player.getHero().getHeroPower(), player)) {
                if (!player.getHero().getHeroPower().isPassive())
                    if (player.getHero().getHeroPower().getNeedTarget()) {
                        actionToWait.add(player.getHero().getHeroPower().getAction());
                        isDefined = true;
                    } else {
                        player.getHero().getHeroPower().fireAction();
                    }
                player.setMana(player.getMana() - gameLogic.getHeroPowerCost(player.getHero().getHeroPower()));
            }
            return;
        }
        for (Entity entity : player.getEntities()) {
            if (entity.isHovered(x, y)) {
                if (actionToWait.size() != 0) {
                    checkForTargetSelected(entity);
                } else {
                    actor = entity;
                    isDefined = true;
                }
                break;
            }
        }
        for (Entity entity : gameLogic.getTheOtherPlayer().getEntities()) {
            if (entity.isHovered(x, y)) {
                if (actionToWait.size() != 0) {
                    checkForTargetSelected(entity);
                }
            }
        }
    }

    void defineTarget(Player player, int x, int y) {
        for (Entity entity : gameLogic.getPlayer().getEntities()) {
            if (entity.isHovered(x, y)) {
                if (actionToWait.size() != 0) {
                    checkForTargetSelected(entity);
                }
            }
        }
        for (Entity entity : player.getEntities()) {
            if (entity.isHovered(x, y)) {
                if (actionToWait.size() != 0) {
                    checkForTargetSelected(entity);
                } else {
                    target = entity;
                }
            }
        }
        if (actor != null && actor.isHovered(x, y)) {
            isDefined = false;
            actor = null;
        }
        if (isValidTarget(actor, target)) {
            Player player1 = gameLogic.getPlayer();
            Player player2 = player;

            AttackAnimationBuilder builder = new AttackAnimationBuilder();
            builder.setOriginX(target.getX() + 30)
                    .setOriginY(target.getY() + 30)
                    .setDestinationX(target.getX() - 20)
                    .setDestinationY(target.getY() - 20)
                    .setPainter(new Painter(Asset.utils.get("splash"), 0, 0))
                    .setSpeed(w -> Math.pow(w, 1 / 1.2))
                    .setActor(actor)
                    .setTarget(target)
                    .setActorPlayer(player)
                    .setTargetPlayer(gameLogic.getTheOtherPlayer())
                    .setListener((actor, targett, actorPlayer, targetPlayer) -> attack(actor, target, player1, player2));
            gameLogic.getAnims().add(new AttackAnimation(builder));
        }
    }

    private void checkForTargetSelected(Entity entity) {
        targetSelected = entity;
        for (Action action : actionToWait) {
            action.execute();
        }
        actionToWait.clear();
        isDefined = false;
    }

    void handleBattleCryActions(Weapon weapon) {
        HashMap<String, Object> actions = weapon.getWeaponCard().getBattleCry();
        if (actions == null)
            return;
        for (String string : actions.keySet()) {
            switchActions(string, (ArrayList) actions.get(string), weapon);
        }
    }

    void handleBattleCryActions(Minion minion) {
        HashMap<String, Object> actions = minion.getMinionCard().getBattleCry();
        if (actions == null)
            return;
        for (String string : actions.keySet()) {
            switchActions(string, (ArrayList) actions.get(string), minion);
        }
    }

    private void handleDeathRattleActions(Entity entity) {
        HashMap<String, Object> actions;
        if (entity instanceof Minion) {
            actions = ((Minion) entity).getMinionCard().getDeathRattle();
        } else if (entity instanceof Weapon) {
            actions = ((Weapon) entity).getWeaponCard().getDeathRattle();
        } else {
            return;
        }
        if (actions == null)
            return;
        for (String string : actions.keySet()) {
            switchActions(string, (ArrayList) actions.get(string), entity);
        }
    }

    void clearActionToWait() {
        actionToWait.clear();
    }

    public void handleSpellActions(SpellCard spellCard) {
        HashMap<String, Object> actions = spellCard.getActions();
        for (String string : actions.keySet()) {
            switchActions(string, (ArrayList) actions.get(string), absurd);
        }
    }

    void handleMinionActions(Minion minion) {
        HashMap<String, Object> actions = minion.getMinionCard().getActions();
        for (String string : actions.keySet()) {
            switchActions(string, (ArrayList) actions.get(string), minion);
        }
    }

    void handleWeaponActions(Weapon weapon) {
        HashMap<String, Object> actions = weapon.getWeaponCard().getActions();
        for (String string : actions.keySet()) {
            switchActions(string, (ArrayList) actions.get(string), weapon);
        }
    }

    void handleOneTurnAction() {
        for (Action action : oneTurnActions) {
            action.deActive();
            oneTurnActions.remove(action);
        }
    }

    public void switchActions(String string, ArrayList arrayList, Entity actor) {
        switch (string) {
            case "draw":
                new DrawCardAction(arrayList, gameLogic.getPlayer(), actor, gameLogic).execute();
                break;
            case "damage":
                new Damage(arrayList, actor, gameLogic.getPlayer(), gameLogic).execute();
                break;
            case "give_hp_attack":
                new GiveHpAttack(arrayList, gameLogic.getPlayer(), actor, gameLogic).execute();
                break;
            case "set_hp_attack":
                new SetHpAttack(arrayList, gameLogic.getPlayer(), actor, gameLogic).execute();
                break;
            case "immune_hero":
                new ImmuneHero(arrayList, gameLogic.getPlayer(), actor, gameLogic).execute();
                break;
            case "give_attribute":
                new GiveAttribute(arrayList, gameLogic.getPlayer(), actor, gameLogic).execute();
                break;
            case "add_copy":
                new AddCopy(arrayList, gameLogic.getPlayer(), actor, gameLogic).execute();
                break;
            case "summon":
                new Summon(arrayList, gameLogic.getPlayer(), actor, gameLogic).execute();
                break;
        }
    }

    private boolean checkAttributes(Entity actor, Entity target) {
        if (actor instanceof Minion) {
            if (actor.getAttributes().contains(Attribute.RUSH) || actor.getAttributes().contains(Attribute.CHARGE)) {
                actor.getAttributes().remove(Attribute.HAS_ATTACKED);
                actor.getAttributes().remove(Attribute.RUSH);
                actor.getAttributes().remove(Attribute.CHARGE);
            }
            if (actor.getAttributes().contains(Attribute.HAS_ATTACKED)) {
                return true;
            }
            if (actor.getAttributes().contains(Attribute.RUSH) && !(target instanceof Minion)) {
                return true;
            }
        }
        if (actor instanceof Weapon) {
            return actor.getAttributes().contains(Attribute.HAS_ATTACKED);
        }
        return false;
    }

    private boolean checkImmunity(Entity target) {
        return target.getAttributes().contains(Attribute.IMMUNE_WHILE_ATTACKING);
    }

    private boolean checkDivineShield(Entity actor, Entity target) {
        if (target.getAttributes().contains(Attribute.DIVINE_SHIELD)) {
            target.getAttributes().remove(Attribute.DIVINE_SHIELD);
            actor.getAttributes().add(Attribute.HAS_ATTACKED);
            actor.takeDamage(target.getAttack());
            checkIfIsDead(actor, gameLogic.getPlayer());
            if (isDead(actor))
                deActiveActions(actor);
            return true;
        }
        return false;
    }

    private boolean checkTaunt(Entity target) {
        for (Minion minion : gameLogic.getTheOtherPlayer().getMinions())
            if (minion.getAttributes().contains(Attribute.TAUNT) && minion != target)
                return true;
        return false;
    }

    public boolean canAttack(Entity actor, Entity target) {
        return checkAttributes(actor, target) || checkImmunity(target)
                || checkDivineShield(actor, target) || checkTaunt(target);
    }

    public void takeDamage(Entity entity, int damage, Player player) {
        entity.takeDamage(damage);
    }

    public void attack(Entity actor, Entity target, Player actorPlayer, Player targetPlayer) {
        isDefined = false;
        if (canAttack(actor, target))
            return;
        takeDamage(actor, target.getAttack(), actorPlayer);
        takeDamage(target, actor.getAttack(), targetPlayer);
        applyWeaponDamage(actor, target, actorPlayer, targetPlayer);
        checkIfIsDead(actor, target, actorPlayer, targetPlayer);
        if (isDead(actor))
            deActiveActions(actor);
        if (isDead(target))
            deActiveActions(target);
        actor.getAttributes().add(Attribute.HAS_ATTACKED);
        this.actor = null;
        this.target = null;
    }

    private void applyWeaponDamage(Entity actor, Entity target, Player actorPlayer, Player targetPlayer) {
        if (actor instanceof Weapon) {
            if (gameLogic.getPlayer().getEntities().contains(actor))
                takeDamage(gameLogic.getPlayer().getHero(), target.getAttack(), actorPlayer);
            else
                takeDamage(gameLogic.getTheOtherPlayer().getHero(), target.getAttack(), targetPlayer);
        }
    }

    private void deActiveActions(Entity entity) {
        for (Action action : entity.getActions())
            action.deActive();
    }

    public void checkIfIsDead(Entity entity, Player player) {
        if (entity.getHp() <= 0) {
            handleDeathRattleActions(entity);
            deActiveActions(entity);
            entity.getAttributes().add(Attribute.DEAD);
            remove(entity, player);
        }
    }

    private void checkIfIsDead(Entity actor, Entity target, Player actorPlayer, Player targetPlayer) {
        System.out.println(actorPlayer);
        System.out.println(targetPlayer);
        if (actor.getHp() <= 0) {
            handleDeathRattleActions(actor);
            actor.getAttributes().add(Attribute.DEAD);
            remove(actor, actorPlayer);
        }
        if (target.getHp() <= 0) {
            handleDeathRattleActions(target);
            target.getAttributes().add(Attribute.DEAD);
            remove(target, targetPlayer);
        }
    }

    private void addDefense(Player player) {
        player.getHero().addDefense(2);
    }

    public void remove(Entity entity, Player player) {
        if (entity instanceof Minion) {
            System.out.println(player);
            resetMinionsBounding(player, player.getMinions().indexOf(entity));
            player.getMinions().remove(entity);
            gameLogic.getMinionObjectPool().checkIn((Minion) entity);
            if (gameLogic.getPassiveLogic().isWarriors())
                addDefense(player);
        } else if (entity instanceof Weapon) {
            gameLogic.getWeaponObjectPool().checkIn((Weapon) entity);
            player.setWeapon(null);
        } else if (entity instanceof Hero) {
            gameLogic.setGameEnded(true);
            gameLogic.WinLose(theOtherPlayer(player), player);
        }
        player.getEntities().remove(entity);
    }

    private Player theOtherPlayer(Player player) {
        if (player.equals(gameLogic.getPlayer()))
            return gameLogic.getTheOtherPlayer();
        else return gameLogic.getPlayer();
    }

    private void resetMinionsBounding(Player player, int index) {
        if (index < 0)
            index = 0;
        for (int i = index; i < player.getMinions().size(); i++) {
            player.getMinions().get(i).resetBounding(-gameLogic.getMinionSpacing());
        }
    }

    private boolean isDead(Entity entity) {
        return entity.getAttributes().contains(Attribute.DEAD);
    }

    void executeEndTurnActions() {
        for (Action action : endTurnActions) {
            if (action.getActor().getAttributes().contains(Attribute.DEAD))
                endTurnActions.remove(action);
            else
                action.execute();
        }
    }

    public void executeDrawCardActions() {
        for (Action action : drawCardActions) {
            if (action.getActor().getAttributes().contains(Attribute.DEAD))
                drawCardActions.remove(action);
            else
                action.execute();
        }
    }

    public void executeStartTurnActions() {
        for (Action action : startTurnActions) {
            if (action.getActor().getAttributes().contains(Attribute.DEAD))
                startTurnActions.remove(action);
            else
                action.execute();
        }
    }

    void executeSummonAction(Entity minion) {
        summonedMinion = minion;
        for (Action action : summonActions) {
            if (!gameLogic.getPlayer().equals(action.getPlayer()))
                continue;
            action.execute();
            summonedMinion = null;
        }
    }

    private boolean isValidTarget(Entity actor, Entity target) {
        return (actor != null && target != null && !(target instanceof Weapon));
    }

    boolean isDefined() {
        return isDefined;
    }

    public HashSet<Action> getEndTurnActions() {
        return endTurnActions;
    }

    public HashSet<Action> getStartTurnActions() {
        return startTurnActions;
    }

    public HashSet<Action> getDrawCardActions() {
        return drawCardActions;
    }

    public HashSet<Action> getSummonActions() {
        return summonActions;
    }

    public HashSet<Action> getOneTurnActions() {
        return oneTurnActions;
    }

    public void addActionToWait(Action actionToWait) {
        this.actionToWait.add(actionToWait);
    }

    public Entity getTargetSelected() {
        return targetSelected;
    }

    public void setTargetSelected(Entity targetSelected) {
        this.targetSelected = targetSelected;
    }

    public Entity getSummonedMinion() {
        return summonedMinion;
    }

    public Entity getTarget() {
        return target;
    }

    public Entity getActor() {
        return actor;
    }

    public Entity getHunterTarget() {
        return hunterTarget;
    }

    void setHunterTarget(Entity hunterTarget) {
        this.hunterTarget = hunterTarget;
    }

    public void setActor(Entity actor) {
        this.actor = actor;
    }

    void setTarget(Entity target) {
        this.target = target;
    }
}
