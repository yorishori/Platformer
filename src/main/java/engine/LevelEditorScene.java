package engine;

import org.joml.Vector2f;
import org.lwjgl.BufferUtils;
import renderer.Shader;
import renderer.Texture;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

public class LevelEditorScene extends Scene{
    // ***ATTRIBUTES***
    private int vertexID, fragmentID, shaderProgram;

    private float[] vertexArray = {
            // position                  color
             0.0f, 500.0f,0.0f,         /*1.0f, 0.0f, 0.0f, 1.0f,*/     0.0f, 1.0f, // Top Left
            500.0f, 500.0f,0.0f,         /*0.0f, 1.0f, 0.0f, 0.0f,*/    1.0f, 1.0f, // Top right
             0.0f,  0.0f,0.0f,         /*0.0f, 0.0f, 1.0f, 0.0f,*/      0.0f, 0.0f, // Bottom left
            500.0f,  0.0f,0.0f,         /*0.0f, 0.0f, 0.0f, 1.0f,*/     1.0f, 0.0f, // Bottom right
    };
    // IMPORTANT: Must be in counter-clockwise order
    private int elementArray[] = {
            /*
                     0          1

                     2          3
             */
            3,1,0, // Top right triangle
            3,0,2  // Bottom left triangle
    };
    private int vaoID, vboID, eboID;

    private Shader defaultShader;
    private Texture texture;
    
    // ***CONSTRUCTOR***
    public LevelEditorScene(){

    }
    
    
    // ***METHODS***
    @Override
    public void init(){
        this.camera = new Camera(new Vector2f(-500, -500));
        // Use shader class to initialize settings
        defaultShader = new Shader("assets/shaders/default.glsl");
        defaultShader.compile();
        this.texture = new Texture("assets/images/test6.jpg");

        // =============================================================
        // Generate VAO, VBO, and EBO buffer objects and send them to GL
        // =============================================================
        vaoID = glGenVertexArrays();
        glBindVertexArray(vaoID);

        // Create a float buffer of vertices
        FloatBuffer vertexBuffer = BufferUtils.createFloatBuffer(vertexArray.length);
        vertexBuffer.put(vertexArray).flip();

        // Create VBO and upload the vertex buffer
        vboID = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vboID);
        glBufferData(GL_ARRAY_BUFFER, vertexBuffer, GL_STATIC_DRAW);

        // Create the indices and upload
        IntBuffer elementBuffer = BufferUtils.createIntBuffer(elementArray.length);
        elementBuffer.put(elementArray).flip();

        eboID = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, eboID);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, elementBuffer, GL_STATIC_DRAW);

        // Add vertex attribute pointers.
        int positionSize = 3;
        int colorSize = 0;//4
        int uvSize = 2;
        int vertexSizeBytes = (positionSize + colorSize + uvSize)*Float.BYTES;

        glVertexAttribPointer(0, positionSize, GL_FLOAT, false, vertexSizeBytes, 0);
        glEnableVertexAttribArray(0);

        //glVertexAttribPointer(1, colorSize, GL_FLOAT, false, vertexSizeBytes, (positionSize*Float.BYTES));
        //glEnableVertexAttribArray(1);

        glVertexAttribPointer(2, uvSize, GL_FLOAT, false, vertexSizeBytes, (positionSize + colorSize)*Float.BYTES);
        glEnableVertexAttribArray(2);
    }
    @Override
    public void update(float dt) {

        defaultShader.use();

        // Upload texture
        defaultShader.uploadTexture("tex", 0);
        glActiveTexture(GL_TEXTURE0);
        texture.bind();

        // Upload the scaling and projection vectors
        defaultShader.uploadMat4f("uProjection", camera.getProjectionMatrix());
        defaultShader.uploadMat4f("uView", camera.getViewMatrix());

        // Bind vaoID
        glBindVertexArray(vaoID);
        // Bind eboID
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, eboID);

        // Enable the vertex attribute pointers
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);
        glEnableVertexAttribArray(2);

        glDrawElements(GL_TRIANGLES, elementArray.length, GL_UNSIGNED_INT, 0);

        // Unbind everything
        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
        glDisableVertexAttribArray(2);

        glBindVertexArray(0);

        defaultShader.detach();
        texture.unbind();
    }
}
