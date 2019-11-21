package characters;

import characters.base.CreationType;
import characters.base.DynamicCreation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class Fireball extends DynamicCreation {

    public Fireball(World world, Vector2 position) {
        super(world, position, CreationType.Fireball);
        sprite.setSize(0.25f, 0.25f);

    }

    @Override
    protected Shape initFixtureDef() {
        CircleShape hitBox = new CircleShape();
        hitBox.setRadius(0.25f);

        fixtureDef = new FixtureDef();
        fixtureDef.shape = hitBox;
        fixtureDef.restitution = 1.0f;
        fixtureDef.density = 1.0f;
        fixtureDef.isSensor = true;
        return hitBox;
    }
}
