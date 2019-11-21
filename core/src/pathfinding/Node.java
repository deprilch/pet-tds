package pathfinding;

import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class Node extends Vector2{

    public int index;

    private Array<Connection<Node>> connections = new Array<Connection<Node>>();

    public int getIndex() {
        return index;
    }

    public Array<Connection<Node>> getConnections() {
        return connections;
    }

    public void createConnection(Node toNode, float cost){
        connections.add(new ConnectionImp(this, toNode, cost));
    }

    public void setCoordinates(float x, float y) {
        //center path in nodes
        this.x = x + 0.5f;
        this.y = y + 0.5f;
    }

    public Vector2 getCoordinates() {
        return this;
    }

}
