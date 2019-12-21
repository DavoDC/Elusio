package states.base;

import main.Globals;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Models a state that is automatically integrated into the game
 *
 * @author David
 */
public abstract class AutoState extends BasicGameState {

    /**
     * Return ID used to identify state
     *
     * @return ID
     */
    @Override
    public final int getID() {
        return Globals.states.get(getClass().getSimpleName());
    }

    /**
     * Initialize state
     *
     * @param container
     * @param game
     * @throws SlickException
     */
    @Override
    public abstract void init(GameContainer container, StateBasedGame game) throws SlickException;

    /**
     * Render state
     *
     * @param container
     * @param game
     * @param g
     * @throws SlickException
     */
    @Override
    public abstract void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException;

    /**
     * Update state
     *
     * @param container
     * @param game
     * @param delta
     * @throws SlickException
     */
    @Override
    public abstract void update(GameContainer container, StateBasedGame game, int delta) throws SlickException;

}
