package steering;

import characters.base.Creation;
import characters.base.DynamicCreation;
import com.badlogic.gdx.ai.steer.Steerable;
import com.badlogic.gdx.ai.steer.SteeringAcceleration;
import com.badlogic.gdx.ai.steer.SteeringBehavior;
import com.badlogic.gdx.ai.steer.behaviors.LookWhereYouAreGoing;
import com.badlogic.gdx.ai.utils.Location;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

public class SteerableEntity implements Steerable<Vector2> {

    private float boundingRadius;
    private float maxLinearSpeed;
    private float maxLinearAcceleration;
    private float maxAngularSpeed;
    private float maxAngularAcceleration;
    private boolean tagged;
    private SteeringBehavior<Vector2> steeringBehavior;
    private SteeringAcceleration<Vector2> acceleration;

    private LookWhereYouAreGoing<Vector2> lookWhereYouAreGoing;
    private SteeringAcceleration<Vector2> lookTo;

    private Body entityBody;

    public SteerableEntity(DynamicCreation creation){
        entityBody = creation.getBody();

        maxLinearAcceleration = creation.maxLinearAcceleration;

        maxAngularSpeed = creation.maxAngularSpeed;
        maxAngularAcceleration = creation.maxAngularAcceleration;
        acceleration = new SteeringAcceleration<Vector2>(new Vector2());

        lookWhereYouAreGoing = new LookWhereYouAreGoing<Vector2>(this);
        lookTo = new SteeringAcceleration<Vector2>(new Vector2());

        tagged = false;
    }

    public void setSteeringBehavior(SteeringBehavior<Vector2> steeringBehavior){
        this.steeringBehavior = steeringBehavior;
    }

    public void applySteering(){
        steeringBehavior.setEnabled(true);
        steeringBehavior.calculateSteering(acceleration);
        entityBody.setLinearVelocity(0.0f, 0.0f); //this needed for const velocity (acceleration does not increase)
        entityBody.applyForceToCenter(acceleration.linear, true);

        lookWhereYouAreGoing.setEnabled(true);
        lookWhereYouAreGoing.calculateSteering(lookTo);
        entityBody.setAngularVelocity(lookTo.angular);
        //System.out.println(lookTo.angular);

    }

    @Override
    public Vector2 getPosition() {
        return entityBody.getPosition();
    }

    @Override
    public Vector2 getLinearVelocity() {
        return entityBody.getLinearVelocity();
    }

    @Override
    public float getAngularVelocity() {
        return entityBody.getAngularVelocity();
    }

    @Override
    public float getBoundingRadius() {
        return boundingRadius;
    }

    @Override
    public boolean isTagged() {
        return tagged;
    }

    @Override
    public void setTagged(boolean tagged) {
        this.tagged = tagged;
    }

    @Override
    public float getZeroLinearSpeedThreshold() {
        return 0;
    }

    @Override
    public void setZeroLinearSpeedThreshold(float value) {

    }

    @Override
    public float getMaxLinearSpeed() {
        return maxLinearSpeed;
    }

    @Override
    public void setMaxLinearSpeed(float maxLinearSpeed) {
        this.maxLinearSpeed = maxLinearSpeed;
    }

    @Override
    public float getMaxLinearAcceleration() {
        return maxLinearAcceleration;
    }

    @Override
    public void setMaxLinearAcceleration(float maxLinearAcceleration) {
        this.maxLinearAcceleration = maxLinearAcceleration;
    }

    @Override
    public float getMaxAngularSpeed() {
        return maxAngularSpeed;
    }

    @Override
    public void setMaxAngularSpeed(float maxAngularSpeed) {
        this.maxAngularSpeed = maxAngularSpeed;
    }

    @Override
    public float getMaxAngularAcceleration() {
        return maxAngularAcceleration;
    }

    @Override
    public void setMaxAngularAcceleration(float maxAngularAcceleration) {
        this.maxAngularAcceleration = maxAngularAcceleration;
    }

    @Override
    public float getOrientation() {
        return entityBody.getAngle();
    }

    @Override
    public void setOrientation(float orientation) {

    }

    @Override
    public float vectorToAngle(Vector2 vector) {
        return (float) Math.atan2(-vector.y, vector.x);
    }

    @Override
    public Vector2 angleToVector(Vector2 outVector, float angle) {
        outVector.x = -(float)Math.sin(angle);
        outVector.y = (float)Math.cos(angle);
        return outVector;
    }

    @Override
    public Location<Vector2> newLocation() {
        return null;
    }
}
