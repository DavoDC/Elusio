package entity.item.types;

import entity.item.Item;
import base.Globals;

/**
 * Models an item that can be used
 *
 * @author David
 */
public abstract class UsableItem extends Item {

    // The name of sound that characterizes the item
    private final String soundName;

    /**
     * Create a single tile item
     *
     * @param name
     * @param infoArr
     * @param soundName
     * @param col
     * @param row
     */
    public UsableItem(String name, String[] infoArr, String soundName,
            int col, int row) {

        // Call Item constructor
        super(name, infoArr, col, row);

        // Get and save sound
        this.soundName = soundName;
    }

    /**
     * Create a multi tile item
     *
     * @param name
     * @param infoArr
     * @param soundName
     * @param tlc
     * @param tlr
     * @param w
     * @param h
     */
    public UsableItem(String name, String[] infoArr, String soundName,
            int tlc, int tlr, int w, int h) {

        // Call Item constructor
        super(name, infoArr, tlc, tlr, w, h);

        // Get and save sound
        this.soundName = soundName;
    }

    /**
     * Return the drawing configuration for this item
     *
     * @return
     */
    public abstract String getDrawConfig();

    /**
     * Do the action that occurs when the item is used
     *
     * @return Success status
     */
    public abstract boolean doAction();

    /**
     * Play sound that represents this item being used
     */
    public void playUseSound() {
        Globals.audioServer.playSound(soundName);
    }

    /**
     * Get number of times the item can be used
     *
     * @return
     */
    public String getUsageTimes() {
        return "1";
    }
}
