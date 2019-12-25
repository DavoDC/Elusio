package states.special;

import base.Globals;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import states.base.AutoState;

/**
 * Provides actual gameplay state
 *
 * @author David
 */
public class Play extends AutoState {

    /**
     * Do initialization tasks
     *
     * @param gc
     * @param game
     * @throws org.newdawn.slick.SlickException
     */
    @Override
    public void init(GameContainer gc, StateBasedGame game) throws SlickException {

    }

    /**
     * Handles logic and input every game loop
     *
     * @param gc Holds the game
     * @param sbg
     * @param delta Amount of time since between updates
     * @throws org.newdawn.slick.SlickException
     */
    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {

        // Update delta value
        Globals.curDelta = delta;

        // Handle input
        Globals.conServer.handleInput(new String[]{"All"});

        // Process item use
        Globals.itemProc.processItemUse();
    }

    /**
     * Render map, alien (player) and HUD
     *
     * @param gc
     * @param sbg
     * @param g
     * @throws org.newdawn.slick.SlickException
     */
    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
            throws SlickException {

        // Draw camera's view of map
        // Note: Drawing by layers individually is not done due to severe lag
        Globals.cam.drawViewPort();
        Globals.cam.translateGraphics();

        // Account for entity interactions
        Globals.enemyStore.updateMap(g);
        Globals.obStore.updateMap(g);
        Globals.itemStore.updateMap(g);
        Globals.itemProc.renderItemUse(g);

        // Draw player
        Globals.player.draw();

        // Draw HUD
        Globals.hud.drawHUD(g);

        // Draw popups
        Globals.popStore.renderCurPopup(g);
    }

}
