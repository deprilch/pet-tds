package pathfinding;

import com.badlogic.gdx.ai.pfa.Connection;

public class ConnectionImp implements Connection<Node> {

    Node toNode;
    Node fromNode;
    float cost;

    public ConnectionImp(Node fromNode, Node toNode, float cost) {
        this.toNode = toNode;
        this.fromNode = fromNode;
        this.cost = cost;
    }

    @Override
    public float getCost() {
        //System.out.println(cost);
        return cost;
    }

    @Override
    public Node getFromNode() {
        return fromNode;
    }

    @Override
    public Node getToNode() {
        return toNode;
    }
}
