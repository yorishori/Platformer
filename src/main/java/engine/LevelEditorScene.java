package engine;

import org.lwjgl.BufferUtils;
import renderer.Shader;

import java.awt.event.KeyEvent;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

public class LevelEditorScene extends Scene{
    // ***ATTRIBUTES***
    private String vertexShaderScr = "#version 330 core\n" +
            "\n" +
            "layout(location=0) in vec3 aPos;\n" +
            "layout(location=1) in vec4 aColor;\n" +
            "\n" +
            "out vec4 fColor;\n" +
            "\n" +
            "void main(){\n" +
            "    fColor = aColor;\n" +
            "    gl_Position = vec4(aPos, 1.0);\n" +
            "}";
    private String fragmentShaderScr ="#version 330 core\n" +
            "\n" +
            "in vec4 fColor;\n" +
            "\n" +
            "out vec4 color;\n" +
            "\n" +
            "void main(){\n" +
            "    color = fColor;\n" +
            "}";
    private int vertexID, fragmentID, shaderProgram;
    private float[] vertexArray = {
            // postion           color
            0.5f ,-0.5f,0.0f,         1.0f, 0.0f, 0.0f, 1.0f,// Bottom right
            -0.5f, 0.5f,0.0f,         0.0f, 1.0f, 0.0f, 1.0f,// Top left
            0.5f , 0.5f,0.0f,         1.0f, 0.0f, 0.0f, 1.0f,// Top right
            -0.5f,-0.5f,0.0f,         1.0f, 0.8f, 0.5f, 1.0f,// Bottom left
    };
    // IMPORTAT: Must be in couter-clockwise order
    private int elementArray[] = {
            /*
                     1          2

                     3          0
             */
            2,1,0, // Top right triangle
            0,1,3  // Bottom left triangle
    };
    private int vaoID, vboID, eboID;

    private Shader defaultShader;
    
    // ***CONSTRUCTOR***
    public LevelEditorScene(){

    }
    
    
    // ***METHODS***
    @Override
    public void update(float dt) {

        defaultShader.use();
        // Bind vaoID
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

    }

    @Override
    public void init(){
        // Use shader class to initialize settings
        defaultShader = new Shader("assets/shaders/default.glsl");
        defaultShader.compile();

        // =============================================================
        // Generate VAO, VBO, and EBO buffer objects and send them to GL
        // =============================================================
        vaoID = glGenVertexArrays();
        glBindVertexArray(vaoID);

        // Create a float buffer of vertices
        FloatBuffer vertexBuffer = BufferUtils.createFloatBuffer(vertexArray.length);
        vertexBuffer.put(vertexArray).flip();

        // Create VCO and upload the vertex buffer
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
        int colorSize = 4;
        int floatSizeBytes = 4;

        int vertexSizeBytes = (positionSize + colorSize)*floatSizeBytes;
        glVertexAttribPointer(0, positionSize, GL_FLOAT, false, vertexSizeBytes, 0);
        glEnableVertexAttribArray(0);

        glVertexAttribPointer(1, colorSize, GL_FLOAT, false, vertexSizeBytes, (positionSize*floatSizeBytes));
        glEnableVertexAttribArray(1);
    }
    
}
