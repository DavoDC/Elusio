package entity.obstacle;

import entity.base.Entity;

/**
 * Represents the zone near an obstacle, where it can be unlocked
 *
 * @author David Charkey
 */
public class ObstacleZone extends Entity
{

    // Name of item that removes obstacle
    private String keyItem;

    // Status of lock
    private boolean locked;

    public ObstacleZone(String name, String keyItem, int tlc, int tlr, int w, int h)
    {
        super(name, tlc, tlr, w, h);

        this.keyItem = keyItem;
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
