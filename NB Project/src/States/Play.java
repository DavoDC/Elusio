package States;

import Entity.Item.Item;
import Entity.Item.ItemStore;
import Main.Globals;
import Utility.Camera;
import Entity.Player;
import Utility.UI.HUD;
import Utility.TiledMapPlus;
import Utility.UI.PopupDisplayer;

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
public class Play extends BasicGameState
{

    // Player
    private Player alien;

    // Map objects
    private TiledMapPlus map;
    private Camera cam;

    // Items
    private ItemStore itemStore;

    // HUD
    private HUD hud;
    
    // Popups
    private PopupDisplayer popupDisp;

    /**
     * Used to identify STATES and switch to them
     *
     * @return id
     */
    @Override
    public int getID()
    {
        return Globals.STATES.get("PLAY");
    }

    /**
     * This is only called when the game starts Used to load resources Used to
     * initialise the game state.
     *
     * @param game
     * @throws org.newdawn.slick.SlickException
     */
    @Override
    public void init(GameContainer gc, StateBasedGame game) throws SlickException
    {
        alien = new Player();

        map = new TiledMapPlus("res/map/map.tmx");

        cam = new Camera(gc, map);
        cam.centerOn(alien.getX(), alien.getX());

        itemStore = new ItemStore(map);

        hud = new HUD(cam, alien, gc, game);
        
        popupDisp = new PopupDisplayer();
    }

    /**
     * The method is called each game loop to cause your game to update it's
     * logic. This is where you should make objects move. This is also where you
     * should check input and change the state of the game.
     *
     * @param gc Holds the game
     * @param sbg
     * @param delta Amount of time since between updates
     * @throws org.newdawn.slick.SlickException
     */
    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException
    {
        // Make animation use game time
        alien.updateAnimation(delta);

        // Relative speed
        int relSpeed = (int) (Math.round(delta * alien.getMovSpeed()));

        // Handle input
        handleInput(gc.getInput(), sbg, relSpeed);

        // Update camera
        cam.centerOn(alien.getX(), alien.getY());

        // Update hud
        hud.update(cam, alien);
        
        // Update popups
        popupDisp.updatePD(delta);

    }

    /**
     * Acts based on user input
     */
    private void handleInput(Input input, StateBasedGame sbg, int rSpd) throws SlickException
    {
        // Get key down
        boolean upArrowDown = input.isKeyDown(Input.KEY_UP);
        boolean downArrowDown = input.isKeyDown(Input.KEY_DOWN);
        boolean leftArrowDown = input.isKeyDown(Input.KEY_LEFT);
        boolean rightArrowDown = input.isKeyDown(Input.KEY_RIGHT);
        
        boolean escapeDown = input.isKeyDown(Input.KEY_ESCAPE);
        boolean altDown = input.isKeyDown(Input.KEY_RALT) || input.isKeyDown(Input.KEY_LALT);
        
        boolean fKeyDown = input.isKeyDown(Input.KEY_F);
        boolean gKeyDown = input.isKeyDown(Input.KEY_G);
        
        // Check and act
        if (upArrowDown)
        {
            // Change animation
            alien.startAnim("up");

            // Move but dont collide
            if (map.isUpAllowed(alien.getX(), alien.getY(), rSpd))
            {
                alien.adjustY(-rSpd);
            }
        }
        else if (downArrowDown) 
        {
            // Change animation
            alien.startAnim("down");

            // Move if conditions are satisfied
            if (map.isDownAllowed(alien.getX(), alien.getY(), rSpd))
            {
                alien.adjustY(rSpd);
            }
        }
        else if (leftArrowDown) 
        {
            // Change animation
            alien.startAnim("left");

            // Move if conditions are satisfied
            if (map.isLeftAllowed(alien.getX(), alien.getY(), rSpd))
            {
                alien.adjustX(-rSpd);
            }
        }
        else if (rightArrowDown) 
        {
            // Change animation
            alien.startAnim("right");

            // Move if conditions are satisfied
            if (map.isRightAllowed(alien.getX(), alien.getY(), rSpd))
            {
                alien.adjustX(rSpd);
            }
        }
        else if (escapeDown || altDown) // Paused when alt-tabbed too
        {
            sbg.enterState(Globals.STATES.get("MAINMENU"));
            // No transitions because map goes weird
            Globals.hasBeenPaused = true;
        }
        else if (fKeyDown) // Fullscreen toggle
        {
            boolean newStatus = !Globals.agc.isFullscreen();
            Globals.agc.setFullscreen(newStatus);
        }
        else if (gKeyDown) // Grab key 
        {
            Item itemFound = (Item) itemStore.getEntityUnder(alien);
            if (itemFound != null)
            {
                alien.addToInv(itemFound);
                Globals.itemGrabbed = true;
            }
        }
        else // When nothing is being pressed
        {
            alien.stopAnim();
        }
    }


/**
 * This method should be used to draw to the screen. All of your game's
 * rendering should take place in this method (or via calls) It is called
 * constantly. Items are constantly redrawn
 *
 * @param gc
 * @param sbg
 * @param g
 * @throws org.newdawn.slick.SlickException
 */
@Override
public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException
    {
        // Draw camera's view of map
        // Note: Drawing by layers individually is not done due to severe lag
        cam.drawMap();
        cam.translateGraphics();

        // Account for grabbed items
        itemStore.updateMap(g, alien);

        // Draw player
        alien.drawPlayer(alien.getX(), alien.getY());

        // Draw HUD
        hud.drawHUD(g);
        
        // Draw popups
        popupDisp.renderPopups(g);
    }

}
