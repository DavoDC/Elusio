package entity.item.types;

import entity.item.Item;
import main.Globals;

/**
 * Models an item that can be used
 *
 * @author David
 */
public abstract class UsableItem extends Item {

    // The name of sound made when used successfully
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
     * Play sound that represents successful use of this item
     */
    public void playSuccessSound() {
        Globals.audioServer.playSound(soundName);
    }
}
