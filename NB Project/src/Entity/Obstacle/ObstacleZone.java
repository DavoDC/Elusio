package Entity.Obstacle;

import Entity.Base.Entity;

/**
 * Represents the zone near an obstacle, where it can be unlocked
 *
 * @author David Charkey
 */
public class ObstacleZone extends Entity
{

    // Name of item that removes obstacle
    String keyItem;

    // Status of lock
    boolean locked;

    public ObstacleZone(String name, String key, int tlc, int tlr, int w, int h)
    {
        super(name, tlc, tlr, w, h);

        keyItem = key;
        locked = true;
    }

    /**
     * Get name of key item
     *
     * @return
     */
    public String getKeyItem()
    {
        return keyItem;
    }

    /**
     * Return lock status
     *
     * @return
     */
    public boolean isLocked()
    {
        return locked;
    }

    /**
     * Change lock status
     *
     * @param newStatus
     */
    public void setLocked(boolean newStatus)
    {
        locked = newStatus;
    }

}
