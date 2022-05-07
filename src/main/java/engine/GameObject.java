package engine;

import components.Component;
import org.joml.Vector2f;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for the Game Object: middleman to initialize,
 * start and point to all components in the game.
 */
public class GameObject {
    // ***ATRIBUTES***
    private String name;
    private List<Component> components;
    public Transform transform;

    // ***CONSTRUCTORS***
    /**
     * Initializes the game object name, components list, and transform.
     * @param name name of the object
     */
    public GameObject(String name){
        this.name = name;
        this.components = new ArrayList<>();
        this.transform = new Transform();
    }

    /**
     * Initializes the game object name, components list, and transform.
     * @param name name of the object
     * @param transform position and scale of the object
     */
    public GameObject(String name, Transform transform){
        this.name = name;
        this.components = new ArrayList<>();
        this.transform = transform;
    }


    // ***METHODS***
    /**
     * Gets the component from the list that matches the class.
     * @param componentClass class of the object you want to get
     * @param <T> generic class abstraction
     * @return the object with the specified class, null if not found
     */
    public <T extends Component> T getComponent(Class<T> componentClass){
        for(Component c: components){
            if(componentClass.isAssignableFrom(c.getClass())){
                try {
                    return componentClass.cast(c);
                }catch(ClassCastException e){
                    e.printStackTrace();
                    assert false: "Error:  Casting component.";
                }
            }
        }

        return null;
    }

    /**
     * Removes the component from the list.
     * @param componentClass class of the component to be removed
     * @param <T> generic class abstraction
     */
    public <T extends Component> void removeComponent(Class<T> componentClass){
        for (int i=0; i < components.size(); i++){
            Component c = components.get(i);
            if(componentClass.isAssignableFrom(c.getClass())){
                components.remove(i);
                return;
            }
        }
    }

    /**
     * Adds a component to the list.
     * @param c component to be added
     */
    public void addComponent(Component c){
        this.components.add(c);
        // Assign the component's game object
        c.gameObject = this;
    }

    /**
     * Starts all the components of the game object
     */
    public void start(){
        for(int i=0; i<components.size(); i++){
            components.get(i).start();
        }
    }
    /**
     * Updates all components of the game object.
     * @param dt differential time
     */
    public void update(float dt){
        for(int i=0; i<components.size(); i++){
            components.get(i).update(dt);
        }
    }


}
