package util;

/**
 * Class that returns system time and lifetime of program.
 */
public class Time {
    // Time when the app started
    public static float timeStarted = System.nanoTime();

    /**
     * Get the time elapsed since the first call to the class
     * @return time in seconds (in float format)
     */
    public static float getTime(){
        return (float)((System.nanoTime()-timeStarted)* 1E-9);
    }
}
