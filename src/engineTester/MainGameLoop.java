package engineTester;

import entities.Camera;
import entities.Entity;
import entities.Light;
import models.TexturedModel;
import org.lwjgl.opengl.Display;

import org.lwjgl.util.vector.Vector3f;
import renderEngine.*;
import models.RawModel;
import shaders.StaticShader;
import texture.ModelTexture;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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


        RawModel treeModel = OBJLoader.loadObjModel("tree", loader);

        TexturedModel treeTexturedModel = new TexturedModel(treeModel,new ModelTexture(loader.loadTexture("tree")));

        ModelTexture texture = treeTexturedModel.getTexture();
        texture.setShineDamper(10);
        texture.setReflectivity(1);

//        Entity entity = new Entity(texturedModel, new Vector3f(0,0,-50), 0,0,0, 1);

        Camera camera = new Camera();

        List<Entity> allTrees = new ArrayList<Entity>();

        Random random = new Random();

        for (int i = 0; i < 200; i++) {
            float x = random.nextFloat() * 800 - 400;
//            float y = random.nextFloat() * 100 - 50;
            float z = random.nextFloat() * -300;

            allTrees.add(new Entity(treeTexturedModel, new Vector3f(x,0,z), 0,
                    0,0f, 1f));
        }

        Light light = new Light(new Vector3f(0,0,-20), new Vector3f(1,1,1));

        MasterRenderer renderer = new MasterRenderer();

        while (!Display.isCloseRequested()) {
//            entity.increaseRotation(0, 1,-0);
            camera.move();
            // game logic
            for (Entity tree: allTrees) {
                renderer.processEntity(tree);
            }

            renderer.render(light,camera);
            DisplayManager.updateDisplay();
        }

        loader.cleanUp();
        DisplayManager.closeDisplay();
    }

}