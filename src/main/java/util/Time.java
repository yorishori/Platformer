package util;


public class Time {
    // Time when the app started
    public static float timeStarted = System.nanoTime();
    
    // get the time elpased in seconds since the game started
    public static float getTime(){
        return (float)((System.nanoTime()-timeStarted)* 1E-9);
    }
}
