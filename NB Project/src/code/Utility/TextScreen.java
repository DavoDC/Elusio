package code.Utility;


import java.util.ArrayList;
import org.newdawn.slick.BigImage;
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
public class TextScreen extends BasicGameState
{
    // Helps to display text
    private TextScreenHelper textscreen;
    
    // Stores text
    private ArrayList<String> text;
    
    // Background
    private BGBank bgb;
    private BigImage bg;
    
    
    /**
     * Used to identify states
     * Used to switch to state
     * @return state ID
     */
    @Override
    public int getID() { return -1; }

    
     /**
     * This is only called when the game starts
     * Used to load resources
     * Used to initialise the game state.
     * @param container
     * @param game
     * @throws org.newdawn.slick.SlickException
     */
    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException 
    {
       // Initialise objects
       textscreen = new TextScreenHelper();
       text = new ArrayList<>();
       bgb = new BGBank();
       bg = bgb.getRandomBG();
       
       // Add text to Info Writer
       textscreen.addText(getTextList());

       
    }
    
     /**
     * The method is called each game loop to cause your game to update it's logic. 
     * This is where you should make objects move.
     * This is also where you should check input and change the state of the game.
     * @param gc
     * @param game
     * @param delta Amount of time since last update
     * @throws org.newdawn.slick.SlickException
     */
    @Override
    public void update(GameContainer gc, StateBasedGame game, int delta) throws SlickException 
    {
       Boolean mouseClicked = gc.getInput().isMouseButtonDown(Input.MOUSE_MIDDLE_BUTTON);
       
       if (mouseClicked)
       {
       game.enterState(code.MainGame.MAIN_MENU);
       }
       
    }
    
        /**
     * This method should be used to draw to the screen. 
     * All of your game's rendering should take place in this method (or via calls)
     * It is called constantly. Items are constantly redrawn
     * @param gc
     * @param game
     * @param g
     * @throws org.newdawn.slick.SlickException
     */
    @Override
    public void render(GameContainer gc, StateBasedGame game, Graphics g) throws SlickException 
    {
        // Draw bg
        bg.draw(0, 0, Color.darkGray);
        
        // Draw text on top
        textscreen.writeParagraph(g);
    }

    public ArrayList<String> getTextList()
    {
        ArrayList<String> list = new ArrayList<>();
        list.add("This is default method");
        list.add("Override needs to occur!");
        return list;
    }
    
    
    
}
