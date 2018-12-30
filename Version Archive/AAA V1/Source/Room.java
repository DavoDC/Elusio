import java.util.HashMap; 
import java.util.Set;
import java.util.ArrayList;

/**
 * This class is part of the "Alien Aztec Adventure" application.
 * 
 * A "Room" represents one location in the scenery of the game.  
 * It is connected to other rooms via exits.  
 * For each direction, the room stores a reference to the neighboring room, 
 * or null if there is no exit in that direction.
 * 
 * 
 * @author David Charkey , Michael Kolling, David J. Barnes, 
 */
public class Room extends Entity
{
    private String description;
    private HashMap<String, Room> exits;
    private String event;
    private String imageName;

    /**
     * Create a room 
     */
    public Room() 
    { 
        description = null;
        exits = new HashMap<>();
        event = null;
    }

    /**
     * Add a description to a room
     */
    public void addDesc(String desc)
    {
        description = desc;
    }
    
    /**
     * Return a description of the room, as isa
     */
    public String getSimpleDesc()
    {
        return description;
    }

    /**
     * Return a detailed, processed description of the room
     */
    public String getRoomDesc()
    {
        //Add location
        String desc = "Current Location = " + description + "\n";

        // Add item status
        desc += getISS() + "\n";
        
        // Add exits
        desc += "Exits = ";
        for (String exit : getExitArray())
        {
            if (exit != null) 
            {
                desc += exit;
            }
        }

        return desc;
    }
    
    /**
     * Add exits to this room
     */
    public void addExit(String direction, Room neighbor)
    {
        exits.put(direction, neighbor);
    }

    /**
     * Return the room that is reached if we go from this room in direction "direction". 
     * If there is no room in that direction, return null.
     * @param direction The exit's direction.
     * @return The room in the given direction.
     */
    public Room getExit(String direction) 
    {
        return exits.get(direction);
    }

    /**
     * Return a array of string holding one string for each of the room’s exits
     * Each string will have correct capitalization
     * 
     * @return An array of strings holding the 
     */
    public String[] getExitArray()
    {      
        String[] exitA = new String[6];

        // Make a set of strings , and store all the keys in there
        Set<String> setofexits = exits.keySet();

        // for every exitstring in the set, add it to the string
        int counter = 0;
        for (String exitstring : setofexits)
        {
            exitstring = capitalize(exitstring,1);

            if (exitstring.startsWith("Toward"))
            {
                exitstring = capitalize(exitstring,7);
            }
            else if (exitstring.startsWith("To") )
            {
                exitstring = capitalize(exitstring,3);
            }
            else if (exitstring.contains("Into"))
            {
                exitstring = capitalize(exitstring,5);
            }

            exitA[counter] = (" " + exitstring + " ");
            counter++;
        }

        return exitA;
    }

    /**
     * Capitalizes a character at a certain position in a string
     */
    private String capitalize(String input, int pos)
    {
        pos = pos - 1;
        String output = "";

        output += input.substring(0, pos);
        output += input.substring(pos, pos + 1).toUpperCase();
        output += input.substring(pos+1);

        return output;
    }

    /**
     * Return the event of this room, as is
     */
    public String getEvent()
    {
        return event;
    }

    public void addEvent(String event)
    {
        this.event = event;
    }
    
    /**
     * Removes a room's event
     */
    public void eventFinished()
    {
        event = null;
    }

    public void setImage(String input)
    {
        imageName = input;
    }
    
    public String getImage()
    {
        return imageName;
    }
}
