package entity.item;

import java.util.ArrayList;
import java.util.Arrays;

import entity.base.Entity;
import org.newdawn.slick.Image;

/**
 * Models in-game items
 *
 * @author David
 */
public class Item extends Entity {

    // Description lines
    private final ArrayList<String> info;

    /**
     * Create a single tile item
     *
     * @param name Item name
     * @param infoArr Description lines
     * @param col Column on map
     * @param row Row on map
     */
    public Item(String name, String[] infoArr, int col, int row) {

        // Call entity constructor
        super(name, col, row, 1, 1);

        // Initialize list
        info = new ArrayList<>();
        info.addAll(Arrays.asList(infoArr));
    }

    /**
     * Create a multi tile item
     *
     * @param name Item name
     * @param infoArr Description lines
     * @param tlc Top left column
     * @param tlr Top left row
     * @param w Width
     * @param h Height
     */
    public Item(String name, String[] infoArr,
            int tlc, int tlr, int w, int h) {

        // Call entity constructor
        super(name, tlc, tlr, w, h);

        // Initialize list
        info = new ArrayList<>();
        info.addAll(Arrays.asList(infoArr));
    }

    /**
     * Get info lines
     *
     * @return
     */
    public ArrayList<String> getInfoLines() {
        return info;
    }

    /**
     * Get image of item
     *
     * @return Image
     */
    public Image getImage() {

        // Array location
        int row = 0;
        int col = 0;

        // Adjust for ShipGold case
        if (getName().contains("Treasure")) {
            col++;
        }

        // Return image
        return super.getImage(row, col);
    }

}
