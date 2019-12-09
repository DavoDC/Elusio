package entity.item.types;

import components.structures.Player;
import main.Globals;

/**
 * Models items that, when used, have instant effects, often on the player
 *
 * @author David
 */
public abstract class InstantItem extends UsableItem {

    // Whether the effect has been applied yet
    private final boolean effectApplied;

    /**
     * Create an instant item
     *
     * @param name Item name
     * @param infoArr Description lines
     * @param soundName Name of sound
     * @param col Column on map
     * @param row Row on map
     */
    public InstantItem(String name, String[] infoArr, String soundName,
            int col, int row) {

        // Call UsableItem constructor
        super(name, infoArr, soundName, col, row);

        // Initialize as 'effect not applied;
        effectApplied = false;
    }

    /**
     * Set the drawing configuration for InstantItems
     *
     * @return
     */
    @Override
    public String getDrawConfig() {
        return "Front, Intake";
    }

    /**
     * Apply the effect of this instant item (Do not call directly)
     *
     * @param player
     */
    public abstract void applyEffect(Player player);

    /**
     * Override usual item action to provide wrapper for item effect
     */
    @Override
    public final boolean doAction() {

        // Apply effect
        applyEffect(Globals.player);

        // Remove item
        Globals.player.removeItemByName(super.getName());

        // Finish successfully
        return true;
    }

}
