package characters.base;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import systems.TextureManager;

public abstract class Creation {

    protected World world;
    protected Sprite sprite;
    protected BodyDef bodyDef = new BodyDef();
    protected FixtureDef fixtureDef = new FixtureDef();
    protected Body body;

    public boolean isAlive = true;

    public Creation(World world, Vector2 position, CreationType type) {
        this.world = world;

        Texture texture = TextureManager.getInstance().getTexture(type);
        sprite = new Sprite(texture);
        sprite.setSize(1, 1); //size in meters

        bodyDef.position.set(position);
        if (this instanceof DynamicCreation)
            bodyDef.type = BodyDef.BodyType.DynamicBody;

        body = world.createBody(bodyDef);
        body.setUserData(sprite);

        Shape shape = initFixtureDef();
        Fixture fixture = body.createFixture(fixtureDef);
        fixture.setUserData(type);
        shape.dispose();

    }

    public Vector2 getPosition() {
        return body.getPosition();
    }

    public Body getBody() {
        return body;
    }

    protected abstract Shape initFixtureDef();


}
