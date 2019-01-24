package States;

import Main.Globals;
import static Main.Globals.screenW;
import Utility.GUI.FontServer;
import Utility.GUI.InfoScreen;
import static Utility.GUI.InfoScreen.headerX;
import java.util.ArrayList;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Shown just before exit
 * 
 * @author David
 */
public class Exit extends InfoScreen
{
    // The absolute time of closing
    private long exitConstant = 3369/2;
    
    // The actual time of closing
    private long exitTime = 0;
    
    // The time left before closing
    private long timeLeft = exitConstant;
    
    //Font 
    private TrueTypeFont lineFont;
   
    
    

    @Override
    public int getID() { return Globals.states.get("EXIT"); }
    
    @Override
    public float[] initHeaderParams() 
    { 
        // Initialise header parameters
        // Order = startXpos, startYpos, width, height
        float[] hParams = new float[] {headerX - 300, 100f, screenW, 50f};
        
        return hParams;
    }
    
    @Override
    public void customInit()
    {
        lineFont = FontServer.getFont(InfoScreen.lineFont);
    }
    
    @Override
    public ArrayList<String> initButtonLabels() 
    {
        //Create AL
       ArrayList<String> text = new ArrayList<>();
       
       // Add to text
       text.add("THANKYOU FOR PLAYING");
       text.add("   ");
       text.add("The game will close shortly");
       
       return text;
    }
    
    
    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int delta) 
    {
        // Initialise time of closing
        // Must be initialised here
        if (exitTime == 0) // Only do once
        {
            exitTime = Globals.agc.getTime() + exitConstant;
        }
        
        // Get time right now
        long now = Globals.agc.getTime();
        
        // Calculate time left
        timeLeft = exitTime - now;

        // Exit near when timeLeft has elapsed
        // Must be greater than 0 to prevent negative from showing
        if (timeLeft < 50)
        {
           Globals.agc.exit();
        }
        
    }
     
    
    @Override
    public void customPostRender(Graphics g)
    {
        // Show status
        g.setColor(Color.white);
        lineFont.drawString(
                InfoScreen.lineX, 
                400f, 
                "Time left = " + timeLeft 
        );
    }
    
    
    @Override
    public boolean isDarkened()
    {
        return true;
    }
    
}
