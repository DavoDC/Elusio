package entity.item;

/**
 * Models items protected by enemies
 *
 * @author David
 */
public class ProtectedItem extends Item {

    // The name of the enemy protecting the item
    private final String protectorName;

    /**
     * Create a single-tile item with many description lines
     *
     * @param name
     * @param infoArr
     * @param protectorName
     * @param col
     * @param row
     */
    public ProtectedItem(String name, String[] infoArr,
            String protectorName, int col, int row) {

        // Call entity constructor
        super(name, infoArr, col, row);

        // Save protector name
        this.protectorName = protectorName;
    }

    /**
     * Create a single tile, double info-line item
     *
     * @param name
     * @param info1
     * @param info2
     * @param protector
     * @param col
     * @param row
     */
    public ProtectedItem(String name, String info1, String info2,
            String protector, int col, int row) {
        super(name, info1, info2, col, row);

        this.protectorName = protector;
    }

    /**
     * Constructor for multi tile, double info-line items
     *
     * @param name
     * @param info1
     * @param info2
     * @param prot
     * @param c
     * @param r
     * @param w
     * @param h
     */
    public ProtectedItem(String name, String info1,
            String info2, String prot, int c, int r, int w, int h) {
        super(name, info1, info2, c, r, w, h);

        this.protectorName = prot;
    }

    /**
     * Get protector name
     *
     * @return
     */
    public String getProtector() {
        return protectorName;
    }
}
