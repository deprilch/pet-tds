package systems;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.RayCastCallback;
public class Raycasting {

    public RayCastCallback rayCastCallback;
    public static final int NOTHING = 0;
    public static final int WALL = 1;
    public static final int AGENT = 2;

    public static int type = NOTHING;

    public Raycasting(){
        rayCastCallback = new RayCastCallback() {
            @Override
            public float reportRayFixture(Fixture fixture, Vector2 point, Vector2 normal, float fraction) {

                if(fixture.getUserData() == "Brick"){
                    type = WALL;

                }
                if(fixture.getUserData() == "Hero"){
                    type = AGENT;
                }

                return fraction;
            }
        };
    }
}
