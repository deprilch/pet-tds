package characters;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;

public class Creation {

    public Texture creationT;
    public Sprite creationS;
    public Body creationBody;
    public BodyDef creationDef;
    public FixtureDef fixtureDef;
    public Vector2 position;
    public float maxLinearSpeed = 0.5f;
    public float maxLinearAcceleration = 0.1f;
    public float maxAngularSpeed;
    public float maxAngularAcceleration;

    public boolean isAlive = true;

    public Creation() {
        position = new Vector2();
        creationDef = new BodyDef();
        creationDef.type = BodyDef.BodyType.DynamicBody;
        fixtureDef = new FixtureDef();
    }

}
