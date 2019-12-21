package states.menu;

import java.util.ArrayList;

import main.Globals;
import states.base.InterfaceScreen;
import components.popups.StringWriter;
import components.buttons.Button;
import java.util.Timer;
import java.util.TimerTask;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.gui.AbstractComponent;
import states.special.Rift;

/**
 * Provides screen with buttons for accessing other screens/states
 *
 * @author David
 */
public class Menu extends InterfaceScreen {

    // Title line writer
    private StringWriter titleWriter;

    // Whether the play button has been updated
    private boolean playButtonUpdated;

    /**
     * Do custom initialization
     */
    @Override
    public void customInit() {

        // Create delay writer
        titleWriter = new StringWriter(Globals.gameTitle + " - The Game",
                Color.white, "gamefont-plain-70", true,
                200, 50, 80);

        // Add action to all buttons
        super.getButtonGrid().applyActions((AbstractComponent source) -> {

            // Process button using its label
            handleMenuButton(((Button) source).getLabel());

        });

        // Initialize as not changed
        playButtonUpdated = false;
    }

    /**
     * Handle menu button press using its label
     *
     * @param label
     */
    public void handleMenuButton(String label) {

        // Act based on label
        switch (label) {

            case "Play":

                // When 'Play' is clicked for the first time:
                // Block input
                Globals.isInputBlocked = true;

                // Enter Rift 
                Globals.changeState("Rift", true);

                // Set game as started
                Globals.isGameStarted = true;

                // Schedule ship moving up
                Timer shipMov = new Timer();
                shipMov.scheduleAtFixedRate(new TimerTask() {
                    @Override
                    public void run() {
                        Rift rift = (Rift) Globals.SBG.
                                getState(Globals.states.get("Rift"));
                        rift.moveShip("Up");
                    }
                }, 1, 40);

                // Schedule the movement stopping and the popup showing
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {

                        // Terminate timer
                        shipMov.cancel();

                        // Load popup
                        Globals.popStore.loadPopup("RiftIntro");

                        // Unblock input
                        Globals.isInputBlocked = false;
                    }
                }, 2369);

                break;

            case "Continue":

                // When 'Continue' is clicked:
                // If rift has been used
                if (Globals.isIntroRiftDone) {

                    // Go directly to play state
                    Globals.changeState("Play", true);
                } else {

                    // Else if rift has not been used,
                    // go back to finish it
                    Globals.changeState("Rift", true);
                }
                break;

            default:

                // For all other buttons,
                // transition to the state with the same name as the label
                Globals.changeState(label, true);
                break;
        }
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
        feats.add("gamefont-plain-50"); // FontString

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

        labels.add("Play");
        labels.add("Controls");
        labels.add("Settings");
        labels.add("Credits");
        labels.add("About");
        labels.add("Exit");

        return labels;
    }

    /**
     * Do custom updating
     */
    @Override
    public void customUpdate() {

        // Reset input offset
        Globals.agc.getInput().setOffset(0, 0);

        // If game has started and playButton has not been updated yet,
        // change "Play" to "Continue" once
        if (Globals.isGameStarted && !playButtonUpdated) {

            // Rename button label
            super.getButtonGrid().replaceButtonLabel("Play", "Continue");

            // Set play button as updated
            playButtonUpdated = true;
        }
    }

    /**
     * Do custom rendering
     *
     * @param g
     */
    @Override
    public void customRender(Graphics g) {

        // Draw title line
        titleWriter.drawText();

        // Draw version string in bottom left
        g.setColor(Color.white);
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
