package States;

import Main.Globals;
import Utility.Menu.ButtonManager;
import Utility.Menu.InterfaceScreen;

import java.util.ArrayList;
import org.newdawn.slick.Color;
import org.newdawn.slick.state.StateBasedGame;

/**
 *
 * @author David
 */
public class Settings extends InterfaceScreen
{

    @Override
    public int getID() 
    {
        return Globals.states.get("SETTINGS");
    }
    
    @Override
    public ButtonManager initButtonManager() 
    {
        return new ButtonManager("Gamefont-Plain-65", "OCR A Extended-Plain-35");
    }

    @Override
    public float[] initHeaderParams() 
    {
        return 
           new float[] 
           {
           (Globals.screenW/2) - 175, // start X pos
           150, // start Y pos
           350, // Width
           60,  // Height
           };
    }

    @Override
    public float[] initLineParams() 
    {
        return 
           new float[] 
           {
           300, // start X pos
           200, // start Y pos
           450, // Width
           40,  // Height
           0, //Xspace
           50, //Yspace
           1 //colNo
           };
    }
  

    @Override
    public ArrayList<String> initButtonLabels() 
    {
       // Create strings
       boolean status = Globals.showStats;
       String label = processSwitchString("SHOW STATS: X", status);
        
       // Create AL
       ArrayList<String> labels = new ArrayList<>();
       
       // Add to AL
       labels.add("SETTINGS");
       labels.add(label);
       
       return labels;
    }

    
    @Override
    public void clickAction(StateBasedGame sbg, String label) 
    {
       if (label.contains("STATS"))
           {
               Globals.showStats = !Globals.showStats;
               switchLabel(1, Globals.showStats);
           }
    }
    
    /**
     * Alter a label after it is clicked
     * 
     * @param prev previous label
     * @param pos in button list
     * @param state boolean status
     * @return 
     */
    private void switchLabel(int pos, boolean state)
    {
       // Get previous string
       String prev = getButtonLabels().get(pos);
       
       // Generate new string
       String newS = processSwitchString(prev, state);

       // Replace old label with new
       getButtonLabels().set(pos, newS);
       
       // Reinitialise buttons
       getButtonManager().createButtonGrid
       (getHeaderParams(), getLineParams(), getButtonLabels());
               
    }
    
    private String processSwitchString(String prev, boolean state)
    {
       // Initialise new label with first part
       String newS = prev.split(":")[0];
        
       // Add semicolon and space
       newS += ": ";
        
       // Add true if ON, and OFF if false
       if (state) { newS += "ON"; }
       else { newS += "OFF"; }
       
       // Return new string
       return newS;
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
