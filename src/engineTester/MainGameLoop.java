package engineTester;

import models.TexturedModel;
import org.lwjgl.opengl.Display;

import renderEngine.DisplayManager;
import renderEngine.Loader;
import models.RawModel;
import renderEngine.Renderer;
import shaders.StaticShader;
import texture.ModelTexture;

/**
 * This class contains the main method and is used to test the engine.
*/
public class MainGameLoop {


    /**
     * Loads up the position data for two triangles (which together make a quad)
     * into a VAO. This VAO is then rendered to the screen every frame.
     *
     * @param args
     */
    public static void main(String[] args) {

        DisplayManager.createDisplay();
        Loader loader = new Loader();
        Renderer renderer = new Renderer();
        StaticShader shader = new StaticShader();

        float[] vertices = {
                -0.5f, 0.5f, 0f,//v0
                -0.5f, -0.5f, 0f,//v1
                0.5f, -0.5f, 0f,//v2
                0.5f, 0.5f, 0f,//v3
        };

        int[] indices = {
                0,1,3,//top left triangle (v0, v1, v3)
                3,1,2//bottom right triangle (v3, v1, v2)
        };

        float[] texturedCoords = {
                0,0, //V0
                1,0, //V1
                1,1, //V2
                0,1  //V3
        };

        RawModel model = loader.loadToVao(vertices, texturedCoords, indices);
        ModelTexture texture = new ModelTexture(loader.loadTexture("image"));
        TexturedModel texturedModel = new TexturedModel(model,texture);


        while (!Display.isCloseRequested()) {
            // game logic
            renderer.prepare();
            shader.start();
            renderer.render(texturedModel);
            shader.stop();
            DisplayManager.updateDisplay();
        }

        loader.cleanUp();
        DisplayManager.closeDisplay();
    }

}