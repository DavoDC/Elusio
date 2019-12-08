package entity.item;

import components.structures.Player;
import main.Globals;

/**
 * Items that have instant effects
 *
 * @author David
 */
public abstract class InstantItem extends Item {

    // Whether the effect has been applied yet
    private boolean effectApplied;

    /**
     * Create an instant item with 2 description lines
     *
     * @param name
     * @param info1
     * @param info2
     * @param col
     * @param row
     */
    public InstantItem(String name, String info1, String info2, int col, int row) {
        super(name, info1, info2, col, row);

        // Initialize as 'effect not applied;
        effectApplied = false;
    }

    /**
     * Create an instant item with many description lines
     *
     * @param name
     * @param infoArr
     * @param col
     * @param row
     */
    public InstantItem(String name, String[] infoArr, int col, int row) {
        super(name, infoArr, col, row);

        // Initialize as 'effect not applied;
        effectApplied = false;
    }

    /**
     * Override usual item action
     */
    @Override
    public final boolean doAction() {

        // If item effect has not been applied
        if (!effectApplied) {

            // Apply effect
            applyEffect(Globals.player);

            // Set effect as applied
            effectApplied = true;

            // Applied succesfully
            return true;
        }

        // Application did not occur
        return false;
    }

    /**
     * Apply the effect of this instant item (Do not call directly)
     *
     * @param player
     */
    public abstract void applyEffect(Player player);

}
