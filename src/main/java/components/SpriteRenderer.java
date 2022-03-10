package components;

import engine.Component;

public class SpriteRenderer extends Component {
    // ***ATTRIBUTES***
    //Testing
    private boolean firstTime = true;


    // ***METHODS***
    @Override
    public void start() {
        //Testing
        System.out.println("I am starting.");
    }

    @Override
    public void update(float dt) {
        //Testing
        if(firstTime) {
            System.out.println("I am updating.");
            firstTime = false;
        }
    }
}
