package engine;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

/**
 * Singleton method. Only one instance of the object in the game.
 * Class that listens and interprets user mouse movements and buttons.
 */
public class MouseListener {
    // ***ATTRIBUTES***
    private double scrollX, scrollY;
    private double xPos, yPos, lastX, lastY;
    private boolean mouseButtonPress[] = new boolean[3];    // May be greater depending on the mouse
    private boolean isDragging;

    private static MouseListener instance;                  //  Singleton class variable
    
    
    // ***CONSTRUCTOR***
    private MouseListener(){
        // Calibrate everything to 0
        this.scrollX = 0.0;
        this.scrollY = 0.0;
        this.xPos = 0.0;
        this.yPos = 0.0;
        this.lastX = 0.0;
        this.lastY = 0.0;
    }
    
    // ***METHODS***
    /**
     * Returns the static mouse listener object. If it doesn't exist, it will be created.
     * @return mouse listener
     */
    public static MouseListener get(){
        if(MouseListener.instance==null) {
            MouseListener.instance = new MouseListener();
        }
        return MouseListener.instance;
    }


    /**
     *  Mouse position listener callback function for use in "glfwSetCursorPosCallback()".
     *  Sets the position of the mouse and the dragging functionality.
     * @param window
     * @param xpos
     * @param ypos
     */
    public static void mousePosCallback(long window, double xpos, double ypos){
        // Set current position to las position
        get().lastX = get().xPos;
        get().lastY = get().yPos;
        
        // Set current position
        get().xPos = xpos;
        get().yPos = ypos;
        
        // dragging will occur when either mouse button is pressed
        // and mouse is moving (ie. this function is called)
        get().isDragging = get().mouseButtonPress[0]||get().mouseButtonPress[1]||get().mouseButtonPress[2];
    }

    /**
     * Mouse buttons listener callback function for use in "glfwSetMouseButtonCallback()".
     * Sets/resets the mouse buttons
     * @param window
     * @param button
     * @param action
     * @param mods
     */
    public static void mouseButtonCallback(long window, int button, int action, int mods){
        if(action == GLFW_PRESS){
            if(button < get().mouseButtonPress.length){
                get().mouseButtonPress[button] = true;
            }
        }else if(action == GLFW_RELEASE){
            if(button < get().mouseButtonPress.length){
                get().mouseButtonPress[button] = false;
                get().isDragging = false;
            }
        }
    }

    /**
     * Mouse scroll listener callback function for use in "glfwSetScrollCallback()".
     * Sets the position of the scroll.
     * @param window
     * @param xOffset
     * @param yOffset
     */
    public static void mouseScrollCallback(long window, double xOffset, double yOffset){
        get().scrollX = xOffset;
        get().scrollY = yOffset;
    }

    /**
     * Resets mouse current position and updates its previous position.
     */
    public static void endFrame(){
        get().scrollX = 0;
        get().scrollY = 0;
        get().lastX = get().xPos;
        get().lastY = get().yPos;
    }
    
    // mouse position getters
    public static float getX(){
        return (float)get().xPos;
    }
    public static float getY(){
        return (float)get().yPos;
    }
    public static float getDX(){
        return (float)(get().lastX-get().xPos);
    }
    public static float getDY(){
        return (float)(get().lastY-get().yPos);
    }
    
    // mouse scroll getters
    public static float getScrollX(){
        return (float)get().scrollX;
    }
    public static float getScrollY(){
        return (float)get().scrollY;
    }
    
    // mouse button action getters
    public static boolean isDragging(){
        return get().isDragging;
    }
    public static boolean mouseButtonDown(int button){
        if(button < get().mouseButtonPress.length){
            return get().mouseButtonPress[button];
        }else{
            return false;
        }
    }
}
