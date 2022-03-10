package engine;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

public class KeyListener {
    // ***ATTRIBUTES***
    private boolean keyPressed[] = new boolean[350];    //  Array representing all keys (pressed or not pressed)
    private static KeyListener instance;                //  Singleton class variable
    
    
    // ***CONSTRUCTOR***
    private KeyListener(){
        
    }
    
    // ***METHODS***

    /**
     * Returns the static key listener object. If it doesn't exist, it will be created.
     * @return key listener
     */
    public static KeyListener get(){
        if(KeyListener.instance == null) {
            KeyListener.instance = new KeyListener();
        }
        return KeyListener.instance;
    }

    /**
     * Key listener callback function for use in "glfwSetKeyCallback()"
     * @param window
     * @param key
     * @param scancode
     * @param action
     * @param mods
     */
    public static void keyCallback(long window, int key, int scancode, int action, int mods){
        // If a key is being pressed, set that key in the array to true
        if(action == GLFW_PRESS){
            get().keyPressed[key] = true;
        // If a key is being released, set that key to false
        }else if(action == GLFW_RELEASE){
            get().keyPressed[key] = false;
        }
    }

    /**
     * Returns true if the key passed is being pressed.
     * @param key
     * @return
     */
    public static boolean isKeyPressed(int key){
        return get().keyPressed[key];
    }
}
