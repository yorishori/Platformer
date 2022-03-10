package components;

import engine.Component;

public class FontRenderer extends Component {
    // ***METHODS***
    @Override
    public void start() {
        //Testing
        if(gameObject.getComponent(SpriteRenderer.class) != null){
            System.out.println("Found Font Render!");
        }
    }

    @Override
    public void update(float dt) {

    }
}
