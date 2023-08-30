package renderEngine;

import models.RawModel;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

//Loading 3D model into memory by storing positional data about the model in a VAO
public class Loader {

    private List<Integer> vaos = new ArrayList<Integer>();
    private List<Integer> vbos = new ArrayList<Integer>();
    private List<Integer> textures = new ArrayList<Integer>();


    //    Take positions of the model vertices
//    Load data into a Vao
//    return information about a vao as a raw model object
    public RawModel loadToVao(float[] positions, float[] texturedCoords, float[] normals, int[] indices) {
       int vaoId = createVAO();
       vaos.add(vaoId);
       bindIndicesBuffer(indices);
       storeDataInAttributeList(0, 3, positions);
       storeDataInAttributeList(1, 2, texturedCoords);
        storeDataInAttributeList(2, 3, normals);
       unbindVao();
//      each vertex has 3 floats x,y,z
       return new RawModel(vaoId,indices.length);


    }

    public int loadTexture(String fileName) {
        Texture texture = null;
        try {
            texture = TextureLoader.getTexture("PNG", new FileInputStream("res/" + fileName + ".png"));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        int textureId = texture.getTextureID();
        textures.add(textureId);
        return textureId;


    }

    // return Id of the vao
    private int createVAO() {
//   creates a empty vao and return its id
        int vaoId = GL30.glGenVertexArrays();
//   activate a vao by binding it
        GL30.glBindVertexArray(vaoId);
        return vaoId;
    }

//    store data in attribute list of vao
    private void storeDataInAttributeList(int attributeNumber, int coordinateSize,float[] data) {
        int vboId = GL15.glGenBuffers();
        vbos.add(vboId);
//        1st argument: specify the type of buffer
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER , vboId);
//        Data has to be stored in vbo as a float Buffer
        FloatBuffer buffer = storeDataInFloatBuffer(data);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
//        put vbo into the vao
        GL20.glVertexAttribPointer(attributeNumber, coordinateSize, GL11.GL_FLOAT, false, 0,0);
//        unbind the current vbo
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER , 0);

    }

    private void unbindVao() {
        GL30.glBindVertexArray(0);

    }

    private IntBuffer storeDataInIntBuffer(int[] data) {
        IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
        buffer.put(data);
        buffer.flip();
        return buffer;
    }

    private FloatBuffer storeDataInFloatBuffer(float[] data) {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
        buffer.put(data);
        buffer.flip();
        return buffer;
    }
//    load indices buffer and bind to vao

    private void bindIndicesBuffer(int[] indices) {
        int vboId = GL15.glGenBuffers();
        vbos.add(vboId);
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboId);
        IntBuffer buffer = storeDataInIntBuffer(indices);
        GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);

    }
//    After everything is done delete the vaos and vbos
    public void cleanUp() {
        for (int vao : vaos) {
            GL30.glDeleteVertexArrays(vao);
        }
        for (int vbo : vbos) {
            GL15.glDeleteBuffers(vbo);
        }
        for(int texture : textures) {
            GL11.glDeleteTextures(texture);
        }

    }
}
