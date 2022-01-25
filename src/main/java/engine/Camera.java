package engine;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;

public class Camera {
    // ***ATTRIBUTES***
    private Matrix4f projectionMatrix, viewMatrix;
    private Vector2f position;

    // ***CONSTRUCTOR***
    public Camera(Vector2f position) {
        this.position = position;
        this.projectionMatrix = new Matrix4f();
        this.viewMatrix = new Matrix4f();
        adjustProjection();
    }

    // ***METHODS***
    //  adjust matrix to a projection view
    public void adjustProjection(){
        // Delete gibberish from variable
        projectionMatrix.identity();
        // Apply an orthographic transformation with the center at the bottom-left, and 0 to 100 view
        projectionMatrix.ortho(0.0f, 1920.0f, 0.0f, 1080.0f, 0.0f, 100.0f);
    }

    // Getter for the view matrix
    public Matrix4f getViewMatrix(){
        Vector3f cameraFront = new Vector3f(0.0f, 0.0f, -1.0f);
        Vector3f cameraUp = new Vector3f(0.0f, 1.0f, 0.0f);
        this.viewMatrix.identity();
        this.viewMatrix = viewMatrix.lookAt(new Vector3f(position.x, position.y, 20.f),
                                            cameraFront.add(position.x, position.y, 0.0f),
                                            cameraUp);
        return this.viewMatrix;
    }
    // Getter for the projection matrix
    public Matrix4f getProjectionMatrix(){
        return this.projectionMatrix;
    }
}
