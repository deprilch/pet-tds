package characters;

import characters.base.CreationType;
import characters.base.DynamicCreation;
import characters.base.Shootable;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import systems.Aiming;

import java.util.Timer;
import java.util.TimerTask;

public class EnemyDevil extends DynamicCreation implements Shootable{

    private int cooldown = 3;
    private boolean isLoaded = true;
    private boolean raycast;

    public EnemyDevil(World world, Vector2 position) {
        super(world, position, CreationType.EnemyDevil);

        maxLinearSpeed = 5.0f;
        maxLinearAcceleration = 200f;
        maxAngularSpeed = 5.0f;

    }

    @Override
    protected Shape initFixtureDef() {
        CircleShape circle = new CircleShape();
        circle.setRadius(0.5f);

        fixtureDef.shape = circle;
        fixtureDef.density = 0.0f;
        fixtureDef.friction = 0.0f;
        fixtureDef.restitution = 0.0f; // Make it bounce a little bit
        fixtureDef.isSensor = true;

        return circle;
    }

    @Override
    public void shoot(Vector2 target) {
        if (!isLoaded)
            return;

        Evilball evilball = new Evilball(world, getPosition());
        //calculate angle for shooting between two points (to mouse position)
        float angle = Aiming.angleBetweenTwoPoints(target, getPosition());

        evilball.getBody().applyLinearImpulse(
                10.0f * MathUtils.cos(angle) * evilball.getBody().getMass(),
                10.0f * MathUtils.sin(angle) * evilball.getBody().getMass(),
                evilball.getPosition().x,
                evilball.getPosition().y,
                true);

        isLoaded = false;
        cooldown();
    }

    private void cooldown() {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                cooldown--;
                if (cooldown <= 0) {
                    this.cancel();
                    isLoaded = true;
                    cooldown = 2;
                }
            }
        }, 0, 1000);
    }

    public boolean raycast(final Vector2 position) {
        world.rayCast(rayCastCallback, getPosition(), position);
        return raycast;
    }

    private RayCastCallback rayCastCallback = new RayCastCallback() {
        @Override
        public float reportRayFixture(Fixture fixture, Vector2 point, Vector2 normal, float fraction) {
            if (fixture.getUserData() == CreationType.Brick)
                raycast = false;
            if (fixture.getUserData() == CreationType.Hero)
                raycast = true;
            return fraction;
        }
    };
}
