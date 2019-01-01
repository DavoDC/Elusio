import java.net.*;
import java.io.*;
import java.awt.*;
import javax.swing.*;
import javax.imageio.ImageIO;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.AudioDevice;
import javazoom.jl.player.FactoryRegistry;
import javazoom.jl.player.advanced.AdvancedPlayer;

/**
 * This class is part of the "Alien Aztec Adventure" application.
 * 
 * Handles audiovisual elements
 *
 * @author David Charkey
 */
public class AV
{
    private AdvancedPlayer player;
    private boolean playing;
    private Thread playerThread; 

    public ImageIcon generateImageIcon(String type, String folder, String imagename)
    {
        int w = 0;
        int h = 0;

        if (type.contains("icon"))
        {
            w = h = 40;
        }
        else if (type.contains("loc"))
        {
            w = 400;
            h = 250;
        }
        else if (type.contains("elem"))
        {
            w = h = 50;
        }
        else if (type.contains("custom"))
        {
            w = Integer.parseInt(type.split("-")[1]);
            h = Integer.parseInt(type.split("-")[2]);
        }

        // Create url string
        String urlS = "/resources/images/" + folder + "/" + imagename + ".jpg";

        try 
        {

            // Get image
            URL url;
            url = getClass().getResource(urlS);
            Image image = ImageIO.read(url);

            // Resize
            image = image.getScaledInstance(w, h,  java.awt.Image.SCALE_SMOOTH);

            ImageIcon imageI = new ImageIcon(image);
            return imageI;
        } 
        catch (Exception e) 
        {            
            System.err.println(e.toString());
            System.err.println("URL was " + urlS);
        }

        return null;
    }
    
    

    public void showSplash()
    {
        JFrame splash = new JFrame();
        splash.setAlwaysOnTop(true);

        JButton imageB = new JButton();
        imageB.setIcon( generateImageIcon("custom-1000-700", "special", "splash") );
        imageB.removeMouseListener(imageB.getMouseListeners()[0]);
        splash.add(imageB);

        splash.setVisible(true);
        splash.setLocation( 190 ,0 );
        splash.setSize( 1000 , 700 );

        Game.events.sleep(3000);

        splash.setVisible(false);
    }

    /**
     * Start playing the given audio file.
     * The method returns once the playing has been started.
     * @param filename The file to be played.
     */
    public void startMusic()
    {
       Timer t = new Timer( (6*60*1000) + 5 , e -> playFile() );
       t.setInitialDelay(0);
       t.start();
    }

    private void playFile()
    {
        String audioFile = getRandomTrack();

        try 
        {
            InputStream is = new BufferedInputStream( getClass().getResourceAsStream(audioFile) );
            player = new AdvancedPlayer(is, FactoryRegistry.systemRegistry().createAudioDevice() );

            playerThread = new Thread() 
            {
                public void run()
                {
                    try 
                    {   
                        playing = true;
                        player.play(6*60*1000);
                    }
                    catch (Exception e)
                    {
                        reportMusicIssue(audioFile);
                    }
                };
            };

            playerThread.start();
        }
        catch (Exception e) 
        {
            reportMusicIssue(audioFile);
        }
    }
    
    private String getRandomTrack()
    {
        String output = "resources/audio/";
        
        double r = Math.random();
        
        if (r < 0.25) {  output += "dollars";}
        else if (r < 0.5 ) { output += "dwarfland";}
        else if (r < 0.75) { output += "obe";}
        else { output += "wildarms";}
        
        output += ".mp3";
        return output;
    }
    
    private void reportMusicIssue(String file)
    {
        System.out.println("Music issue. File was: " + file);
        stopMusic();
    }

    /**
     * Terminate the player, if there is one.
     */
    private void stopMusic()
    {
        player.stop(); 
        playing = false;
    }

    public void toggleMusic()
    {
        if (playing)
        {
            stopMusic();
        }
        else if (!playing)
        {
            startMusic();
        }
    }
}
