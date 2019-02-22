package Entity.Item;

/**
 * Models in-game items protected by enemies
 *
 * @author David Charkey
 */
public class ProtectedItem extends Item
{

    // Info
    private String protector;

    public ProtectedItem(String name, String[] infoArr, String protector, int col, int row)
    {
        super(name, infoArr, col, row);

        this.protector = protector;
    }

    public ProtectedItem(String name, String info1, String info2, String protector, int col, int row)
    {
        super(name, info1, info2, col, row);

        this.protector = protector;
    }

    /**
     * Get protector name
     *
     * @return
     */
    public String getProtector()
    {
        return protector;
    }
}
