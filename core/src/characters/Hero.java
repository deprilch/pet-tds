package characters;

import characters.base.CreationType;
import characters.base.DynamicCreation;
import characters.base.Shootable;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ai.steer.Steerable;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import systems.Aiming;

public class Hero extends DynamicCreation implements Shootable {

    public Hero(World world, Vector2 position) {
        super(world, position, CreationType.Hero);

    }

    @Override
    protected Shape initFixtureDef() {
        CircleShape circle = new CircleShape();
        circle.setRadius(0.5f);

        fixtureDef = new FixtureDef();
        fixtureDef.shape = circle;
        fixtureDef.density = 0.0f;
        fixtureDef.friction = 0.0f;
        fixtureDef.restitution = 0.0f; // Make it bounce a little bit
        return circle;
    }

    @Override
    public void shoot(Vector2 target) {
        Fireball fireball = new Fireball(world, getPosition());
        //calculate angle for shooting between two points (to mouse position)
        float angle = Aiming.angleBetweenTwoPoints(target, getPosition());

        fireball.getBody().applyLinearImpulse(
                10.0f * MathUtils.cos(angle) * fireball.getBody().getMass(),
                10.0f * MathUtils.sin(angle) * fireball.getBody().getMass(),
                fireball.getPosition().x,
                fireball.getPosition().y,
                true);

    }

    //Here we move physic body. Graphics move in GameScreen based on physic body position
    public void movement() {
        stop();
        //Speed in meters per second. Max speed = 120 m/s
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            body.applyLinearImpulse(0.0f, 5f * body.getMass(), getPosition().x, getPosition().y, true);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            body.applyLinearImpulse(0.0f, -5f * body.getMass(), getPosition().x, getPosition().y, true);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            body.applyLinearImpulse(-5f * body.getMass(), 0.0f * body.getMass(), getPosition().x, getPosition().y, true);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            body.applyLinearImpulse(5f * body.getMass(), 0.0f * body.getMass(), getPosition().x, getPosition().y, true);
        }
    }

    public void stop() {
        body.setLinearVelocity(0.0f, 0.0f);
    }

    //hero rotation
    public void rotate(float worldX, float worldY) {
        //calculation of angle between 2 points
        float rotateAngle = Aiming.rotate(new Vector2(worldX, worldY), getPosition());
        //rotation based on that angle
        body.setTransform(getPosition(), rotateAngle);
    }


}
