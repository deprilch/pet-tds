package systems;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class Aiming {

    public static float angleBetweenTwoPoints(Vector2 target, Vector2 position) {
         return target.sub(position).angle() * MathUtils.degRad;
    }

    public static float rotate(Vector2 target, Vector2 position) {
        //angle between target x,y (which is mouse) and body position x,y
        return new Vector2(target.x, target.y).sub(position).angleRad();
    }

    public static float distanceBetweenTwoPoints(Vector2 target, Vector2 position) {
        return (float) Math.sqrt(((target.x - position.x) * (target.x - position.x)) +
                ((target.y - position.y) * (target.y - position.y)));
    }

}
