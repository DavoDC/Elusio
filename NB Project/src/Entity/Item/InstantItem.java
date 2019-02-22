package Entity.Item;

import Components.Structures.Player;

/**
 * Items that have instant effects
 *
 * @author David Charkey
 */
public abstract class InstantItem extends Item
{

    public InstantItem(String name, String[] infoArr, int col, int row)
    {
        super(name, infoArr, col, row);
    }

    public InstantItem(String name, String info1, String info2, int col, int row)
    {
        super(name, info1, info2, col, row);
    }

    public abstract void activateEffect(Player player);

}
