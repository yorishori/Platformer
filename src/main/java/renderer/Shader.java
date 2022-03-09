package renderer;

import org.joml.*;
import org.lwjgl.BufferUtils;

import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL20.glGetProgramInfoLog;

public class Shader {
    // ***ATTRIBUTES***
    private int shaderProgramID;
    private String vertexSrc;
    private String fragmentSrc;
    private String filepath;

    private boolean beingUsed = false;

    // ***CONSTRUCTOR***
    public Shader(String filepath) {
        // Download the shader source to a couple of Strings
        this.filepath = filepath;
        try{
            // Read shader source code and copy it to src string
            String src = new String(Files.readAllBytes(Paths.get(filepath)));
            String[] splitString = src.split("(#type)( )+([a-zA-Z]+)");

            // Find the first pattern after #type
            int index = src.indexOf("#type") + 6;
            int eol = src.indexOf("\r\n", index);
            String firstPattern = src.substring(index, eol).trim();

            // Find the other pattern after the second #type
            index = src.indexOf("#type",eol) + 6;
            eol = src.indexOf("\r\n", index);
            String secondPattern = src.substring(index, eol).trim();

            // Match the first pattern to the corresponding shader String
            if(firstPattern.equals("vertex")){
                vertexSrc = splitString[1];
            }else if(firstPattern.equals("fragment")){
                fragmentSrc = splitString[1];
            }else{
                throw new IOException("Unexpected token '" + firstPattern + "'");
            }

            // Match the second pattern to the corresponding shader String
            if(secondPattern.equals("vertex")){
                vertexSrc = splitString[2];
            }else if(secondPattern.equals("fragment")){
                fragmentSrc = splitString[2];
            }else{
                throw new IOException("Unexpected token '" + firstPattern + "'");
            }
        }catch(IOException e){
            e.printStackTrace();
            assert false : "Error: Could not open file for shader: '" + filepath + "'.";
        }
    }

    // ***METHODS***
    // Compile the vertex and fragment shaders
    public void compile(){
        int vertexID, fragmentID;
        // =================================
        // Compiling and linking the shaders
        // =================================

        // First load and compile vertex shaders
        vertexID = glCreateShader(GL_VERTEX_SHADER);

        // Pass the shader src code to GL
        glShaderSource(vertexID, vertexSrc);
        glCompileShader(vertexID);

        // Check for compilation errors
        int success = glGetShaderi(vertexID, GL_COMPILE_STATUS);
        if(success == GL_FALSE){
            int len = glGetShaderi(vertexID, GL_INFO_LOG_LENGTH);
            System.out.println("Error: '"+ filepath +"'\n\tVertex shader compilation failed.");
            System.out.println(glGetShaderInfoLog(vertexID, len));
            assert false: "";
        }

        // Now load and compile fragment shaders
        fragmentID = glCreateShader(GL_FRAGMENT_SHADER);

        // Pass the shader src code to GL
        glShaderSource(fragmentID, fragmentSrc);
        glCompileShader(fragmentID);

        // Check for compilation errors
        success = glGetShaderi(fragmentID, GL_COMPILE_STATUS);
        if(success == GL_FALSE){
            int len = glGetShaderi(fragmentID, GL_INFO_LOG_LENGTH);
            System.out.println("Error: '"+ filepath +"'\n\tFragment shader compilation failed.");
            System.out.println(glGetShaderInfoLog(fragmentID, len));
            assert false: "Error (GL Compilation)";
        }

        // Link shaders
        shaderProgramID = glCreateProgram();
        glAttachShader(shaderProgramID, vertexID);
        glAttachShader(shaderProgramID, fragmentID);
        glLinkProgram(shaderProgramID);

        // Check for linking errors
        success = glGetProgrami(shaderProgramID, GL_LINK_STATUS);
        if(success == GL_FALSE){
            int len = glGetProgrami(shaderProgramID, GL_INFO_LOG_LENGTH);
            System.out.println("Error: '"+ filepath +"'\n\tShader linking failed.");
            System.out.println(glGetProgramInfoLog(shaderProgramID, len));
            assert false: "Error (GL Linking)";
        }
    }

    // Bind elements to GL
    public void use(){
        if(!beingUsed) {
            // Bind shader program
            glUseProgram(shaderProgramID);
            beingUsed = true;
        }
    }

    // Unbind elements from GL
    public void detach(){
        glUseProgram(0);
        beingUsed = false;
    }

    // Upload a 4x4 matrix to the shader
    public void uploadMat4f(String varName, Matrix4f mat){
        int varLocation = glGetUniformLocation(shaderProgramID, varName);
        use();
        FloatBuffer matBuffer = BufferUtils.createFloatBuffer(16);
        mat.get(matBuffer);
        glUniformMatrix4fv(varLocation, false, matBuffer);
    }

    // Upload a 3x3 matrix to the shader
    public void uploadMat3f(String varName, Matrix3f mat){
        int varLocation = glGetUniformLocation(shaderProgramID, varName);
        use();
        FloatBuffer matBuffer = BufferUtils.createFloatBuffer(9);
        mat.get(matBuffer);
        glUniformMatrix3fv(varLocation, false, matBuffer);
    }

    // Upload a 4 vector to the shader
    public void uploadVec4f(String varName, Vector4f vec){
        int varLocation = glGetUniformLocation(shaderProgramID, varName);
        use();
        glUniform4f(varLocation, vec.x, vec.y, vec.z, vec.w);
    }

    // Upload a 3 vector to the shader
    public void uploadVec3f(String varName, Vector3f vec){
        int varLocation = glGetUniformLocation(shaderProgramID, varName);
        use();
        glUniform3f(varLocation, vec.x, vec.y, vec.z);
    }

    // Upload a 2 vector to the shader
    public void uploadVec2f(String varName, Vector2f vec){
        int varLocation = glGetUniformLocation(shaderProgramID, varName);
        use();
        glUniform2f(varLocation, vec.x, vec.y);
    }

    // Upload a float to the shader
    public void uploadFloat(String varName, float val){
        int varLocation = glGetUniformLocation(shaderProgramID, varName);
        use();
        glUniform1f(varLocation, val);
    }

    // Upload an int to the shader
    public void uploadInt(String varName, int val){
        int varLocation = glGetUniformLocation(shaderProgramID, varName);
        use();
        glUniform1i(varLocation, val);
    }

    // Upload a texture to the shader
    public void uploadTexture(String varName, int slot){
        int varLocation = glGetUniformLocation(shaderProgramID, varName);
        use();
        glUniform1i(varLocation, slot);
    }

}
