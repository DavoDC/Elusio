package GameStates.Core;

import Main.Globals;
import Utility.ButtonManager;
import Utility.InterfaceScreen;

import java.util.ArrayList;
import java.util.Arrays;
import org.newdawn.slick.Color;
import org.newdawn.slick.state.StateBasedGame;

/**
 *
 * @author David
 */
public class MainMenu extends InterfaceScreen
{
    
    @Override
    public int getID() 
    {
        return Globals.states.get("MAINMENU");
    }
    
    @Override
    public ButtonManager initButtonManager() 
    {
        String gfs = "gamefont-plain-70";
        return new ButtonManager(gfs, gfs);
    }
    
    @Override
    public ArrayList<String> initButtonLabels() 
    {
       ArrayList<String> labels = new ArrayList<String>();
       labels.add("PLAY");
       labels.add("CONTROLS");
       labels.add("SETTINGS");
       labels.add("CREDITS");
       labels.add("ABOUT");
       labels.add("EXIT");
       
       return labels;
    }
    
      @Override
    public float[] initHeaderParams() 
    {
        return 
           new float[] 
           {
           300, // start X pos
           100, // start Y pos
           370, // Width
           60,  // Height
           };
    }

    @Override
    public float[] initLineParams() 
    {
        // Get header params
        float[] header = initHeaderParams();
        
        // Make array with last few variables
        float[] last = new float[] 
           {
           0, //Xspace
           Globals.screenH/16, //Yspace //dynamic to fit on screen
           1 //colNo
           };
        
        // Join header and last
        float[] join= Arrays.copyOf(header, header.length + last.length);
        System.arraycopy(last, 0, join, header.length, last.length);
        
        return join;
           
    }

    @Override
    public void customPreUpdate() 
    {
 
        // If paused, change "PLAY" to "CONTINUE"
        if (Globals.isPaused)
        {
            getButtonLabels().set(0, "CONTINUE");
            getButtonManager().createButtonGrid
            (getHeaderParams(), getLineParams(), getButtonLabels());

            int pos = Globals.states.get("PLAY");
            Globals.states.put("CONTINUE", pos);
        }
    
    }

    @Override
    public void clickAction(StateBasedGame sbg, String label) 
    {
        // Close game if "EXIT" pressed
        if (label.contains("EXIT")) 
        { 
            Globals.agc.exit(); 
        }
        else 
        {
            // Figure out next state ID
            int newStateID = Globals.states.get(label);

            // Transition to that state
            sbg.enterState(newStateID, Globals.leave, Globals.enter); 
        }
        
        
    }
    
    @Override
    public boolean isDarkened() 
    {
        return false;
    }

    @Override
    public Color getButtonCol() 
    {
        return Color.black;
    }
    
    
    
}
    