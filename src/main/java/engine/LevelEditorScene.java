package engine;

import components.SpriteRenderer;
import org.joml.Vector2f;
import org.joml.Vector4f;


/**
 * Scene object for the Lever Editor
 */
public class LevelEditorScene extends Scene {
    // ***ATTRIBUTES***

    // ***CONSTRUCTOR***
    public LevelEditorScene() {

    }

    // ***METHODS***

    /**
     * Initialize and create game objects for the scene.
     */
    @Override
    public void init() {
        this.camera = new Camera(new Vector2f());

        int xOffset = 100;
        int yOffset = 100;

        float totalWidth = (float) (1000);
        float totalHeight = (float) (560);
        float sizeX = totalWidth / 50.0f ;
        float sizeY = totalWidth / 50.0f ;


        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
                float xPos = xOffset + (x * sizeX);
                float yPos = yOffset + (y * sizeY);

                GameObject go = new GameObject("obj " + x + " " + y,
                        new Transform(
                                new Vector2f(xPos, yPos),
                                new Vector2f(sizeX, sizeX)));

                go.addComponent(new SpriteRenderer(
                        new Vector4f(xPos / totalWidth, yPos / totalHeight, 1, 1)));

                this.addGameObjectToScene(go);
            }
        }
    }

    /**
     * Update game objects
     *
     * @param dt time differential
     */
    @Override
    public void update(float dt) {
        for (GameObject go : this.gameObjects) {
            //go.update(dt);
            //this.renderer.render();
        }
    }
}