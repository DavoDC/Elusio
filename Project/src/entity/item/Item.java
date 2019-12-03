package entity.item;

import java.util.ArrayList;
import java.util.Arrays;

import entity.base.Entity;

/**
 * Models in-game items
 *
 * @author David
 */
public class Item extends Entity {

    // Description lines
    private final ArrayList<String> info;

    // Discovery status
    private boolean isFound;

    /**
     * Create a single tile, single info-line item
     *
     * @param name Item name
     * @param infoLine
     * @param col Column on tile grid
     * @param row Row on tile grid
     */
    public Item(String name, String infoLine, int col, int row) {

        // Call entity constructor
        super(name, col, row, 1, 1);

        // Initialize list
        info = new ArrayList<>();
        info.add(infoLine);

        // Initialize as 'not found'
        isFound = false;
    }

    /**
     * Create a single tile, double info-line item
     *
     * @param name Item name
     * @param info1 First info line
     * @param info2 Second info line
     * @param col Column on tile grid
     * @param row Row on tile grid
     */
    public Item(String name, String info1, String info2, int col, int row) {

        // Call entity constructor
        super(name, col, row, 1, 1);

        // Initialize list
        info = new ArrayList<>();
        info.add(info1);
        info.add(info2);

        // Initialize as 'not found'
        isFound = false;
    }

    /**
     * Create a single-tile item with many description lines
     *
     * @param name Item name
     * @param infoArr Item information
     * @param col Column on tile grid
     * @param row Row on tile grid
     */
    public Item(String name, String[] infoArr, int col, int row) {

        // Call entity constructor
        super(name, col, row, 1, 1);

        // Initialize list
        info = new ArrayList<>();
        info.addAll(Arrays.asList(infoArr));

        // Initialize as 'not found'
        isFound = false;
    }

    /**
     * Constructor for multi tile, double info-line items
     *
     * @param name
     * @param info1
     * @param info2
     * @param tlc
     * @param tlr
     * @param w
     * @param h
     */
    public Item(String name, String info1, String info2,
            int tlc, int tlr, int w, int h) {

        // Call entity constructor
        super(name, tlc, tlr, w, h);

        // Initialize list
        info = new ArrayList<>();
        info.add(info1);
        info.add(info2);

        // Initialize as 'not found'
        isFound = false;
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
     * Get discovery status
     *
     * @return
     */
    public boolean isFound() {
        return isFound;
    }

    /**
     * Set discovery status
     *
     * @param newStatus
     */
    public void setFound(boolean newStatus) {
        isFound = newStatus;
    }

}
