package client.animation;


import client.models.Player;
import client.models.entities.Entity;

public interface AttackListener {
    void eventOccurred(Entity actor, Entity target, Player actorPlayer, Player targetPlayer);
}
