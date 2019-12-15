package states;

import java.util.ArrayList;

import main.Globals;
import states.screens.InterfaceScreen;
import components.structures.DelayWriter;
import components.buttons.Button;
import components.servers.FontServer;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.gui.AbstractComponent;

/**
 * Provides screen for accessing other screens/states
 *
 * @author David
 */
public class MainMenu extends InterfaceScreen {

    // Title fields
    private DelayWriter dw;
    private final String titleFontS = "gamefont-plain-70";
    private TrueTypeFont titleFont;

    // Menu fields
    private final String menuFont = "gamefont-plain-50";

    // Title string
    private final String titleS = Globals.gameTitle + " - The Game";

    // Periodic title reset
    private final long interval = 5369;
    private long timeElapsed;
    private long lastReset;

    // Whether the play button has been updated
    private boolean playButtonUpdated;

    /**
     * Return ID used to identify state
     *
     * @return ID
     */
    @Override
    public int getID() {
        return Globals.STATES.get("MAINMENU");
    }

    /**
     * Do custom initialization
     */
    @Override
    public void customInit() {

        // Create delay writer
        dw = new DelayWriter(80, Color.white);
        dw.setText(titleS);

        // Update last reset (of delay writer)
        lastReset = System.currentTimeMillis();

        // Initialize font
        titleFont = FontServer.getFont(titleFontS);

        // Add state transition action to all buttons
        super.getButtonGrid().applyActions((AbstractComponent source) -> {

            // Get label
            String label = ((Button) source).getLabel();

            // Figure out next state ID
            int newStateID = Globals.STATES.get(label);

            // Transition to that state
            Globals.SBG.enterState(
                    newStateID,
                    Globals.getLeave(),
                    Globals.getEnter());
        });

        // Initialize as not changed
        playButtonUpdated = false;
    }

    /**
     * Set button features
     *
     * @return
     */
    @Override
    public ArrayList<Object> getButtonFeatures() {

        // Create AL
        ArrayList<Object> feats = new ArrayList<>();

        // Add to AL
        feats.add(getButtonLabels().size()); // Number of buttons
        feats.add(Globals.getFP("button")); // Image Location
        feats.add(300); // StartXpos
        feats.add(150); // StartYpos 
        feats.add(350); // Width
        feats.add(55); // Height
        feats.add(0); // XSpacing
        feats.add((int) Globals.screenH / 25); // YSpacing
        feats.add(1); // Number of Columns
        feats.add(menuFont); // FontString

        return feats;
    }

    /**
     * Set button labels
     *
     * @return
     */
    @Override
    public ArrayList<String> getButtonLabels() {
        ArrayList<String> labels = new ArrayList<>();
        labels.add("PLAY");
        labels.add("CONTROLS");
        labels.add("SETTINGS");
        labels.add("CREDITS");
        labels.add("ABOUT");
        labels.add("EXIT");

        return labels;
    }

    /**
     * Do custom pre updating
     */
    @Override
    public void customUpdate() {

        // Reset input offset
        Globals.agc.getInput().setOffset(0, 0);

        // If paused and playButton has not been updated yet,
        // change "PLAY" to "CONTINUE"
        if (Globals.hasBeenPaused && !playButtonUpdated) {

            // Rename button label
            super.getButtonGrid().replaceButtonLabel("PLAY", "CONTINUE");

            // Get location of PLAY in state map
            int pos = Globals.STATES.get("PLAY");

            // Overwrite state map entry with CONTINUE
            Globals.STATES.put("CONTINUE", pos);

            // Set play button as updated
            playButtonUpdated = true;
        }

        // Update delay writer
        dw.update();

        // Reset delay writer periodically
        // Update time elapsed
        timeElapsed = System.currentTimeMillis() - lastReset;
        // If interval has elapsed
        if (timeElapsed >= interval) {

            // Reset delay writer
            dw.setText(titleS);

            // Update last reset
            lastReset = System.currentTimeMillis();
        }
    }

    /**
     * Do custom post rendering
     *
     * @param g
     */
    @Override
    public void customRender(Graphics g) {

        // Draw title through delay writer
        g.setColor(Color.white);
        dw.drawText(titleFont, 200, 50);

        // Draw version string in bottom left
        g.drawString("Version " + Globals.VERSION, 20, Globals.screenH - 25);
    }

    /**
     * Set darkened state
     *
     * @return
     */
    @Override
    public boolean isDarkened() {
        return true;
    }

}
