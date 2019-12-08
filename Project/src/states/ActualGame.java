package states;

import entity.item.ItemProcessor;
import main.Globals;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Provides actual gameplay state
 *
 * @author David
 */
public class ActualGame extends BasicGameState {

    /**
     * Return ID used to identify state
     *
     * @return ID
     */
    @Override
    public int getID() {
        return Globals.STATES.get("PLAY");
    }

    /**
     * Do initialization tasks
     *
     * @param gc
     * @param game
     * @throws org.newdawn.slick.SlickException
     */
    @Override
    public void init(GameContainer gc, StateBasedGame game) throws SlickException {

        // Get all items for faster IDE testing
        if (Globals.inIDE) {
//            Globals.itemStore.getEntities().forEach((cur) -> {
//                Globals.player.addItem((Item) cur);
//            });
        }

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

        // Make animation use game time
        Globals.player.updateAnimation(delta);

        // Handle input
        Globals.conServer.handlePlayInput(delta);

        // Update camera
        Globals.cam.centerOn(Globals.player.getX(), Globals.player.getY());

        // Update HUD
        Globals.hud.update(Globals.cam, Globals.player, delta);

        // Process item use
        ItemProcessor.processItemUse();
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
        Globals.cam.drawMap();
        Globals.cam.translateGraphics();

        // Account for entity interactions
        Globals.enemyStore.updateMap(g);
        Globals.obStore.updateMap(g);
        Globals.itemStore.updateMap(g);
        ItemProcessor.renderItemUse(g);

        // Draw player
        Globals.player.drawPlayer(Globals.player.getX(), Globals.player.getY());

        // Draw HUD
        Globals.hud.drawHUD(g);
    }

}
