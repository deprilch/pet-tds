package systems;

import characters.Evilball;
import characters.base.Creation;
import characters.base.CreationType;
import com.badlogic.gdx.physics.box2d.*;

import java.util.ArrayList;

public class ListenerClass implements ContactListener {

    public World world;
    public ArrayList<Body> toRemove;

    public ListenerClass(World world) {
        this.world = world;
        //array of objects for remove after step
        toRemove = new ArrayList<Body>();
    }

    @Override
    public void beginContact(Contact contact) {

        Body a = contact.getFixtureA().getBody();
        Body b = contact.getFixtureB().getBody();

        if (contact.getFixtureA().getUserData() == CreationType.Brick && contact.getFixtureB().getUserData() == CreationType.Fireball) {
            toRemove.add(b);
        }
        if (contact.getFixtureA().getUserData() == CreationType.EnemyDevil && contact.getFixtureB().getUserData() == CreationType.Fireball) {
            toRemove.add(a);
            toRemove.add(b);
        }
        if (contact.getFixtureA().getUserData() == CreationType.Brick & contact.getFixtureB().getUserData() == CreationType.Evilball) {
            toRemove.add(b);
        }
        if (contact.getFixtureA().getUserData() == CreationType.Hero & contact.getFixtureB().getUserData() == CreationType.Evilball) {
            toRemove.add(a);
            toRemove.add(b);
        }
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
