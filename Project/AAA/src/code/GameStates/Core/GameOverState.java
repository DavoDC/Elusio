package code.GameStates.Core;


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
public class GameOverState extends BasicGameState
{
    
    
    
    @Override
    /**
     * Used to identify states
     * Used to switch to state
     */
    public int getID() { return 5; }

    @Override
     /**
     * This is only called when the game starts
     * Used to load resources
     * Used to initialise the game state.
     */
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
       
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
       //ground tiles with dead sprite on top
        
        g.setColor(Color.white);
        g.drawString("GAMEOVER", 300, 100);
        
         //g.drawString("Your progress was " + MainGame.mgProgress, 300, 200);
        
        g.drawString("CLICK TO RETRY", 300, 300);
        
       
        
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
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
     
        Boolean mouseClicked = container.getInput().isMouseButtonDown(Input.MOUSE_LEFT_BUTTON);
       
       if (mouseClicked)
       {
       game.enterState(3);
       }
       
    }
    
}
