package states.menu;

import java.util.ArrayList;

import base.Globals;
import states.base.InfoScreen;
import states.base.InterfaceScreen;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Provides game exit screen
 *
 * @author David
 */
public class Exit extends InterfaceScreen {

    // The absolute time it takes to exit
    private final long exitConstant = 3369 / 2;

    // The actual time of closing
    private long exitTime = 0;

    // Percentage closed
    private int percentage = 0;

    // Text
    private final String mainFontS = "Cambria-Bold-60";
    private final String closeFontS = "Calibri-Plain-20";
    private TrueTypeFont closeFont;
    private final int closeX = (int) InfoScreen.lineX + 400;

    /**
     * Do custom post initialization
     */
    @Override
    public void customInit() {
        closeFont = Globals.fontServer.getFont(closeFontS);
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
        feats.add(Globals.getFP("nothing")); // Image Location
        feats.add(300); // startXpos
        feats.add(125); // startYpos 
        feats.add(350); // width
        feats.add(80); // height
        feats.add(0); // XSpacing
        feats.add((int) Globals.screenH / 25); // YSpacing //always fits
        feats.add(1); // NumberofColumns
        feats.add(mainFontS); // FontString
        feats.add(false); // isSoundWanted

        return feats;
    }

    /**
     * Set button labels
     *
     * @return
     */
    @Override
    public ArrayList<String> getButtonLabels() {
        //Create AL
        ArrayList<String> text = new ArrayList<>();

        // Add to text
        text.add("THANKYOU");
        text.add("FOR");
        text.add("PLAYING");

        return text;
    }

    /**
     * Update variables
     *
     * @param gc
     * @param sbg
     * @param delta
     */
    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int delta) {

        // Initialise time of closing, if not already
        if (exitTime == 0) {
            exitTime = Globals.agc.getTime() + exitConstant;
        }

        // Get time right now
        long now = Globals.agc.getTime();

        // Calculate time left
        long timeLeft = exitTime - now;

        // Calculate percentage
        float perF = (float) timeLeft / (float) exitConstant;
        perF = Math.round(perF * 100);
        percentage = 100 - (int) perF;

        // Exit after time has elapsed or Exit immediately in IDE
        if (percentage >= 100 || Globals.inIDE) {
            Globals.agc.exit();
        }

    }

    /**
     * Do custom rendering
     *
     * @param g
     */
    @Override
    public void customRender(Graphics g) {

        // Calculate position of text
        int x = closeX;
        int y = Globals.screenH - 350;

        // Make closing strings
        String close1 = "The game will close shortly";
        String close2 = percentage + "% Closed";

        // Draw closing status
        g.setColor(Color.white);
        closeFont.drawString(x, y, close1);
        closeFont.drawString(x, y + 25, close2);
    }

    /**
     * Set darkened option
     *
     * @return
     */
    @Override
    public boolean isDarkened() {
        return true;
    }

}
