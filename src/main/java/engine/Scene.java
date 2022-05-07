package engine;

import renderer.Renderer;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstraction of the Scene object. Stores all the game objects of the scene.
 */
public abstract class Scene {
    // ***ATTRIBUTES***
    protected Renderer renderer = new Renderer();
    protected Camera camera;
    private boolean isRunning = false;
    protected List<GameObject> gameObjects = new ArrayList<>();     // List of all game objects in scene

    // ***CONSTRUCTOR***
    public Scene(){
        
    }

    // ***METHODS***
    /**
     * Adds the game object to the list in the scene.
     * @param go game object to add
     */
    public void addGameObjectToScene(GameObject go){
        if(!isRunning){
            gameObjects.add(go);
        }else{
            gameObjects.add(go);
            go.start();
            this.renderer.add(go);
        }
    }

    /**
     * Method to initialize the scene. Optional. Must be overridden.
     */
    public void init(){
        
    }

    /**
     * Method to start all game objects and run scene.
     */
    public void start(){
        // Iterate over all GOs and start them.
        for(GameObject go : gameObjects){
            go.start();
            this.renderer.add(go);
        }
        isRunning = true;
    }

    /**
     * Mandatory method for updating the scene.
     * @param dt time differential
     */
    public abstract void update(float dt);

    public Camera camera(){
        return this.camera;
    }
}
