package entity.item.types;

import entity.obstacle.Obstacle;
import entity.obstacle.ObstacleZone;
import java.util.ArrayList;
import main.Globals;

/**
 * Models items that can be used to unlock obstacle zones, and may be protected
 * by an enemy
 *
 * @author David
 */
public class KeyItem extends UsableItem {

    // The name of the enemy protecting the item
    private final String protName;

    /**
     * Create a single tile KeyItem
     *
     * @param name Item name
     * @param infoArr Description lines and Usage lines ("Usage:")
     * @param soundName Sound name
     * @param protName Protector name or "None"
     * @param col Column on map
     * @param row Row on map
     */
    public KeyItem(String name, String[] infoArr, String soundName,
            String protName, int col, int row) {

        // Call UsableItem constructor
        super(name, infoArr, soundName, col, row);

        // Save protector name
        this.protName = protName;
    }

    /**
     * Create a multi tile KeyItem
     *
     * @param name Item name
     * @param infoArr Description lines and Usage lines ("Usage:")
     * @param soundName Sound name
     * @param protName Protector name or "None"
     * @param tlc Top left column
     * @param tlr Top left row
     * @param w Width
     * @param h Height
     */
    public KeyItem(String name, String[] infoArr, String soundName,
            String protName, int tlc, int tlr, int w, int h) {

        // Call UsableItem constructor
        super(name, infoArr, soundName, tlc, tlr, w, h);

        // Save protector name
        this.protName = protName;
    }

    /**
     * Get protector name
     *
     * @return
     */
    public String getProtName() {
        return protName;
    }

    /**
     * Set the drawing configuration for KeyItems
     *
     * @return
     */
    @Override
    public String getDrawConfig() {
        return "Front, Flash";
    }

    /**
     * Attempt to use this item to unlock a zone
     *
     * @return
     */
    @Override
    public boolean doAction() {

        // Get the obstacle zone the player is currently inside, if any
        ObstacleZone obZone = Globals.obStore.getZoneUnder(Globals.player);

        // If 
        // - No zone found
        // - Zone is not locked
        // - Wrong item used
        if (!(((obZone != null) && (obZone.isLocked()))
                && (super.getName().equals(obZone.getKeyItem())))) {

            // Zone unlock failed
            return false;
        }

        // Else if all conditions are satisified:
        // Set obstacle zone as unlocked
        obZone.setLocked(false);

        // Get the zone's matching obstacle(s)
        ArrayList<Obstacle> obstacles = Globals.obStore.getLinkedObstacles(obZone);

        // For every matching obstacle
        for (Obstacle obst : obstacles) {

            // Hide the obstacle
            Globals.obStore.addEncounter(obst);

            // Get obstacle name
            String obN = obst.getName();

            // Unblock the obstacle
            if (obst.isUnblockOn()) {
                Globals.map.unblockEntity(obst);
            }
        }

        // Do custom action
        doCustomAction();

        // Zone unlocked successfully
        return true;
    }

    /**
     * Do a custom action
     */
    public void doCustomAction() {
        // To be overridden
    }

    /**
     * Set number of usage times
     *
     * @return
     */
    @Override
    public String getUsageTimes() {
        return "Inf";
    }

}
