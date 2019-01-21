package GameStates.Core;


import Main.Globals;
import Utility.FontServer;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 *
 * @author David
 */
public class Loading extends BasicGameState
{
    // Background
    private Image introLogo;
    
    // Font
    private TrueTypeFont font;
   
    /**
     * Used to identify states
     * Used to switch to state
     */
    @Override
    public int getID() { return Globals.LOADING_ID; }

    
     /**
     * This is only called when the game starts
     * Used to load resources
     * Used to initialise the game state.
     */
    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException 
    {
        try 
        {
            // Initialise and adjust bg
            introLogo = new Image("res/misc/intro.png");
            introLogo = introLogo.getScaledCopy(Globals.screenW, Globals.screenH);
            
            // Initialise font
            font = FontServer.getFont("gamefont-plain-75");
            
        } 
        catch (Exception ex) 
        {
        } 
           
    }
    
    
     /**
     * The method is called each game loop to cause your game to update it's logic. 
     * This is where you should make objects move.
     * This is also where you should check input and change the state of the game.
     * @param gc Holds the game
     * @param game
     * @param delta Amount of time since last update
     * @throws org.newdawn.slick.SlickException
     */
    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException 
    {
       // Add all states
       // Note: Loading state already added
        
       Globals.states.put("ABOUT", 1);
       sbg.addState(new About());
      
       Globals.states.put("CONTROLS", 2);
       sbg.addState(new Controls());
       
       Globals.states.put("CREDITS", 3);
       sbg.addState(new Credits());
       
       Globals.states.put("GAMEOVER", 4);
       sbg.addState(new GameOver());
      
       Globals.states.put("MAINMENU", 6);
       sbg.addState(new MainMenu());
       
       Globals.states.put("PLAY", 7);
       sbg.addState(new Play());
       
       Globals.states.put("SETTINGS", 8);
       sbg.addState(new Settings());
       

       // Initialise resources
       sbg.init(gc);

       // Enter main menu
       sbg.enterState(Globals.states.get("MAINMENU"), Globals.leave , Globals.enter); 
    }
   
    /**
     * This method should be used to draw to the screen. 
     * All of your game's rendering should take place in this method (or via calls)
     * It is called constantly. Items are constantly redrawn
     * @param gc
     * @param game
     * @param g
     */
    @Override
    public void render(GameContainer gc, StateBasedGame game, Graphics g)
    {
        // Draw intro/logo background
        g.drawImage(introLogo, 0, 0);
        
        // Write loading line
        int Xpos = (Globals.screenW/2)-150;
        int Ypos = Globals.screenH - 100;
        font.drawString(Xpos, Ypos, "LOADING");
    }

    
    
    
    
    
}
