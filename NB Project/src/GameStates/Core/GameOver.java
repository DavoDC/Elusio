package GameStates.Core;


import Main.Globals;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 *
 * @author David
 */
public class GameOver extends BasicGameState
{
    
    
    
    @Override
    /**
     * Used to identify states
     * Used to switch to state
     */
    public int getID() { return Globals.states.get("GAMEOVER"); }

    @Override
     /**
     * This is only called when the game starts
     * Used to load resources
     * Used to initialise the game state.
     */
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
       
    }
    
    /**
     * The method is called each game loop to cause your game to update it's logic. 
     * This is where you should make objects move.
     * This is also where you should check input and change the state of the game.
     * @param gc Holds the game
     * @param delta Amount of time since last update
     * @throws org.newdawn.slick.SlickException
     */
    @Override
    public void update(GameContainer gc, StateBasedGame game, int delta) throws SlickException {
     
        // Enter main menu when back pushed
       Boolean escClicked = gc.getInput().isKeyDown(Input.KEY_ESCAPE);
       Boolean middleClicked = gc.getInput().isMouseButtonDown(Input.MOUSE_MIDDLE_BUTTON);
       
       if (escClicked || middleClicked)
       {
       game.enterState(Globals.states.get("MAINMENU"));
       }
       
       
       
    }
    
    
    
    /**
     * This method should be used to draw to the screen. 
     * All of your game's rendering should take place in this method (or via calls)
     * It is called constantly. Items are constantly redrawn
     * @param gc
     * @param g
     * @throws org.newdawn.slick.SlickException
     */
    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
       
        // Grey version of alien
        
        // State tag
        g.setColor(Color.white);
        g.drawString("GAMEOVER", 300, 100);
       
        // Reveal back button at top left
        g.setColor(Color.white);
        g.drawString("BACK = MIDMOUSE/ESC", 10, 35);
    }

    
    
    
}
