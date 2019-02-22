package Components.Buttons;

import Main.Globals;
import Components.Helpers.FontServer;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.GUIContext;
import org.newdawn.slick.gui.MouseOverArea;

/**
 * Models a button
 *
 * @author David Charkey
 */
public class Button extends MouseOverArea
{

    // Basic components
    private Image img;
    private Rectangle shape;
    private String label;
    private TrueTypeFont font;

    // Text position
    private float textXoffset;
    private float textYoffset;

    // Alert status
    private boolean alertOn;

    /**
     * Default constructor - Don't use!
     *
     * @param container
     * @param image
     * @param shape
     */
    public Button(GUIContext container, Image image, Shape shape)
    {
        super(container, image, shape);
        throw new IllegalArgumentException("Wrong constructor");
    }

    /**
     * Refined constructor
     *
     * @param image
     * @param shape
     * @param font
     */
    public Button(Image image, Shape shape, TrueTypeFont font)
    {
        super(Globals.agc, image, shape);
        this.img = image;
        this.shape = (Rectangle) shape;
        label = "label";
        this.font = font;

        textXoffset = shape.getWidth() / 5;
        textYoffset = -(shape.getHeight() / 20);

        alertOn = false;

        // Turn off alert after clicks
        super.addListener(
                (AbstractComponent source) ->
        {
            alertOn = false;
        }
        );
    }

    /**
     * Change the position of the button
     *
     * @param x
     * @param y
     */
    private void setPosition(float x, float y)
    {
        // Change mouseoverarea
        super.setLocation(x, y);

        // Change shape
        shape.setLocation(x, y);
    }

    /**
     * Change button dimensions
     *
     * @param w
     * @param h
     */
    public void setDimensions(int w, int h)
    {
        // Change image
        img = img.getScaledCopy(w, h);

        // Change shape
        shape.setSize(w, h);
    }

    /**
     * Change position and dimensions
     *
     * @param x
     * @param y
     * @param w
     * @param h
     */
    public void setBounds(int x, int y, int w, int h)
    {
        setPosition(x, y);
        setDimensions(w, h);
        updateImage();
    }

    /**
     * Change the label of the button
     *
     * @param label
     */
    public void setLabel(String label)
    {
        this.label = label;
    }

    /**
     * Retrieve the label of a button
     *
     * @return
     */
    public String getLabel()
    {
        return label;
    }

    /**
     * Draw the button fully
     *
     * @param g
     */
    public void drawFull(Graphics g)
    {
        drawImage(g);
        drawText(g);
        drawAlert(g);
    }

    /**
     * Draw the button, shifted
     *
     * @param b
     * @param g
     * @param sX
     * @param sY
     */
    public void drawShifted(Graphics g, int sX, int sY)
    {
        setX(getX() + sX);
        setY(getY() + sY);
        drawFull(g);
    }

    /**
     * Draw the image only
     */
    public void drawImage(Graphics g)
    {
        // Draw image and MouseOverArea
        super.render(Globals.agc, g);
    }

    /**
     * Draw the text only Action listener not included
     *
     * @param g
     */
    public void drawText(Graphics g)
    {
        // Calculate text position
        float textX = shape.getX() + textXoffset;
        float textY = shape.getY() + textYoffset;

        // Draw label using font
        font.drawString(textX, textY, label);
    }

    /**
     * Change where text is drawn Relative to top left hand corner of rectangle
     *
     * @param xOff
     * @param yOff
     */
    public void changeTextOffset(float xOff, float yOff)
    {
        textXoffset = xOff;
        textYoffset = yOff;
    }

    /**
     * Set the font of the button
     *
     * @param fontS
     */
    public void setFont(String fontS)
    {
        TrueTypeFont newFont = FontServer.getFont(fontS);
        this.font = newFont;
    }

    /**
     * Set the image
     *
     * @param resLoc
     */
    public void setImageLoc(String resLoc)
    {
        // Load new image
        Image newImg = null;
        try
        {
            newImg = new Image(resLoc);
        }
        catch (SlickException e)
        {
            System.err.println("Image loading failed");
        }

        // Adjust new image
        int oldW = img.getWidth();
        int oldH = img.getHeight();
        newImg = newImg.getScaledCopy(oldW, oldH);

        // Replace old image
        img = newImg;

        // Update image
        updateImage();

    }

    private void updateImage()
    {
        setNormalImage(img);
        setMouseOverImage(img);
        setMouseDownImage(img);
    }

    /**
     * Draw a red exclamation mark near the button
     *
     * @param g
     */
    private void drawAlert(Graphics g)
    {
        // Only continue if alert is on
        if (!alertOn)
        {
            return;
        }

        // Load image if not already done so
        if (Globals.alertMark == null)
        {
            try
            {
                Globals.alertMark = new Image(Globals.alertRes);
                int newSide = (int) shape.getHeight() / 2;
                Globals.alertMark = Globals.alertMark.getScaledCopy(newSide, newSide);
            }
            catch (SlickException e)
            {
            }
        }

        // Calculate position of alert
        int imageW = Globals.alertMark.getWidth();
        float imgX = shape.getX() + imageW - imageW / 2 + 5;
        float imgY = shape.getY() + shape.getHeight() + 5;

        // Add jiggle factor
        if (Math.random() < 0.15)
        {
            //imgX += Math.random()*5;
            imgY += Math.random() * 9;
        }

        // Draw alert
        g.drawImage(Globals.alertMark, imgX, imgY);
    }

    /**
     * Switch on the alert
     */
    public void activateAlert()
    {
        alertOn = true;
    }

}
