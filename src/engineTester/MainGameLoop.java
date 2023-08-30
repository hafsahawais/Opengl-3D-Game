package engineTester;

import entities.Camera;
import entities.Entity;
import entities.Light;
import models.TexturedModel;
import org.lwjgl.opengl.Display;

import org.lwjgl.util.vector.Vector3f;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import models.RawModel;
import renderEngine.OBJLoader;
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

        StaticShader shader = new StaticShader();
        Renderer renderer = new Renderer(shader);


        RawModel model = OBJLoader.loadObjModel("dragon", loader);

        TexturedModel texturedModel = new TexturedModel(model,new ModelTexture(loader.loadTexture("image")));
        ModelTexture texture = texturedModel.getTexture();
        texture.setShineDamper(10);
        texture.setReflectivity(1);

        Entity entity = new Entity(texturedModel, new Vector3f(0,0,-50), 0,0,0, 1);

        Camera camera = new Camera();

        Light light = new Light(new Vector3f(0,0,-20), new Vector3f(1,1,1));

        while (!Display.isCloseRequested()) {
            entity.increaseRotation(0, 1,-0);
            camera.move();
            // game logic
            renderer.prepare();
            shader.start();
            shader.loadLight(light);
            shader.loadViewMatrix(camera);
            renderer.render(entity, shader);
            shader.stop();
            DisplayManager.updateDisplay();
        }
        shader.cleanUp();
        loader.cleanUp();
        DisplayManager.closeDisplay();
    }

}