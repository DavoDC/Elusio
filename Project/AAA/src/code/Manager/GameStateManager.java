

package code.Manager;

import java.awt.Graphics2D;

import code.GameState.GameOverState;
import code.GameState.GameState;
import code.GameState.IntroState;
import code.GameState.MenuState;
import code.GameState.PauseState;
import code.GameState.PlayState;

/**
 * The GameStateManager does exactly what its name says. 
 * It contains a list of GameStates.
 * It decides which GameState to update() and draw()
 * Handles switching between different GameStates.
 * @author David
 */
public class GameStateManager {
	
	private boolean paused;
	private PauseState pauseState;
	
	private GameState[] gameStates;
	private int currentState;
	private int previousState;
	
	public static final int NUM_STATES = 4;
	public static final int INTRO = 0;
	public static final int MENU = 1;
	public static final int PLAY = 2;
	public static final int GAMEOVER = 3;
	
	public GameStateManager() {
		
		JukeBox.init();
		
		paused = false;
		pauseState = new PauseState(this);
		
		gameStates = new GameState[NUM_STATES];
		setState(INTRO);
		
	}
	
	public void setState(int i) {
		previousState = currentState;
		unloadState(previousState);
		currentState = i;
                switch (i) {
                    case INTRO:
                        gameStates[i] = new IntroState(this);
                        gameStates[i].init();
                        break;
                    case MENU:
                        gameStates[i] = new MenuState(this);
                        gameStates[i].init();
                        break;
                    case PLAY:
                        gameStates[i] = new PlayState(this);
                        gameStates[i].init();
                        break;
                    case GAMEOVER:
                        gameStates[i] = new GameOverState(this);
                        gameStates[i].init();
                        break;
                    default:
                        break;
            }
	}
	
	public void unloadState(int i) {
		gameStates[i] = null;
	}
	
	public void setPaused(boolean b) {
		paused = b;
	}
	
	public void update() {
		if(paused) {
			pauseState.update();
		}
		else if(gameStates[currentState] != null) {
			gameStates[currentState].update();
		}
	}
	
	public void draw(Graphics2D g) {
		if(paused) {
			pauseState.draw(g);
		}
		else if(gameStates[currentState] != null) {
			gameStates[currentState].draw(g);
		}
	}
	
}
