package engine;

import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;
import util.Time;

/**
 * Singleton method. Only one instance of the object in the game.
 *
 */
public class Window {
    // ***ATTRIBUTES***
    private int width, height;
    private String title;
    public float r,g,b,a;
    
    private long glfwWindow;
    
    private static Scene currentScene;
    
    //  only one window object
    private static Window window = null;
    
    
    // ***CONSTRUCTOR***

    /**
     * Private constructor. Run only once.
     */
    private Window(){
        this.width = 1280;
        this.height = 720;
        
        this.title = "platformer";
        r = 1;
        g = 1;
        b = 1;
        a = 1;
    }
    
    
    // ***METHODS***

    /**
     * Method to change game scenes.
     * @param newScene number of the scene to change to
     */
    public static void changeScene(int newScene){
        switch(newScene){
            case 0:
                currentScene = new LevelEditorScene();
                currentScene.init();
                currentScene.start();
                break;
            case 1:
                currentScene = new LevelScene();
                currentScene.init();
                currentScene.start();
                break;
            default:
                assert false: "Unknown scene '"+newScene+"'";
                break;
        }
    }

    /**
     * Get the Window object. Create it if it doesn't exist.
     * @return
     */
    public static Window get(){
        // Create a window object if it's not created(only in first call of function)
        if(Window.window == null){
            Window.window = new Window();
        }
        
        // Return the static window object
        return Window.window;
    }

    /**
     * Run the window object; initialize and loop.
     */
    public void run(){
        System.out.println("Java Libraries v."+Version.getVersion()+"!");
        
        inti();
        loop();
        
        // Free memory
        glfwFreeCallbacks(glfwWindow);
        glfwDestroyWindow(glfwWindow);
        
        // Terminate GLFW and the free error callback
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    /**
     * Initialize the OpenGL libraries and create the window.
     */
    public void inti(){
        // Setup an error callback
        GLFWErrorCallback.createPrint(System.err).set();
        
        // Initialize GLFW
        if(!glfwInit()){
            throw new IllegalStateException("Unable to initialize GLFW");
        }
        
        // Configure GLFW
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
        glfwWindowHint(GLFW_MAXIMIZED, GLFW_TRUE);
        
        // Create the window
        glfwWindow = glfwCreateWindow(this.width, this.height, this.title, NULL, NULL);
        if(glfwWindow == NULL){
            throw new IllegalStateException("Failed to create GLFW window.");
        }
        
        // Set mouse callback functions
        glfwSetCursorPosCallback(glfwWindow, MouseListener::mousePosCallback);
        glfwSetMouseButtonCallback(glfwWindow, MouseListener::mouseButtonCallback);
        glfwSetScrollCallback(glfwWindow, MouseListener::mouseScrollCallback);
        
        // Set keyboard callback functions
        glfwSetKeyCallback(glfwWindow, KeyListener::keyCallback);
        
        // Make the OpenGL context current
        glfwMakeContextCurrent(glfwWindow);
        
        // Enable V-Sync
        glfwSwapInterval(1);
        
        // Make the window visible
        glfwShowWindow(glfwWindow);
        
        // This line is critical for LWJGL's interoperation with GLFW's
        // OpenGL context, or any context that is managed externally.
        // LWJGL detects the context that is current in the current thread,
        // creates the GLCapabilities instance and makes the OpenGL
        // bindings available for use.
        GL.createCapabilities();
        
        Window.changeScene(0);
    }

    /**
     * Loop to change frames of the game and update screen.
     */
    public void loop(){
        float beginTime = Time.getTime();
        float endTime;
        float dt = -1.0f;

        while(!glfwWindowShouldClose(glfwWindow)){
            // Poll events
            glfwPollEvents();
            
            // Set RGBA to the color buffer
            glClearColor(r, g, b, a);
            // Flush the color buffer to screen
            glClear(GL_COLOR_BUFFER_BIT);

            if(dt >= 0){
                currentScene.update(dt);
            }
            glfwSwapBuffers(glfwWindow);
            
            // Time it took the loop to execute
            endTime = Time.getTime();
            dt = endTime - beginTime;
            beginTime = endTime;
        }
    }
}
