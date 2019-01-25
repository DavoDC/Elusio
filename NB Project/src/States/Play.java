package States;

import Utility.Camera;
import Entity.Player;
import Main.Globals;
import Utility.UI.HUD;
import Utility.TiledMapPlus;

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
    private int playerX;
    private int playerY;
    
    // Map objects
    private TiledMapPlus map;
    private Camera cam;
    
    // HUD
    private HUD hud;
    
    
            
    /**
     * Used to identify states and switch to them
     * @return id
     */
    @Override
    public int getID() { return Globals.states.get("PLAY"); }

    
     /**
     * This is only called when the game starts
     * Used to load resources
     * Used to initialise the game state.
     * @param container
     * @param game
     * @throws org.newdawn.slick.SlickException
     */
    @Override
    public void init(GameContainer gc, StateBasedGame game) throws SlickException 
    {
        alien = new Player();
        playerX = alien.getStartX();
        playerY = alien.getStartY();

        map = new TiledMapPlus("res/map/map.tmx");

        cam = new Camera(gc, map);
        cam.centerOn(playerX, playerX);

        hud = new HUD(cam, alien, gc, game); 
    }

    
    
    /**
     * The method is called each game loop to cause your game to update it's logic. 
     * This is where you should make objects move.
     * This is also where you should check input and change the state of the game.
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
        float relSpeed = delta * alien.getMovSpeed();
        
        // Handle input
        handleInput(gc.getInput(), sbg, relSpeed);         
         
        // Update camera
        cam.centerOn(playerX, playerY);
         
        // Update hud
        hud.update(cam, playerX, playerY);

    }

    
    /**
     * Handles input
     */
    private void handleInput(Input input, StateBasedGame sbg, float rSpd) throws SlickException
    {
        
        if(input.isKeyDown(Input.KEY_UP)) // Up arrow
            {
                // Change animation
                alien.startAnim("up");
              
                // Move but dont collide
                if (map.isUpAllowed(playerX, playerY, rSpd, cam))
                {
                    playerY -= rSpd;
                }
            } 
        else if(input.isKeyDown(Input.KEY_DOWN)) // Down arrow
            {
                // Change animation
                alien.startAnim("down");

                // Move if conditions are satisfied
                if (map.isDownAllowed(playerX, playerY, rSpd, cam))
                {
                    playerY += rSpd;
                }
            }
        else if(input.isKeyDown(Input.KEY_LEFT)) //Left arrow
            {
                // Change animation
                alien.startAnim("left");
              
                // Move if conditions are satisfied
                if (map.isLeftAllowed(playerX, playerY, rSpd, cam))
                {
                    playerX -= rSpd;
                }
            }
        else if (input.isKeyDown(Input.KEY_RIGHT)) //Right arrow
            {
                // Change animation
                alien.startAnim("right");

                // Move if conditions are satisfied
                if (map.isRightAllowed(playerX, playerY, rSpd, cam))
                {
                    playerX += rSpd;
                }
            }
        else if (input.isKeyDown(Input.KEY_ESCAPE)) // Escape key
            {
                sbg.enterState(
                    Globals.states.get("MAINMENU"),
                    Globals.getLeave(),
                    Globals.getEnter()
                );
                Globals.hasBeenPaused = true;
            }
        else if (input.isKeyDown(Input.KEY_F)) // F key
            {
                boolean newStatus = !Globals.agc.isFullscreen();
                Globals.agc.setFullscreen(newStatus);
            }
        else // When nothing is being pressed
            {
                alien.stopAnim();
            }

    }
   
  
   
    /**
     * This method should be used to draw to the screen. 
     * All of your game's rendering should take place in this method (or via calls)
     * It is called constantly. Items are constantly redrawn
     * @param gc
     * @param sbg
     * @param g
     * @throws org.newdawn.slick.SlickException
     */
    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException 
    {  
        // Draw camera's view of map
        cam.drawMap();
        cam.translateGraphics();
          
        // Draw player
        alien.drawPlayer(playerX, playerY);
        
        // Draw HUD
        hud.drawHUD(gc, g);    
    }

    
    
    
    
  
    
    

   
   
}