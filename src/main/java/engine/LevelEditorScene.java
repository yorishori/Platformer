package engine;

import components.FontRenderer;
import components.SpriteRenderer;
import org.joml.Vector2f;
import org.lwjgl.BufferUtils;
import renderer.Shader;
import renderer.Texture;
import util.Time;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

/**
 * Scene object for the Lever Editor
 */
public class LevelEditorScene extends Scene {
    // ***ATTRIBUTES***
    private float[] vertexArray = {
            // position               // color                  // UV Coordinates
            100f,   0f, 0.0f,       1.0f, 0.0f, 0.0f, 1.0f,     1, 1, // Bottom right 0
            0f, 100f, 0.0f,       0.0f, 1.0f, 0.0f, 1.0f,     0, 0, // Top left     1
            100f, 100f, 0.0f ,      1.0f, 0.0f, 1.0f, 1.0f,     1, 0, // Top right    2
            0f,   0f, 0.0f,       1.0f, 1.0f, 0.0f, 1.0f,     0, 1  // Bottom left  3
    };

    // IMPORTANT: Must be in counter-clockwise order
    private int[] elementArray = {
            /*
                    1        2
                    3        0
             */
            0,2,1, // Top right triangle
            1,3,0 // bottom left triangle
    };

    private int vaoID, vboID, eboID;

    private Shader defaultShader;
    private Texture texture;

    //TODO: Remove test code
    //Testing
    private GameObject testObj;
    private boolean firstTime = true;

    // ***CONSTRUCTOR***
    public LevelEditorScene() {

    }

    // ***METHODS***

    /**
     * Initialize and create game objects for the scene,
     * load shaders and textures and upload drawings to OpenGL libraries.
     */
    @Override
    public void init() {
        //TODO: Remove test code
        //Testing
        System.out.println("Creating test object");
        // Create game object
        this.testObj = new GameObject("test object");
        // Add
        this.testObj.addComponent(new SpriteRenderer());
        this.testObj.addComponent(new FontRenderer());
        this.addGameObjectToScene(this.testObj);


        // Create camera, compile shaders and add textures
        this.camera = new Camera(new Vector2f(-200, -300));
        defaultShader = new Shader("assets/shaders/default.glsl");
        defaultShader.compile();
        this.texture = new Texture("assets/images/kirby.png");

        // ============================================================
        // Generate VAO, VBO, and EBO buffer objects, and send to GPU
        // ============================================================
        vaoID = glGenVertexArrays();
        glBindVertexArray(vaoID);

        // Create a float buffer of vertices
        FloatBuffer vertexBuffer = BufferUtils.createFloatBuffer(vertexArray.length);
        vertexBuffer.put(vertexArray).flip();

        // Create VBO upload the vertex buffer
        vboID = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vboID);
        glBufferData(GL_ARRAY_BUFFER, vertexBuffer, GL_STATIC_DRAW);

        // Create the indices and upload
        IntBuffer elementBuffer = BufferUtils.createIntBuffer(elementArray.length);
        elementBuffer.put(elementArray).flip();

        eboID = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, eboID);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, elementBuffer, GL_STATIC_DRAW);

        // Upload the vertex attribute pointers
        int positionsSize = 3;
        int colorSize = 4;
        int uvSize = 2;
        int vertexSizeBytes = (positionsSize + colorSize + uvSize) * Float.BYTES;
        glVertexAttribPointer(0, positionsSize, GL_FLOAT, false, vertexSizeBytes, 0);
        glEnableVertexAttribArray(0);

        glVertexAttribPointer(1, colorSize, GL_FLOAT, false, vertexSizeBytes, positionsSize * Float.BYTES);
        glEnableVertexAttribArray(1);

        glVertexAttribPointer(2, uvSize, GL_FLOAT, false, vertexSizeBytes, (positionsSize + colorSize) * Float.BYTES);
        glEnableVertexAttribArray(2);
    }

    /**
     * Update game objects, shaders, textures and cameras,
     * redraw shapes
     * @param dt time differential
     */
    @Override
    public void update(float dt) {
        //  Use the shader program
        defaultShader.use();

        // Upload texture to shader
        defaultShader.uploadTexture("tex", 0);
        glActiveTexture(GL_TEXTURE0);
        texture.bind();

        defaultShader.uploadMat4f("uProjection", camera.getProjectionMatrix());
        defaultShader.uploadMat4f("uView", camera.getViewMatrix());
        defaultShader.uploadFloat("uTime", Time.getTime());
        // Bind the VAO that we're using
        glBindVertexArray(vaoID);

        // Enable the vertex attribute pointers
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);

        glDrawElements(GL_TRIANGLES, elementArray.length, GL_UNSIGNED_INT, 0);

        // Unbind everything
        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);

        glBindVertexArray(0);

        defaultShader.detach();

        //TODO: Remove test code
        //Testing
        if(firstTime) {
            GameObject go = new GameObject("Test 2");
            go.addComponent(new SpriteRenderer());
            this.addGameObjectToScene(go);
            firstTime = false;
        }
        for(GameObject go: this.gameObjects){
            go.update(dt);
        }
    }
}