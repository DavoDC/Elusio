package components.popups;

import components.buttons.Button;
import components.modules.Map;
import components.servers.FontServer;
import main.Globals;
import org.newdawn.slick.Color;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.geom.Rectangle;

/**
 * Models interactive text boxes that come up during game play
 *
 * @author David
 */
public abstract class Popup {

    // The popup's name
    private String name;

    // Underlying button
    private final Button underB;

    // Text lines
    private final ListWriter textLW;

    // Instruction line
    private final StringWriter instSW;

    // Speaker information 
    private final Button spInfo;

    // True means popup can be seen
    private boolean visible;

    // True means popup has been shown
    private boolean shown;

    // True means it blocks input
    private boolean inputBlocker;

    /**
     * Create a popup
     *
     * @param name The popup's name
     * @param rawLines Lines for popup
     */
    public Popup(String name, String[] rawLines) {

        // Save name
        this.name = name;

        // Initialize button image
        Image undImg = null;
        try {
            undImg = new Image(Globals.getFP("popup"));
        } catch (SlickException e) {
            System.err.print("Image load error in Popup constructor");
        }

        // Initialize underlying button 
        int camYadj = 3 * Globals.tileSize;
        int camXadj = 2 * Globals.tileSize;
        int undRow = Map.convYtoRow(Globals.cam.getY() + camYadj);
        int undCol = Map.convertXtoCol(Globals.cam.getX() + camXadj);
        int undX = Map.convColtoX(undCol) + 20;
        int undY = Map.convRowtoY(undRow) - 40;
        int undW = (int) (Globals.screenW / 80 * Globals.tileSize);
        int undH = 2 * Globals.tileSize;
        Rectangle undRect = new Rectangle(undX, undY, undW, undH);
        TrueTypeFont undFont = FontServer.getFont("Candara-Bold-26");
        underB = new Button(undImg, undRect, undFont);
        underB.setLabel("");

        // Initialise text writer
        textLW = new ListWriter(rawLines, Color.black,
                "Candara-Bold-26",
                undX + 185, undY + 36, 20) {
            @Override
            public void doFinalAction() {

                // Popup has been shown
                shown = true;

                // Popup doesn't need to be visible
                visible = false;

                // If popup blocks input
                if (inputBlocker) {

                    // Re-enable input as popup has been shown
                    Globals.isInputBlocked = false;
                }

                // Do custom action
                doPostShowAction();
            }
        };

        // Initialise instruction DW
        int instX = undX + underB.getWidth() / 2 - 100;
        int instY = undY + underB.getHeight() - 48;
        instSW = new StringWriter("Click here to continue!",
                Color.black, "Corbel-Italic-22", true, instX, instY, 30);

        // Initialise speaker info
        Rectangle spRect = new Rectangle(undX + 55, undY + 28, 69, 69);
        TrueTypeFont spFont = FontServer.getFont("Candara-Bold-20");
        spInfo = new Button(getCurSpImg(), spRect, spFont);
        spInfo.setLabel(textLW.getCurSpeaker());
        spInfo.setTextOffsets(5f, spInfo.getHeight());
        spInfo.setTextColor(Color.black);

        // Add action when popup button is clicked
        underB.addListener((source) -> {

            // When popup is clicked:
            // Load next line
            textLW.requestNextLine();

            // Update speaker info
            spInfo.setLabel(textLW.getCurSpeaker());
            spInfo.setImage(getCurSpImg(), true);
        });

        // Popup is not visible or shown yet
        visible = false;
        shown = false;

        // Popup blocks input by default
        inputBlocker = true;
    }

    /**
     * Render the popup on the screen
     *
     * @param g
     */
    public void render(Graphics g) {

        // If not visible
        if (!visible) {

            // Do not render
            return;
        }

        // Draw button
        underB.drawFull(g);

        // Draw popup text
        textLW.drawText();

        // Draw instruction text
        instSW.drawText();

        // Draw speaker info
        spInfo.drawFull(g);
    }

    /**
     * Get current speaker's image
     *
     * @return
     */
    private Image getCurSpImg() {

        // Make holder
        Image spImg = null;

        // Get image name and path
        String spImgName = textLW.getCurSpeaker() + "Pic";
        String spImgPath = Globals.getFP(spImgName);

        // Get image
        try {
            spImg = new Image(spImgPath);

        } catch (SlickException e) {
            System.err.print("Speaker image load error");
        }

        // Return
        return spImg;
    }

    /**
     * Retrieve the name of this popup
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * Change visible status
     *
     * @param newStatus
     */
    public void setVisible(boolean newStatus) {
        visible = newStatus;
    }

    /**
     * Change whether the popup blocks input or not
     *
     * @param newValue
     */
    public void setInputBlocking(boolean newValue) {
        inputBlocker = newValue;
    }

    /**
     * Return true if the popup has been shown
     *
     * @return
     */
    public boolean hasBeenShown() {
        return shown;
    }

    /**
     * Return true if the popup blocks input while being shown
     *
     * @return
     */
    public boolean isInputBlocker() {
        return inputBlocker;
    }

    /**
     * Do the action that occurs once the popup has shown
     */
    public abstract void doPostShowAction();
}
