package Entity.Item;

/**
 * Models a weapon
 *
 * @author David Charkey
 */
public class Weapon extends Item
{

    //ranking
    /// gun > spear > crystal > metal > wood
    private int rank;

    public Weapon(String name, String desc, int xPos, int yPos)
    {
        super(name, desc, xPos, yPos);
    }
}
