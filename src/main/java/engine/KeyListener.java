package engine;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

public class KeyListener {
    // ***ATTRIBUTES***
    private boolean keyPressed[] = new boolean[350];
    //  only one listener object
    private static KeyListener instance;
    
    
    // ***CONSTRUCTOR***
    private KeyListener(){
        
    }
    
    // ***METHODS***
    //  get the listener object
    public static KeyListener get(){
        if(KeyListener.instance == null)
            KeyListener.instance = new KeyListener();
        
        return KeyListener.instance;
        
    }
    
    // key callback function
    public static void keyCallback(long window, int key, int scancode, int action, int mods){
        if(action == GLFW_PRESS){
            get().keyPressed[key] = true;
        }else if(action == GLFW_RELEASE){
            get().keyPressed[key] = false;
        }
    }
    
    // key pressed getter
    public static boolean isKeyPressed(int key){
        return get().keyPressed[key];
    }
}
