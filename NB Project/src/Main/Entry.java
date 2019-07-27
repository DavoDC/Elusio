package main;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;
import states.Loading;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

/**
 * The entry class from which the game application starts
 *
 * @author David Charkey
 */
public class Entry extends StateBasedGame
{

    /**
     * Creates an entry point object
     */
    public Entry()
    {
        super(Globals.gameTitle);
    }

    /**
     * Entry method - Sets up the game-containing application
     *
     * @param args
     */
    public static void main(String[] args)
    {
        try
        {
            // Load OpenAL to fix sound
            fixSoundError();

            // Create AGC
            Globals.agc = new AppGameContainer(new Entry());

            // Retrieve and store local screen info
            Globals.screenW = Globals.agc.getScreenWidth();
            Globals.screenH = Globals.agc.getScreenHeight();

            // Adjust AGC
            Globals.agc.setDisplayMode(Globals.screenW, Globals.screenH, true);
            Globals.agc.setTargetFrameRate(60);
            Globals.agc.setAlwaysRender(true);
            Globals.agc.setSmoothDeltas(true);
            Globals.agc.setShowFPS(false);

            // Start AGC
            Globals.agc.start();

        }
        catch (SlickException e)
        {
            System.err.println("Main method in 'Entry' had an error");
        }
    }

    /**
     * Determines if OS is 64 and 32 and loads corresponding OpenAL DLL
     */
    private static void fixSoundError()
    {
        // Run CMD command
        ProcessBuilder builder = new ProcessBuilder(
                "cmd.exe", "/c", "wmic OS get OSArchitecture");
        builder.redirectErrorStream(true);
        Process p;

        // Process result
        String OSresult = "";
        try
        {
            p = builder.start();
            InputStream is = p.getInputStream();
            BufferedReader buffer = new BufferedReader(new InputStreamReader(is));
            OSresult = buffer.lines().collect(Collectors.joining("\n"));
        }
        catch (Exception ex)
        {
            System.err.println("Error in fixSound method");
        }

        // Load DLL based on OS found
        if (OSresult.contains("64"))
        {
            System.loadLibrary("OpenAL64");
        }
        else
        {
            System.loadLibrary("OpenAL32");
        }
    }

    /**
     * Add the first state to the game
     *
     * @param gc
     * @throws SlickException
     */
    @Override
    public void initStatesList(GameContainer gc) throws SlickException
    {
        addState(new Loading());
    }

}
