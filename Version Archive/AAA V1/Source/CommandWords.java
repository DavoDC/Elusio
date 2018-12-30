
/**
 * This class is part of the "Alien Aztec Adventure" application.
 * 
 * This class holds all command words known to the game.
 * It is used to recognise commands as they are typed in.
 *
 * @author David Charkey , Michael Kolling, David J. Barnes
 */

public class CommandWords
{
    // a constant array that holds all valid command words
    private static final String[] validCommands = 
        { "Go", "Look" , "Inspect" , "Grab" , "Drop",  "Items", "Construct" , "Cheat", "Quit"};

    /**
     * Check whether a given String is a valid command word. 
     * @return true if a given string is a valid command, false if it isn't.
     */
    public boolean isCommand(String aString)
    {

        for(int i = 0; i < validCommands.length; i++) 
        {
            String comparison = validCommands[i];
            comparison = comparison.toLowerCase();

            if(comparison.equals(aString))
            {
                return true;
            }
        }

        // if we get here, the string was not found in the commands
        return false;
    }
}
