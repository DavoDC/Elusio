package entity.item;

/**
 * Models items that can be used for some special action
 *
 * @author David
 */
public class KeyItem extends Item {

    // Text shown when item is used
    private final String useLine;

    /**
     * Create usable item
     *
     * @param name
     * @param info1
     * @param info2
     * @param useLine
     * @param c
     * @param r
     */
    public KeyItem(String name, String info1, String info2, String useLine, int c, int r) {

        // Call entity constructor
        super(name, info1, info2, c, r);

        // Save usage line
        this.useLine = useLine;
    }

    /**
     * Get description of action when item is used
     *
     * @return
     */
    public String getUseLine() {
        return useLine;
    }
}
