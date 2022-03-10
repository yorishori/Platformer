package engine;

public abstract class Component {
    // ***ATTRIBUTES***
    public GameObject gameObject = null;        // GameObject it belongs to. Default to null.

    // ***METHODS***
    /**
     * Starts the component. Not obligatory.
     */
    public void start(){

    }

    /**
     * Updates the component. Must be implemented.
     * @param dt time differential
     */
    public abstract void update(float dt);
}
