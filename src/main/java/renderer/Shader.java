package renderer;

import java.io.IOException;
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

    // ***CONSTRUCTOR***
    public Shader(String filepath) {
        // get the filepath of the shader
        this.filepath = filepath;
        try{
            // read shader source code and copy it to src string
            String src = new String(Files.readAllBytes(Paths.get(filepath)));
            String[] splitString = src.split("(#type)( )+([a-zA-Z]+)");

            // find the first pattern after #type
            int index = src.indexOf("#type") + 6;
            int eol = src.indexOf("\r\n", index);
            String firstPattern = src.substring(index, eol).trim();

            // find the other pattern after the second #type
            index = src.indexOf("#type",eol) + 6;
            eol = src.indexOf("\r\n", index);
            String secondPattern = src.substring(index, eol).trim();

            // match the first pattern to the corresponding shader String
            if(firstPattern.equals("vertex")){
                vertexSrc = splitString[1];
            }else if(firstPattern.equals("fragment")){
                fragmentSrc = splitString[1];
            }else{
                throw new IOException("Unexpected token '" + firstPattern + "'");
            }

            // match the second pattern to the corresponding shader String
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
    //  compile the vertex and fragment shaders
    public void compile(){
        int vertexID, fragmentID;
        // =================================
        // Compiling and linking the shaders
        // =================================

        // First load and compile vertex shaders
        vertexID = glCreateShader(GL_VERTEX_SHADER);

        // pass the shader src code to GL
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

        // pass the shader src code to GL
        glShaderSource(fragmentID, fragmentSrc);
        glCompileShader(fragmentID);

        // Check for compilation errors
        success = glGetShaderi(fragmentID, GL_COMPILE_STATUS);
        if(success == GL_FALSE){
            int len = glGetShaderi(fragmentID, GL_INFO_LOG_LENGTH);
            System.out.println("Error: '"+ filepath +"'\n\tFragment shader compilation failed.");
            System.out.println(glGetShaderInfoLog(fragmentID, len));
            assert false: "";
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
            assert false: "";
        }
    }

    //  bind elements to GL
    public void use(){
        // Bind shader program
        glUseProgram(shaderProgramID);
    }

    //  unbind elements from GL
    public void detach(){
        glUseProgram(0);
    }
}
