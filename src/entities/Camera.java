package entities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

public class Camera {

    private Vector3f position = new Vector3f(0,0,0);
//    high or low
    private float pitch;
//    right or left
    private float yaw;


    public Vector3f getPosition() {
        return position;
    }

    public void move() {

        if(Keyboard.isKeyDown(Keyboard.KEY_W)) {
            position.z -= 0.02f;
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_D)) {
            position.x += 0.02f;
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_A)) {
            position.x -= 0.02f;
        }
    }

    public float getPitch() {
        return pitch;
    }

    public float getYaw() {
        return yaw;
    }


}
