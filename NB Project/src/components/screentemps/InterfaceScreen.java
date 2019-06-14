package components.screentemps;

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
 *
 * @author David Charkey
 */
public abstract class InterfaceScreen extends BasicGameState
{

    // Resource folder
    private final String folder = Globals.root + "backgrounds/";

    // Background
    private BigImage bg;

    // Button Manager
    private ButtonGrid buttonGrid;

    // Font
    private TrueTypeFont font;

    // States where you can't go back
    private ArrayList<Integer> specialStates;

    @Override
    public abstract int getID();

    @Override
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException
    {
        // If backgrounds have not been loaded, load them
        if (Globals.backgrounds.size() != Globals.BG_COUNT)
        {
            // Add every background to the array        
            for (int i = 1; i <= Globals.BG_COUNT; i++)
            {

                String imgPath = folder + "bg" + i + ".png";
                BigImage curBG = new BigImage(imgPath);
                curBG = (BigImage) curBG.getScaledCopy(Globals.screenW, Globals.screenH);
                Globals.backgrounds.add(curBG);
            }
        }

        // Get random background
        Random rng = new Random();
        int ranIndex = rng.nextInt(Globals.BG_COUNT);
        bg = Globals.backgrounds.get(ranIndex);

        // Initialise buttons
        buttonGrid = new ButtonGrid(getButtonFeatures(), getButtonLabels());

        // Initialise image and replace cursor
        if (Globals.cursor == null) // Only do once
        {
            Globals.cursor = new Image(Globals.cursorRes);
            Globals.cursor = Globals.cursor.getScaledCopy(0.75f);
        }
        gc.setMouseCursor(Globals.cursor, 0, 0);

        // Initialise font
        font = FontServer.getFont("Segoe UI-Italic-22");

        // States where you can't go back
        specialStates = new ArrayList<>();
        specialStates.add(Globals.STATES.get("MAINMENU"));
        specialStates.add(Globals.STATES.get("EXIT"));
        specialStates.add(Globals.STATES.get("GAMEOVER"));

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

    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int delta)
    {
        customPreUpdate();

        // Get input
        Input input = gc.getInput();

        // Act on back button presses
        boolean escClicked = input.isKeyDown(Input.KEY_ESCAPE);
        boolean middleClicked = input.isMouseButtonDown(Input.MOUSE_RIGHT_BUTTON);
        if (escClicked || middleClicked)
        {
            // Unless there, go to main menu
            int curID = sbg.getCurrentStateID();
            int menuID = Globals.STATES.get("MAINMENU");
            if (curID != menuID)
            {
                // Transition to that state
                sbg.enterState(
                        menuID,
                        Globals.getLeave(),
                        Globals.getEnter()
                );
            }

        }
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException
    {
        // Draw background darkened
        if (isDarkened())
        {
            g.drawImage(bg, 0, 0, Color.darkGray);
        }
        else
        {
            g.drawImage(bg, 0, 0);
        }

        // Draw buttons
        buttonGrid.drawButtons(g);

        // On certain STATES, reveal back button 
        int curStateID = game.getCurrentStateID();
        if (!specialStates.contains(curStateID))
        {
            g.setColor(Color.white);
            font.drawString(10, Globals.screenH - 40, "Press ESC or Right Click to go back");
        }

        customPostRender(g);
    }

    /**
     * Add additional initialization tasks here
     */
    public void customPostInit()
    {
        // For overriding
    }

    /**
     * Do custom pre updating here
     */
    public void customPreUpdate()
    {
        // For overriding
    }

    /**
     * Do custom post rendering here
     */
    public void customPostRender(Graphics g)
    {
        // For overriding
    }

    /**
     * True = Background will be darkened
     */
    public abstract boolean isDarkened();

    /**
     * Access Button Grid
     *
     * @return
     */
    public ButtonGrid getButtonGrid()
    {
        return buttonGrid;
    }

}
