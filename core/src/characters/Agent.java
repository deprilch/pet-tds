package characters;

import characters.base.CreationType;
import characters.base.DynamicCreation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;

public class Agent extends DynamicCreation {

    public Agent(World world, Vector2 position){
        super(world, position, CreationType.Agent);
    }

    @Override
    protected Shape initFixtureDef() {
        PolygonShape body = new PolygonShape();
        body.setAsBox(0.5f, 0.5f);

        fixtureDef.shape = body;

        fixtureDef.shape = body;
        fixtureDef.density = 0.0f;
        fixtureDef.isSensor = true;
        return body;
    }

    public void stop() {
        body.setLinearVelocity(0.0f, 0.0f);
    }
}
