package engine;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that stores all the components for this game object.
 */
public class GameObject {
    private String name;
    private List<Component> components;

    /**
     * Initializes the game object name and components list.
     * @param name
     */
    public GameObject(String name){
        this.name = name;
        this.components = new ArrayList<>();
    }

    /**
     * Gets the component from the list that matches the class.
     * @param componentClass class of the object you want
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
        // Assign the component's game object to this
        c.gameObject = this;
    }

    /**
     * Starts all the components in the class
     */
    public void start(){
        for(int i=0; i<components.size(); i++){
            components.get(i).start();
        }
    }
    /**
     * Updates all components of this game object.
     * @param dt differential time
     */
    public void update(float dt){
        for(int i=0; i<components.size(); i++){
            components.get(i).update(dt);
        }
    }


}
