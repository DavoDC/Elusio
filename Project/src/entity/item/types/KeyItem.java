package entity.item.types;

import entity.obstacle.Obstacle;
import entity.obstacle.ObstacleZone;
import java.util.ArrayList;
import base.Globals;
import entity.enemy.Enemy;

/**
 * Models items that can be used to unlock obstacle zones, and may be protected
 * by an enemy
 *
 * @author David
 */
public class KeyItem extends UsableItem {

    // The name of the enemy protecting the item
    private String protName;

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
        saveProtName(protName);

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
        saveProtName(protName);
    }

    /**
     * Save and check protector name
     *
     * @param protName
     */
    private void saveProtName(String protName) {

        // Save protector name
        this.protName = protName;

        // Check protector
        if (protName != null) {

            // Try to retrieve enemy
            Enemy enemy = Globals.enemyStore.getEnemy(protName);

            // If enemy is null
            if (enemy == null) {
                System.out.println("Error!");
                System.out.println("'" + protName + "' is not a valid enemy.");
                System.out.println("Change protector of item or add/change enemy");
            }
        }
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

        // If valid zone was found
        if (obZone != null) {

            // If zone has been already unlocked
            // OR incorrect item has been used
            if (!obZone.isLocked()
                    || !super.getName().contains(obZone.getKeyItem())) {

                // TEST
//                System.out.println("########### Unlock failed! "
//                        + "= already unlocked OR bad item"
//                        + "\nused item name:" + super.getName()
//                        + " , key item:" + obZone.getKeyItem());
                // Finish unsuccessfully
                return false;
            }

        } else {

            // TEST
            // System.out.println("########### Unlock failed! = no zone");
            // Else if no zone was found, 
            // finish unsuccessfully
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
                Globals.gameMap.unblockEntity(obst);
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
