package renderer;

import components.SpriteRenderer;
import engine.GameObject;

import java.util.ArrayList;
import java.util.List;

public class Renderer {
    // ***ATTRIBUTES***
    private final int MAX_BATCH_SIZE = 10000;
    private List<RenderBatch> batches;

    // ***CONSTRUCTOR***
    public Renderer(){
        this.batches = new ArrayList<>();
    }

    // ***METHODS***

    /**
     * Class that adds the Sprites to the batch renderers.
     * @param go game object with sprite components
     */
    public void add(GameObject go){
        SpriteRenderer spr = go.getComponent(SpriteRenderer.class);
        if(spr != null){
            add(spr);
        }
    }

    /**
     * Add the sprite to the batch. If full, create and add it to a new one.
     * @param sprite sprite to add
     */
    private void add(SpriteRenderer sprite) {
        boolean added = false;
        for(RenderBatch batch: batches){
            if(batch.hasRoom()) {
                batch.addSprite(sprite);
                added = true;
                break;
            }
        }

        if(!added){
            RenderBatch newBatch = new RenderBatch(MAX_BATCH_SIZE);
            newBatch.start();
            batches.add(newBatch);
            newBatch.addSprite(sprite);
        }
    }

    /**
     * Render all the batches.
     */
    public void render(){
        for(RenderBatch batch: batches){
            batch.render();
        }
    }
}
