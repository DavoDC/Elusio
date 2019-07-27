package entity.item;

import components.structures.Player;

/**
 * Items that have instant effects
 *
 * @author David Charkey
 */
public abstract class InstantItem extends Item
{

    /**
     * Create an instant item with 2 description lines
     *
     * @param name
     * @param info1
     * @param info2
     * @param col
     * @param row
     */
    public InstantItem(String name, String info1, String info2, int col, int row)
    {
        super(name, info1, info2, col, row);
    }

    /**
     * Create an instant item with many description lines
     *
     * @param name
     * @param infoArr
     * @param col
     * @param row
     */
    public InstantItem(String name, String[] infoArr, int col, int row)
    {
        super(name, infoArr, col, row);
    }

    /**
     * Activate the effect of this instant item
     * @param player 
     */
    public abstract void activateEffect(Player player);

}
