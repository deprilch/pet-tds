package pathfinding;

import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.ai.pfa.indexed.IndexedGraph;
import com.badlogic.gdx.utils.Array;
import systems.LevelManager;

public class GraphImp implements IndexedGraph<Node> {
    public Array<Node> nodes = new Array<Node>();

    public GraphImp(Array<Node> nodes){
        this.nodes = nodes;
    }

    @Override
    public Array<Connection<Node>> getConnections(Node fromNode) {
        return fromNode.getConnections();
    }

    @Override
    public int getNodeCount() {
        return nodes.size;
    }

    @Override
    public int getIndex(Node node) {
        return node.index;
    }

    //Get Node by x and y meters (f)
    public Node getNode(int fX, int fY){
        return nodes.get(LevelManager.getInstance().lvlTileWidth * fY + fX);
    }

}
