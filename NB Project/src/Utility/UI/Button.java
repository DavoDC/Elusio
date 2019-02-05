package Utility.UI;

import Main.Globals;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.gui.ComponentListener;
import org.newdawn.slick.gui.GUIContext;
import org.newdawn.slick.gui.MouseOverArea;

/**
 *
 * @author David Charkey
 */
public class Button extends MouseOverArea
{

    // Fields
    private Image img;
    private Rectangle shape;
    private String label;
    private TrueTypeFont font;

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
     * Draw the text only
     *
     * @param g
     */
    public void drawText(Graphics g)
    {
        // Calculate text position
        float textX = shape.getX() + shape.getWidth() / 5;
        float textY = shape.getY() - shape.getHeight() / 20;

        // Draw label using font
        font.drawString(textX, textY, label);
    }

    /**
     * Add the action that occurs upon click
     *
     * @param cl
     */
    public void addAction(ComponentListener cl)
    {
        addListener(cl);
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
        } catch (SlickException e)
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

}
