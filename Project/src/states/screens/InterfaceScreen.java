package states.screens;

import java.util.ArrayList;
import java.util.Random;

import main.Globals;
import components.buttons.ButtonGrid;
import components.helpers.FontServer;

import org.newdawn.slick.BigImage;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Models a game state which is a user interface screen
 *
 * @author David
 */
public abstract class InterfaceScreen extends BasicGameState {

    // Resource folder
    private final String folder = Globals.root + "backgrounds/";

    // Background
    private BigImage bg;

    // Handles button group
    private ButtonGrid buttonGrid;

    // Font
    private TrueTypeFont font;

    // A list of states where you cannot go back
    private ArrayList<Integer> specialStates;

    /**
     * Return ID used to identify state
     *
     * @return ID
     */
    @Override
    public abstract int getID();

    /**
     * Initialize state
     *
     * @param gc
     * @param sbg
     * @throws SlickException
     */
    @Override
    public void init(GameContainer gc, StateBasedGame sbg)
            throws SlickException {

        // If backgrounds have not been loaded, load them
        if (Globals.backgrounds.size() != Globals.BG_COUNT) {

            // Add every background to the array        
            for (int i = 1; i <= Globals.BG_COUNT; i++) {

                String imgPath = folder + "bg" + i + ".png";
                BigImage curBG = new BigImage(imgPath);
                curBG = (BigImage) curBG.getScaledCopy(
                        Globals.screenW, Globals.screenH);
                Globals.backgrounds.add(curBG);
            }
        }

        // Get random background
        Random rng = new Random();
        int ranIndex = rng.nextInt(Globals.BG_COUNT);
        bg = Globals.backgrounds.get(ranIndex);

        // Initialise buttons
        buttonGrid = new ButtonGrid(getButtonFeatures(), getButtonLabels());

        // Initialise and adjust cursor image if not done already
        if (Globals.cursor == null) {
            Globals.cursor = new Image(Globals.cursorRes);
            Globals.cursor = Globals.cursor.getScaledCopy(0.75f);
        }

        // Apply cursor image
        gc.setMouseCursor(Globals.cursor, 0, 0);

        // Initialise font
        font = FontServer.getFont("Segoe UI-Italic-22");

        // States where you can't go back
        specialStates = new ArrayList<>();
        specialStates.add(Globals.STATES.get("MAINMENU"));
        specialStates.add(Globals.STATES.get("EXIT"));
        specialStates.add(Globals.STATES.get("GAMEOVER"));

        // Custom initialization
        customPostInit();
    }

    /**
     * Setup buttons here
     *
     * @return
     */
    public abstract ArrayList<Object> getButtonFeatures();

    /**
     * Setup button parameters
     *
     * @return
     */
    public abstract ArrayList<String> getButtonLabels();

    /**
     * Update internal variables
     *
     * @param gc
     * @param sbg
     * @param delta
     */
    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int delta) {

        // Do custom pre update
        customPreUpdate();

        // If you cannot go back on current state
        if (specialStates.contains(sbg.getCurrentStateID())) {

            // Do not process back button presses
            return;
        }

        // Act on back button presses
        // If ESC pressed or RIGHT MOUSE clicked
        Input input = gc.getInput();
        boolean escPress = input.isKeyDown(Input.KEY_ESCAPE);
        boolean riClick = input.isMouseButtonDown(Input.MOUSE_RIGHT_BUTTON);
        if (escPress || riClick) {

            // Get current state ID and back state ID
            int curID = sbg.getCurrentStateID();
            int backID = Globals.STATES.get(getBackState());

            // If not currently in back state
            if (curID != backID) {

                // Transition to back state
                sbg.enterState(
                        backID,
                        Globals.getLeave(),
                        Globals.getEnter()
                );
            }

        }
    }

    /**
     * Get the name of the state to go back to
     *
     * @return
     */
    public String getBackState() {
        return "MAINMENU";
    }

    /**
     * Handle graphics rendering
     *
     * @param container
     * @param game
     * @param g
     * @throws SlickException
     */
    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g)
            throws SlickException {

        // Draw background (darkened or not)
        if (isDarkened()) {
            g.drawImage(bg, 0, 0, Color.darkGray);
        } else {
            g.drawImage(bg, 0, 0);
        }

        // Draw buttons
        buttonGrid.drawButtons(g);

        // If not a special state, reveal back button 
        int curStateID = game.getCurrentStateID();
        if (!specialStates.contains(curStateID)) {
            g.setColor(Color.white);
            font.drawString(10, Globals.screenH - 40,
                    "Press ESC or Right Click to go back");
        }

        // Do custom post rendering
        customPostRender(g);
    }

    /**
     * Add additional initialization tasks here
     */
    public void customPostInit() {
        // For overriding
    }

    /**
     * Do custom pre updating here
     */
    public void customPreUpdate() {
        // For overriding
    }

    /**
     * Do custom post rendering here
     *
     * @param g
     */
    public void customPostRender(Graphics g) {
        // For overriding
    }

    /**
     * True = Background will be darkened
     *
     * @return
     */
    public abstract boolean isDarkened();

    /**
     * Access Button Grid
     *
     * @return
     */
    public ButtonGrid getButtonGrid() {
        return buttonGrid;
    }

}
