package entity.obstacle;

import entity.base.Entity;

/**
 * Represents the zone near an obstacle, in which an item can be used to remove
 * the obstacle
 *
 * @author David
 */
public class ObstacleZone extends Entity {

    // The name of item that removes the obstacle
    private final String keyItem;

    // The status of the lockable obstacle zone
    private boolean locked;

    /**
     * Create an ObstacleZone
     *
     * @param name The zone the name
     * @param keyItem The name of the item that unlocks the zone
     * @param tlc The map column
     * @param tlr The map row
     * @param w The width (in tiles)
     * @param h The height (in tiles)
     */
    public ObstacleZone(String name, String keyItem, int tlc, int tlr, int w, int h) {

        // Call entity constructor
        super(name, tlc, tlr, w, h);

        // Save keyItem
        this.keyItem = keyItem;

        // Initialize as locked
        locked = true;
    }

    /**
     * Get name of key item
     *
     * @return keyItem name string
     */
    public String getKeyItem() {
        return keyItem;
    }

    /**
     * Return lock status
     *
     * @return lock status
     */
    public boolean isLocked() {
        return locked;
    }

    /**
     * Change lock status
     *
     * @param newStatus The new lock status
     */
    public void setLocked(boolean newStatus) {
        locked = newStatus;
    }

}
