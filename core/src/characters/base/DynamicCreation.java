package characters.base;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

public abstract class DynamicCreation  extends Creation {

    public float maxLinearSpeed;
    public float maxLinearAcceleration;
    public float maxAngularSpeed;
    public float maxAngularAcceleration;

    public DynamicCreation(World world, Vector2 position, CreationType type) {
        super(world, position, type);
    }
}
