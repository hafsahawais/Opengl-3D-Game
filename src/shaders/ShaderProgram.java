package shaders;

import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL11;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

//generic class for all the shader programs
public abstract class ShaderProgram {

    private final int programId;
    private final int vertexShaderId;
    private final int fragmentShaderId;

    public ShaderProgram(String vertexFile, String FragmentFile) {
        vertexShaderId = loadShader(vertexFile, GL20.GL_VERTEX_SHADER);
        fragmentShaderId = loadShader(FragmentFile, GL20.GL_FRAGMENT_SHADER);
        programId = GL20.glCreateProgram();
        GL20.glAttachShader(programId, vertexShaderId);
        GL20.glAttachShader(programId, fragmentShaderId);
        bindAttributes();
        GL20.glLinkProgram(programId);
        GL20.glValidateProgram(programId);

    }

    protected abstract void bindAttributes();

    public void start() {
        GL20.glUseProgram(programId);
    }

    public void stop() {
        GL20.glUseProgram(0);
    }
//   memory management
    public void cleanUp(){
        stop();
        GL20.glDetachShader(programId, vertexShaderId);
        GL20.glDetachShader(programId, fragmentShaderId);
        GL20.glDeleteShader(vertexShaderId);
        GL20.glDeleteShader(fragmentShaderId);
        GL20.glDeleteProgram(programId);
    }

    protected void bindAttribute(int attribute, String variableName) {
        GL20.glBindAttribLocation(programId, attribute, variableName);
    }

    private static int loadShader(String file,int type) {


        StringBuilder shaderSource = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {

                shaderSource.append(line).append('\n');
            }
            reader.close();
        }  catch (IOException e) {
            System.err.println("could not read file");
            e.printStackTrace();
        }

        int shaderId = GL20.glCreateShader(type);
        GL20.glShaderSource(shaderId,shaderSource);
        GL20.glCompileShader(shaderId);
        if(GL20.glGetShaderi(shaderId, GL20.GL_COMPILE_STATUS )== GL11.GL_FALSE){
            System.out.println(GL20.glGetShaderInfoLog(shaderId, 500));
            System.err.println("Could not compile shader!");
            System.exit(-1);
        }

        return shaderId;
    }


}
