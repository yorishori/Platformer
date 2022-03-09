package engine;

public abstract class Scene {
    // ***ATTRIBUTES***
    protected Camera camera;
    // ***CONSTRUCTOR***
    public Scene(){
        
    }

    // ***METHODS***
    public void init(){
        
    }
    public abstract void update(float dt);
}
