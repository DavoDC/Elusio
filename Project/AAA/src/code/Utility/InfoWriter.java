
package code.Utility;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.util.ResourceLoader;



/**
 * Makes rendering large amounts of text easier
 * 
 * Utilizes ButtonManger sneakily 
 * 
 * @author David
 */
public class InfoWriter 
{
    // Button Manager
    private ButtonManager bMan;
    
    // Font 
    private TrueTypeFont smallFont;
    
    // Header 
    private ArrayList<String> header;
    private float[] hParams;
    
    // Paragraph
    private ArrayList<String> paragraph;
    private float[] pParams;
    
    
    public InfoWriter()
    {
        // Create button manager
        bMan = new ButtonManager();
        
        // Load small font
        Font awtFont = new Font("Segoe UI", Font.PLAIN, 25);
        smallFont = new TrueTypeFont(awtFont, true);
        
        // Initialise ArrayLists
        header = new ArrayList<>();
        paragraph = new ArrayList<>();
        
        // Get needed info
        int screenW = code.MainGame.screenW;
        float startX = (screenW/2)-100;
        float width = screenW-300;
        
        // Initialise header parameters
        //            startXpos, startYpos, width, height, Xspace, Yspace, colNo
        hParams = new float[] {startX, 200f, width-300, 50f, 0f, 0f, 1f};
        
        // Initalise paragraph parameters

    }
    
    /**
     * First string will be the header
     */
    public void writeParagraph(Graphics g, ArrayList<String> text)
    {
      // Separate ArrayList into header and paragraph
      String curS = "";
      for (int i = 0; i < text.size(); i++)
      {
          curS = text.get(i);
          if (i == 0) { header.add(curS); }
          else { paragraph.add(curS); }
      }
      
      // Initialize and draw header
      bMan.createButtonGrid(hParams, header);
      bMan.drawButtonGrid(g, Color.transparent, Color.black);
      
      // Make button manager use small font
      
      // Initialise and draw paragraphs
      bMan.createButtonGrid(pParams, paragraph);
      bMan.drawButtonGrid(g, Color.transparent, Color.black);

        
    }
    
    

 
    

    
}
