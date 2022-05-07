package engine;

import org.joml.Vector2f;

/**
 * Class to make publicly available the position and scale variables.
 */
public class Transform {
    // ***ATTRIBUTES***
    public Vector2f position;
    public Vector2f scale;

    // ***CONSTRUCTORS***
    public Transform(){
        init(new Vector2f(), new Vector2f());
    }

    public Transform(Vector2f position){
        init(position, new Vector2f());
    }

    public Transform(Vector2f position, Vector2f scale){
        init(position, scale);
    }

    // ***METHODS***
    public void init(Vector2f position, Vector2f scale) {
        this.position = position;
        this.scale = scale;
    }

}
