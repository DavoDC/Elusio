package States;

import Main.Globals;
import Components.Helpers.FontServer;
import org.newdawn.slick.Color;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.*;

/**
 *
 * @author David
 */
public class Loading extends BasicGameState
{

    // Background
    private Image introLogo;

    // Font
    private TrueTypeFont font;

    // Time
    long startRef = Globals.agc.getTime();
    long introTime = 3669;

    /**
     * Used to identify state
     * Used to switch to state
     * @return 
     */
    @Override
    public int getID()
    {
        return 0;
    }

    /**
     * This is only called when the game starts Used to load resources Used to
     * initialise the game state.
     * @param gc
     * @param game
     * @throws org.newdawn.slick.SlickException
     */
    @Override
    public void init(GameContainer gc, StateBasedGame game) throws SlickException
    {
        try
        {
            // Initialise and adjust bg
            introLogo = new Image("res/misc/intro.png");
            introLogo = introLogo.getScaledCopy(Globals.screenW, Globals.screenH);

            // Initialise font
            font = FontServer.getFont("gamefont-plain-75");

            // Initialise SBG
            Globals.SBG = game;

            // Initialise AGC
            Globals.agc.setDefaultFont(FontServer.getFont("Segoe UI-Plain-16"));

        }
        catch (SlickException ex)
        {
            System.err.println("Loading init issue");
        }

    }

    /**
     * The method is called each game loop to cause your game to update it's
     * logic. This is where you should make objects move. This is also where you
     * should check input and change the state of the game.
     *
     * @param gc Holds the game
     * @param sbg
     * @param delta Amount of time since last update
     * @throws org.newdawn.slick.SlickException
     */
    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException
    {
        // Add all STATES
        // Loading state is state 0
        // IDs are determined automatically

        Globals.STATES.put("ABOUT", Globals.STATES.size() + 1);
        sbg.addState(new About());

        Globals.STATES.put("CONTROLS", Globals.STATES.size() + 1);
        sbg.addState(new Controls());

        Globals.STATES.put("CREDITS", Globals.STATES.size() + 1);
        sbg.addState(new Credits());

        Globals.STATES.put("EXIT", Globals.STATES.size() + 1);
        sbg.addState(new Exit());

        Globals.STATES.put("GAMEOVER", Globals.STATES.size() + 1);
        sbg.addState(new GameOver());

        // LOADING already in
        
        Globals.STATES.put("MAINMENU", Globals.STATES.size() + 1);
        sbg.addState(new MainMenu());

        Globals.STATES.put("PLAY", Globals.STATES.size() + 1);
        sbg.addState(new Play());

        Globals.STATES.put("SETTINGS", Globals.STATES.size() + 1);
        sbg.addState(new Settings());

        // Load resources
        sbg.init(gc);

        // Wait to ensure loading screen is displayed 
        if (Globals.agc.getTime() > startRef + introTime)
        {
            // Enter main menu
            sbg.enterState(Globals.STATES.get("MAINMENU"),
                    new FadeOutTransition(Color.black, 2000), //leave
                    new FadeInTransition(Color.black, 10) //enter
            );
        }

    }

    /**
     * This method should be used to draw to the screen. All of your game's
     * rendering should take place in this method (or via calls) It is called
     * constantly. Items are constantly redrawn
     *
     * @param gc
     * @param game
     * @param g
     */
    @Override
    public void render(GameContainer gc, StateBasedGame game, Graphics g)
    {
        // Draw intro/logo background
        g.drawImage(introLogo, 0, 0);

        // Write loading line
        int Xpos = (Globals.screenW / 2) - 150;
        int Ypos = Globals.screenH - 100;
        font.drawString(Xpos, Ypos, "LOADING");
    }

}
