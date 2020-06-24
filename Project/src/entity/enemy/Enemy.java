package entity.enemy;

import entity.base.Entity;
import entity.base.Entity;

/**
 * Models an enemy
 *
 * @author David
 */
public abstract class Enemy extends Entity {

    /**
     * Construct an enemy that is a 4 tile square
     *
     * @param name
     * @param tlc
     * @param tlr
     */
    public Enemy(String name, int tlc, int tlr) {
        super(name, tlc, tlr, 2, 2);
    }

    /**
     * Do enemy action
     */
    public abstract void doAction();

}
