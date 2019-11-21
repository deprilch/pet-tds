package characters;

import characters.base.Creation;
import characters.base.CreationType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class Brick extends Creation {

    public Brick(World world, float x, float y) {
        super(world, new Vector2(x, y), CreationType.Brick);
    }

    @Override
    protected Shape initFixtureDef() {
        PolygonShape brickBox = new PolygonShape();
        brickBox.setAsBox(0.5f, 0.5f);

        fixtureDef.shape = brickBox;
        fixtureDef.density = 1.0f;

        return brickBox;
    }
}
