package client.state.game.objectPool;

import client.models.cards.WeaponCard;
import client.models.entities.Weapon;

import java.awt.geom.Ellipse2D;

public class WeaponObjectPool extends ObjectPool<Weapon> {

    @Override
    protected Weapon create() {
        return new Weapon();
    }

    public Weapon getWeapon(WeaponCard weaponCard, int x, int y) {
        Weapon w = checkOut();
        w.setWeaponCard(weaponCard);
        w.setHp(weaponCard.getDurability());
        w.setAttack(weaponCard.getAttack());
        w.setBounding(new Ellipse2D.Double(x, y, 120, 120));
        w.setX(x);
        w.setY(y);
        return w;
    }
}
