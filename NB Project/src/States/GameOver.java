package States;

import Main.Globals;
import Utility.GUI.FontServer;
import Utility.GUI.InfoScreen;
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
public class GameOver extends InfoScreen
{
    
    //Font 
    private TrueTypeFont lineFont;
   
    
    @Override
    public int getID() { return Globals.states.get("GAMEOVER"); }
    
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
       text.add("GAMEOVER");
       
       return text;
    }
    
    
    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int delta) 
    {
        
    }
     
    
    @Override
    public void customPostRender(Graphics g)
    {
        // Show status
        g.setColor(Color.white);
        lineFont.drawString(
                InfoScreen.lineX + 200, 
                200f, 
                "Lives left = " + Globals.playerLives 
        );
    }
    
    
    @Override
    public boolean isDarkened()
    {
        return true;
    }
    
}
