package characters;

import characters.base.Creation;
import characters.base.CreationType;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class Water extends Creation {

    public Water(World world, float x, float y) {
        super(world, new Vector2(x, y), CreationType.Water);
        sprite.setSize(1, 1); //size in meters

    }

    @Override
    protected Shape initFixtureDef() {
        PolygonShape box = new PolygonShape();
        box.setAsBox(0.5f, 0.5f);

        fixtureDef = new FixtureDef();
        fixtureDef.shape = box;
        fixtureDef.density = 1.0f;
        return box;
    }
}
