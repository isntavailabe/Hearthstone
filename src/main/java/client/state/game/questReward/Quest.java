package client.state.game.questReward;

import client.models.Player;
import client.models.cards.SpellCard;
import client.models.cards.card;
import client.state.game.ActionLogic;
import client.utils.Asset;

import java.awt.*;

import static client.utils.Text.drawString;

public class Quest {
    private SpellCard spellCard;
    private Player player;
    private String condition;
    private double total;
    private int done;
    private double frac;


    public Quest(SpellCard spellCard, Player player) {
        this.spellCard = spellCard;
        this.player = player;
        total = (double) spellCard.getQuest().get(0);
        condition = (String) spellCard.getQuest().get(1);
    }

    public void render(Graphics2D g, int y) {
        drawString(g, spellCard.getName(), 390, y, true, Color.white, Asset.font20);
        drawString(g, String.valueOf(frac), 500, y, true, Color.white, Asset.font20);
    }

    public void update(int mana, ActionLogic logic, card c, Player player) {
        if (!condition.toLowerCase().equals(c.getType()) || !this.player.equals(player))
            return;

        done += mana;
        frac = done / total;

        if (isCompleted()) {
            logic.handleSpellActions(spellCard);
            player.getQuests().remove(this);
        }
    }

    private boolean isCompleted() {
        return frac >= 1;
    }


}
