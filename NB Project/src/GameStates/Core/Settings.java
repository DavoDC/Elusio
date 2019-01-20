package GameStates.Core;

import Main.Globals;
import Utility.OptionScreen;

import java.util.ArrayList;
import org.newdawn.slick.state.StateBasedGame;

/**
 *
 * @author David
 */
public class Settings extends OptionScreen
{

    @Override
    public int getID() 
    {
        return Globals.states.get("SETTINGS");
    }
    
    @Override
    public float[] initButtonParams() 
    {
        return 
           new float[] 
           {
           300, // start X pos
           200, // start Y pos
           350, // Width
           50,  // Height
           0, //Xspace
           60, //Yspace
           1 //colNo
           };
    }

    @Override
    public ArrayList<String> initButtonLabels() 
    {
       ArrayList<String> labels = new ArrayList<String>();
       labels.add("SETTINGS");
       labels.add("SHOW FPS: OFF");
       labels.add("SHOW MEMORY: OFF");
       labels.add("SHOW COORDS: OFF");
       
       return labels;
    }

    
    @Override
    public void clickAction(StateBasedGame sbg, String label) 
    {
       if (label.contains("FPS"))
           {
               // Alter global status
               Globals.showFPS = !Globals.showFPS;
               // Generate new label
               String newLabel = switchLabel(getButtonLabels().get(1), Globals.showFPS);
               // Replace old label with new
               getButtonLabels().set(1, newLabel);
               // Reinitialise buttons
               getButtonManager().createButtonGrid(getButtonParams(), getButtonLabels());
           }
       else if(label.contains("MEMORY"))
           {
               Globals.showMemory = !Globals.showMemory;
           }
       else if (label.contains("COORD"))
           {
               Globals.showCoordinates = !Globals.showCoordinates;
           }
    }
    
    private String switchLabel(String prev, boolean state)
    {
        // Get firstPart
        String newS = prev.split(":")[0];
        
        // Add semicolon and space
        newS += ": ";
        
        if (state) // True = ON
        {
            newS += "ON";
        }
        else // False = OFF 
        {
            newS += "OFF";
        }
        
        return newS;

    }


    
    
}
