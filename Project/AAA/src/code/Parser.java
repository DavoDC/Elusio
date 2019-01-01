import java.util.Scanner;

/**
 * This class is part of the "Alien Aztec Adventure" application.
 * 
 * This parser reads user input and tries to interpret it as an "Adventure" command
 * Every time it is called it reads a line from the terminal and tries to interpret the line as a two word command. 
 * It returns the command as an object of class Command.
 *
 * The parser has a set of known command words. It checks user input against the known commands, 
 * and if the input is not one of the known commands, it returns a command object that is marked as an unknown command.
 * 
 * @author David Charkey , Michael Kolling, David J. Barnes
 */
public class Parser 
{
    private CommandWords commands;  // holds all valid command words
    private Scanner reader;         // source of command input

    /**
     * Create a parser to read from the terminal window.
     */
    public Parser() 
    {
        commands = new CommandWords();
        reader = new Scanner(System.in);
    }

    /**
     * @return The next command from the user.
     */
    public Command getCommand() 
    {
        String inputLine;   // will hold the full input line
        String word1 = null;
        String word2 = null;

        System.out.print("> ");     // print prompt

        inputLine = reader.nextLine();

        // Find up to two words on the line.
        Scanner tokenizer = new Scanner(inputLine);
        if(tokenizer.hasNext()) {
            word1 = tokenizer.next();  // get first word
            word1 = word1.toLowerCase();
            if(tokenizer.hasNext()) {
                word2 = tokenizer.next();      // get second word
                word1 = word1.toLowerCase();
                // note: we just ignore the rest of the input line.
            }
        }

        // Now check whether this word is known. If so, create a command
        // with it. If not, create a "null" command (for unknown command).
        if(commands.isCommand(word1)) {
            return new Command(word1, word2);
        }
        else {
            return new Command(null, word2); 
        }
    }

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    public void processCommand(Command command) 
    {
        if(command.isUnknown()) {
            System.out.println("\n" + "I don't know what you mean..." + "\n" );
            return;
        }

        try 
        {
            switch(command.getCommandWord())
            {
                case "go" : Game.actions.goRoom(command);
                break;

                case "look" : Game.actions.look();
                break;

                case "inspect" : Game.actions.inspect();
                break;

                case "grab" : Game.actions.grab(command);
                break;

                case "drop" : Game.actions.drop(command);
                break;

                case "items" : Game.actions.items();
                break;

                case "construct" : Game.actions.construct();
                break;

                case "cheat" : Game.actions.cheat(command);
                break;

                case "quit" : Game.events.finishGame();
                break;
            }
        }
        catch (Exception e)
        {
            System.out.println("Error encountered");
            System.out.println( e.toString() );
        }
    }
}
