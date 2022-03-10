package engine;

import java.util.ArrayList;
import java.util.List;

public abstract class Scene {
    // ***ATTRIBUTES***
    protected Camera camera;
    private boolean isRunning = false;
    protected List<GameObject> gameObjects = new ArrayList<>();     // List of all gameobjects in scene

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
        }
    }

    /**
     * Method to initialize the scene. Overridable but not necessary.
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
        }
        isRunning = true;
    }

    /**
     * Oblighatory child method for updating the scene.
     * @param dt time diferential
     */
    public abstract void update(float dt);
}
