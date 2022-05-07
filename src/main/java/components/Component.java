package components;

import engine.GameObject;

/**
 * Abstract class for all game components.
 */
public abstract class Component {
    // ***ATTRIBUTES***
    public GameObject gameObject = null;        // GameObject it belongs to. Default to null.

    // ***METHODS***
    /**
     * Starts the component. Optional. Must be overridden.
     */
    public void start(){

    }

    /**
     * Updates the component. Must be implemented.
     * @param dt time differential
     */
    public abstract void update(float dt);
}
