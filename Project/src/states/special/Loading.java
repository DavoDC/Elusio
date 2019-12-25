package states.special;

import states.menu.settings.Settings;
import states.menu.Credits;
import states.menu.Menu;
import states.menu.Exit;
import states.menu.About;
import states.menu.Controls;
import base.Globals;
import components.servers.FontServer;
import components.servers.controls.ControlServer;
import components.servers.AudioServer;
import components.servers.particles.ParticleServer;
import components.modules.Camera;
import components.modules.HUD;
import components.modules.GameMap;
import components.modules.Player;
import components.popups.PopupStore;
import entity.enemy.EnemyStore;
import entity.item.ItemProcessor;
import entity.item.ItemStore;
import entity.obstacle.ObstacleStore;

import java.util.Timer;
import java.util.TimerTask;
import org.newdawn.slick.Color;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;
import states.base.AutoState;

/**
 * Shows a loading screen while its loads resources
 *
 * @author David
 */
public class Loading extends BasicGameState {

    // Background image
    private Image introLogo;

    // Text font
    private TrueTypeFont font;

    // Duration of loading
    private long introTime = 1369;

    // Update method run status
    private boolean updateOcc = false;

    /**
     * Return ID used to identify state
     *
     * @return ID
     */
    @Override
    public int getID() {
        return 0;
    }

    /**
     * Loads the game's resources
     *
     * @param gc
     * @param sbg
     * @throws org.newdawn.slick.SlickException
     */
    @Override
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
        try {

            // Initialise and adjust background
            introLogo = new Image(Globals.getFP("intro.png"));
            introLogo = introLogo.getScaledCopy(Globals.screenW, Globals.screenH);

            // Initialise font
            font = FontServer.getFont("gamefont-plain-75");

            // Initialize framework
            Globals.sbg = sbg;
            Globals.agc.setDefaultFont(FontServer.getFont("Segoe UI-Plain-16"));

            // Initialise main structures
            Globals.gameMap = new GameMap(Globals.getFP("map.tmx"));
            Globals.player = new Player();
            Globals.cam = new Camera(gc, Globals.gameMap);
            Globals.cam.centerOn(Globals.player.getX(), Globals.player.getX());
            Globals.hud = new HUD(Globals.cam, Globals.player);

            // Initialise servers
            Globals.conServer = new ControlServer();
            Globals.audioServer = new AudioServer();
            Globals.partServer = new ParticleServer();

            // Initialise misc structures
            Globals.popStore = new PopupStore();
            Globals.itemProc = new ItemProcessor();

            // Initialize entity stores
            Globals.itemStore = new ItemStore();
            Globals.enemyStore = new EnemyStore();
            Globals.obStore = new ObstacleStore();

            // Hide alien ship
            Globals.obStore.addEncounterByName("StartAlien");

            // When in IDE, quickload
            if (Globals.inIDE) {
                introTime = 5;
            }

        } catch (SlickException ex) {
            System.err.println("Error in 'init' of Loading");
        }
    }

    /**
     * Update state
     *
     * @param gc Holds the game
     * @param sbg
     * @param delta Amount of time since last update
     * @throws org.newdawn.slick.SlickException
     */
    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {

        // If update has not occurred
        if (!updateOcc) {

            // Add all states except for Loading state (ID = 0)
            // Add menu states
            integrateState(new About());
            integrateState(new Controls());
            integrateState(new Credits());
            integrateState(new Exit());
            integrateState(new Menu());
            integrateState(new Settings());

            // Add special states
            integrateState(new Play());
            integrateState(new Inventory());
            integrateState(new Rift());

            //
            // Add challenge states
            // integrateState(new Challenge());
            //
            //
            // Initialize SBG
            sbg.init(gc);

            // Schedule state change after certain time
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {

                    // Enter first state
                    Globals.sbg.enterState(Globals.states.get("Menu"),
                            // Leave
                            new FadeOutTransition(Color.black, 1000),
                            // Enter
                            new FadeInTransition(Color.black, 1000)
                    );
                }
            }, introTime);

            // Update has now occurred
            updateOcc = true;
        }
    }

    /**
     * Integrate a given AutoState into the game
     *
     * @param newState
     */
    private void integrateState(AutoState newState) {

        // NewID will be current size of directory + 1
        int newID = Globals.states.size() + 1;

        // Get name of state
        String name = newState.getClass().getSimpleName();

        // Add name-ID pairing
        Globals.states.put(name, newID);

        // Add state
        Globals.sbg.addState(newState);
    }

    /**
     * Render state
     *
     * @param gc
     * @param game
     * @param g
     */
    @Override
    public void render(GameContainer gc, StateBasedGame game, Graphics g) {

        // Draw intro/logo background
        g.drawImage(introLogo, 0, 0);

        // Write 'LOADING' text
        int loadX = (Globals.screenW / 2) - 130;
        int loadY = Globals.screenH - 100;
        font.drawString(loadX, loadY, "LOADING");
    }

}
