package entity.item;

/**
 * items activated by pressing E
 *
 * @author David
 */
public class UsableItem extends Item
{

    // usage text
    private String useLine;

    public UsableItem(String name, String info1, String info2, String useLine, int c, int r)
    {
        super(name, info1, info2, c, r);

        this.useLine = useLine;
    }

    public String getUseLine()
    {
        return useLine;
    }
}
