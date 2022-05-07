package engine;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;

/**
 * Class that determines the position of the camera in relative space.
 * You can get the view matrix and projection matrix for input to the GLSL Shader program.
 */
public class Camera {
    // ***ATTRIBUTES***
    private Matrix4f projectionMatrix, viewMatrix;          //  Matrices  for the correct transformation of coordinate systems
    private Vector2f position;                              //  Position of the camera in space

    private final float width = 1920;                       //  Display parameters for correct transformation
    private final float height = 1080;
    private final float view = 100;

    // ***CONSTRUCTOR***
    /**
     * Constructor for the camera: Handles views and projections automatically in 2D space.
     * @param position 2D vector for the position of the camera
     */
    public Camera(Vector2f position) {
        this.position = position;
        this.projectionMatrix = new Matrix4f();
        this.viewMatrix = new Matrix4f();
        adjustProjection();
    }

    // ***METHODS***
    /**
     * Adjusts the projection matrix taking into account the screen dimensions and making the center
     * the bottom left of the screen.
     */
    private void adjustProjection(){
        // Delete gibberish from variable
        projectionMatrix.identity();
        // Apply an orthographic transformation with the center at the bottom-left, and 0 to 100 view
        projectionMatrix.ortho(0.0f, width, 0.0f, height, 0.0f, view);
    }

    /**
     * Calculates the view matrix to be passed down to the shader program.
     * @return view matrix
     */
    public Matrix4f getViewMatrix(){
        Vector3f cameraFront = new Vector3f(0.0f, 0.0f, -1.0f);
        Vector3f cameraUp = new Vector3f(0.0f, 1.0f, 0.0f);
        this.viewMatrix.identity();
        this.viewMatrix = viewMatrix.lookAt(new Vector3f(position.x, position.y, 20.f),
                                            cameraFront.add(position.x, position.y, 0.0f),
                                            cameraUp);
        return this.viewMatrix;
    }

    /**
     * Calculates the projection matrix to be passed down to the shader program.
     * @return
     */
    public Matrix4f getProjectionMatrix(){
        return this.projectionMatrix;
    }
}
