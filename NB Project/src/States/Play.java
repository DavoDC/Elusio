package States;

import Components.Popups.Popup;
import Main.Globals;
import Components.Structures.Camera;
import Components.Structures.Player;
import Components.Structures.HUD;
import Components.Structures.Map;
import Entity.Enemy.Enemy;
import Entity.Enemy.EnemyStore;
import Entity.Item.Item;
import Entity.Item.ItemStore;

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

    // Structures
    private Player alien;
    private Camera cam;
    private HUD hud;

    // Entity Stores
    private ItemStore itemStore;
    private EnemyStore enemyStore;

    

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
     * @param gc
     * @param game
     * @throws org.newdawn.slick.SlickException
     */
    @Override
    public void init(GameContainer gc, StateBasedGame game) throws SlickException
    {
        alien = new Player();

        cam = new Camera(gc, Globals.map);
        cam.centerOn(alien.getX(), alien.getX());

        itemStore = new ItemStore();
        enemyStore = new EnemyStore();

        hud = new HUD(cam, alien, gc, game);
       
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
        hud.update(cam, alien, delta);
    }

    /**
     * Acts based on user input
     */
    private void handleInput(Input input, StateBasedGame sbg, int rSpd) throws SlickException
    {
        // Only process keys if input is not been ignored
        if (Globals.inputIgnored) { return; }
        
        // Get key down
        boolean upArrowDown = input.isKeyDown(Input.KEY_UP);
        boolean downArrowDown = input.isKeyDown(Input.KEY_DOWN);
        boolean leftArrowDown = input.isKeyDown(Input.KEY_LEFT);
        boolean rightArrowDown = input.isKeyDown(Input.KEY_RIGHT);
        
        boolean escapeDown = input.isKeyDown(Input.KEY_ESCAPE);
        boolean altDown = input.isKeyDown(Input.KEY_RALT) || input.isKeyDown(Input.KEY_LALT);
        
        boolean fKeyDown = input.isKeyDown(Input.KEY_F);
        boolean qKeyDown = input.isKeyDown(Input.KEY_Q);
        
        // Check and act
        if (upArrowDown)
        {
            // Change animation
            alien.startAnim("up");

            // Move but dont collide
            if (Globals.map.isUpAllowed(alien.getX(), alien.getY(), rSpd))
            {
                alien.adjustY(-rSpd);
            }
        }
        else if (downArrowDown) 
        {
            // Change animation
            alien.startAnim("down");

            // Move if conditions are satisfied
            if (Globals.map.isDownAllowed(alien.getX(), alien.getY(), rSpd))
            {
                alien.adjustY(rSpd);
            }
        }
        else if (leftArrowDown) 
        {
            // Change animation
            alien.startAnim("left");

            // Move if conditions are satisfied
            if (Globals.map.isLeftAllowed(alien.getX(), alien.getY(), rSpd))
            {
                alien.adjustX(-rSpd);
            }
        }
        else if (rightArrowDown) 
        {
            // Change animation
            alien.startAnim("right");

            // Move if conditions are satisfied
            if (Globals.map.isRightAllowed(alien.getX(), alien.getY(), rSpd))
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
        else if (qKeyDown) // Grab key 
        {
            processItem();
        }
        else // When nothing is being pressed
        {
            alien.stopAnim();
        }
    }

private void processItem()
{
    // Get the item underneath the player
    // Otherwise, return null
    Item itemFound = itemStore.getItemUnder(alien);
    
    // If item was found, and hasn't been found before
    if (itemFound != null && !itemFound.isFound())
    {
        // Process item
        itemFound.setFound(true); // Set the item as found
        alien.addToEntities(itemFound); // Add to player inventory
        Globals.itemGrabbed = true; // Notify itemStore
        
        // Check for protectors
        String protectorName = itemFound.getProtector();
        if (!protectorName.equals("none"))
        {
            Enemy enemy = enemyStore.getEnemy(protectorName);
            alien.addToEntities(enemy); // Add to player
            Globals.enemyEnc = true; // Notify enemyStore
        }
        
        // Show item info as a popup
        int r = Map.convertYtoRow(cam.getY() + 3*Globals.tileSize);
        int c = Map.convertXtoCol(cam.getX() + 2*Globals.tileSize);
        
        Popup itemInfo = itemStore.getInfoPopup(itemFound, r, c); // Generate info
        hud.loadPopup(itemInfo); // Show it
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
        enemyStore.updateMap(g, alien);

        // Draw player
        alien.drawPlayer(alien.getX(), alien.getY());

        // Draw HUD
        hud.drawHUD(g);
    }

}
