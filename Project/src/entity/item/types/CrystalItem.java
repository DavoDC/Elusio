package entity.item.types;

import entity.base.Entity;
import entity.obstacle.Obstacle;
import java.util.ArrayList;
import base.Globals;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Models a crystal KeyItem
 *
 * @author David
 */
public class CrystalItem extends KeyItem {

    /**
     * Create a CrystalItem
     *
     * @param quality
     * @param freq
     * @param msg
     * @param protName
     * @param col
     * @param row
     */
    public CrystalItem(String quality, int freq, String msg,
            String protName, int col, int row) {

        // Call KeyItem constructor
        super("Crystal of " + quality,
                new String[]{
                    "Emits EMR with a frequency of " + freq + "Hz.",
                    "Decoded: " + msg,
                    "Usage: The area's vibrational frequency has increased!"},
                "crystalHum",
                protName,
                col, row);
    }

    /**
     * Override the usual action
     */
    @Override
    public void doCustomAction() {

        // Increase crystal count
        Globals.crystalsPlaced++;

        // Remove item
        Globals.player.removeItemByName(super.getName());

        // If all crystals have been placed
        if (Globals.crystalsPlaced == 4) {

            // Get all obstacles
            ArrayList<Entity> entities = Globals.obStore.getEntityList();

            // For all obstacles
            for (Entity curEnt : entities) {

                // If the entity is an obstacle AND has Gate in its name
                if ((curEnt instanceof Obstacle)
                        && (curEnt.getName().contains("Gate"))) {

                    // Unblock all magic gate parts
                    Globals.gameMap.unblockEntity(curEnt);
                }
            }

            // Load popup
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {

                    // Show magic gate popup
                    Globals.popStore.loadPopup("MagicGate");
                }
            }, 50);
        }
    }

    /**
     * Get number of times the item can be used
     *
     * @return
     */
    @Override
    public String getUsageTimes() {
        return "1";
    }
}
