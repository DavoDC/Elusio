package Utility;

import Main.Globals;
import static Main.Globals.screenW;
import java.util.ArrayList;
import org.newdawn.slick.Color;

/**
 *
 * @author CHARKEYD
 */
public abstract class InfoScreen extends InterfaceScreen
{
    // Get needed info
    private static final int startX = (Globals.screenW/4) - 200;
    
    @Override
    public abstract int getID();

    @Override
    public ButtonManager initButtonManager() 
    {
        return new ButtonManager("Gamefont-Plain-65", "Segoe UI-Plain-35");
    }

    @Override
    public float[] initHeaderParams() 
    { 
        // Initialise header parameters
        // Order = startXpos, startYpos, width, height
        float[] hParams = new float[] {startX, 100f, screenW, 50f};
        
        return hParams;
    }

    @Override
    public float[] initLineParams() 
    {
        // Initalise paragraph parameters
        // Order = startXpos, startYpos, width, height, Xspace, Yspace, colNo
        float[] lParams = new float[] {startX, 160f, screenW, 40f, 15f, 0f, 1f};
        
        return lParams;
    }
    
    public abstract ArrayList<String> initButtonLabels();
    
    @Override
    public boolean isDarkened() 
    {
        return true;
    }

    @Override
    public Color getButtonCol() 
    {
        return Color.transparent;
    }
    
}
