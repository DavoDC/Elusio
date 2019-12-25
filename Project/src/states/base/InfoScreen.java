package states.base;

import java.util.ArrayList;

import base.Globals;

/**
 * Models a screen that provides information
 *
 * @author David
 */
public abstract class InfoScreen extends InterfaceScreen {

    // Line constants
    public static final float lineX = (Globals.screenW / 4) - 300;
    public static final float lineY = 210;

    // Header constant
    public static final float headerX = (Globals.screenW / 2) - 175;

    /**
     * Set features of buttons
     *
     * @return
     */
    @Override
    public ArrayList<Object> getButtonFeatures() {

        // Initialize list
        ArrayList<Object> feats = new ArrayList<>();

        // Add featues to list
        feats.add(getButtonLabels().size()); // Number of buttons
        feats.add(Globals.getFP("nothing")); // Image Location
        feats.add(getStartXPos()); // startXpos
        feats.add(getStartYPos()); // startYpos
        feats.add((int) Globals.screenW); // Width
        feats.add(40); // Height
        feats.add(getXSpacing()); // XSpacing
        feats.add(getYSpacing()); // YSpacing
        feats.add(getColumnNo()); // NumberofColumns
        feats.add(getLineFontString()); // FontString
        feats.add(false); // isSoundWanted

        // Return list
        return feats;
    }

    /**
     * Can be overridden to change start X pos
     *
     * @return start X pos
     */
    public int getStartXPos() {
        return (int) lineX;
    }

    /**
     * Can be overridden to change start Y pos
     *
     * @return start Y pos
     */
    public int getStartYPos() {
        return (int) lineY;
    }

    /**
     * Can be overridden to change X spacing
     *
     * @return X spacing
     */
    public int getXSpacing() {
        return 20;
    }

    /**
     * Can be overridden to change column amount
     *
     * @return column amount
     */
    public int getColumnNo() {
        return 1;
    }

    /**
     * Can be overridden to change Y spacing
     *
     * @return Y spacing
     */
    public int getYSpacing() {
        return 5;
    }

    /**
     * Can be overridden to change line font
     *
     * @return line font string
     */
    public String getLineFontString() {
        return "Segoe UI-Plain-30";
    }

    /**
     * Must be implemented to provide button labels
     *
     * @return list of label strings
     */
    @Override
    public abstract ArrayList<String> getButtonLabels();

    /**
     * Can be overridden to change darkened status
     *
     * @return darkened status
     */
    @Override
    public boolean isDarkened() {
        return true;
    }

}
