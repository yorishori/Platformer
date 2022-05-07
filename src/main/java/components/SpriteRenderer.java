package components;

import org.joml.Vector4f;

public class SpriteRenderer extends Component {
    // ***ATTRIBUTES***
    private Vector4f color;

    // ***CONSTRUCTOR***
    public SpriteRenderer(Vector4f color){
        this.color = color;
    }
    // ***METHODS***
    @Override
    public void start() {

    }

    @Override
    public void update(float dt) {

    }


    public Vector4f getColor(){
        return this.color;
    }
}
