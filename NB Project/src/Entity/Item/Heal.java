package Entity.Item;

/**
 *
 * @author David Charkey
 */
public class Heal extends Item
{

    //Healing value
    private int healAmount;

    public Heal(String name, String desc, int xPos, int yPos)
    {
        super(name, desc, xPos, yPos);
    }

}
