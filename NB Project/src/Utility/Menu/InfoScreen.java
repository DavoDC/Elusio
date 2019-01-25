package Utility.Menu;

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
    // Adjust X positions of text
    public static final int headerX = (Globals.screenW/2) - 175;
    public static final int lineX = (Globals.screenW/4) - 200;
    
    
    // Fonts
    public static final String headerFont = "Gamefont-Plain-65";
    public static final String lineFont = "Segoe UI-Plain-35";
    
    @Override
    public abstract int getID();

    @Override
    public ButtonManager initButtonManager() 
    {
        return new ButtonManager( headerFont, lineFont );
    }

    @Override
    public float[] initHeaderParams() 
    { 
        // Initialise header parameters
        // Order = startXpos, startYpos, width, height
        float[] hParams = new float[] {headerX, 100f, screenW, 50f};
        
        return hParams;
    }

    @Override
    public float[] initLineParams() 
    {
        // Initalise paragraph parameters
        // Order = startXpos, startYpos, width, height, Xspace, Yspace, colNo
        float[] lParams = new float[] {lineX, 160f, screenW, 40f, 15f, 0f, 1f};
        
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
