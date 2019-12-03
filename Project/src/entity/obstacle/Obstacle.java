package entity.obstacle;

import entity.base.Entity;

/**
 * Represents an obstructed area
 *
 * @author David
 */
public class Obstacle extends Entity {

    // Matching zone name
    private final String obZoneName;

    // Records whether to unblock when zone unlocked
    private final boolean unblockOn;

    /**
     * Create obstacle
     *
     * @param name
     * @param obZoneName
     * @param tlc
     * @param tlr
     * @param w
     * @param h
     * @param unblockOn
     */
    public Obstacle(String name, String obZoneName, int tlc, int tlr,
            int w, int h, boolean unblockOn) {

        // Call entity constructor
        super(name, tlc, tlr, w, h);

        // Save obstacle zone name
        this.obZoneName = obZoneName;

        // Save unblocking status
        this.unblockOn = unblockOn;
    }

    /**
     * Get obstacle zone name
     *
     * @return
     */
    public String getOZName() {
        return obZoneName;
    }

    /**
     * Return whether obstacle is unblocked after unlocking
     *
     * @return
     */
    public boolean isUnblockOn() {
        return unblockOn;
    }

}
