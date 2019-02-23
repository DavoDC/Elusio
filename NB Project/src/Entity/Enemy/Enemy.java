package Entity.Enemy;

import Entity.Base.Entity;

/**
 * Models an enemy
 *
 * @author David Charkey
 */
public abstract class Enemy extends Entity
{

    /**
     * Default constructor = Don't use
     *
     * @param name
     * @param tlc
     * @param tlr
     * @param w
     * @param h
     */
    private Enemy(String name, int tlc, int tlr, int w, int h)
    {
        super(name, tlc, tlr, w, h);
    }

    /**
     * Quick constructor for 2x2 enemies
     *
     * @param name
     * @param tlc
     * @param tlr
     */
    public Enemy(String name, int tlc, int tlr)
    {
        super(name, tlc, tlr, 2, 2);
    }

    public abstract void doAction();

}
