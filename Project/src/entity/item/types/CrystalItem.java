package entity.item.types;

import entity.base.Entity;
import entity.obstacle.Obstacle;
import java.util.ArrayList;
import base.Globals;

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

        // If all crystals have been placed
        if (Globals.crystalsPlaced == 4) {

            // Get all obstacles
            ArrayList<Entity> entities = Globals.obStore.getEntityList();

            // For all obstacles
            for (Entity curEnt : entities) {

                // If the entity is an obstacle AND has Gate in its name
                if ((curEnt instanceof Obstacle) && (curEnt.getName().contains("Gate"))) {

                    // Unblock all magic gate parts
                    Globals.gameMap.unblockEntity(curEnt);

                    // Show magic gate popup
                    //Globals.hud.loadPopup(getGatePopup());
                }
            }
        }
    }
}
