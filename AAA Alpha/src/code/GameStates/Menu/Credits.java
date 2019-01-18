package code.GameStates.Menu;

import code.Utility.InfoWriter;
import java.util.ArrayList;

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
public class Credits extends BasicGameState
{
    // InfoWriter to help with drawing text
    private InfoWriter infoW;
    
    // Stores text
    private ArrayList<String> text;
    
    
    /**
     * Used to identify states
     * Used to switch to state
     * @return state ID
     */
    @Override
    public int getID() { return code.MainGame.CREDITS; }

    
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
       // Initialise ALs
       infoW = new InfoWriter();
       text = new ArrayList<>();
       
       // Add to text
       text.add("CREDITS");
       text.add("Programmer = David Charkey");
       text.add("Designer = David Charkey");
       text.add("Testers = David Charkey");
       text.add("Artists");
       text.add("David Charkey (Intro Background) \n" +
                "Rawdanitsu (Menu Backgrounds)\n" +
                "hiddenone (Alien sprites)\n" +
                "Kadokawa (Alien sprites)\n" +
                "Ivan Voirol (Tileset)\n" +
                "Aweryn (Tileset)\n" +
                "MillionthVector (Alien ship sprite)\n" +
                "Clint Bellanger (Gold Item Sprites)\n" +
                "Aaron D. Chand (Special pixelated font)\n" +
                "Tuomo Untinen (Sailing Ship Sprites)");
       text.add("    ");
       text.add("PRESS MIDDLE MOUSE BUTTON TO GO BACK");
       
       // Add text to Info Writer
       infoW.addText(text);

       
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
        infoW.writeParagraph(g);
    }

    
    
    
}
