package components.screentemps;

import java.util.ArrayList;

import main.Globals;

/**
 * Models a screen containing info
 *
 * @author David Charkey
 */
public abstract class InfoScreen extends InterfaceScreen
{

    // Line constants
    public static final float lineX = (Globals.screenW / 4) - 300;
    public static final float lineY = 210;

    // Header constants
    public static final float headerX = (Globals.screenW / 2) - 175;

    @Override
    public abstract int getID();

    @Override
    public ArrayList<Object> getButtonFeatures()
    {
        // Create AL
        ArrayList<Object> feats = new ArrayList<>();

        // Add to AL
        feats.add(getButtonLabels().size()); // Number of buttons
        feats.add(Globals.emptyImgRes); // Image Location
        feats.add(getStartXPos()); // startXpos
        feats.add(getStartYPos()); // startYpos
        feats.add((int) Globals.screenW); // Width
        feats.add(40); // Height
        feats.add(getXSpacing()); // XSpacing
        feats.add(getYSpacing()); // YSpacing
        feats.add(getColumnNo()); // NumberofColumns
        feats.add(getLineFontString()); // FontString

        return feats;
    }

    public int getStartXPos()
    {
        return (int) lineX;
    }

    public int getStartYPos()
    {
        return (int) lineY;
    }

    public int getXSpacing()
    {
        return 20;
    }

    public int getColumnNo()
    {
        return 1;
    }

    public int getYSpacing()
    {
        return 5;
    }

    public String getLineFontString()
    {
        return "Segoe UI-Plain-30";
    }

    @Override
    public abstract ArrayList<String> getButtonLabels();

    @Override
    public boolean isDarkened()
    {
        return true;
    }

}
