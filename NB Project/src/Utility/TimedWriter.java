package Utility;

/**
 * Helps writing text sequentially
 * @author CHARKEYD
 */
public class TimedWriter 
{
    // Text
    private String text;
   
    // End index of string 
    int endIndex = 0;
    
    // The time between character writes
    private long interval;
    
    // The time of adding
    private long addTime;
    

    public TimedWriter(String text, int interval)
    {
        this.text = text;
        this.interval = interval;
        addTime = 0;
    }
    
    
    public void update()
    {
        // If the last index hasn't been reached
        if (endIndex != text.length())
        {
            // When addTime hits 0
            if (addTime == 0)
            {
                // Initialise addTime to interval
                addTime = interval;

                // Add to index
                endIndex++;
            }

            // Subtract to simulate time passing
            addTime -= 10;
        }
        
    }
    
    /*
    * Get a part of the string
    * The part gets longer over time
    */
    public String getText()
    {
        // Return part of the string
        return text.substring(0, endIndex);
        
    }
    
}
