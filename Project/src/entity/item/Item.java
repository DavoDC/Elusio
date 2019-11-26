package entity.item;

import java.util.ArrayList;
import java.util.Arrays;

import entity.base.Entity;


/**
 * Models in-game items
 *
 * @author David 
 */
public class Item extends Entity
{

    // Description lines
    private ArrayList<String> info;

    // Discovery Status
    private boolean isFound;

    /**
     * Create an oversimplified item (do not use)
     *
     * @param name
     * @param tlc
     * @param tlr
     * @param w
     * @param h
     */
    private Item(String name, int tlc, int tlr, int w, int h)
    {
        super(name, tlc, tlr, w, h);
    }

    /**
     * Create a item with many description lines
     *
     * @param name Item name
     * @param infoArr Item information
     * @param col Column on tile grid
     * @param row Row on tile grid
     */
    public Item(String name, String[] infoArr, int col, int row)
    {
        super(name, col, row, 1, 1);

        info = new ArrayList<>();
        info.addAll(Arrays.asList(infoArr));

        isFound = false;
    }

    /**
     * Quick constructor for single info-line items
     *
     * @param name Item name
     * @param infoLine
     * @param col Column on tile grid
     * @param row Row on tile grid
     */
    public Item(String name, String infoLine, int col, int row)
    {
        super(name, col, row, 1, 1);

        info = new ArrayList<>();
        info.add(infoLine);

        isFound = false;
    }

    /**
     * Quick constructor for double info-line items
     *
     * @param name Item name
     * @param infoLine1
     * @param infoLine2
     * @param col Column on tile grid
     * @param row Row on tile grid
     */
    public Item(String name, String infoLine1, String infoLine2, int col, int row)
    {
        super(name, col, row, 1, 1);

        info = new ArrayList<>();
        info.add(infoLine1);
        info.add(infoLine2);

        isFound = false;
    }

    public Item(String name, String info1, String info2, int tlc, int tlr, int w, int h)
    {
        super(name, tlc, tlr, w, h);

        info = new ArrayList<>();
        info.add(info1);
        info.add(info2);

        isFound = false;
    }

    /**
     * Get info lines
     *
     * @return
     */
    public ArrayList<String> getInfoLines()
    {
        return info;
    }

    /**
     * Get discovery status
     *
     * @return
     */
    public boolean isFound()
    {
        return isFound;
    }

    /**
     * Set discovery status
     *
     * @param newStatus
     */
    public void setFound(boolean newStatus)
    {
        isFound = newStatus;
    }

}
